package com.halaltokens.halaltokens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FavAlertDialog extends AlertDialog{
    private final OnDialogFavListener listener;
    private AlertDialog dialog;

    protected FavAlertDialog(Context context, OnDialogFavListener onDialogFavListener, String title, ArrayList<RoomInfo> message) {
        super(context);
        listener = onDialogFavListener;

        LayoutInflater factory = LayoutInflater.from(context);
        final View view = factory.inflate(R.layout.alert_layout, null);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listView = inflater.inflate(R.layout.scroll_rooms, null, false);
        TextView tv = listView.findViewById(R.id.textViewWithScroll);
        for (int i=0;i<message.size();i++) {
            tv.append(message.get(i).toString());
        }


        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setTitle(title)
                .setPositiveButton("TEST", (dialogs, which) -> listener.onDialogFavButtonClicked())
                .setView(listView)
                .create();

        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

            //could replace this drawable with a material heart outline and when clicked a material filled heart
            Drawable drawable = context.getApplicationContext().getResources().getDrawable(android.R.drawable.star_off, null);

            // set the bounds to place the drawable a bit right
            drawable.setBounds((int) (drawable.getIntrinsicWidth() * 0.5),
                    0,
                    (int) (drawable.getIntrinsicWidth() * 1.5),
                    drawable.getIntrinsicHeight());
            button.setCompoundDrawables(drawable, null, null, null);
            button.setTextColor(Color.BLACK);

            // could modify the placement more here if desired
            // button.setCompoundDrawablePadding();
        });
    }

    public void show() {
        dialog.show();
    }


}
