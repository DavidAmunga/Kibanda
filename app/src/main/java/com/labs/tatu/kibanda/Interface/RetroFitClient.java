package com.labs.tatu.kibanda.Interface;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by amush on 21-Oct-17.
 */

public class RetroFitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUri) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUri)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
