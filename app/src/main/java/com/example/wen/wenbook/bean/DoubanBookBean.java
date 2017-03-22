package com.example.wen.wenbook.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by wen on 2017/3/22.
 */

public class DoubanBookBean {
    private Integer count;
    private Integer start;
    private Integer total;
    private List<DoubanBook> books;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DoubanBook> getBooks() {
        return books;
    }

    public void setBooks(List<DoubanBook> books) {
        this.books = books;
    }

    @Override
    public String toString() {

        return new Gson().toJson(this);
    }
}
