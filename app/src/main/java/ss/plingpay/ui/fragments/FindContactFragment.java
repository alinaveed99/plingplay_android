package ss.plingpay.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.plingpay.R;
import ss.plingpay.infrastructure.WebServices;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.pojos.Users.Users;
import ss.plingpay.ui.activities.ContactListActivity;
import ss.plingpay.ui.activities.PhoneContactActivity;
import ss.plingpay.ui.adapters.ContactListAdapter;
import ss.plingpay.ui.adapters.FavContactListAdapter;
import ss.plingpay.ui.dialogFragments.OtherContactInfoDialog;
import ss.plingpay.ui.dialogFragments.SendInviteDialog;
import ss.plingpay.utilz.Listeners;
import ss.plingpay.utilz.Utilz;

import static ss.plingpay.ui.activities.ContactListActivity.USER_DETAILS;

/**
 * Created by samar_000 on 8/22/2016.
 */

public class FindContactFragment extends BaseFragment implements Listeners.FavContactListener {


    private static final String TAG = FindContactFragment.class.getName().toUpperCase();
    private Unbinder unbinder;


//    @BindView(R.id.fragment_find_searchField)
//    EditText etSearchField;


    @BindView(R.id.fragment_find_contacts_favoriteList)
    RecyclerView favoriteUsers;

    @BindView(R.id.fragment_find_contacts_userList)
    RecyclerView userList;


    @BindView(R.id.fragment_find_contacts_checkFriend)
    AppCompatCheckBox cbCheckFriends;
    private boolean isFriend = false;


    @BindView(R.id.fragment_find_btnInvite)
    Button btnInvite;
    private SearchView searchView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Find Contact");
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_find_contacts, container, false);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {

        cbCheckFriends.setOnCheckedChangeListener((buttonView, isChecked) -> isFriend = isChecked);
        populateFavoriteList();

    }

    private void populateFavoriteList() {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);

        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Loading");
        api.getFavoriteUsers(getApp().getAuth().getUser().getUserinfo().getId()).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {


                if (response.body().getUserinfo().size() > 0) {


                    favoriteUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
                    favoriteUsers.setHasFixedSize(true);

                    FavContactListAdapter adap =
                            new FavContactListAdapter(getActivity(), response.body().getUserinfo(), new Listeners.FavContactListener() {
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
                            });


                    favoriteUsers.setAdapter(adap);

                } else {
                    Utilz.tmsgError(getActivity(), "No Favorite User");

                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                progressDialog.dismiss();
            }
        });


    }

    //    @OnClick(R.id.fragment_find_btnFind)
    void onFindClick(String s) {

//
//        if (etSearchField.getText().toString().isEmpty()) {
//            etSearchField.setError("Write Something");
//            return;
//        }

        if (isFriend) {
            //Find In Favorite
            findInFavorite(s);
            userList.setVisibility(View.GONE);
        } else {
            //Find In All Users
            findInAllUsers(s);
            populateFavoriteList();
        }


        Utilz.hideKeyboard(getActivity());


    }


    @OnClick(R.id.fragment_find_btnInvite)
    void onInviteClick() {

        DialogFragment dialogFragment = new SendInviteDialog();
        dialogFragment.show(getActivity().getSupportFragmentManager(), "invite_dialog");

    }

    private void findInAllUsers(String s) {
        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");
        api.getUsersList(s).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                Log.d(TAG, "onResponse: " + response.body().getResponse());

                if (response.body().getUserinfo().size() > 0) {


                    userList.setLayoutManager(new LinearLayoutManager(getActivity()));
                    userList.setHasFixedSize(true);
                    userList.setAdapter(new ContactListAdapter(getActivity(), response.body().getUserinfo(), FindContactFragment.this));
                    userList.setVisibility(View.VISIBLE);

                    btnInvite.setVisibility(View.GONE);


                } else {
                    Utilz.tmsgError(getActivity(), "User Not Found");
                    btnInvite.setVisibility(View.VISIBLE);
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                dialog.dismiss();
            }
        });
    }

    private void findInFavorite(String s) {

        api = getRetrofit(Utilz.AppConstants.BASE_URL_1).create(WebServices.class);


        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Please Wait", "Loading...");
        api.getFavoriteUsersList(s, getApp().getAuth().getUser().getUserinfo().getId()).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                Log.d(TAG, "onResponse: " + response.body().getResponse());

                if (response.body().getUserinfo().size() > 0) {


                    favoriteUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
                    favoriteUsers.setHasFixedSize(true);
                    favoriteUsers.setAdapter(new ContactListAdapter(getActivity(), response.body().getUserinfo(), FindContactFragment.this));


                } else {
                    Utilz.tmsgError(getActivity(), "User Not Found");
                }

                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                dialog.dismiss();
            }
        });
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.contact_list_list, menu);


        MenuItem mMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) mMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Utilz.tmsgInfo(getActivity(), query);

                onFindClick(query.trim());

                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }

                mMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.phoneContacts) {
            startActivityForResult(new Intent(getActivity(), PhoneContactActivity.class), 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == 1) {

            UserDetails user = data.getParcelableExtra(ContactListActivity.USER_DETAILS);
            Intent i = new Intent();
            i.putExtra(USER_DETAILS, user);
            getActivity().setResult(getActivity().RESULT_OK, i);
            getActivity().finish();

        }
    }
}
