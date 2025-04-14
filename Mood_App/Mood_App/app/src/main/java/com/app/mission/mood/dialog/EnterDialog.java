package com.app.mission.mood.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;

import com.app.mission.mood.R;
import com.app.mission.mood.click.OnEnterListener;

import java.util.Objects;

public class EnterDialog {

    private Context context;

    private Dialog dialog;

    public EnterDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_enter);
    }

    public void showDialog(String valueS, OnEnterListener runnable) {
        show(valueS, runnable);
    }

    public void showDialog(OnEnterListener runnable) {
        show("", runnable);
    }

    private void show(String valueS, OnEnterListener runnable) {
        dialog.setCancelable(false);

        ((AppCompatEditText) dialog.findViewById(R.id.edtContent)).setText(valueS);

        ((TextView) dialog.findViewById(R.id.txtYes)).setOnClickListener(v -> {
            String value = ((AppCompatEditText) dialog.findViewById(R.id.edtContent)).getText().toString().trim();
            if (!value.isEmpty()) {
                runnable.onEnter(value);
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.txtNo)).setOnClickListener(v -> dialog.dismiss());

        if (!dialog.isShowing())
            dialog.show();
    }

    public void hideDialog() {
        dialog.dismiss();
    }

}