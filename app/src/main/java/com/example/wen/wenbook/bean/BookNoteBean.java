package com.example.wen.wenbook.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 图书笔记bean
 * Created by wen on 2017/3/27.
 */

public class BookNoteBean extends DataSupport implements Serializable {

    private String mNoteId;

    private String isbn;

    private Date mDate;

    private List<String> noteContent;

    public BookNoteBean() {
        this(UUID.randomUUID().toString());
    }


    public BookNoteBean(String noteId) {
        mNoteId = noteId;
        mDate = new Date();
    }

    public String getNoteId() {
        return mNoteId;
    }

    public void setNoteId(String noteId) {
        mNoteId = noteId;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public List<String> getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(List<String> noteContent) {
        this.noteContent = noteContent;
    }
}
