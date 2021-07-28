package com.allan.lin.zhou.scheduler.camera;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.databinding.CameraViewActivityBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class CameraView extends AppCompatActivity {

    private Toolbar toolbar;
    private CameraViewActivityBinding binding;

    // Camera Files
    private File output = null;

    // Camera IDs
    private final int cameraRequestCode = 100;
    private String currentPhotoPath = "";

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view_activity);

        binding = CameraViewActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, CameraView.this);
            }
        });

        // Camera Settings and Permissions
        if (ContextCompat.checkSelfPermission(CameraView.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraView.this, new String[] {
                    Manifest.permission.CAMERA
            }, cameraRequestCode);
        }

        if (ContextCompat.checkSelfPermission(CameraView.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraView.this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, cameraRequestCode);
        }

        if (ContextCompat.checkSelfPermission(CameraView.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraView.this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, cameraRequestCode);
        }

        /* if (ContextCompat.checkSelfPermission(CameraView.this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraView.this, new String[] {
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
            }, 103);
        } */

        binding.openCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                takePictureIntent();
            }
        });

        binding.openCameraRevised.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(CameraView.this, CameraReminder.class));
                Toast.makeText(CameraView.this, "Camera Reminders", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == cameraRequestCode) {
            // Get Image Capture
            Bundle extras = data.getExtras();
            Bitmap capture = (Bitmap) extras.get("data");

            // Set to ImageView
            binding.cameraView.setImageBitmap(capture);
            galleryAddPic();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        if (Environment.DIRECTORY_PICTURES != null) {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            currentPhotoPath = image.getAbsolutePath();
            return image;
        } else {
            return null;
        }

        // Save a file: path for use with ACTION_VIEW intents

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(CameraView.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Camera View", ex.getMessage());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.allan.lin.zhou.scheduler",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, cameraRequestCode);
            }
        }
    }
}