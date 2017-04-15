package com.example.wen.wenbook.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wen on 2017/4/15.
 */

public class SearchGankBean implements Serializable {
    private int count;
    private String error;
    private List<SearchGank> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<SearchGank> getResults() {
        return results;
    }

    public void setResults(List<SearchGank> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
