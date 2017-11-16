package com.labs.tatu.kibanda.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.labs.tatu.kibanda.Interface.ItemClickListener;
import com.labs.tatu.kibanda.R;
import com.labs.tatu.kibanda.common.Common;

/**
 * Created by amush on 06-Oct-17.
 */

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txtFoodName;
    public ImageView foodImage;
    public ImageView favImage;

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);

        txtFoodName=(TextView)itemView.findViewById(R.id.food_name);
        foodImage=(ImageView)itemView.findViewById(R.id.food_image);
        favImage=(ImageView)itemView.findViewById(R.id.fav);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");
        menu.add(0,0,getAdapterPosition(), Common.UPDATE);
        menu.add(0,1,getAdapterPosition(), Common.DELETE);
    }
}
