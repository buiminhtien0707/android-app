package com.app.mission.mood.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

import com.app.mission.mood.R;

import java.util.Objects;

public class LoadingDialog {

    private Context context;

    private Dialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_loading);
    }

    public void showDialog() {
        dialog.setCancelable(false);

        if (!dialog.isShowing())
            dialog.show();
    }

    public void hideDialog() {
        dialog.dismiss();
    }

}