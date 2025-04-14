package com.app.mission.mood.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import com.app.mission.mood.R;

import java.util.Objects;

public class DeleteDialog {

    private Context context;

    private Dialog dialog;

    public DeleteDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_delete);
    }

    public void showDialog(Runnable runnable) {
        dialog.setCancelable(false);

        ((TextView) dialog.findViewById(R.id.txtYes)).setOnClickListener(v -> {
            runnable.run();
            dialog.dismiss();
        });
        ((TextView) dialog.findViewById(R.id.txtNo)).setOnClickListener(v -> dialog.dismiss());

        if (!dialog.isShowing())
            dialog.show();
    }

    public void hideDialog() {
        dialog.dismiss();
    }

}