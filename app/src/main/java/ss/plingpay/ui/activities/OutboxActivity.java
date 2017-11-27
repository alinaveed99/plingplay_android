package ss.plingpay.ui.activities;

import android.os.Bundle;
import android.view.View;

import ss.plingpay.R;
import ss.plingpay.ui.fragments.InboxRecurringInvoicesFragment;
import ss.plingpay.ui.fragments.OutboxInvoicesFragment;

/**
 * Created by samar_000 on 9/12/2016.
 */

public class OutboxActivity extends BaseActivity {

    public static String INBOX_TYPE = "type";

    private int type;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbox);
        type = getIntent().getIntExtra(INBOX_TYPE, 0);
        init();
    }

    private void init() {

        getToolbar().setNavigationIcon(R.drawable.ic_cross);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        if (type == 0) {
            openFragment(R.id.activity_outbox_container, new OutboxInvoicesFragment(), OutboxInvoicesFragment.class.getName(), false);
        } else {
            openFragment(R.id.activity_outbox_container, new InboxRecurringInvoicesFragment(), InboxRecurringInvoicesFragment.class.getName(), false);
        }
    }
}