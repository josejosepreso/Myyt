class Configuration {
		public static final int N_MAX_RESULTS = 10;
		public static final String YT_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&q=";
		public static final String YT_URL_1 = String.format("&type=video&maxResults=%d&key=", N_MAX_RESULTS);
		public static final String YT_KEY_PATH = "/home/jose/api/youtube";
		public static final String YT = "https://youtu.be/";
		public static final String VIDEO_INFO_REGEX = ".*videoId.*|.*title.*|.*channelTitle.*|.*publishTime.*";
		public static final String NIGHTZ = "BjJ_fH4uzRU";
		public static final String VIDEO_CACHE_PATH = "/home/jose/.cache/Myyt/";
		public static final String NOT_FOUND = "not_found_cached_video";
}
