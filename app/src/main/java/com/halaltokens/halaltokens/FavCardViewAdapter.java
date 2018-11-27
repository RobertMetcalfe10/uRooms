package com.halaltokens.halaltokens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.iambedant.text.OutlineTextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavCardViewAdapter extends RecyclerView.Adapter<FavCardViewAdapter.MyViewHolder>{

    private List<String> buildings;
    private static FavOnItemClickedListener onItemClickedListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private OutlineTextView roomName;
        private ImageButton favButton;
        private ImageButton shareButton;

        MyViewHolder(View view) {
            super(view);
            //initialize cardview
            CardView cardView = view.findViewById(R.id.card_view_fav);
            cardView.setCardElevation(20);//setcardview elevation
            cardView.setRadius(30);       //set radius of cardview
            //initialize textviews
            roomName = view.findViewById(R.id.roomName);
            favButton = view.findViewById(R.id.unFav);
            shareButton = view.findViewById(R.id.shareButton);

            View.OnClickListener onClickListener = v -> {
                switch(v.getId())
                {
                    case R.id.roomName:
                        onItemClickedListener.OnTextItemClicked(roomName.getText().toString().trim());
                        break;

                    case R.id.unFav:
                        Realm realm = Realm.getDefaultInstance();
                        realm.beginTransaction();
                        RealmResults<RoomInfo> result = realm.where(RoomInfo.class).findAll();
                        for (int i=0; i<result.size(); i++) {
                            if (result.get(i).getRoomName().trim().equals(roomName.getText().toString().trim())) {
                                result.deleteFromRealm(i);
                            }
                        }
                        realm.commitTransaction();
                        onItemClickedListener.OnFavItemClicked(roomName.getText().toString().trim());
                        break;

                    case R.id.shareButton:
                        onItemClickedListener.OnShareItemClicked(roomName.getText().toString().trim());
                        break;
                    default:
                        break;
                }
            };
            roomName.setOnClickListener(onClickListener);
            favButton.setOnClickListener(onClickListener);
            shareButton.setOnClickListener(onClickListener);

        }
    }

    //constructor
    FavCardViewAdapter(List<String> buildings) {
        this.buildings = buildings;
    }

    public void setOnItemClickedListener(FavOnItemClickedListener clickedListener) {
        onItemClickedListener = clickedListener;
    }

    @NonNull
    @Override
    public FavCardViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflator-set view of each item of recyclerview
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_item_recycler_cardview, parent, false);
        return new FavCardViewAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavCardViewAdapter.MyViewHolder holder, int position) {
        String building = this.buildings.get(position);
        holder.roomName.setText(building);
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }

    public interface FavOnItemClickedListener {
        public void OnFavItemClicked(String roomName);
        public void OnShareItemClicked(String roomName);
        public void OnTextItemClicked(String roomName);
    }
}