package com.nikitha.android.booklistingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<ListItems>> {
    Bundle input=new Bundle();
    String url="https://www.googleapis.com/books/v1/volumes?q=";
    BooksAdaptor booksAdaptor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksAdaptor=new BooksAdaptor(this,new ArrayList<ListItems>());
        ListView listView=findViewById(R.id.ListView);
        listView.setAdapter(booksAdaptor);

        SearchView simpleSearchView = (SearchView) findViewById(R.id.search_bar); // inititate a search view
        //LoaderManager.getInstance(this).initLoader(1, input, this).forceLoad();
        final MainActivity a = this;

        // perform set on query text listener event
        simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query!=null){
                    String url1=url+query;
                    System.out.println("----------------------------------------"+url1);
                    input.putString("url",url1);
                    LoaderManager.getInstance(a).initLoader(1, input, a).forceLoad();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String text = newText;
                if(text!=null) {
                    booksAdaptor.clear();
                }
                return false;
            }
        });
    }

    @NonNull
    @Override
    public Loader<ArrayList<ListItems>> onCreateLoader(int id, @Nullable Bundle args) {
        TextView emptyview=findViewById(R.id.emptyView);
        ProgressBar progressBar=findViewById(R.id.progressBarspinner);
        progressBar.setVisibility(View.VISIBLE);
        return new BookListLoader(MainActivity.this,args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<ListItems>> loader, ArrayList<ListItems> data) {
        TextView emptyview=findViewById(R.id.emptyView);
        ProgressBar progressBar=findViewById(R.id.progressBarspinner);
        progressBar.setVisibility(View.INVISIBLE);

        if(data!=null && data.size()!=0){
            emptyview.setVisibility(View.INVISIBLE);
            booksAdaptor.setData(data);

        }
        else{
            SearchView sv=findViewById(R.id.search_bar);
            ListView listView=findViewById(R.id.ListView);
            emptyview.setVisibility(View.VISIBLE);
            listView.setEmptyView(emptyview);
            ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
            if(networkInfo==null){
                emptyview.setText(getResources().getString(R.string.noDatabczNoInternet));
            }
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<ListItems>> loader) {
        booksAdaptor.clear();
        TextView emptyview=findViewById(R.id.emptyView);
        emptyview.setVisibility(View.GONE);

    }
}
