class Video {
    private int id;
    private String videoId;
    private String title;
    private String channel;
    private String date;
    
    public Video(int id, String videoId, String title, String channel, String date) {
	this.id = id;
	this.videoId = videoId;
	this.title = title;
	this.channel = channel;
	this.date = date;
    }

    public String getVideoId() {
	return this.videoId;
    }
    
    public String toString() {
	return String.format("%d: %s (%s, %s)", this.id, this.title, this.channel, this.date);
    }
}
