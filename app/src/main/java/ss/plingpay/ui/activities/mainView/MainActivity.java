package ss.plingpay.ui.activities.mainView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.plingpay.R;
import ss.plingpay.data.TaskRepository;
import ss.plingpay.infrastructure.PlingPay;
import ss.plingpay.ui.activities.AboutUsActivity;
import ss.plingpay.ui.activities.BaseMvpActivity;
import ss.plingpay.ui.activities.ContactListActivity;
import ss.plingpay.ui.activities.FaqActivity;
import ss.plingpay.ui.activities.InboxActivity;
import ss.plingpay.ui.activities.LoginActivity;
import ss.plingpay.ui.activities.OutboxActivity;
import ss.plingpay.ui.activities.SettingActivity;
import ss.plingpay.ui.activities.SettingView.AccountSettingActivity;
import ss.plingpay.ui.activities.TransactionHistoryActivity;
import ss.plingpay.ui.fragments.HomeFragment;
import ss.plingpay.ui.fragments.SendMoneyFragment;
import ss.plingpay.utilz.Utilz;

/*** Created by samar_000 on 8/16/2016.*/


public class MainActivity extends BaseMvpActivity<MainView, MainPresenter> implements MainView {

    private static final String TAG = MainActivity.class.getName().toUpperCase();
    private static final String METHOD = "method";
    @BindView(R.id.nav_left_view)
    NavigationView navigationView;

    protected Drawer drawer;
    protected Drawer Rdrawer;


    private boolean disableDrawer = false;
    private TextView balance;

    private String method;

    private boolean backPress = true;
    private TextView currency1;
    private TextView name1;

    @Inject
    TaskRepository taskRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getToolbar().setNavigationIcon(R.drawable.ic_left_drawer_button);
        init();
        addDrawers();
    }

    @Override
    public MainPresenter gifPresenter() {

        ((PlingPay) getApplication()).getAppComponents().Inject(this);

        return new MainPresenter(taskRepository);
    }

    private void addDrawers() {

        //Left Drawer Item
        final PrimaryDrawerItem home = new PrimaryDrawerItem().withName("Home").withSelectable(false).withSelectedTextColor(ContextCompat.getColor(this, R.color.md_black_1000));
        final PrimaryDrawerItem contacts = new PrimaryDrawerItem().withName("Contacts").withSelectable(false);
        final PrimaryDrawerItem transaction = new PrimaryDrawerItem().withName("Transaction").withSelectable(false);
        final PrimaryDrawerItem faQs = new PrimaryDrawerItem().withName("FAQs").withSelectable(false);
        final PrimaryDrawerItem aboutUs = new PrimaryDrawerItem().withName("About Us").withSelectable(false);
        final PrimaryDrawerItem accountSettings = new PrimaryDrawerItem().withName("Account Settings").withSelectable(false);
        final PrimaryDrawerItem logout = new PrimaryDrawerItem().withName("Log out").withSelectable(false);

        final SecondaryDrawerItem inboxInvoices =
                new SecondaryDrawerItem().withName("Requests").withSelectable(false);
        final SecondaryDrawerItem inboxRec =
                new SecondaryDrawerItem().withName("Recurring Requests").withSelectable(false).withTextColor(ContextCompat.getColor(this, R.color.md_blue_grey_300));

        final SecondaryDrawerItem outboxInvoices =
                new SecondaryDrawerItem().withName("Requests").withSelectable(false);
        final SecondaryDrawerItem outboxRec =
                new SecondaryDrawerItem().withName("Recurring Requests").withSelectable(false).withTextColor(ContextCompat.getColor(this, R.color.md_blue_grey_300));


        //Right Drawer Item
        final PrimaryDrawerItem payment =
                new PrimaryDrawerItem().withName("Payment").withSelectedTextColor(ContextCompat.getColor(this, R.color.md_black_1000));
        final PrimaryDrawerItem invoice =
                new PrimaryDrawerItem().withName("Request").withSelectedTextColor(ContextCompat.getColor(this, R.color.md_black_1000));
        final PrimaryDrawerItem rec =
                new PrimaryDrawerItem().withName("Rec..").withTextColor(ContextCompat.getColor(this, R.color.md_blue_grey_300));
        final PrimaryDrawerItem rec2 =
                new PrimaryDrawerItem().withName("Rec..").withTextColor(ContextCompat.getColor(this, R.color.md_blue_grey_300));
        final PrimaryDrawerItem buyBtc =
                new PrimaryDrawerItem().withName("BuyBtc").withTextColor(ContextCompat.getColor(this, R.color.md_blue_grey_300));


        drawer = new DrawerBuilder()
                .withActivity(this)
                .withDrawerGravity(GravityCompat.START)
                .withHeader(R.layout.nav_header_main_left)
                .withSliderBackgroundDrawableRes(R.drawable.drawerbg)
                .withDrawerWidthDp(260)
                .addDrawerItems(
                        home, contacts, transaction, faQs, aboutUs,
                        new ExpandableDrawerItem().withName("Inbox").withSelectable(false).withSubItems(
                                inboxInvoices, inboxRec
                        ),
                        new ExpandableDrawerItem().withName("Outbox").withSelectable(false).withSubItems(
                                outboxInvoices, outboxRec
                        ), accountSettings, logout
                ).withOnDrawerItemClickListener((view, position, drawerItem) -> {


                    if (drawerItem.equals(home)) {
                        startActivity(getIntent());
                        finish();
                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(contacts)) {
                        startActivity(new Intent(MainActivity.this, ContactListActivity.class));

                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(transaction)) {
                        startActivity(new Intent(MainActivity.this, TransactionHistoryActivity.class));

                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(faQs)) {
                        startActivity(new Intent(MainActivity.this, FaqActivity.class));

                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(aboutUs)) {
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));

                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(inboxInvoices)) {
                        Intent i = new Intent(MainActivity.this, InboxActivity.class);
                        i.putExtra(InboxActivity.INBOX_TYPE, 0);
                        startActivity(i);


                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(inboxRec)) {

                        Utilz.showAlert(MainActivity.this, "Note", getResources().getString(R.string.Message));

                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(outboxInvoices)) {
                        Intent i = new Intent(MainActivity.this, OutboxActivity.class);
                        i.putExtra(InboxActivity.INBOX_TYPE, 0);
                        startActivity(i);


                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(outboxRec)) {
                        Utilz.showAlert(MainActivity.this, "Note", getResources().getString(R.string.Message));


                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(accountSettings)) {
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
//                        startActivity(new Intent(MainActivity.this, AccountSettingActivity.class));

                        drawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(logout)) {


                        getApp().getAuth().getUser().setUserinfo(null);
                        getApp().getAuth().getUser().setBitcoindetails(null);
                        getApp().getAuth().getUser().setRecurringpayments(null);
                        getApp().getAuth().getUser().setRecurringreports(null);

                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
//                            i.putExtra(ISLOGIN, true);
                        startActivity(i);
                        finish();


                        drawer.closeDrawer();
                        return true;
                    }

                    return false;
                }).build();


        Rdrawer = new DrawerBuilder()
                .withActivity(this)
                .withHeader(R.layout.nav_header_main)
                .withDrawerWidthDp(200)
                .withSliderBackgroundDrawableRes(R.drawable.drawerbg)
                .addDrawerItems(
                        payment, invoice, rec, rec2, buyBtc
                )
                .withDrawerGravity(GravityCompat.END)
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {

                    if (drawerItem.equals(payment)) {

                        method = Utilz.PAYMENT_METHOD[0];
                        Bundle b = new Bundle();
                        b.putString(METHOD, method);
                        openFragment(R.id.activity_main_container, new SendMoneyFragment(), SendMoneyFragment.class.getName(), true, b);

                        Rdrawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(invoice)) {

                        method = Utilz.PAYMENT_METHOD[1];
                        Bundle b = new Bundle();
                        b.putString(METHOD, method);
                        openFragment(R.id.activity_main_container, new SendMoneyFragment(), SendMoneyFragment.class.getName(), true, b);

                        Rdrawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(rec)) {

                        Utilz.showAlert(MainActivity.this, "Note", getResources().getString(R.string.Message));
                        Rdrawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(rec2)) {

                        Utilz.showAlert(MainActivity.this, "Note", getResources().getString(R.string.Message));

                        Rdrawer.closeDrawer();
                        return true;
                    } else if (drawerItem.equals(buyBtc)) {

                        Utilz.showAlert(MainActivity.this, "Note", getResources().getString(R.string.Message));


                        Rdrawer.closeDrawer();
                        return true;
                    }

                    return false;
                })
                .append(drawer);


        View v = drawer.getHeader();


        name1 = (TextView) v.getRootView().findViewById(R.id.naheader_name);
        balance = (TextView) v.getRootView().findViewById(R.id.nav_header_currentBalance);
        currency1 = (TextView) v.getRootView().findViewById(R.id.nav_header_currency);


        name1.setText(getApp().getAuth().getUser().getUserinfo().getFirstName());
        currency1.setText(getApp().getAuth().getUser().getUserinfo().getCurrancyId());

        getApp().getAuth().getUser().getUserinfo().getCurrencyIdWithListener(text -> currency1.setText(text));

        getApp().getAuth().getUser().getUserinfo().getFirstNameWithListener(text -> name1.setText(text));


        getToolbar().setNavigationIcon(R.drawable.ic_left_drawer_button);
        getToolbar().setNavigationOnClickListener(v1 -> drawer.openDrawer());

    }


    private void init() {

        //TODO add tvCurrency converter

        updateBalance();
        openFragment(R.id.activity_main_container, new HomeFragment(), HomeFragment.class.getName(), false);


    }


    private void updateBalance() {


        String str_curr = getApp().getAuth().getUser().getUserinfo().getCurrancyId().toUpperCase();
        presenter.convertCurrency(str_curr, "BTC",
                Utilz.satoshiToBtc(Double.parseDouble(getApp().getAuth().getWallet().getBalance())));


    }


    @Override
    public void onFailedConvertingCurrency() {
        balance.setText("00.00");

    }

    @Override
    public void onSuccessfullyCurrencyConverted(String convertedAmount) {

        double am = Double.parseDouble(convertedAmount);

        String am1 = new DecimalFormat("##.##").format(am);

        balance.setText(am1);

    }

    @Override
    public void dismissDialog() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        updateBalance();
    }

    public void disableRightDrawer(boolean b) {

        DrawerLayout drawerLayout = Rdrawer.getDrawerLayout();

        if (b) {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, GravityCompat.END);
            disableDrawer = true;
        } else {
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, GravityCompat.END);
            disableDrawer = false;
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (disableDrawer) {
            menu.getItem(0).setVisible(false);
        } else {
            menu.getItem(0).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_openRight) {
            Rdrawer.openDrawer();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        } else if (Rdrawer.isDrawerOpen()) {
            Rdrawer.closeDrawer();
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count > 0)
                getSupportFragmentManager().popBackStack();
            else {

                if (backPress) {
                    backPress = false;
                    Utilz.tmsgInfo(this, "Press back button again to leave the App");
                } else {
                    super.onBackPressed();
                }

            }


        }
    }


}
