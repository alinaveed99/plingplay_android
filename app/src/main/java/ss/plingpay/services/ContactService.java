package ss.plingpay.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonElement;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.data.TaskRepository;
import ss.plingpay.infrastructure.PlingPay;

/**
 * Created by Sammie on 7/21/2017.
 */

public class ContactService extends Service {

    private final String TAG = ContactService.class.getName().toUpperCase();

    private PlingPay application;

    @Inject
    TaskRepository taskRepository;

    @Override
    public void onCreate() {
        application = (PlingPay) getApplication();
        application.getAppComponents().Inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getUserContacts();
        return START_STICKY;
    }

    private void getUserContacts() {
        taskRepository.getPhoneContacts(getContentResolver())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(phoneUsers -> {

                    if (!phoneUsers.isEmpty()) {

                        StringBuilder users = new StringBuilder();

                        for (int i = 0; i < phoneUsers.size(); i++) {

                            users.append("" + phoneUsers.get(i).getEmail());
                            users.append(",");
                            users.append("" + phoneUsers.get(i).getPhone());

                            if (i == phoneUsers.size() - 1) {
                                users.append("");
                            } else {
                                users.append(";");
                            }

                        }

                        String b = users.toString().replace("-", "");
                        String c = b.replace(" ", "");

                        return c;

                    } else {
                        return null;
                    }


                })
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {


                        if (s != null) {
                            Log.d(TAG, "onSuccess: " + s);
                            sendContactToServer(s);
                        } else {
                            Log.e(TAG, "onSuccess: No Contact");
                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        Log.e(TAG, "onError", throwable);

                    }
                });
    }

    public void sendContactToServer(String s) {
        taskRepository.sendPhoneContact(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<JsonElement>() {
                    @Override
                    public void onSuccess(JsonElement jsonElement) {


                        if (jsonElement != null) {

                            String jsonString = jsonElement.toString();

                            if (jsonString != null) {
                                Log.d(TAG, "onSuccess: " + jsonString);
                                application.getAuth().setPhoneContacts(jsonString);
                            } else {
                                Log.e(TAG, "onSuccess: No Contact Return");
                            }


                        } else {
                            Log.e(TAG, "onSuccess: No Contact Return");
                        }


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        Log.e(TAG, "onError", throwable);
                    }
                });
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
