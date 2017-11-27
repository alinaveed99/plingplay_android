package ss.plingpay.ui.adapters;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ss.plingpay.R;
import ss.plingpay.pojos.Users.UserDetails;
import ss.plingpay.ui.dialogFragments.ContactInfoDialog;


public class AdapterContactList extends ArrayAdapter<UserDetails> implements Filterable {
    public static final String CONTACT_NAME = "contact_name";
    ArrayList<UserDetails> names;
    Context context;
    AppCompatActivity activity;
    public static final String USER_DETAILS = "user_detail";


    private Filter searchFilter;
    private ArrayList<UserDetails> origNamesList;

    public AdapterContactList(AppCompatActivity context, ArrayList<UserDetails> names) {
        super(context, R.layout.fav_contact_list, names);

        this.names = names;
        this.context = context;
        activity = context;

        this.origNamesList = names;

    }


    public int getCount() {
        return names.size();
    }

    public UserDetails getItem(int position) {
        return names.get(position);
    }

    public long getItemId(int position) {
        return names.get(position).hashCode();
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View single_row = inflater.inflate(R.layout.fav_contact_list, null, true);
        TextView textView = (TextView) single_row.findViewById(R.id.fav_contact_list_tvName);

        textView.setText(names.get(position).getFname() + " " + names.get(position).getLname());


        single_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(USER_DETAILS, names.get(position));
                activity.setResult(activity.RESULT_OK, i);
                activity.finish();
            }
        });


        View moreinfo = single_row.findViewById(R.id.fav_contact_list_moreInfo);
        moreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new ContactInfoDialog();
                Bundle b = new Bundle();
                b.putParcelable(USER_DETAILS, names.get(position));
                dialog.setArguments(b);
                dialog.show(activity.getSupportFragmentManager(), ContactInfoDialog.class.getName());
            }
        });


        return single_row;
    }

    @Override
    public Filter getFilter() {
        if (searchFilter == null)
            searchFilter = new SearchFilter();

        return searchFilter;
    }


    public void resetData() {
        names = origNamesList;
    }


    class SearchFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origNamesList;
                results.count = origNamesList.size();
            } else {
                // We perform filtering operation
                List<UserDetails> nPlanetList = new ArrayList<UserDetails>();

                for (UserDetails p : names) {
                    if (p.getFname().toString().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nPlanetList.add(p);
                }

                results.values = nPlanetList;
                results.count = nPlanetList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                names = (ArrayList<UserDetails>) results.values;
                notifyDataSetChanged();
            }

        }
    }


}
