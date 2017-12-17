package com.labs.tatu.kibanda;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.labs.tatu.kibanda.ViewHolder.CartAdapter;
import com.labs.tatu.kibanda.common.Common;
import com.labs.tatu.kibanda.database.Database;
import com.labs.tatu.kibanda.model.Order;
import com.labs.tatu.kibanda.model.Request;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {
    private static final String TAG = "Cart";
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


    DatabaseReference mDatabase;

    TextView txtTotalPrice;

    Button btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


//        Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("Requests");

//        Init
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cart.size() > 0) {
                    showAlertDialog();
                } else {
                    Toast.makeText(Cart.this, "Your cart is empty!", Toast.LENGTH_SHORT).show();
                }

            }


        });


        loadListFood();


    }

    private void showAlertDialog() {

        new LovelyTextInputDialog(this, R.style.AppTheme)
                .setTopColorRes(R.color.colorPrimary)
                .setTitle("Delivery location")
                .setMessage("Enter location")
                .setIcon(R.drawable.ic_order_location)
                .setInputFilter("Delivery location can not be empty", new LovelyTextInputDialog.TextFilter() {
                    @Override
                    public boolean check(String text) {
                        return text.length() > 0;
                    }
                })
                .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                    @Override
                    public void onTextInputConfirmed(String location) {
                        //Create New Request
                        Request request = new Request(
                                Common.currentUser.getPhone(),
                                Common.currentUser.getName(),
                                location,
                                txtTotalPrice.getText().toString(),
                                cart);
                        //            Submit to Firebase Use System.currentMillis
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Requests");
                        ref.child(String.valueOf(System.currentTimeMillis())).setValue(request);
//                Delete Cart
                        new Database(getBaseContext()).cleanCart();

                        Toast.makeText(Cart.this, "Thank you!, Order Placed", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();


    }

    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);

        recyclerView.setAdapter(adapter);
        //Calculate total price


        int total = 0;
        for (Order order : cart) {
            Log.d(TAG, "Order Name :" + order.getProductName());

            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));


            txtTotalPrice.setText(String.valueOf(total));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals("Delete")) {
            deleteCart(item.getOrder());
        }
        return true;
    }

    private void deleteCart(int position) {
        //Remove item at List<Order> by position
        cart.remove(position);
        //After that, we will delete all old data from SQLite
        new Database(this).cleanCart();
        //Update data from List<Order> to SQLite
        for (Order item : cart) {
            new Database(this).addToCart(item);
        }
        loadListFood();
    }
}
