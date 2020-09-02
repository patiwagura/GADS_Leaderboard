package com.pato.gadsleaderboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.pato.gadsleaderboard.model.LearnerHours;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView tv_showResults;
    private GadsApiInterface gadsApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_showResults = findViewById(R.id.tv_showResults);

        // get REST-API Interface.
        gadsApi = RetrofitClient.getClient().create(GadsApiInterface.class);

        //list all hours.
        Call<List<LearnerHours>> callgetHours = gadsApi.getLeanerHours();
        callgetHours.enqueue(new Callback<List<LearnerHours>>() {
            @Override
            public void onResponse(Call<List<LearnerHours>> call, Response<List<LearnerHours>> response) {
                if (response.isSuccessful() && response != null) {
                    // successful response.
                    List<LearnerHours> listHours = response.body();

                    for (int i = 0; i < listHours.size(); i++) {
                        LearnerHours learnerHours = listHours.get(i);

                        Log.e(TAG, "onResponse: " + learnerHours.toString());
                        tv_showResults.append(learnerHours.toString() + "\n\n");

                    }

                }
            }

            @Override
            public void onFailure(Call<List<LearnerHours>> call, Throwable t) {
                tv_showResults.setText(t.getMessage());
            }
        });


    }
}
