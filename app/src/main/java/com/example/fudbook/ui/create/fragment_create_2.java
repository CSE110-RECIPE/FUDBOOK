package com.example.fudbook.ui.create;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fudbook.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class fragment_create_2 extends Fragment {

    // initializations
    private static final int UPLOAD_IMAGE_RESULT = 1;
    private Button upload_btn;
    private ImageView image;
    private String strUri;
    Bundle args;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_2, container, false);
        args = getArguments();

        upload_btn = view.findViewById(R.id.add_photo_btn);
        image = view.findViewById(R.id.recipe_photo);

        upload_btn.setOnClickListener(upload_listener);

        return view;
    }

    private Button.OnClickListener upload_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //get photo from gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, UPLOAD_IMAGE_RESULT);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        //check whether photo was uploaded successfully from gallery
        if(requestCode == UPLOAD_IMAGE_RESULT && resultCode == RESULT_OK && data != null) {
            Uri uploadedImage = data.getData();
            //Convert uri to a string
            strUri = uploadedImage.toString();

            //Fit picture to View
            Glide.with (getContext())
                    .asBitmap()
                    .load(uploadedImage)
                    .centerCrop()
                    .into(image);

            //send to bundle
            Log.d("fragment_create_2", strUri);
            args.putString("recipe image", strUri);
        }
    }
}
