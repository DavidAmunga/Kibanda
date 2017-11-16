package com.labs.tatu.kibanda.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.labs.tatu.kibanda.Interface.ItemClickListener;
import com.labs.tatu.kibanda.R;
import com.labs.tatu.kibanda.common.Common;

/**
 * Created by amush on 07-Oct-17.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener {

    public TextView txtOrderId,txtOrderPhone,txtOrderAddress,txtOrderStatus;
    private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderAddress=(TextView)itemView.findViewById(R.id.order_address);
        txtOrderId=(TextView)itemView.findViewById(R.id.order_id);
        txtOrderPhone=(TextView)itemView.findViewById(R.id.order_phone);
        txtOrderStatus=(TextView)itemView.findViewById(R.id.order_status);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        if(Common.currentUser.getName().equals("admin")) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
       if(Common.currentUser.getName().equals("admin"))
       {
           menu.setHeaderTitle("Select the action");
           menu.add(0,0,getAdapterPosition(), Common.UPDATE);
           menu.add(0,1,getAdapterPosition(), Common.DELETE);
       }

    }
}
