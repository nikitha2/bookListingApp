package com.nikitha.android.booklistingapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import static java.lang.System.in;

public class BookListLoader extends AsyncTaskLoader<ArrayList<ListItems>> {
    String jsonResponse;
    Bundle args;
    ArrayList<ListItems> data=new ArrayList<ListItems>();

    public BookListLoader(@NonNull Context context, Bundle args) {
        super(context);
        this.args=args;
    }

    @Nullable
    @Override
    public ArrayList<ListItems> loadInBackground() {
        String stringUrl=args.getString("url");
        URL url=null;
        if(stringUrl!=null){
            url=createUrl(stringUrl);
        }

        try {
            jsonResponse = makeHttpCall(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            data= extractFeatureFromJson(jsonResponse);
        }catch (Exception e){
            e.printStackTrace();
        }


        return data;
    }

    private ArrayList<ListItems> extractFeatureFromJson(String jsonResponse) throws JSONException, IOException {
        String title = null,author = null,webReaderLink=null;Bitmap image=null;
        ArrayList<ListItems> extractedDataArray=new ArrayList<ListItems>();
        ListItems listItems=new ListItems();
        int responseCode = 0;
        JSONObject baseJsonResponse = new JSONObject(jsonResponse);
        JSONArray itemsArray=baseJsonResponse.getJSONArray("items");
        int length = itemsArray.length();
        for(int i=0;i<itemsArray.length();i++){
            JSONObject items= itemsArray.getJSONObject(i);
            JSONObject volumeInfo= items.getJSONObject("volumeInfo");
           // JSONObject searchInfo= items.getJSONObject("searchInfo");
            JSONArray authorArray = null;

            if(volumeInfo.has("authors")) {
                authorArray = volumeInfo.getJSONArray("authors");
                author=authorArray.getString(0);
            }
            if(volumeInfo.has("title")) {
                title=volumeInfo.getString("title");
                
            }
            if(items.has("accessInfo")) {
                JSONObject accessInfo = items.getJSONObject("accessInfo");
                webReaderLink = accessInfo.getString("webReaderLink");
            }
            if(volumeInfo.has("imageLinks")) {
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String imageLink = imageLinks.getString("smallThumbnail");
                BitmapFactory.Options bmOptions;
                bmOptions = new BitmapFactory.Options();
                bmOptions.inSampleSize = 1;
                image = loadBitmap(imageLink, bmOptions);
            }


            extractedDataArray.add(new ListItems( "title: "+title,  "author: "+author ,webReaderLink,image));
        }
        return extractedDataArray;
    }

    public static Bitmap loadBitmap(String URL, BitmapFactory.Options options) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            in.close();
        } catch (IOException e1) {
        }
        return bitmap;
    }

    private static InputStream OpenHttpConnection(String strURL)
            throws IOException {
        InputStream inputStream = null;
        URL url = new URL(strURL);
        URLConnection conn = url.openConnection();

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpConn.getInputStream();
            }
        } catch (Exception ex) {
        }
        return inputStream;
    }

    public  Bitmap LoadImageFromWebOperations(String url1) {
        int responseCode = 0;
        try {Bitmap mIcon11 = null;
            //InputStream is = (InputStream) new URL(url).getContent();
            //Drawable d = Drawable.createFromStream(is, "src name");
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            URL url=createUrl(url1);
            try{
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            if(responseCode==200){
                inputStream= urlConnection.getInputStream();
                mIcon11 = BitmapFactory.decodeStream(inputStream);
            }
           
            return mIcon11;
        } catch (Exception e) {
            return null;
        }
    }
    private String makeHttpCall(URL url) throws IOException {
        String jsonResponse = "";
        int responseCode;
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        responseCode = urlConnection.getResponseCode();
        if(responseCode==200){
            inputStream= urlConnection.getInputStream();
            jsonResponse= readInputSteam(inputStream);
        }else{
            jsonResponse="";
        }
        return jsonResponse;
    }

    private String readInputSteam( InputStream inputStream) throws IOException {
        StringBuilder jsonResponse= new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String readLine = bufferedReader.readLine();
            while(readLine!=null){
                jsonResponse.append(readLine);
                readLine = bufferedReader.readLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return jsonResponse.toString();
    }

    private URL createUrl(String stringUrl) {
        try{
            return new URL(stringUrl); }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;}
    }

}
