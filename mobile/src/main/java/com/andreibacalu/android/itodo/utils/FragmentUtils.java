package com.andreibacalu.android.itodo.utils;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

public class FragmentUtils {

    public static Fragment getCurrentFragment(Activity activity) {
        FragmentManager fragmentManager = activity.getFragmentManager();
        int index = fragmentManager.getBackStackEntryCount() - 1;
        if (index >= 0) {
            String tag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
            return fragmentManager.findFragmentByTag(tag);
        }
        return null;
    }
}
