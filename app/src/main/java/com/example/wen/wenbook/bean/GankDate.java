package com.example.wen.wenbook.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wen on 2017/4/13.
 */

public class GankDate implements Serializable {
    public String error;
    public List<String> results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }
}
