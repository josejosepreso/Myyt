package com.myyt.entity;

public class Video {
    private int id;
    private String videoId;
    private String title;
    private String channel;
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%d: %s (%s, %s)", this.id, this.title, this.channel, this.date);
    }

    public String toString(Video video) {
        return video.toString();
    }

    public static Video.Builder builder() {
        return new Video.Builder();
    }

    public static class Builder {
        private int id;
        private String videoId;
        private String title;
        private String channel;
        private String date;

        public Video.Builder id(int id) {
            this.id = id;
            return this;
        }

        public Video.Builder videoId(String videoId) {
            this.videoId = videoId;
            return this;
        }

        public Video.Builder title(String title) {
            this.title = title;
            return this;
        }

        public Video.Builder channel(String channel) {
            this.channel = channel;
            return this;
        }

        public Video.Builder date(String date) {
            this.date = date;
            return this;
        }

        public Video build() {
            Video video = new Video();
            video.setId(this.id);
            video.setVideoId(this.videoId);
            video.setTitle(this.title);
            video.setChannel(this.channel);
            video.setDate(this.date);
            return video;
        }
    }
}