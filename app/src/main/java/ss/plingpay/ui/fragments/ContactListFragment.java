package ss.plingpay.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.R;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.ui.adapters.ContactListAdapter;
import ss.plingpay.ui.dialogFragments.OtherContactInfoDialog;

import static ss.plingpay.ui.activities.ContactListActivity.USER_DETAILS;

/**
 * Created by samar_000 on 8/22/2016.
 */

public class ContactListFragment extends BaseFragment implements Listeners.FavContactListener {

    public static final String USER_ARRAY = "USER_ARRAY";
    private static final String TAG = ContactListFragment.class.getName().toUpperCase();
    @BindView(R.id.fragment_find_contact_contactList)
    RecyclerView contactsList;
    private Unbinder unbinder;
    private ArrayList<UserDetails> users;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        setNavFragment(false);
        super.onCreate(savedInstanceState);
        setTitle("Favorite Contact");
        users = getArguments().getParcelableArrayList(USER_ARRAY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_list_fragment, container, false);
        unbinder = ButterKnife.bind(this, v);

        init();

        return v;
    }

    private void init() {
        contactsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        contactsList.setHasFixedSize(true);
        contactsList.setAdapter(new ContactListAdapter(getActivity(), users, this));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClick(UserDetails user) {
        Intent i = new Intent();
        i.putExtra(USER_DETAILS, user);
        getActivity().setResult(getActivity().RESULT_OK, i);
        getActivity().finish();
    }

    @Override
    public void onRemoveClick(UserDetails user) {

    }

    @Override
    public void OnInfoClick(UserDetails user) {
        DialogFragment dialog = new OtherContactInfoDialog();
        Bundle b = new Bundle();
        b.putParcelable(USER_DETAILS, user);
        dialog.setArguments(b);
        dialog.show(getActivity().getSupportFragmentManager(), OtherContactInfoDialog.class.getName());
    }


}