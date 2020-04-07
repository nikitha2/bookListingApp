package com.nikitha.android.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import static androidx.core.content.ContextCompat.startActivity;

public class BooksAdaptor extends ArrayAdapter<ListItems> {
   Context contextActivity;
    ListItems currentword;
    URL url;
    ArrayList<ListItems> listItemsArray=new ArrayList<>();
    public BooksAdaptor(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public BooksAdaptor(@NonNull Context context, ArrayList<ListItems> listItemsArray) {
        super(context, 0, listItemsArray);
        contextActivity=context;
        this.listItemsArray=listItemsArray;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        final String[] parts = (parent.toString()).split("app:id/", 2);
        currentword = getItem(position);

        TextView title=listItemView.findViewById(R.id.titlebook);
        title.setText(currentword.getBookTitle());

        TextView bookAutor=listItemView.findViewById(R.id.author);
        bookAutor.setText(currentword.getBookAutor());

        ImageView image=listItemView.findViewById(R.id.imageView);
        image.setImageBitmap(currentword.getImage());

        TextView readbooklink=listItemView.findViewById(R.id.txtDefaultpassword);
        readbooklink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               String webReaderLink= currentword.getWebReaderLink();

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(webReaderLink));
                contextActivity.startActivity(i);
            }

        });
        return listItemView;
    }

    public void setData(ArrayList<ListItems> data) {
        listItemsArray.addAll(data);
        notifyDataSetChanged();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if (charText.length() == 0) {
            //listItemsArray.addAll(listItemsArray);
        } else {
            listItemsArray.clear();
            for (ListItems wp : listItemsArray) {
                if (wp.getBookTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listItemsArray.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
