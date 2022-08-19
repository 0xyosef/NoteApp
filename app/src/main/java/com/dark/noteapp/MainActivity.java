package com.dark.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dark.noteapp.addapter.Adapter;
import com.dark.noteapp.data.Constant;
import com.dark.noteapp.data.Note;
import com.dark.noteapp.listener.ItemClickListener;
import com.dark.noteapp.listener.ItemLongClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ArrayList<Note> mItems;
    private Adapter mAdapter;
    RecyclerView.LayoutManager mLinearLayoutManager;
    RecyclerView.LayoutManager mGridLayoutManager;
    private static final int ADD_NOTE=150;

    Menu mMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }
    private void init(){
        mRecycleView=findViewById(R.id.recycler_view);
        mItems =new ArrayList<Note>();
        mAdapter =new Adapter(mItems,  new ItemClickListener() {
            @Override
            public void onClickItem(int position) {
                editNote(position);
            }
         },
                new ItemLongClickListener() {
                    @Override
                    public void onLongClickItem(int position) {
                        deleteItem(position);
                    }
                });
        mGridLayoutManager=new GridLayoutManager(this,2);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mRecycleView.setAdapter(mAdapter);
        setListener();
    }

    private void editNote(int position) {
        //TODO: edit note
    }

    private void deleteItem(int position) {
        AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setMessage(R.string.delete_message).setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mItems.remove(position);
                        mAdapter.notifyItemRemoved(position);
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create();
        alertDialog.show();
    }


    private void setListener(){
        findViewById(R.id.floating_button_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddNewPhotoActivity();
            }
        });
    }
    private void startAddNewPhotoActivity(){
        Intent intent=new Intent(this,AddNoteActivity.class);
        startActivityForResult(intent,ADD_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ADD_NOTE){
            if (resultCode == RESULT_OK &&data!=null){
                String title=data.getStringExtra(Constant.EXTRA_TITLE);
                String content=data.getStringExtra(Constant.EXTRA_EDT);
                Note note=new Note(title,content);
                addItem(note);
            }else {
                Toast.makeText(this, R.string.didnt_add_note, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void addItem(Note data){
        mItems.add(data);
        mAdapter.notifyItemInserted(mItems.size()-1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //to show and not show button inside menu
        this.mMenu=menu;
        getMenuInflater().inflate(R.menu.list_menu,menu);
        //to show menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.actionGrid){
            mRecycleView.setLayoutManager(mGridLayoutManager);
            item.setVisible(false);
            mMenu.findItem(R.id.actionList).setVisible(true);
            return true;
        }else if (item.getItemId()==R.id.actionList){
            mRecycleView.setLayoutManager(mLinearLayoutManager);
            item.setVisible(false);
            mMenu.findItem(R.id.actionGrid).setVisible(true);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}