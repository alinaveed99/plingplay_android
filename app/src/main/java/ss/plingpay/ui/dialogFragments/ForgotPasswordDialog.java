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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.ui.adapters.SecretQuestionAdapter;
import ss.plingpay.utilz.Utilz;

import static ss.plingpay.utilz.Utilz.getRxRetrofit;
import static ss.plingpay.utilz.Utilz.tmsg;
import static ss.plingpay.utilz.Utilz.tmsgError;

/**
 * Created by Sammie on 2/9/2017.
 */

public class ForgotPasswordDialog extends DialogFragment {


    @BindView(R.id.dialog_forgot_password_emailFIelds)
    LinearLayout emailFields;

    @BindView(R.id.dialog_forgot_password_secretFields)
    LinearLayout secretFields;


    @BindView(R.id.dialog_forgot_password_email)
    EditText etEmail;


    @BindView(R.id.dialog_forgot_password_questions)
    Spinner questionSpinner;

    @BindView(R.id.dialog_forgot_password_answer)
    EditText etAnswer;

    @BindView(R.id.dialog_forgot_password_tvQuestion)
    TextView tvQuestion;

    private static final String TAG = ForgotPasswordDialog.class.getName().toUpperCase();
    private Unbinder unbinder;
    private WebServices api;
    private String user_question;
    private String user_answer;
    private boolean isQuestionSelected;
    private String currenctQuestion;
    private String user_email;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        init();
    }

    private void init() {

        SecretQuestionAdapter adapter = new SecretQuestionAdapter(getActivity(), Utilz.getQuestionList());
        questionSpinner.setAdapter(adapter);

    }


    @OnItemSelected(R.id.dialog_forgot_password_questions)
    void onItemSelected(int pos) {

        List<String> questions = Utilz.getQuestionList();

        if (questions.get(pos).equalsIgnoreCase(Utilz.getQuestionList().get(0))) {

            isQuestionSelected = false;

        } else {

            isQuestionSelected = true;
            currenctQuestion = questions.get(pos);

        }


    }


    @OnClick(R.id.dialog_forgot_password_btnReset)
    void onResetClick() {


        if (etEmail.getText().toString().isEmpty()) {
            etEmail.setError("Required Field");
            return;
        } else {
            etEmail.setError(null);
        }


        checkForQuestion(etEmail.getText().toString().trim());


    }


    @OnClick(R.id.dialog_forgot_password_btnSecretReset)
    void onSecretResetClick() {


        if (etAnswer.getText().toString().isEmpty()) {
            etAnswer.setError("Required Field");
            return;
        } else {
            etAnswer.setError(null);
        }


        if (!etAnswer.getText().toString().trim().equalsIgnoreCase(user_answer)) {

            tmsgError(getActivity(), "Wrong Information");

            return;
        }


        sendResetPasswordEmail(user_email);

    }


    void sendResetPasswordEmail(String email) {

        api = getRxRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");

        api.resetPin(email, "password")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    Log.e(TAG, "sendResetPasswordEmail: ", throwable);
                    Utilz.tmsgError(getActivity(), "Error, Please Try Again");
                    dialog.dismiss();
                })
                .subscribe(data -> {


                    if (data == null || !data.isSuccessful()) {
                        dialog.dismiss();
                        Utilz.tmsgError(getActivity(), "Error, Please Try Again");
                        return;
                    }


                    try {

                        JsonObject obj = data.body().getAsJsonObject();
                        String response = obj.get("response").getAsString();

                        if (response.equalsIgnoreCase("success")) {
                            Utilz.tmsgSuccess(getActivity(), "New Password Has Been Sent To Your Email");

                            getDialog().dismiss();

                        } else {
                            Utilz.tmsgError(getActivity(), "Error, Please Try Again");
                        }

                    } catch (Exception e) {

                        Utilz.tmsgError(getActivity(), "Error, Please Try Again");
                        Log.e(TAG, "sendResetPasswordEmail: ", e);

                    }


                    dialog.dismiss();

                });


    }


    void checkForQuestion(String email) {


        api = getRxRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");

        api.checkForSecretQuestion(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    dialog.dismiss();
                    tmsg(getActivity(), "Error, Please Try Again");
                    Log.e(TAG, "checkForQuestion: ", throwable);
                })
                .subscribe(data -> {


                    if (data == null || !data.isSuccessful()) {
                        dialog.dismiss();
                        tmsg(getActivity(), "Error, Please Try Again");
                        return;
                    }


                    try {

                        JsonObject obj = data.body().getAsJsonObject();
                        String response = obj.get("response").getAsString();

                        if (response.equalsIgnoreCase("success")) {

                            String question = obj.get("question").getAsString();

                            if (question == null || question.equalsIgnoreCase("") ||
                                    question.equalsIgnoreCase("none")
                                    || question.equalsIgnoreCase("null")) {

                                sendResetPasswordEmail(email);

                            } else {

                                secretFields.setVisibility(View.VISIBLE);
                                emailFields.setVisibility(View.GONE);

                                user_question = obj.get("question").getAsString();
                                tvQuestion.setText(user_question);
                                user_answer = obj.get("answer").getAsString();
                                user_email = email;

                            }


                        } else {
                            tmsg(getActivity(), "Error, Please Try Again");
                        }


                    } catch (Exception e) {
                        tmsg(getActivity(), "Error, Please Try Again");
                        Log.e(TAG, "checkForQuestion: ", e);
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
