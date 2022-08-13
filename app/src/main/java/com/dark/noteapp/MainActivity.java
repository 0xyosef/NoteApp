package com.dark.noteapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dark.noteapp.addapter.Adapter;
import com.dark.noteapp.data.Constant;
import com.dark.noteapp.data.Data;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ArrayList<Data> mItems;
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
        mItems =new ArrayList<Data>();
        mAdapter =new Adapter(mItems);
        mGridLayoutManager=new GridLayoutManager(this,2);
        mLinearLayoutManager=new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(mLinearLayoutManager);
        mRecycleView.setAdapter(mAdapter);
        setListener();
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
                //code get String from intent
            }else {
                Toast.makeText(this, R.string.didnt_add_note, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void addItem(Data data){
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