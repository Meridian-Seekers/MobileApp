package com.example.finalproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PopupUpload extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 101;
    private static final int REQUEST_GALLERY_VIDEO = 103;
    private static final int REQUEST_CODE_PERMISSIONS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_upload_video);

        ImageView imageView10 = findViewById(R.id.imageView10);
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allPermissionsGranted()){
                    requestPermissionsIfNeeded();
                }else{
                    captureVideo();
                }

            }
        });

        ImageView imageView11 = findViewById(R.id.imageView11);
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!allPermissionsGranted()){
                    requestPermissionsIfNeeded();
                }else{
                    openGallery();
                }
            }
        });

        ImageView imageView12 = findViewById(R.id.imageView12);
        imageView12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupUpload.this, HomepageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void captureVideo() {
        Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (videoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(videoIntent, REQUEST_VIDEO_CAPTURE);
        } else {
            Toast.makeText(this, "No camera app found", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_GALLERY_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri videoUri = data.getData();

        if(isMp4AndUnder600MB(this, videoUri)){
            Intent intent = new Intent(PopupUpload.this, TempEditVideoActivity.class);
            intent.setData(videoUri);
            startActivity(intent);

        }else{
            Toast.makeText(this, "Video size should less than 600MB", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isMp4AndUnder600MB(Context context, Uri uri) {

        ContentResolver contentResolver = context.getContentResolver();
        String mimeType = contentResolver.getType(uri);
        if (mimeType == null || !mimeType.equals("video/mp4")) {
            return false;
        }

        long fileSize = getFileSize(context, uri);
        long sizeLimit = 600 * 1024 * 1024;
        return fileSize > 0 && fileSize < sizeLimit;
    }

    private long getFileSize(Context context, Uri uri) {
        long fileSize = -1;
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            if (sizeIndex != -1) {
                cursor.moveToFirst();
                fileSize = cursor.getLong(sizeIndex);
            }
            cursor.close();
        }

        return fileSize;
    }

    private boolean allPermissionsGranted() {
        String[] REQUIRED_PERMISSIONS_ANDROID_12 = new String[]{
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        };

        String[] REQUIRED_PERMISSIONS_ANDROID_13 = new String[]{
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {  // API level 33
            for (String permission : REQUIRED_PERMISSIONS_ANDROID_13) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
            return true;
        }else{
            for (String permission : REQUIRED_PERMISSIONS_ANDROID_12) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void requestPermissionsIfNeeded() {
        List<String> permissionsNeeded = new ArrayList<>();

        String[] REQUIRED_PERMISSIONS_ANDROID_12 = new String[]{
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        };

        String[] REQUIRED_PERMISSIONS_ANDROID_13 = new String[]{
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VIDEO,
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        };


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {  // API level 33
            for (String permission : REQUIRED_PERMISSIONS_ANDROID_13) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNeeded.add(permission);
                }
            }
        }else{
            for (String permission : REQUIRED_PERMISSIONS_ANDROID_12) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNeeded.add(permission);
                }
            }
        }

        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toArray(new String[0]), REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions not granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
