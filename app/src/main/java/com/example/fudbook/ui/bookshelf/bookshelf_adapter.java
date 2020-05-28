package com.example.fudbook.ui.bookshelf;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fudbook.R;
import com.example.fudbook.objects.Book;

import java.util.ArrayList;

// optimize bookshelf adapter for bookshelf or books
public class bookshelf_adapter extends RecyclerView.Adapter<bookshelf_adapter.ViewHolder> {

    // TAG
    private static final String TAG = "bookshelf_adapter";

    private ArrayList<String> mNames; // store bookshelf titles
    private ArrayList<String> mImages; // store images
    private ArrayList<Book> mBooks;
    private Context mContext;
    OnItemClickListener mListener;

    // interface for listener
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    // set up onclick listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    // view holder stores view to be placed
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // hold book title, icon, and layout
        public TextView book_title;
        public ImageView book_icon;
        public View layout;

        public ViewHolder(View v, final OnItemClickListener listener) {
            super(v);
            layout = v;
            book_title = (TextView) v.findViewById(R.id.book_title);
            book_icon = (ImageView) v.findViewById(R.id.icon);
            
            v.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                if(listener != null){
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
                }
            });
        }
    }

    // Constructor for the adapter
    public bookshelf_adapter(Context newcontext, ArrayList<String> newnames, ArrayList<String> newimages, ArrayList<Book> books) {

        mNames = newnames;
        mImages = newimages;
        mBooks = books;
        mContext = newcontext;

    }

    // add selected items to the adapter
    public void add(int position, String name, String image, Book book) {
        mNames.add(position, name);
        mBooks.add(position, book);
        // if not default book
        if (image != null)
            mImages.add(position,image);
            
        notifyItemInserted(position);
    }

    // remove selected item from the adapter
    public void remove(int position) {
        mNames.remove(position);
        mBooks.remove(position);
        // mImages.remove(position);
        notifyItemRemoved(position);
    }

    public Book getBook(int position){ return mBooks.get(position);}

    // creates cell
    @Override
    public bookshelf_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.layout_listitem, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Log.d(TAG, "OnBindViewHolder: created");
        final String name = mNames.get(position);

        // if default image
//        if (position <= mImages.size()){
//            Glide.with(mContext)
//                    .asBitmap()
//                    .load(mImages.get(position))
//                    .centerCrop()
//                    .into(holder.book_icon);
//        }

        // set name of column text
        holder.book_title.setText(name);

    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

}