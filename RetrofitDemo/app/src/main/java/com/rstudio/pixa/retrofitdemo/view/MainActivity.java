package com.rstudio.pixa.retrofitdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rstudio.pixa.retrofitdemo.adapter.MainAdapter;
import com.rstudio.pixa.retrofitdemo.model.MovieModel;
import com.rstudio.pixa.retrofitdemo.presenter.MainActivityPresenter;
import com.rstudio.pixa.retrofitdemo.R;

import java.util.ArrayList;

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
