/*
In the building fragment,
 */




package com.halaltokens.halaltokens;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iambedant.text.OutlineTextView;

import java.util.List;


public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.MyViewHolder> {

    //List
    private List<String> buildings;
    private BuildingFragment.OnItemClickListener clickListener;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public OutlineTextView buildingName;
        public ConstraintLayout buildingImage;

        MyViewHolder(View view) {
            super(view);
            //initialize cardview
            CardView cardView = view.findViewById(R.id.card_view);
            cardView.setCardElevation(20);//setcardview elevation
            cardView.setRadius(30);       //set radius of cardview
            //initialize textviews
            buildingName = view.findViewById(R.id.buildingName);
            buildingImage = view.findViewById(R.id.card_view_layout);
            cardView.setOnClickListener(view1 -> {
                clickListener.onBuildingClicked(buildingConverter(buildingName.getText().toString()));
            });
        }
    }

    //constructor
    CardViewAdapter(List<String> buildings, BuildingFragment.OnItemClickListener onClickListener, Context context) {
        this.buildings = buildings;
        this.clickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //layout inflator-set view of each item of recyclerview
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_cardview, parent, false);
        return new CardViewAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CardViewAdapter.MyViewHolder holder, int position) {
        //get item at position
        String building = this.buildings.get(position);

        switch (building) {
            case "CompSci":
                holder.buildingName.setText("Computer Science");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.cs_building));
                break;
            case "Arts":
                holder.buildingName.setText("Arts");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.arts_building));
                break;
            case "Eng":
                holder.buildingName.setText("Engineering");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.eng_building));
                break;
            case "HealthScience":
                holder.buildingName.setText("Health Science");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.h_sci_building));
                break;
            case "SciEast":
                holder.buildingName.setText("Science East");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.sci_east_building));
                break;
            case "SciHub":
                holder.buildingName.setText("Science Hub");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.science_hub));
                break;
            case "SciNorth":
                holder.buildingName.setText("Science North");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.sci_north_building));
                break;
            case "SciSouth":
                holder.buildingName.setText("Science South");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.science_south));
                break;
            case "SciWest":
                holder.buildingName.setText("Science West");
                holder.buildingImage.setBackground(ContextCompat.getDrawable(context, R.drawable.sci_west_building));
                break;
            default:
                holder.buildingName.setText(building);
        }

    }

    private String buildingConverter(String buildingName) {
        switch (buildingName) {
            case "Computer Science":
                return "CompSci";
            case "Arts":
                return "Arts";
            case "Engineering":
                return "Eng";
            case "Health Science":
                return "HealthScience";
            case "Science East":
                return "SciEast";
            case "Science Hub":
                return "SciHub";
            case "Science North":
                return "SciNorth";
            case "Science South":
                return "SciSouth";
            case "Science West":
                return "SciWest";
            default:
                return "";
        }
    }

    @Override
    public int getItemCount() {
        return buildings.size();
    }
}