package com.halaltokens.halaltokens;

import android.app.AlertDialog;
import android.content.Context;

public class Utils {

    //alert dialog (OK button only)
    public static void showOkAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.show();
    }

}
