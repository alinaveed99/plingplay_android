package ss.plingpay.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ss.plingpay.R;
import ss.plingpay.ui.fragments.slidders.SlidderFragmentA;
import ss.plingpay.ui.fragments.slidders.SlidderFragmentB;
import ss.plingpay.ui.fragments.slidders.SlidderFragmentC;
import ss.plingpay.ui.fragments.slidders.SlidderFragmentD;

/**
 * Created by samar_000 on 8/15/2016.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }
    }

    @OnClick(R.id.activity_welcome_btnLogin)
    void onLoginClick() {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(LoginActivity.ISLOGIN, true);
        startActivity(i);
        finish();
    }

    @OnClick(R.id.activity_welcome_btnRegister)
    void onRegClick() {
        Intent i = new Intent(this, RegistrationActivity.class);
        startActivity(i);
        finish();
    }



    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new SlidderFragmentA(), "A");
        adapter.addFragment(new SlidderFragmentB(), "B");
        adapter.addFragment(new SlidderFragmentC(), "C");
        adapter.addFragment(new SlidderFragmentD(), "D");

        viewPager.setAdapter(adapter);
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
