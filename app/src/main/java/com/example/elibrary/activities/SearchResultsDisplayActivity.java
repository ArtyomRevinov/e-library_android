package com.example.elibrary.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.elibrary.R;
import com.example.elibrary.adapter.VolumeAdapter;
import com.example.elibrary.entity.Book;
import com.example.elibrary.api.GoogleBooksApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultsDisplayActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://www.googleapis.com/";
    private RecyclerView booksRecyclerView;
    private RecyclerView.Adapter volumeAdapter;
    private RecyclerView.LayoutManager booksLayoutManager;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_display);
        context = getApplicationContext();
        initViews();
    }

    public void initViews(){
        booksRecyclerView = findViewById(R.id.recycler_view);
        booksRecyclerView.setHasFixedSize(true);
        booksLayoutManager = new LinearLayoutManager(this);
        booksRecyclerView.setLayoutManager(booksLayoutManager);
        loadJson();
    }

    public void loadJson(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GoogleBooksApi googleBooksApi =
                retrofit.create(GoogleBooksApi.class);

        Intent intent = getIntent();
        String query = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Call<Book> call = googleBooksApi.loadVolumes(query);
        call.enqueue(new Callback<Book>() {

            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful()){
                    Book book = response.body();

                    volumeAdapter = new VolumeAdapter(book.getItems());
                    booksRecyclerView.setAdapter(volumeAdapter);

                    if (volumeAdapter.getItemCount() == 0){
                        sendNotification(R.string.no_results);
                    }

                } else {
                    sendNotification(R.string.load_failed);
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

                ConnectivityManager connectivityManager =
                        (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

                if (activeNetwork != null && activeNetwork.isConnected()){
                    sendNotification(R.string.load_failed);
                } else {
                    sendNotification(R.string.no_internet);
                }
            }
        });
    }

    public void sendNotification(int strId){
        Toast.makeText(context,
               context.getString(strId),
                Toast.LENGTH_LONG).show();
    }
}
