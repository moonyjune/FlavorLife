package moony.vn.flavorlife.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.ntq.fragments.NAlertDialogFragment;

import moony.vn.flavorlife.R;
import moony.vn.flavorlife.activities.BaseActivity;
import moony.vn.flavorlife.activities.MainActivity;

/**
 * Created by moony on 5/2/15.
 */
public class DialogUtils {
    private ProgressDialog mProgressDialog;
    private static DialogUtils mDialogUtils;
    private Dialog dialog;

    public static DialogUtils getInstance() {
        if (mDialogUtils == null)
            mDialogUtils = new DialogUtils();
        return mDialogUtils;
    }

    public void showDialogLoading(Context context) {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.waiting));
        mProgressDialog.setCanceledOnTouchOutside(true);
        mProgressDialog.show();
    }

    public void hideDialogLoading() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    public void showDialogMessageError(Context context, String mess) {
        DialogUtils.getInstance().showDialog(context, mess, true, false, "OK", null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.getInstance().dismissDialog();
            }
        }, null);
    }

    public void showDialogMessageError(Context activity, VolleyError error) {
        DialogUtils.getInstance().showDialog(activity, ErrorStrings.get(activity, error), true, false, "OK", null, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.getInstance().dismissDialog();
            }
        }, null);
    }

    public void showDialogMessage(Context context, String mess, View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        showDialog(context, mess, true, true, context.getString(R.string.ok), context.getString(R.string.cancel), onClickListener, onClickListener2);
    }

    public void showDialog(Context context, String messages, boolean isHaveLeft, boolean isHaveRight, String buttonLeft, String buttonRight, View.OnClickListener onListenerLeft, View.OnClickListener onListenerRight) {
        dialog = new Dialog(context);
        TextView messagesTv;
        Button yes, no;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_message);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

        messagesTv = (TextView) dialog.findViewById(R.id.message);
        yes = (Button) dialog.findViewById(R.id.yes);
        no = (Button) dialog.findViewById(R.id.no);

        if (!isHaveLeft)
            yes.setVisibility(View.GONE);
        else
            yes.setVisibility(View.VISIBLE);
        if (!isHaveRight)
            no.setVisibility(View.GONE);
        else
            no.setVisibility(View.VISIBLE);

        messagesTv.setText(messages);
        yes.setText(buttonLeft);
        no.setText(buttonRight);

        yes.setOnClickListener(onListenerLeft);
        if (onListenerRight != null)
            no.setOnClickListener(onListenerRight);
        else
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissDialog();
                }
            });
        dialog.show();
    }

    public void dismissDialog() {
        if (dialog != null)
            dialog.dismiss();
    }
}
