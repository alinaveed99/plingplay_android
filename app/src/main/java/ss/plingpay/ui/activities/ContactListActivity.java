package ss.plingpay.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.ui.adapters.AdapterContactList;
import ss.plingpay.ui.fragments.FindContactFragment;

/**
 * Created by samar_000 on 8/18/2016.
 */

public class ContactListActivity extends BaseActivity {

    private static final String TAG = ContactListActivity.class.getName().toUpperCase();
    public static final String USER_DETAILS = "user_detail";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        ButterKnife.bind(this);

        getToolbar().setNavigationIcon(R.drawable.ic_cross);
        getToolbar().setNavigationOnClickListener(view -> finish());
        init();
    }

    private void init() {


        openFragment(R.id.activity_contact_list_container, new FindContactFragment(), FindContactFragment.class.getName(), false);


    }


}
