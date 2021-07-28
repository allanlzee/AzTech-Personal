package com.allan.lin.zhou.scheduler.camera;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.allan.lin.zhou.scheduler.R;
import com.allan.lin.zhou.scheduler.Utilities;
import com.allan.lin.zhou.scheduler.databinding.CameraReminderActivityBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.allan.lin.zhou.scheduler.Navigation.backToHome;

public class CameraReminder extends AppCompatActivity {

    private Toolbar toolbar;
    private CameraReminderActivityBinding binding;

    private final int CAMERA_REMINDER_ID = 20;
    private String currentPhotoPath = "";

    private File output = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_reminder_activity);

        binding = CameraReminderActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.homeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                backToHome(view, CameraReminder.this);
            }
        });

        if (ContextCompat.checkSelfPermission(CameraReminder.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraReminder.this, new String[] {
                    Manifest.permission.CAMERA
            }, 100);
        }

        if (ContextCompat.checkSelfPermission(CameraReminder.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraReminder.this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 101);
        }

        if (ContextCompat.checkSelfPermission(CameraReminder.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraReminder.this, new String[] {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 102);
        }

        if (ContextCompat.checkSelfPermission(CameraReminder.this,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(CameraReminder.this, new String[] {
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE
            }, 103);
        }

        binding.openCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    startActivityForResult(cameraIntent, CAMERA_REMINDER_ID);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(CameraReminder.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                /* File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);

                output = new File(dir, "AzTechReminders.jpeg");
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));

                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;

                    try {
                        photoFile = createImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(CameraReminder.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(CameraReminder.this, "com.allan.lin.zhou.scheduler", photoFile);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                        try {
                            startActivityForResult(cameraIntent, CAMERA_REMINDER_ID);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(CameraReminder.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                } */

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
        if (requestCode == CAMERA_REMINDER_ID) {
            if (resultCode == RESULT_OK) {
                /* Intent action = new Intent(Intent.ACTION_VIEW);
                action.setDataAndType(Uri.fromFile(output), "image/jpeg");
                startActivity(action); */

                Bundle extras = data.getExtras();
                Bitmap capture = (Bitmap) extras.get("data");

                Utilities.picReminders++;

                switch (Utilities.picReminders) {
                    case 1:
                        binding.image1.setImageBitmap(capture);
                        break;

                    case 2:
                        binding.image2.setImageBitmap(capture);
                        break;

                    case 3:
                        binding.image3.setImageBitmap(capture);
                        break;

                    case 4:
                        binding.image4.setImageBitmap(capture);
                        break;

                    default:
                        Toast.makeText(CameraReminder.this, "Too Many Reminders", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        if (Environment.DIRECTORY_DCIM != null) {
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
}