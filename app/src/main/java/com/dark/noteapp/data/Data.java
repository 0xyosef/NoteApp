package com.dark.noteapp.data;

import android.widget.TextView;

public class Data {
    private String titleNote;
    private String textNot;

    public String getTitleNote() {
        return titleNote;
    }

    public String getTextNot() {
        return textNot;
    }

    public Data(String titleNote, String textNot) {
        this.titleNote = titleNote;
        this.textNot = textNot;
    }
}
