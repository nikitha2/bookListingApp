package com.nikitha.android.booklistingapp;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItems {

    //Bitmap image;
    String bookTitle;
    String bookAutor;
    String webReaderLink;
    Bitmap image;

    public ListItems(String bookTitle, String bookAutor,String webReaderLink,Bitmap image) {
       // this.image = image;
        this.bookTitle = bookTitle;
        this.bookAutor = bookAutor;
        this.webReaderLink = webReaderLink;
        this.image=image;
    }
    public ListItems() {
      }

    /*public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
*/
    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAutor() {
        return bookAutor;
    }

    public void setBookAutor(String bookAutor) {
        this.bookAutor = bookAutor;
    }

    public void setWebReaderLink(String webReaderLink) {
        this.webReaderLink = webReaderLink;
    }

    public String getWebReaderLink() {
        return webReaderLink;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
