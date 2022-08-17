package com.dark.noteapp.data;

public class Note {
    private String titleNote;
    private String mDescriptionTv;

    public String getTitleNote() {
        return titleNote;
    }

    public String getmDescriptionTv() {
        return mDescriptionTv;
    }

    public Note(String titleNote, String textNot) {
        this.titleNote = titleNote;
        this.mDescriptionTv = textNot;
    }
}
