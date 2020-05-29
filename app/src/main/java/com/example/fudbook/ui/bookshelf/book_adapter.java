package com.example.fudbook.ui.bookshelf;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fudbook.R;
import com.example.fudbook.objects.Recipe;

import java.util.ArrayList;

public class book_adapter extends RecyclerView.Adapter<book_adapter.ViewHolder>{

    // TAG
    private static final String TAG = "book_adapter";
    private ArrayList<String> mNames; // store book titles
    private ArrayList<String> mImages; // store images

    private Context mContext;
    OnItemClickListener mListener;

    /** Will be deprecated later */
    private ArrayList<Recipe> mRecipes; // necessary recipe

    // constructor for the adapter
    public book_adapter(Context newcontext, ArrayList<Recipe> newRecipes){

        mRecipes = newRecipes;
        mNames = new ArrayList<String>();
        mImages = new ArrayList<String>();
        for (Recipe r : newRecipes){
            mNames.add(r.getTitle());
            mImages.add(r.getImage());
        }
        mContext = newcontext;
    }

    // add selected items to the adapter
    public void add(int position, String name, String image, Recipe recipe) {
        mNames.add(position, name);
        mImages.add(position,image);
        mRecipes.add(position, recipe);
        notifyItemInserted(position);
    }

    // remove selected item from the adapter
    public void remove(int position) {
        mNames.remove(position);
        mImages.remove(position);
        mRecipes.remove(position);
        notifyItemRemoved(position);
    }

    public Recipe getRecipe(int position){
        return mRecipes.get(position);
    }

    /** End of test */
    // interface for listener
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    // set up onclick listener
    public void setOnItemClickListener(book_adapter.OnItemClickListener listener) {
        mListener = listener;
    }

    // view holder stores view to be placed
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // hold book title, icon, and layout
        public TextView recipe_title;
        public ImageView recipe_icon;
        public View layout;

        public ViewHolder(View v, final book_adapter.OnItemClickListener listener) {
            super(v);
            layout = v;
            recipe_title = (TextView) v.findViewById(R.id.book_title);
            recipe_icon = (ImageView) v.findViewById(R.id.icon);

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

    // View Holder
    @NonNull
    @Override
    public book_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.layout_listitem, parent, false);

        // set the view's size, margins, paddings and layout parameters
        book_adapter.ViewHolder vh = new book_adapter.ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "OnBindViewHolder: created");

        final String name = mNames.get(position);

        // set name of column text
        holder.recipe_title.setText(name);
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }
}
