package com.example.wen.wenbook.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wen on 2017/4/12.
 */

public class TodayGank implements Serializable {
    public List<String> category;
    public String error;
    public Results results;

    public class Results {
        public List<Gank> Android;
        public List<Gank> iOS;
        @SerializedName("休息视频")
        public List<Gank> relax_video;
        @SerializedName("拓展资源")
        public List<Gank> expand_resources;
        @SerializedName("瞎推荐")
        public List<Gank> random_recommend;
        @SerializedName("福利")
        public List<Gank> welfare;
        @SerializedName("前端")
        public List<Gank> web;
        public List<Gank> App;

        public List<Gank> getAndroid() {
            return Android;
        }

        public void setAndroid(List<Gank> android) {
            Android = android;
        }

        public List<Gank> getiOS() {
            return iOS;
        }

        public void setiOS(List<Gank> iOS) {
            this.iOS = iOS;
        }

        public List<Gank> getRelax_video() {
            return relax_video;
        }

        public void setRelax_video(List<Gank> relax_video) {
            this.relax_video = relax_video;
        }

        public List<Gank> getExpand_resources() {
            return expand_resources;
        }

        public void setExpand_resources(List<Gank> expand_resources) {
            this.expand_resources = expand_resources;
        }

        public List<Gank> getRandom_recommend() {
            return random_recommend;
        }

        public void setRandom_recommend(List<Gank> random_recommend) {
            this.random_recommend = random_recommend;
        }

        public List<Gank> getWelfare() {
            return welfare;
        }

        public void setWelfare(List<Gank> welfare) {
            this.welfare = welfare;
        }

        public List<Gank> getWeb() {
            return web;
        }

        public void setWeb(List<Gank> web) {
            this.web = web;
        }

        public List<Gank> getApp() {
            return App;
        }

        public void setApp(List<Gank> app) {
            App = app;
        }

        @Override
        public String toString() {
            return new Gson().toJson(this);
        }
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}

