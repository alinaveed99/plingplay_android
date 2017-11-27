package ss.plingpay.ui.dialogFragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.gson.JsonObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ss.plingpay.R;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.ui.adapters.SecretQuestionAdapter;
import ss.plingpay.utilz.Utilz;

import static ss.plingpay.utilz.Utilz.getRxRetrofit;

/**
 * Created by Sammie on 2/8/2017.
 */

public class ForgotPinDialog extends DialogFragment {

    private static final String TAG = ForgotPinDialog.class.getName().toUpperCase();
    private Unbinder unbinder;


    @BindView(R.id.dialog_forgot_pin_questions)
    Spinner spinnerQuestions;

    @BindView(R.id.dialog_forgot_pin_answer)
    EditText etAnswer;
    private boolean isQuestionSelected;
    private String mQuestion;

    private PlingPay app;

    protected WebServices api;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((MainActivity) getActivity()).getApp();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_forgot_pin, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        init();
    }

    private void init() {

        SecretQuestionAdapter adapter = new SecretQuestionAdapter(getActivity(), Utilz.getQuestionList());
        spinnerQuestions.setAdapter(adapter);


    }


    protected PlingPay getApp() {
        return app;
    }



    @OnItemSelected(R.id.dialog_forgot_pin_questions)
    void onQuestionSelected(int position) {

        List<String> questions = Utilz.getQuestionList();

        if (questions.get(position).equalsIgnoreCase(Utilz.getQuestionList().get(0))) {

            isQuestionSelected = false;

        } else {

            isQuestionSelected = true;
            mQuestion = questions.get(position);

        }


    }


    @OnClick(R.id.dialog_forgot_pin_btnOk)
    void onOkCLick() {

        if (!isQuestionSelected) {
            Utilz.tmsg(getActivity(), "Select Question");
            return;
        }

        if (etAnswer.getText().toString().isEmpty()){
            etAnswer.setError("Required Field");
            return;
        } else {
            etAnswer.setError(null);
        }


        if(!getApp().getAuth().getUser().getUserinfo().getSecret_question().equalsIgnoreCase(mQuestion) ||
                !getApp().getAuth().getUser().getUserinfo().getSecret_answer().equalsIgnoreCase(etAnswer.getText().toString().trim())) {
            Utilz.tmsg(getActivity(), "Wrong Information");
            return;
        }


        resetPin();


    }

    private void resetPin() {

        api = getRxRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");

        api.resetPin(getApp().getAuth().getUser().getUserinfo().getEmail(), "pin")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Log.e(TAG, "sendNewPin: ", throwable);
                    dialog.dismiss();
                })
                .subscribe(data -> {


                    if (data == null || !data.isSuccessful()) {
                        dialog.dismiss();
                        Utilz.tmsg(getActivity(), "Error, Please Try Again");
                        return;
                    }


                    try {

                        JsonObject obj = data.body().getAsJsonObject();
                        String response = obj.get("response").getAsString();

                        if (response.equalsIgnoreCase("success")) {
                            Utilz.tmsg(getActivity(), "New Pin Has Been Sent To Your Email");

                            String pin = obj.get("pincode").getAsString();

                            getApp().getAuth().getUser().getUserinfo().setPincode(pin);

                            getDialog().dismiss();

                        } else {
                            Utilz.tmsg(getActivity(), "Error, Please Try Again");
                        }

                    } catch (Exception e) {

                        Utilz.tmsg(getActivity(), "Error, Please Try Again");
                        Log.e(TAG, "sendNewPin: ", e);

                    }


                    dialog.dismiss();

                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
