package ss.plingpay.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ss.plingpay.R;
import ss.plingpay.ui.activities.mainView.MainActivity;
import ss.plingpay.utilz.Utilz;

/**
 * Created by samar_000 on 8/18/2016.
 */

public class HomeFragment extends NewBaseFragment {


    public static final String METHOD = "method";
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).disableRightDrawer(false);
        ((MainActivity) getActivity()).getSupportedActionbar().setTitle("Your Payment Bridge To The World");
        setTitle("Home");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {


    }

    @OnClick(R.id.fragment_home_btnSend)
    void onSendClick() {

        Bundle b = new Bundle();
        b.putString(METHOD, Utilz.PAYMENT_METHOD[0]);
        openFragment(R.id.activity_main_container, new SendMoneyFragment(), SendMoneyFragment.class.getName(), true, b);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).disableRightDrawer(false);
    }


}
