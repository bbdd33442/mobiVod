package edu.csu.music;

public class MusicListVO {
	
	private String music_id;
	private String music_name;
    private String music_cover_url;
    private String music_url;
    private String music_singer;
    
	public String getMusic_id() {
		return music_id;
	}
	public void setMusic_id(String movie_id) {
		this.music_id = movie_id;
	}
	public String getMusic_title() {
		return music_name;
	}
	public void setMusic_title(String movie_title) {
		this.music_name = movie_title;
	}
	public String getMusic_pic() {
		return music_cover_url;
	}
	public void setMusic_pic(String movie_pic) {
		this.music_cover_url = movie_pic;
	}
	public String getMusic_time() {
		return music_url;
	}
	public void setMusic_time(String music_artist) {
		this.music_url = music_artist;
	}
	public String getMusic_artist() {
		return music_singer;
	}
	public void setMusic_artist(String music_artist) {
		this.music_singer = music_artist;
	}
	
}
