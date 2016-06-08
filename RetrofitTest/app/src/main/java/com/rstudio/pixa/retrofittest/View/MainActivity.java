package com.rstudio.pixa.retrofittest.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rstudio.pixa.retrofittest.Adapter.MainAdapter;
import com.rstudio.pixa.retrofittest.Model.MovieModel;
import com.rstudio.pixa.retrofittest.Presenter.MainActivityPresenter;
import com.rstudio.pixa.retrofittest.R;
import com.rstudio.pixa.retrofittest.Utils.MovieClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private MainActivityPresenter presenter;

    private RecyclerView mainListView;
    private RecyclerView.LayoutManager mainListLayoutMng;
    public MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this);

        mainListView = ((RecyclerView) findViewById(R.id.main_list));
        mainListLayoutMng = new LinearLayoutManager(this);
        mainAdapter = new MainAdapter(this, new ArrayList<MovieModel>());
        mainListView.setLayoutManager(mainListLayoutMng);
        mainListView.setAdapter(mainAdapter);
        mainAdapter.notifyDataSetChanged();

        presenter.fetchData();


    }
}
