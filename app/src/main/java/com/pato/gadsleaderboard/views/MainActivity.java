package com.pato.gadsleaderboard.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.pato.gadsleaderboard.GadsApiInterface;
import com.pato.gadsleaderboard.R;
import com.pato.gadsleaderboard.RecyclerAdapter;
import com.pato.gadsleaderboard.RetrofitClient;
import com.pato.gadsleaderboard.ViewPagerAdapter;
import com.pato.gadsleaderboard.model.LearnerHours;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private TabLayoutMediator tabMediator;
    private ViewPagerAdapter viewPagerAdapter;

    // ToolBar
    private Button btn_toolbar_project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_toolbar_project = findViewById(R.id.btn_toolbar_submit);
        btn_toolbar_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubmitProjectActivity.class);
                startActivity(intent);
            }
        });


        //reference to views.
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedId = view.getId();
                Toast.makeText(MainActivity.this, "Tab clicked : " + clickedId, Toast.LENGTH_SHORT).show();

            }
        });
        viewPager2 = findViewById(R.id.view_pager);

        // List to hold Tabs Titles.
        List<String> tabsTitles = new ArrayList<>();
        tabsTitles.add("Learning Leaders");
        tabsTitles.add("Skill IQ Leaders");

        // fragments to Add to TabLayout
        List<Fragment> lst = new ArrayList<>();
        lst.add(new TabsFragment());
        lst.add(new TabsFragment());

        // init ViewPager Adapter.
        viewPagerAdapter = new ViewPagerAdapter(this, lst);
        viewPager2.setAdapter(viewPagerAdapter);

        // TabLayout mediator.
        tabMediator = new TabLayoutMediator(tabLayout, viewPager2, true,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabsTitles.get(position));


                        // I can post the current Tab_position to RecyclerViewAdapter => determine which data to load.

                        Log.e(TAG, "onConfigureTab: Setting Tab-Titles: " + tabsTitles.get(position));
                    }
                });

        tabMediator.attach();

    }
}
