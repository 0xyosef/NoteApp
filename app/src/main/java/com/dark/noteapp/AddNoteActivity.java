package com.dark.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dark.noteapp.data.Constant;

public class AddNoteActivity extends AppCompatActivity {
    ImageButton mSelectPhotoBtn;
    ImageView mAddNewPhotoIv;
    EditText mTitleNode;
    EditText mTextNoteEdt;
    Uri mSelectedPhotoUri;
    Button mAddNoteBtn;
    String title_str;
    String text_str;
    private static final int READ_PHOTO_FROM_GALLERY_PERMISSION = 134;
    private static final int PICK_IMAGE= 131;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        mSelectPhotoBtn =findViewById(R.id.imageView_camera);
        mAddNewPhotoIv =findViewById(R.id.imageView_photo);
        mTitleNode =findViewById(R.id.title_note);
        mTextNoteEdt=findViewById(R.id.editText_note);
        mAddNoteBtn=findViewById(R.id.button_add_note);
        initListeners();
    }
    private void initListeners(){
        mSelectPhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhoto();
            }
        });
        mAddNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromEditText();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        firePhotoPickerIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            if (data != null && data.getData() != null) {
                // Get the image from data
                setSelectedPhoto(data.getData());
                getContentResolver().takePersistableUriPermission(mSelectedPhotoUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

            } else {
                Toast.makeText(this, R.string.error_selecting_photo, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setSelectedPhoto(Uri data) {
        mAddNewPhotoIv.setImageURI(data);
        mSelectedPhotoUri = data;
    }

    private void selectPhoto(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},READ_PHOTO_FROM_GALLERY_PERMISSION );
        }
        else {
            //call activity to select photo
            firePhotoPickerIntent();
        }
    }
    private void firePhotoPickerIntent() {
        //create an intent to get to address file type photo
        Intent intent =new Intent();
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        //set the type of file to get
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        //start the activity to get the file
        startActivityForResult(Intent.createChooser(intent,getString(R.string.select_picture)),PICK_IMAGE);
    }
    public void getDataFromEditText() {
        title_str =mTitleNode.getText().toString();
        text_str =mTextNoteEdt.getText().toString();
        addNote(title_str,text_str);
    }
    private void addNote(String title, String text) {
            if (title_str !=null||text_str !=null){
                Intent intent=new Intent();
                intent.putExtra(Constant.EXTRA_TITLE_URI, title_str);
                intent.putExtra(Constant.EXTRA_EDT_URI, text_str);
                setResult(RESULT_OK,intent);
                finish();
            }else
                Toast.makeText(this, R.string.write_title, Toast.LENGTH_SHORT).show();
    }
}