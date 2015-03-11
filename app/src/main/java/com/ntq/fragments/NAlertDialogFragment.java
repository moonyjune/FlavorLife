package com.ntq.fragments;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 *
 * @author TUNGDX
 *
 */

/**
 * Use for display {@link AlertDialog} in {@link DialogFragment}
 */
public class NAlertDialogFragment extends DialogFragment {
    private static final String TAG = "NAlertDialogFragment";
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;

    /**
     * Show {@link AlertDialog} in {@link DialogFragment}
     *
     * @param fragmentManager This is manages dialog fragment that contains alert dialog.
     * @param tag             This value use in
     *                        {@link DialogFragment#show(FragmentTransaction, String)}. If
     *                        value is null then maybe {@link AlertDialog} showed duplicate
     *                        if use click very quickly.
     * @param builder         {@link AlertDialog.Builder} to create alert dialog.
     * @see #show(FragmentManager, Builder)
     */
    public static void show(FragmentManager fragmentManager, String tag,
                            Builder builder) {
        show(fragmentManager, tag, builder, null);
    }

    /**
     * how {@link AlertDialog} in {@link DialogFragment}
     *
     * @param fragmentManager This is manages dialog fragment that contains alert dialog.
     * @param builder         {@link AlertDialog.Builder} to create alert dialog.
     * @see NAlertDialogFragment#show(FragmentManager, String, Builder)
     */
    public static void show(FragmentManager fragmentManager, Builder builder) {
        show(fragmentManager, TAG, builder, null);
    }

    /**
     * @param fragmentManager
     * @param tag
     * @param alertDialog
     */
    public static void show(FragmentManager fragmentManager, String tag,
                            AlertDialog alertDialog) {
        show(fragmentManager, tag, null, alertDialog);
    }

    /**
     * @param fragmentManager
     * @param alertDialog
     */
    public static void show(FragmentManager fragmentManager,
                            AlertDialog alertDialog) {
        show(fragmentManager, TAG, null, alertDialog);
    }

    private static void show(FragmentManager fragmentManager, String tag,
                             Builder buidler, AlertDialog alertDialog) {
        if (buidler == null && alertDialog == null)
            throw new IllegalArgumentException(
                    "Must supplies builder or aleart dialog params.");

        fragmentManager.executePendingTransactions();
        NAlertDialogFragment nDialogFragment = (NAlertDialogFragment) fragmentManager
                .findFragmentByTag(tag);
        // must check to be avoid show duplicate same dialog.
        if (nDialogFragment != null) {
            try {
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction();
                fragmentTransaction.remove(nDialogFragment).commit();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
        NAlertDialogFragment dialogFragment = new NAlertDialogFragment();
        if (buidler != null)
            dialogFragment.setBuilder(buidler);
        if (alertDialog != null)
            dialogFragment.setAlertDialog(alertDialog);
        dialogFragment.show(fragmentManager, tag);
    }

    private void setBuilder(AlertDialog.Builder builder) {
        this.mBuilder = builder;
    }

    private void setAlertDialog(AlertDialog alertDialog) {
        this.mAlertDialog = alertDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (mAlertDialog != null)
            return mAlertDialog;
        return mBuilder.create();
    }
}
