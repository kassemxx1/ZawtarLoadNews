package com.example.kassem.zawtarloadnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage;
    StorageReference storageReference;

//
    String Id;
    String Title;
    String Details;
    String Time;
    String Imagename;
    String pics1;
    String pics2;
    String pics3;
    String pics4;
    String pics5;
    String video1;
    String video2;


    Map<String, Object> docData = new HashMap<>();
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

          Button  btnChoose = (Button) findViewById(R.id.btnChoose);
          Button  btnUpload = (Button) findViewById(R.id.btnUpload);
          Button uploaddata=(Button)findViewById(R.id.uploaddata) ;


        final EditText inputIda=(EditText)findViewById(R.id.inputId);
        final EditText inputTitlea=(EditText)findViewById(R.id.inputTitle);
        final EditText inputDetailsa=(EditText)findViewById(R.id.inputDetails);
        final EditText inputTimea=(EditText)findViewById(R.id.inputTime);
        final EditText inputImagenamea=(EditText)findViewById(R.id.inputImagename);
        final EditText inputPics1=(EditText)findViewById(R.id.inputPics1);
        final EditText inputPics2=(EditText)findViewById(R.id.inputPics2);
        final EditText inputPics3=(EditText)findViewById(R.id.inputPics3);
        final EditText inputPics4=(EditText)findViewById(R.id.inputPics4);
        final EditText inputPics5=(EditText)findViewById(R.id.inputPics5);
        final EditText inputVideosa1=(EditText)findViewById(R.id.inputVideos1);
        final EditText inputVideosa2=(EditText)findViewById(R.id.inputVideos2);





        //////////////////////////////////////////////////////

//    addData();

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        ////////////////////////////////////
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();



        uploaddata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> Pics=new ArrayList<>();
                ArrayList<String> Videos=new ArrayList<>();
                Id=inputIda.getText().toString();
                Title=inputTitlea.getText().toString();
                Details=inputDetailsa.getText().toString();
                Time=inputTimea.getText().toString();
                Imagename=inputImagenamea.getText().toString();
                Pics=new  ArrayList();
//                Videos = (ArrayList) inputVideosa.getText();
                docData.put("title",Title);
                docData.put("details",Details);
                docData.put("time",Time);
                docData.put("imagename",Imagename);
                pics1=inputPics1.getText().toString();
                pics2=inputPics2.getText().toString();
                pics3=inputPics3.getText().toString();
                pics4=inputPics4.getText().toString();
                pics5=inputPics5.getText().toString();

                video1=inputVideosa1.getText().toString();
                video2=inputVideosa2.getText().toString();

                if (pics1.isEmpty()) {

                    }
                    else {
                    Pics.add(pics1);
                  }
                if (pics2.isEmpty()) {

                }
                else {
                    Pics.add(pics2);
                }
                if (pics3.isEmpty()) {

                }
                else {
                    Pics.add(pics3);
                }
                if (pics4.isEmpty()) {

                }
                else {
                    Pics.add(pics4);
                }
                if (pics5.isEmpty()) {

                }
                else {
                    Pics.add(pics5);
                }
                if (Pics!=null) {
                    docData.put("pics", Pics);
                }
                if (video1.isEmpty()){

                }else {
                    Videos.add(video1);
                }
                if (video2.isEmpty()){

                }else {
                    Videos.add(video2);
                }
                if (Videos!=null){
                    docData.put("videos",Videos);
                }
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("news").document(Id).set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        inputIda.setText("");
                        inputTitlea.setText("");
                        inputDetailsa.setText("");
                        inputTimea.setText("");
                        inputImagenamea.setText("");
                        inputPics1.setText("");
                        inputPics2.setText("");
                        inputPics3.setText("");
                        inputPics4.setText("");
                        inputPics5.setText("");
                        inputVideosa1.setText("");
                        inputVideosa2.setText("");


                    }
                });
            }
        });


    }
    void addData(){



    }


    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //       imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    TextView  ttt = (TextView) findViewById(R.id.textView);
                                    ttt.setText(uri.toString());
                                }
                            });
                        }
                    });
        }
    }


}
