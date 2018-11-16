package com.halaltokens.halaltokens;

import android.arch.persistence.room.Room;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.dift.ui.SwipeToAction;
import io.realm.Realm;
import io.realm.RealmResults;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;


public class FavAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /** References to the views for each data item **/
    public class BookViewHolder extends SwipeToAction.ViewHolder {

        Realm realm = Realm.getDefaultInstance();

        // Query Realm for all dogs younger than 2 years old
        final RealmResults<RoomInfo> puppies = realm.where(RoomInfo.class).findAll();



        public TextView titleView;
        public TextView authorView;
        public SimpleDraweeView imageView;

        public BookViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            authorView = (TextView) v.findViewById(R.id.author);
            imageView = (SimpleDraweeView) v.findViewById(R.id.image);
        }
    }

    /** Constructor **/
    public FavAdapter(List items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Book item = items.get(position);
        BookViewHolder vh = (BookViewHolder) holder;
        vh.titleView.setText(item.getTitle());
        vh.authorView.setText(item.getAuthor());
        vh.imageView.setImageURI(Uri.parse(item.getImageUrl()));
        vh.data = item;
    }
}