package com.labs.tatu.kibanda.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.labs.tatu.kibanda.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amush on 07-Oct-17.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME="food.db";
    private static final int DB_VER=1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    public List<Order> getCarts()
    {
        SQLiteDatabase db=getReadableDatabase();
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();

        String[] sqlSelect={"ProductID","ProductName","Quantity","Price","Discount"};
        String sqlTable="OrderDetail";
        qb.setTables(sqlTable);
        Cursor c=qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order> result=new ArrayList<>();
        while(c.moveToNext())
        {
            result.add(new Order(c.getString(c.getColumnIndex("ProductID")),
                    c.getString(c.getColumnIndex("ProductName")),
                    c.getString(c.getColumnIndex("Quantity")),
                    c.getString(c.getColumnIndex("Price")),
                    c.getString(c.getColumnIndex("Discount"))
                    ));
        }
        return  result;
    }

    public void addToCart(Order order)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO OrderDetail(ProductID,ProductName,Quantity,Price,Discount)" +
                " VALUES('%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getPrice(),
                order.getQuantity(),
                order.getDiscount()
                );

        db.execSQL(query);
    }
    public void cleanCart()
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
//    Favorites
    public void addToFavorites(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("INSERT INTO Favorites(FoodID) VALUES('%s');",foodId);
        db.execSQL(query);
    }
    public void removeFromFavorites(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("DELETE FROM Favorites WHERE FoodID='%s';",foodId);
        db.execSQL(query);
    }
    public boolean isFavorite(String foodId)
    {
        SQLiteDatabase db=getReadableDatabase();
        String query=String.format("SELECT * FROM Favorites WHERE FoodID='%s';",foodId);
        Cursor c=db.rawQuery(query,null);
        if(c.getCount()<=0)
        {
            c.close();
            return false;
        }
        c.close();
        return true;

    }

}
