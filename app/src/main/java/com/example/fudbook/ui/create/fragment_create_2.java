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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.fudbook.CreateActivity;
import com.example.fudbook.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class fragment_create_2 extends Fragment {
    private static final int UPLOAD_IMAGE_RESULT = 1;
    private Button upload_btn;
    private ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_2, container, false);

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

            //Fit picture to View
            Glide.with (getContext())
                    .asBitmap()
                    .load(uploadedImage)
                    .centerCrop()
                    .into(image);

            //make Bitmap
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uploadedImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                //write code to send encodedImage or bitmap in bundle



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
