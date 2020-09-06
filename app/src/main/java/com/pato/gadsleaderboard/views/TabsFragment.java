package com.pato.gadsleaderboard.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pato.gadsleaderboard.GadsApiInterface;
import com.pato.gadsleaderboard.R;
import com.pato.gadsleaderboard.RecyclerAdapter;
import com.pato.gadsleaderboard.RetrofitClient;
import com.pato.gadsleaderboard.model.LearnerHours;
import com.pato.gadsleaderboard.model.LearnerSkillIq;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TabsFragment extends Fragment {

    private boolean mThreadRunning = false;

    // indicates position of Fragment to load.
    private int mFragmentPos = 0;

    public static final String ARG_TABS_FRAG_POSITION = "tabs_fragment_position";
    private static final String TAG = "TabsFragment";

    // private FetchData fetchData_Async;

    private RecyclerView recycler_items;
    private RecyclerAdapter recyclerAdapter;
    private List<LearnerHours> mList = new ArrayList<>();

    private GadsApiInterface gadsApi;

    public TabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e(TAG, "onCreateView:  Inflate Layout");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // super.onViewCreated(view, savedInstanceState);

        // display data sent via => Bundle.
        Bundle args = getArguments();
        mFragmentPos = args.getInt(ARG_TABS_FRAG_POSITION);

        Log.e(TAG, "onViewCreated: -----" + mFragmentPos);

        // use android text1 => to display data.
        // tv_Results.append(Integer.toString(args.getInt(ARG_OBJECT)));

        // recyclerView.
        recycler_items = view.findViewById(R.id.recycler_items);
        recycler_items.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // get REST-API Interface.
        gadsApi = RetrofitClient.getClient().create(GadsApiInterface.class);
        loadDataFromApi();

    }

    private void getLearnerSkillIq() {
        // List SKILL_IQ.
        Call<List<LearnerSkillIq>> callGetSkillIq = gadsApi.getSkillIq();
        callGetSkillIq.enqueue(new Callback<List<LearnerSkillIq>>() {
            @Override
            public void onResponse(Call<List<LearnerSkillIq>> call, Response<List<LearnerSkillIq>> response) {
                if (response.isSuccessful() && response != null) {
                    // successful response.
                    List<LearnerSkillIq> listSkillIq = response.body();

                    recyclerAdapter = new RecyclerAdapter(null, listSkillIq, getActivity());
                    //recycler_items.swapAdapter(recyclerAdapter, false);
                    recycler_items.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<List<LearnerSkillIq>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                ToastError(t.getMessage());

            }
        });

    }


    public void getLearnerHours() {

        //list all hours.
        Call<List<LearnerHours>> callGetHours = gadsApi.getLeanerHours();
        callGetHours.enqueue(new Callback<List<LearnerHours>>() {
            @Override
            public void onResponse(Call<List<LearnerHours>> call, Response<List<LearnerHours>> response) {
                if (response.isSuccessful() && response != null) {
                    // successful response.
                    List<LearnerHours> listHours = response.body();
                    recyclerAdapter = new RecyclerAdapter(listHours, null, getActivity());
                    recycler_items.setAdapter(recyclerAdapter);
                    recyclerAdapter.notifyDataSetChanged();

                    // Log
                    Log.e(TAG, "onResponse: --- List Received :" + listHours.toString());

                }
            }

            @Override
            public void onFailure(Call<List<LearnerHours>> call, Throwable t) {

                Log.e(TAG, "onFailure: " + t.getMessage());
                ToastError(t.getMessage());
            }
        });


    }

    private void loadDataFromApi() {
        if (mFragmentPos == 0) {
            // load Learning Hours.
            getLearnerHours();
        } else if (mFragmentPos == 1) {
            // Load Skill_IQ Leaders.
            getLearnerSkillIq();
        }

    }

    // show a Toast message.
    public void ToastError(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onResume() {
        super.onResume();

        mThreadRunning = true;

        // simulate fetching data from network.
        //fetchData_Async = new FetchData();
        //fetchData_Async.execute();

        // In-case we start app without wifi => Load data when wifi is connected.
        loadDataFromApi();

        Log.e(TAG, "onResume: ----------------------");

        // Load data from Api -- here.
    }

    @Override
    public void onPause() {
        super.onPause();

        mThreadRunning = false;

        Log.e(TAG, "onPause: ------");
        //fetchData_Async.cancel(true);
    }

//    class FetchData extends AsyncTask<Void, Void, Void> {
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            for (int i = 0; i <= 50; i++) {
//
//                // we have cancelled the background Thread.
//                if (!mThreadRunning) {
//                    break;
//                    //return;
//                }
//
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    Log.e(TAG, "fetchData  Error: " + e.getLocalizedMessage());
//                }
//
//                int finalI = i;
//                tv_Results.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv_Results.append("Loading-Data: " + finalI + "\n");
//                        Log.e(TAG, "Loading-Data : " + finalI + "\n");
//                    }
//                });
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//        }
//    } //Async-Class
}
