package ss.plingpay.ui.activities;

import android.os.Bundle;

import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.ui.fragments.FindContactFragment;

/**
 * Created by samar_000 on 8/22/2016.
 */

public class FindContactActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_contacts);
        ButterKnife.bind(this);
        init();

    }

    private void init() {
        openFragment(R.id.activity_find_contacts_container, new FindContactFragment(), FindContactFragment.class.getName(), false);
    }
}
