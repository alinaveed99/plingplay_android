package ss.plingpay.ui.activities;

import android.os.Bundle;

import ss.plingpay.R;
import ss.plingpay.ui.fragments.inboxInvoice.invoiceListFragment.InboxInvoicesFragment;
import ss.plingpay.ui.fragments.InboxRecurringInvoicesFragment;

/**
 * Created by samar_000 on 9/1/2016.
 */

public class InboxActivity extends BaseActivity {

    public static String INBOX_TYPE = "type";

    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        type = getIntent().getIntExtra(INBOX_TYPE, 0);
        init();
    }

    private void init() {

        getToolbar().setNavigationIcon(R.drawable.ic_cross);
        getToolbar().setNavigationOnClickListener(view -> finish());


        if (type == 0) {
            openFragment(R.id.activity_inbox_container, new InboxInvoicesFragment(), InboxInvoicesFragment.class.getName(), false);
        } else {
            openFragment(R.id.activity_inbox_container, new InboxRecurringInvoicesFragment(), InboxRecurringInvoicesFragment.class.getName(), false);
        }
    }

}
