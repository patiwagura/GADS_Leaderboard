package com.pato.gadsleaderboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // returns an instance of Retrofit Client.

    // Base_URL => of resource you want to access.
    public static final String BASE_URL = "https://gadsapi.herokuapp.com/";

    // retrofit instance.
    private static Retrofit retrofit = null;

    //method to return retrofit instance.
    public synchronized static Retrofit getClient() {

        if (retrofit == null) {
            // add Retrofit Logging-interceptor to help in Debugging Errors encountered when making requests.
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Logging Level = BASIC | BODY => most verbose | HEADERS

            // Retrofit uses => OkHttpClient as the Networking Layer.
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    // Optional: adding HTTP-Request Headers to LoggingInterceptor => Headers affect all Http-REQUESTS.
                    .addInterceptor(logInterceptor)
                    .build();

            // customize Gson Convertor to serialize nulls => other customizations can be done.
            Gson gson = new GsonBuilder().serializeNulls().create();


            // Create Retrofit instance.
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    //.addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }


}
