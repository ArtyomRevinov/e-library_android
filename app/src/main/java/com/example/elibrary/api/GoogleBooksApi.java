package com.example.elibrary.api;

import com.example.elibrary.entity.Book;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleBooksApi {

    @GET("books/v1/volumes")
    Call<Book> loadVolumes(@Query("q") String query);
}
