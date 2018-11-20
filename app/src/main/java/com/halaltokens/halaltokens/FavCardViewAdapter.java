package com.halaltokens.halaltokens;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iambedant.text.OutlineTextView;

import java.util.List;

public class FavCardViewAdapter extends RecyclerView.Adapter<FavCardViewAdapter.MyViewHolder>{

    //List
    private List<String> buildings;
    private BuildingFragment.OnItemClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public OutlineTextView roomName;

        MyViewHolder(View view) {
            super(view);
            //initialize cardview
            CardView cardView = view.findViewById(R.id.card_view);
            cardView.setCardElevation(20);//setcardview elevation
            cardView.setRadius(30);       //set radius of cardview
            //initialize textviews
            roomName = view.findViewById(R.id.roomName);
            cardView.setOnClickListener(view1 -> clickListener.onBuildingClicked(roomName.getText().toString()));
        }
    }

    //constructor
    FavCardViewAdapter(List<String> buildings, BuildingFragment.OnItemClickListener onClickListener) {
        this.buildings = buildings;
        this.clickListener = onClickListener;
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
}