package com.myyt.entity;

@lombok.AllArgsConstructor
@lombok.Getter
@lombok.Builder
public class Video {
    private int id;
    private String videoId;
    private String title;
    private String channel;
    private String date;

    @Override
    public String toString() {
        return String.format("%d: %s (%s, %s)", this.id, this.title, this.channel, this.date);
    }

    public String toString(Video video) {
        return video.toString();
    }
}
