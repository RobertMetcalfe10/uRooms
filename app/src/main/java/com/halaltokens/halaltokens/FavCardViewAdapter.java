package com.halaltokens.halaltokens;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iambedant.text.OutlineTextView;

import java.util.List;

public class FavCardViewAdapter extends RecyclerView.Adapter<FavCardViewAdapter.MyViewHolder>{

    //List
    private List<String> buildings;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private OutlineTextView roomName;
        private ImageView favImage;

        MyViewHolder(View view) {
            super(view);
            //initialize cardview
            CardView cardView = view.findViewById(R.id.card_view_fav);
            cardView.setCardElevation(20);//setcardview elevation
            cardView.setRadius(30);       //set radius of cardview
            //initialize textviews
            roomName = view.findViewById(R.id.roomName);
            favImage = view.findViewById(R.id.favImage);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch(v.getId())
                    {
                        case R.id.roomName:
                            System.out.println("worked1");
                            break;

                        case R.id.favImage:
                            System.out.println("worked2");
                            break;

                        default:
                            System.out.println("Didn't work");
                            break;
                    }
//                    clickListener.onBuildingClicked(roomName.getText().toString());
                }
            };
            roomName.setOnClickListener(onClickListener);
            favImage.setOnClickListener(onClickListener);

        }
    }

    //constructor
    FavCardViewAdapter(List<String> buildings, FavRoomsFragment.OnItemClickListener onClickListener) {
        this.buildings = buildings;
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