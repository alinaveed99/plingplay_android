package ss.plingpay.ui.dialogFragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.JsonElement;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import ss.plingpay.R;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.ui.activities.BaseActivity;
import ss.plingpay.ui.activities.ContactListActivity;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/22/2016.
 */

public class OtherContactInfoDialog extends DialogFragment {


    private static final String TAG = ContactInfoDialog.class.getName().toUpperCase();
    private Unbinder unbinder;
    private UserDetails user;

    @BindView(R.id.fragment_contact_info_name)
    TextView name;
    @BindView(R.id.fragment_contact_info_email)
    TextView email;
    @BindView(R.id.fragment_contact_info_phone)
    TextView phone;
    @BindView(R.id.fragment_contact_info_favorite)
    CheckBox favorite;

    protected WebServices api;

    private PlingPay app;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = getArguments().getParcelable(ContactListActivity.USER_DETAILS);
        app = ((BaseActivity)getActivity()).getApp();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contact_info, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        name.setText(user.getFname() + " " + user.getLname());
        email.setText(user.getEmail());
        phone.setText(user.getPhone());

        try {
            if (user.getIsfavourite().equalsIgnoreCase("true")) {
                favorite.setChecked(true);
            } else {
                favorite.setChecked(false);
            }
        } catch (Exception e) {
            Log.d(TAG, "init: " + e.toString());
            favorite.setVisibility(View.GONE);
        }


        favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    addToFavorite();
                } else if (b == false) {
                    removeFromFavorite();
                }
            }
        });


    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private void addToFavorite() {
        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Adding to favorite");

        Log.d(TAG, "User id: " + getApp().getAuth().getUser().getUserinfo().getId());
        Log.d(TAG, "fav id: " + user.getId());

        api.addToFavorite(getApp().getAuth().getUser().getUserinfo().getId(),
                user.getId()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                startActivity(getActivity().getIntent());
                getActivity().finish();

                Log.d(TAG, "onFailure: " + response.body().toString());

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                dialog.dismiss();
            }
        });
    }

    private void removeFromFavorite() {
        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Removing from favorite");

        Log.d(TAG, "User id: " + getApp().getAuth().getUser().getUserinfo().getId());
        Log.d(TAG, "fav id: " + user.getFavouriteuserid());

        api.removeFromFavorite(getApp().getAuth().getUser().getUserinfo().getId(),
                user.getFavouriteuserid()).enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                startActivity(getActivity().getIntent());
                getActivity().finish();

                Log.d(TAG, "onFailure: " + response.body().toString());

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.toString());
                dialog.dismiss();
            }
        });
    }

    @OnClick(R.id.icon)
    void onCross() {
        this.getDialog().dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    Retrofit getRetrofit(String url) {
        return Utilz.getRetrofit(url);
    }

    protected PlingPay getApp() {
        return app;
    }


}
