package com.example.wen.wenbook.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wen on 2017/4/12.
 *
 *  {
 "_id": "56cc6d23421aa95caa707a69",
 "createdAt": "2015-08-06T07:15:52.65Z",
 "desc": "类似Link Bubble的悬浮式操作设计",
 "publishedAt": "2015-08-07T03:57:48.45Z",
 "type": "Android",
 "url": "https://github.com/recruit-lifestyle/FloatingView",
 "used": true,
 "who": "mthli"
 }
 */

public class Gank implements Serializable {
    public String _id;
    public String createdAt;
    public String desc;
    public List<String> images;
    public String publishedAt;
    public String source;
    public String type;
    public String url;
    public String used;
    public String who;
    public boolean isHeader = false;

    public Gank(String type){
        this.type = type;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
