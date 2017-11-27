package ss.plingpay.ui.dialogFragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import ss.plingpay.R;

/**
 * Created by samar_000 on 9/30/2016.
 */

public class AgreementDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_agreement, null, false);

        View v = dialogView.findViewById(R.id.dialog_agreement_link);
        v.setOnClickListener(v1 -> {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.plingpay.com/terms/User%20Agreement%201.6%20-%20Beta.pdf"));
            startActivity(browserIntent);
        });


        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Terms And Conditions")
                .setView(dialogView)
                .setPositiveButton("Ok", (dialogInterface, i) -> dialogInterface.dismiss())
                .create();

        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
