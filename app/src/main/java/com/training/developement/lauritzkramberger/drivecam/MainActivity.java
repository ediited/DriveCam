package com.training.developement.lauritzkramberger.drivecam;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static String logtag = "bisher Nix";
    private static int TAKE_PICTURE = 1;
    private Uri image_uri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cameraButton = (Button)findViewById(R.id.button_camera);
        cameraButton.setOnClickListener(cameraListener);
    }
    private View.OnClickListener cameraListener = new View.OnClickListener() {
        public void onClick(View v) {
            takePhoto(v);
        }
    };
    private void takePhoto(View v){
        Intent intent = new Intent("android.media.action.IMAGECAPTURE");
        File photo = new  File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"pictures.jps");
        image_uri = Uri.fromFile(photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(intent,TAKE_PICTURE);


    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode,resultCode,intent);
        if(resultCode == Activity.RESULT_OK){

            Uri selectedImage = image_uri;
            getContentResolver().notifyChange(selectedImage,null);

            ImageView imageView =(ImageView)findViewById(R.id.bild);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;

            try{
                bitmap = MediaStore.Images.Media.getBitmap(cr,selectedImage);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this,"worked",Toast.LENGTH_LONG).show();

            }
            catch(Exception e){
                Log.e(logtag,e.toString());
            }
        }


    }

}

