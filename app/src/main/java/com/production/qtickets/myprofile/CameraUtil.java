package com.production.qtickets.myprofile;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Harsh on 11/27/2017.
 */

public class CameraUtil extends Activity {
    private int  CAMERA = 2;
    private Uri picUri;
    final int CROP_PIC = 200;
    Uri extra;
    private boolean onbackclick = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            if (ContextCompat.checkSelfPermission(CameraUtil.this, android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CameraUtil.this,
                        new String[]{android.Manifest.permission.CAMERA},
                        0);
            }else {
                //Pick Image From Gallery
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                File imageFile = new File(imageFilePath);
                picUri = Uri.fromFile(imageFile); // convert path to Uri
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(takePictureIntent, CAMERA);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA) {
            if(resultCode!=0) {
                performCropcamera();

            }else {
                onBackPressed();
            }
        }else if (requestCode == CROP_PIC) {
            try {
                extra = data.getData();
                if (ContextCompat.checkSelfPermission(CameraUtil.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CameraUtil.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CameraUtil.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else {
                    try {
                        Bitmap bitmaps = MediaStore.Images.Media.getBitmap(this.getContentResolver(), extra);
                        try {
                            File f = new File(this.getCacheDir(),
                                    System.currentTimeMillis() + ".jpg");
                            f.createNewFile();

//Convert bitmap to byte array
                            Bitmap bitmap = bitmaps;
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                onBackPressed();
            }
        }
    }
    private void performCropcamera() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
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
            startActivityForResult(cropIntent, CROP_PIC);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {

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
                        Bitmap bitmaps = MediaStore.Images.Media.getBitmap(this.getContentResolver(), extra);
                        try {
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Set The Bitmap Data To ImageView


                    return;
                }
            }
            break;
            case 0:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                String imageFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/picture.jpg";
                File imageFile = new File(imageFilePath);
                picUri = Uri.fromFile(imageFile); // convert path to Uri
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                startActivityForResult(takePictureIntent, CAMERA);
                }else {
                    onBackPressed();
                }
        }

    }
    @Override
    public void onBackPressed(){
        onbackclick = false;
        super.onBackPressed();
    }

}
