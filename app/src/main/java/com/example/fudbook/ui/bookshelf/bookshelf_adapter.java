package com.example.fudbook.ui.bookshelf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.fudbook.R;

import java.util.List;


// optimize bookshelf adapter for bookshelf
public class bookshelf_adapter extends RecyclerView.Adapter<bookshelf_adapter.ViewHolder> {

    private List<String> names; // store bookshelf titles
    private List<String> images; // store images

    // view holder stores book_title, book_icon
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // hold book title, icon, and layout
        public TextView book_title;
        public ImageView book_icon;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            book_title = (TextView) v.findViewById(R.id.book_title);
            book_icon = (ImageView) v.findViewById(R.id.icon);
        }
    }

    // New item
    public void add(int position, String name, String image) {
        names.add(position, name);
        images.add(position,image);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        names.remove(position);
        notifyItemRemoved(position);
    }

    public bookshelf_adapter(List<String> myDataset) {
        names = myDataset;
    }

    // creates cell
    @Override
    public bookshelf_adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View v = inflater.inflate(R.layout.layout_listitem, parent, false);

        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = names.get(position);
        holder.book_title.setText(name);
        holder.book_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        holder.book_title.setText("Footer: " + name);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

}