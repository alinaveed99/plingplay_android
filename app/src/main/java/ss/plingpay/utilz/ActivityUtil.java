package ss.plingpay.utilz;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Sammie on 5/31/2017.
 */

public class ActivityUtil {

    public static void openFragment(FragmentManager manager, @IdRes int ResId, Fragment fragment, String tag, boolean addToBackStack) {

        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(ResId, fragment, tag);

        if (addToBackStack)
            ft.addToBackStack(null);

        ft.commit();
    }


    public static void openFragment(FragmentManager manager, @IdRes int ResId, Fragment fragment, String tag, boolean addToBackStack, Bundle b) {
        fragment.setArguments(b);
        openFragment(manager ,ResId, fragment, tag, addToBackStack);
    }

}
