package ss.plingpay.ui.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import io.realm.Realm;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.CountriesList;
import ss.plingpay.pojos.CurrencyPOJO.CurrenciesPOJO;
import ss.plingpay.pojos.CurrencyPOJO.CurrencyList;
import ss.plingpay.pojos.CurrencyPOJO.ExchangeList;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/15/2016.
 */

@Deprecated
public class SplashScreenActivity extends BaseActivity {

    private static final String TAG = SplashScreenActivity.class.getName().toUpperCase();
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private Realm realm;

    private ArrayList<UserDetails> phoneUsers;
    private String _PhoneUsers;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        initComponents();
    }

    private void initComponents() {

        phoneUsers = new ArrayList<>();

        realm = Realm.getDefaultInstance();


        saveCurrency();

    }

    private void saveCountries() {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        api.getCountriesList("bit4m").enqueue(new Callback<List<CountriesList>>() {
            @Override
            public void onResponse(Call<List<CountriesList>> call, Response<List<CountriesList>> response) {

//                Log.d(TAG, "onResponse: " + response.body().getCurrencyList().get(1).getCurrancyName());

                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().get(1).getCountryName());

                    realm.beginTransaction();
                    realm.delete(CountriesList.class);
                    realm.commitTransaction();

                    for (int i = 0; i < response.body().size(); i++) {
                        realm.beginTransaction();
                        CountriesList curr = realm.createObject(CountriesList.class);
                        curr.setCountryName(response.body().get(i).getCountryName());
                        curr.setId(response.body().get(i).getId());
                        realm.commitTransaction();
                    }

                    ReadPhoneContacts();

//                    getPhoneContacts();
//
//                    new SendPhoneUsers().execute(_PhoneUsers);


                }


            }

            @Override
            public void onFailure(Call<List<CountriesList>> call, Throwable t) {
                Log.e(TAG, "onResponse: " + t.toString());
                Utilz.tmsg(SplashScreenActivity.this, "Something Went Wrong, Please Check Your Internet Connection");
                finish();
            }
        });

    }

    private void saveCurrency() {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        api.getCurrencies("bit4m").enqueue(new Callback<CurrenciesPOJO>() {
            @Override
            public void onResponse(Call<CurrenciesPOJO> call, Response<CurrenciesPOJO> response) {

                Log.d(TAG, "onResponse: " + response.body().getCurrencyList().get(1).getCurrancyName());

                realm.beginTransaction();
                realm.delete(CurrencyList.class);
                realm.commitTransaction();

                for (int i = 0; i < response.body().getCurrencyList().size(); i++) {
                    realm.beginTransaction();
                    CurrencyList curr = realm.createObject(CurrencyList.class);
                    curr.setCurrancyName(response.body().getCurrencyList().get(i).getCurrancyName());
                    curr.setId(response.body().getCurrencyList().get(i).getId());
                    realm.commitTransaction();
                }

                realm.beginTransaction();
                realm.delete(ExchangeList.class);
                realm.commitTransaction();

                for (int i = 0; i < response.body().getExchangeList().size(); i++) {
                    realm.beginTransaction();
                    ExchangeList curr = realm.createObject(ExchangeList.class);
                    curr.setId(response.body().getExchangeList().get(i).getId());
                    curr.setCurrancyId(response.body().getExchangeList().get(i).getCurrancyId());
                    curr.setTitle(response.body().getExchangeList().get(i).getTitle());
                    curr.setUrl(response.body().getExchangeList().get(i).getUrl());
                    realm.commitTransaction();
                }


                saveCountries();


            }

            @Override
            public void onFailure(Call<CurrenciesPOJO> call, Throwable t) {
                Log.e(TAG, "onResponse: " + t.toString());
                Utilz.tmsg(SplashScreenActivity.this, "Something Went Wrong, Please Check Your Internet Connection");
                finish();

            }
        });


    }

    private void ReadPhoneContacts() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            contactsPermission();

        } else {

            getPhoneContacts();
//            new SendPhoneUsers().execute(_PhoneUsers);

        }

    }

    private void getPhoneContacts() {
//
//        UserDetails mPhoneUser;
//
//
//        ContentResolver cr = getContentResolver();
//        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
//                null, null, null, null);
//
//        if (cur.getCount() > 0) {
//            while (cur.moveToNext()) {
//                mPhoneUser = new UserDetails();
//                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
//                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
//                    System.out.println("name : " + name + ", ID : " + id);
//
//                    // get the phone number
//                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (pCur.moveToNext()) {
//                        String phone = pCur.getString(
//                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        System.out.println("phone " + phone);
//
//                        mPhoneUser.setPhone(phone);
//                    }
//                    pCur.close();
//
//
//                    // get email and type
//
//                    Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
//                            null,
//                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
//                            new String[]{id}, null);
//                    while (emailCur.moveToNext()) {
//                        // This would allow you get several email addresses
//                        // if the email addresses were stored in an array
//                        String email = emailCur.getString(
//                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
//                        String emailType = emailCur.getString(
//                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
//
//                        System.out.println("Email " + email + " Email Type : " + emailType);
//                        mPhoneUser.setEmail(email);
//                    }
//                    emailCur.close();
//
//                    phoneUsers.add(mPhoneUser);
//
//                }
//            }
//        }
//
//        phoneContactsJson();


        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {

                UserDetails mPhoneUser;


                ContentResolver cr = getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null, null);

                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {
                        mPhoneUser = new UserDetails();
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                            System.out.println("name : " + name + ", ID : " + id);

                            // get the phone number
                            Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (pCur.moveToNext()) {
                                String phone = pCur.getString(
                                        pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                System.out.println("phone " + phone);

                                mPhoneUser.setPhone(phone);
                            }
                            pCur.close();


                            // get email and type

                            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                    new String[]{id}, null);
                            while (emailCur.moveToNext()) {
                                // This would allow you get several email addresses
                                // if the email addresses were stored in an array
                                String email = emailCur.getString(
                                        emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                                String emailType = emailCur.getString(
                                        emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                                System.out.println("Email " + email + " Email Type : " + emailType);
                                mPhoneUser.setEmail(email);
                            }
                            emailCur.close();

                            phoneUsers.add(mPhoneUser);

                        }
                    }
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                phoneContactsJson();
                new SendPhoneUsers().execute(_PhoneUsers);


            }
        }.execute();


    }


    private void contactsPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
                finish();

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {

            getPhoneContacts();
//            new SendPhoneUsers().execute(_PhoneUsers);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    getPhoneContacts();
//                    new SendPhoneUsers().execute(_PhoneUsers);


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void phoneContactsJson() {


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
        _PhoneUsers = c.toString();


        Log.e("Users : ", _PhoneUsers);


    }

    private class SendPhoneUsers extends AsyncTask<String, Void, String> {

        OkHttpClient client;
        String URL = "http://bit4m.org/admin/favouriteUsers/phonecontacts";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            client = new OkHttpClient();
        }


        @Override
        protected String doInBackground(String... strings) {

            String response = "";


            try {
                response = post(strings[0], URL);

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }


            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null) {

//                saveInDb(s);

                getApp().getAuth().setPhoneContacts(s);

                Log.d(TAG, "onPostExecute: " + s);


            }

            startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
            finish();
        }


        protected String post(String allPhoneContacts, String url) throws JSONException, IOException {

            RequestBody body = new FormBody.Builder()
                    .add("phoneusers", allPhoneContacts)
                    .build();


            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Content-Type", "Content-Type: application/x-www-form-urlencoded")
                    .post(body)
                    .build();
            okhttp3.Response response = client.newCall(request).execute();

            return response.body().string();
        }

    }


}
