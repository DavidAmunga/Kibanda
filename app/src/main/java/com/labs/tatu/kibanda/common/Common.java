package com.labs.tatu.kibanda.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.labs.tatu.kibanda.Interface.IGeoCoordinates;
import com.labs.tatu.kibanda.Interface.RetroFitClient;
import com.labs.tatu.kibanda.model.Request;
import com.labs.tatu.kibanda.model.User;

/**
 * Created by amush on 06-Oct-17.
 */

public class Common {
    public static User currentUser;
    public static Request currentRequest;

    public static final String UPDATE="Update";
    public static final String DELETE="Delete";
    public static final String USER_KEY="User";
    public static final String PWD_KEY="Password";

    public static final String baseUri="https://maps.googleapi.com";

    public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
        {
            return "Placed";
        }
        else if(status.equals("1"))
        {
            return  "On its way";
        }
        else
        {
            return "Shipped";
        }
    }

    public static IGeoCoordinates getGeoCodeService()
    {
        return RetroFitClient.getClient(baseUri).create(IGeoCoordinates.class);
    }

    public static Bitmap scaleBitmap(Bitmap bitmap,int newWidth,int newHeight)
    {
        Bitmap scaledBitmap=Bitmap.createBitmap(newWidth,newHeight,Bitmap.Config.ARGB_8888);

        float scaleX=newWidth/(float)bitmap.getWidth();
        float scaleY=newHeight/(float)bitmap.getHeight();
        float pivotX=0,pivotY=0;

        Matrix scaleMatrix=new Matrix();
        scaleMatrix.setScale(scaleX,scaleY,pivotX,pivotY);

        Canvas canvas=new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap,0,0,new Paint(Paint.FILTER_BITMAP_FLAG));

        return scaledBitmap;
    }

    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null)
        {
            NetworkInfo[] info=connectivityManager.getAllNetworkInfo();
            if(info!=null)
            {
                for(int i=0;i<info.length;i++)
                {
                    if(info[i].getState()==NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
