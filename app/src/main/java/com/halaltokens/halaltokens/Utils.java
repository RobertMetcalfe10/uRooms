package com.halaltokens.halaltokens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class Utils {

    //alert dialog (OK button only)
    public static AlertDialog showOkAlertDialog(Context context, String title, String message) {

        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.alert_layout, null);

        AlertDialog dialog = new AlertDialog.Builder(context)
        .setView(view)
        .setTitle(title)
        .setMessage(message)
//        .setPositiveButton(android.R.string.ok, null);
        .setNegativeButton("Cancel", (dialogs, which) -> {
            //

        }).setPositiveButton("", (dialogs, which) -> {
            //

        }).create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            // if you do the following it will be left aligned, doesn't look
            // correct
            // button.setCompoundDrawablesWithIntrinsicBounds(android.R.drawable.ic_media_play,
            // 0, 0, 0);

            Drawable drawable = context.getApplicationContext().getResources().getDrawable(
                    android.R.drawable.btn_star);

            // set the bounds to place the drawable a bit right
            drawable.setBounds((int) (drawable.getIntrinsicWidth() * 0.5),
                    0, (int) (drawable.getIntrinsicWidth() * 1.5),
                    drawable.getIntrinsicHeight());
            button.setCompoundDrawables(drawable, null, null, null);
            button.setTextColor(Color.BLACK);

            // could modify the placement more here if desired
            // button.setCompoundDrawablePadding();
        });


        return dialog;
    }

}
