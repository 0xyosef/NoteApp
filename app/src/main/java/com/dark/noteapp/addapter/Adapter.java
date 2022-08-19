package com.dark.noteapp.addapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dark.noteapp.R;
import com.dark.noteapp.data.Note;
import com.dark.noteapp.listener.ItemClickListener;
import com.dark.noteapp.listener.ItemLongClickListener;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    public ArrayList<Note> mItems;
    private ItemClickListener mItemClickListener;   //interface
    private ItemLongClickListener mItemLongClickListener; //interface
    public Adapter(ArrayList<Note> mItems, ItemClickListener mItemClickListener, ItemLongClickListener mItemLongClickListener) {
        this.mItems = mItems;
        this.mItemClickListener = mItemClickListener;
        this.mItemLongClickListener = mItemLongClickListener;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_list, parent, false);
        return new AdapterViewHolder(view , mItemClickListener, mItemLongClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder,  @SuppressLint("RecyclerView") int position) {
      Note data=mItems.get(position);
      holder.mDescriptionTv.setText(data.getmDescriptionTv());
      holder.aTitleNote.setText(data.getTitleNote());
      holder.position=position;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    static class AdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView aTitleNote;
        private TextView mDescriptionTv;
        private int position;
        public AdapterViewHolder(@NonNull View itemView ,final ItemClickListener mItemClickListener,final ItemLongClickListener mItemLongClickListener) {
            super(itemView);
            mDescriptionTv =itemView.findViewById(R.id.text_view_note);
            aTitleNote=itemView.findViewById(R.id.title_note_text);
            initItemClickListener(mItemClickListener,mItemLongClickListener);

        }
        private void initItemClickListener(final ItemClickListener mItemClickListener,final ItemLongClickListener mItemLongClickListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClickItem(position);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.onLongClickItem(position);
                    return true;
                }
            });
        }
    }
}
