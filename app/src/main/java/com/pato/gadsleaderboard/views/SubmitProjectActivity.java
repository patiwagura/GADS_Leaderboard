package com.pato.gadsleaderboard.views;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pato.gadsleaderboard.GadsApiInterface;
import com.pato.gadsleaderboard.R;
import com.pato.gadsleaderboard.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitProjectActivity extends AppCompatActivity {

    private static final String TAG = "SubmitProjectActivity";

    // references to ui-views.
    private EditText et_fName, et_lName, et_email, et_gitRepoUrl;
    private Button btn_submit;
    Toolbar mToolBar;

    private GadsApiInterface gadsApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_project);

        // Toolbar
//        mToolBar = new Toolbar(this);
//        mToolBar.setTitle(getString(R.string.app_name));
//        setSupportActionBar(mToolBar);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Default => ActionBar
        ActionBar actionBar = getSupportActionBar();
        //enable up button.
        actionBar.setLogo(R.drawable.gads_logo);
        actionBar.setDisplayHomeAsUpEnabled(true);

        gadsApi = RetrofitClient.getClient().create(GadsApiInterface.class);

        et_fName = findViewById(R.id.et_first_name);
        et_lName = findViewById(R.id.et_last_name);
        et_email = findViewById(R.id.et_email);
        et_gitRepoUrl = findViewById(R.id.et_project_url);

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postProjectData();
            }
        });

    }

    private void postProjectData() {
        if (et_fName.getText().toString().trim().isEmpty()) {
            et_fName.requestFocus();
            et_fName.setError("Required !");
            return;
        } else if (et_lName.getText().toString().trim().isEmpty()) {
            et_lName.requestFocus();
            et_lName.setError("Required !");
            return;
        } else if (et_email.getText().toString().trim().isEmpty()) {
            et_email.requestFocus();
            et_email.setError("Required !");
        } else if (et_gitRepoUrl.getText().toString().trim().isEmpty()) {
            et_gitRepoUrl.requestFocus();
            et_gitRepoUrl.setError("Required !");
        } else {
            // submit project to server.

            showDialog("Title here", "Are you sure ?");

        }
    }

    private void showAlertDialog(int iconId, String message) {
        View view = getLayoutInflater().inflate(R.layout.my_alert_dialog, null);
        ((TextView) view.findViewById(R.id.tv_alertMessage)).setText(message);
        ((ImageView) view.findViewById(R.id.imgAlert)).setImageResource(iconId);

        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog
                .setView(view)
                .setCancelable(true);

        AlertDialog dialog = myDialog.create();
        dialog.show();
    }


    // AlertDialog.
    private void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Layout inflater => inflate Layout & setView on dialog.
        LayoutInflater lytInflater = this.getLayoutInflater();
        View view = lytInflater.inflate(R.layout.my_custom_alert_dialog, null);
        builder.setView(view)
                .setCancelable(false);
        AlertDialog dialog = builder.create();

        view.findViewById(R.id.img_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        view.findViewById(R.id.btn_dialog_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = et_fName.getText().toString();
                String lName = et_lName.getText().toString();
                String email = et_email.getText().toString();
                String gitRepoUrl = et_gitRepoUrl.getText().toString();

                // post GitRepo Data.
                Call<Void> postRepoCall = gadsApi.postProject(
                        "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse",
                        email,
                        fName,
                        lName,
                        gitRepoUrl
                );

                postRepoCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response != null) {
                            if (response.isSuccessful()) {
                                Log.e(TAG, "post-onResponse: " + response.message() + "\n\n" +
                                        response.body() + "\n\n" + response.errorBody());
                                Toast.makeText(SubmitProjectActivity.this, "Success :" + response.message(), Toast.LENGTH_LONG).show();

                                // show success Dialog.
                                showAlertDialog(R.drawable.ic_check_circle, "Submission Successful");

                            } else {
                                // show Failure Dialog.
                                showAlertDialog(R.drawable.ic_warning, "Submission not Successful");

                            }
                        } else {
                            // response is null => some error.
                            showAlertDialog(R.drawable.ic_warning, "Submission not Successful");
                        }

                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // show Failure dialog.

                        Toast.makeText(SubmitProjectActivity.this, "onFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
                        showAlertDialog(R.drawable.ic_warning, "Submission not Successful");
                        dialog.dismiss();
                    }
                });

            }
        });


        dialog.show();
    }
}
