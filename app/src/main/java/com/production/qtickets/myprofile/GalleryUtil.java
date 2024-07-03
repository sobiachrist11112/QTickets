package com.production.qtickets.myprofile;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.production.qtickets.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Harsh on 11/23/2017.
 */

public class GalleryUtil extends Activity {
    private final static int RESULT_SELECT_IMAGE = 100;
    private static final String TAG = "GalleryUtil";
    private final int RESULT_CROP = 400;
    private final static int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 20;
    Uri extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            if (ContextCompat.checkSelfPermission(GalleryUtil.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GalleryUtil.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(GalleryUtil.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            } else {
                //Pick Image From Gallery
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(i, RESULT_SELECT_IMAGE);

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case RESULT_SELECT_IMAGE:
                if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        performCrop(picturePath);

                    } catch (Exception e) {
                        e.printStackTrace();
                        onBackPressed();
//                        Intent returnFromGalleryIntent = new Intent();
//                        setResult(RESULT_CANCELED, returnFromGalleryIntent);
//                        finish();
                    }
                } else {
                    Log.i(TAG, "RESULT_CANCELED");
                    onBackPressed();
//                    Intent returnFromGalleryIntent = new Intent();
//                    setResult(RESULT_CANCELED, returnFromGalleryIntent);
//                    finish();
                }
                break;
            case RESULT_CROP:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        extra = data.getData();

                        try {
                            Bitmap bitmaps = MediaStore.Images.Media.getBitmap(this.getContentResolver(), extra);
                            File f = new File(this.getCacheDir(),
                                    System.currentTimeMillis() + ".jpg");
                            f.createNewFile();

//Convert bitmap to byte array
                            Bitmap bitmap = bitmaps;
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90 /*ignored for PNG*/, bos);
                            byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(bitmapdata);
                            fos.flush();
                            fos.close();
                            String path = f.getAbsolutePath();
                            //return Image Path to the Main Activity
                            Intent returnFromGalleryIntent = new Intent();
                            returnFromGalleryIntent.putExtra("picturePath", path);
                            setResult(RESULT_OK, returnFromGalleryIntent);
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    // Set The Bitmap Data To ImageView

                }catch(Exception e){
                e.printStackTrace();
                onBackPressed();
            }
        }
                onBackPressed();
                break;

        }
    }
    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = getResources().getString(R.string.crop);
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    try {
                        //Pick Image From Gallery
                        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_SELECT_IMAGE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else {
                    onBackPressed();
                }
                // Set The Bitmap Data To ImageView




                return;
            }
        }
    }
}
