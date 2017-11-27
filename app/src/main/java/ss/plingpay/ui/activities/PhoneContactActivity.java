package ss.plingpay.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.R;
import ss.plingpay.pojos.PhoneContactPOJO;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.ui.adapters.PhoneContactAdapter;
import ss.plingpay.ui.dialogFragments.ContactInfoDialog;
import ss.plingpay.ui.dialogFragments.OtherContactInfoDialog;

/**
 * Created by samar_000 on 8/24/2016.
 */

public class PhoneContactActivity extends BaseActivity implements Listeners.FavContactListener {

    private static final String TAG = PhoneContactActivity.class.getName().toUpperCase();
    @BindView(R.id.phone_contact_contactList)
    RecyclerView contactsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_contact_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {

        getToolbar().setNavigationIcon(R.drawable.ic_cross);
        getToolbar().setNavigationOnClickListener(view -> finish());

        contactsList.setLayoutManager(new LinearLayoutManager(this));
        contactsList.setHasFixedSize(true);

        String s = getApp().getAuth().getPhoneContacts();
        saveInDb(s);
        Log.d(TAG, "init: " + s);


    }

    @Override
    public void onItemClick(UserDetails user) {

        Intent i = new Intent();
        i.putExtra(ContactListActivity.USER_DETAILS, user);
        setResult(RESULT_OK, i);
        finish();

    }

    @Override
    public void onRemoveClick(UserDetails user) {

    }

    @Override
    public void OnInfoClick(UserDetails user) {
        DialogFragment dialog = new OtherContactInfoDialog();
        Bundle b = new Bundle();
        b.putParcelable(ContactListActivity.USER_DETAILS, user);
        dialog.setArguments(b);
        dialog.show(getSupportFragmentManager(), ContactInfoDialog.class.getName());
    }

    private void saveInDb(String s) {

        PhoneContactPOJO ud = null;

        if (s == null || s.isEmpty()) {

            return;
        }

        Gson gson = new Gson();
        try {
            TypeAdapter<PhoneContactPOJO> adapter = gson.getAdapter(PhoneContactPOJO.class);
            ud = adapter.fromJson(s);

            String response = ud.getResponse();

            if (response.equalsIgnoreCase("failed")) {

                Log.e(TAG, "User Name: " + ud.getResponse());

            } else {

                String name = ud.getUsers().get(0).getFname();
                Log.d(TAG, "User Name:: " + name);


                PhoneContactAdapter ad = new PhoneContactAdapter(this, ud.getUsers(), this);
                contactsList.setAdapter(ad);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
