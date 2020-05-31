package com.example.fudbook.ui.bookshelf;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fudbook.R;
import com.example.fudbook.objects.Recipe;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class book_adapter extends RecyclerView.Adapter<book_adapter.ViewHolder>{

    // TAG
    private static final String TAG = "book_adapter";
    private ArrayList<String> mNames; // store book titles
    private ArrayList<String> mImages; // store images

    private Context mContext;
    OnItemClickListener mListener;
    OnItemClickListener mListener2;

    /** Will be deprecated later */
    private ArrayList<Recipe> mRecipes; // necessary recipe

    // constructor for the adapter
    public book_adapter(Context newcontext, ArrayList<Recipe> newRecipes){
        mRecipes = newRecipes;
        mContext = newcontext;
    }

    // add selected items to the adapter
    public void add(int position, Recipe recipe) {
        mRecipes.add(position, recipe);
        notifyItemInserted(position);
    }

    // remove selected item from the adapter
    public void remove(int position) {
        mRecipes.remove(position);
        notifyItemRemoved(position);
    }

    public Recipe getRecipe(int position){
        return mRecipes.get(position);
    }

    // interface for listener
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onImageButtonClick(int position);
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
        public AppCompatImageButton remove_button;
        public View layout;
        public OnItemClickListener adapter_listener;

        public ViewHolder(View v, final book_adapter.OnItemClickListener listener) {
            super(v);
            layout = v;
            recipe_title = (TextView) v.findViewById(R.id.book_title);
            recipe_icon = (ImageView) v.findViewById(R.id.icon);
            remove_button = (AppCompatImageButton) v.findViewById(R.id.remove_btn);

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

            remove_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onImageButtonClick(position);
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

        View v = inflater.inflate(R.layout.layout_recipeitem, parent, false);

        // set the view's size, margins, paddings and layout parameters
        book_adapter.ViewHolder vh = new book_adapter.ViewHolder(v, mListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "OnBindViewHolder: created");

        final String name = mRecipes.get(position).getTitle();

        // set name of column text
        holder.recipe_title.setText(name);

        final String image = mRecipes.get(position).getImage();


        System.out.println("Loading image");
        // load image
        Picasso.get().load(image)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .centerCrop()
                .into(holder.recipe_icon);
    }


    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
}
