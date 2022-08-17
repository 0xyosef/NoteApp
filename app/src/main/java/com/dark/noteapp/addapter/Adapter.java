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

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    public ArrayList<Note> mItems;

    public Adapter(ArrayList<Note> mItems) {
        this.mItems = mItems;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_view_list, parent, false);
        return new AdapterViewHolder(view);
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
        public AdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mDescriptionTv =itemView.findViewById(R.id.text_view_note);
            aTitleNote=itemView.findViewById(R.id.title_note_text);
        }
    }
}
