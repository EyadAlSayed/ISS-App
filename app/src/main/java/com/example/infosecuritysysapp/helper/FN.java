package com.example.infosecuritysysapp.helper;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.infosecuritysysapp.R;


public class FN {
    static FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;

    static final String FIXED_NAME = "fixed_fragment_name";

    public static final int MAIN_FC = R.id.main_fcr;

    // replace methods
    public static void replaceSlideFragmentUD(int container, FragmentActivity fragmentActivity, Fragment fragment) {
        fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_up, R.anim.slide_down, R.anim.slide_up, R.anim.slide_down);
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    public static void replaceSlideFragmentDU(int container, FragmentActivity fragmentActivity, Fragment fragment) {
        fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up);
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }


    public static void replaceSlideFragmentLTR(int container, FragmentActivity fragmentActivity, Fragment fragment) {
        fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_left, R.anim.slide_right, R.anim.slide_left, R.anim.slide_right);
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    public static void replaceFadeFragment(int container, FragmentActivity fragmentActivity, Fragment fragment) {
        fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    public static void popStack(FragmentActivity fragmentActivity) {
        fragmentActivity.getSupportFragmentManager().popBackStack();
    }

    // add to stack methods

    public static void addToStackFadeFragment(int container, FragmentActivity fragmentActivity, Fragment fragment, String name) {
        fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

    public static void addToStackSlideLRFragment(int container, FragmentActivity fragmentActivity, Fragment fragment, String name) {
        fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right, R.anim.slide_left, R.anim.slide_right, R.anim.slide_left);
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }


    public static void addFragmentUpFragment(int container, FragmentActivity fragmentActivity, Fragment fragment, String name) {
        fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

    public static void addFixedNameFadeFragment(int container, FragmentActivity fragmentActivity, Fragment fragment) {
        addToStackFadeFragment(container, fragmentActivity, fragment, FIXED_NAME);
    }

    public static void addToStackSlideUDFragment(int container, FragmentActivity fragmentActivity, Fragment fragment, String name) {
        fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_down, R.anim.slide_up, R.anim.slide_down, R.anim.slide_up);
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

    public static void addSlideLRFragmentUpFragment(int container, FragmentActivity fragmentActivity, Fragment fragment, String name) {
        fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_right, R.anim.slide_left, R.anim.slide_right, R.anim.slide_left);
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }

    public static void addFadeFragmentUpFragment(int container, FragmentActivity fragmentActivity, Fragment fragment, String name) {
        fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.attach(fragment);
        fragmentTransaction.addToBackStack(name);
        fragmentTransaction.commit();
    }


    public static void popFragmentFromStack(FragmentActivity fragmentActivity, String fragmentName) {
        fragmentManager = fragmentActivity.getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(fragmentName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void popTopStack(FragmentActivity fragmentActivity) {
        fragmentActivity.getSupportFragmentManager().popBackStack();
    }

    public static void popAllStack(FragmentActivity fragmentActivity) {
        FragmentManager fm = fragmentActivity.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }


    public static Fragment getCurrentFragment(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentManager.findFragmentByTag(fragmentTag);
    }

    public static String getCurrentFragmentName(FragmentActivity fragmentActivity) {
        FragmentManager fragmentManager = fragmentActivity.getSupportFragmentManager();
        String name = "";
        try {
            name = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        } catch (Exception ignore) {
        }

        return name;
    }

}
