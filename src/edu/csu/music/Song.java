package edu.csu.music;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Song {

	  private String fileName;
	  private String title;
	  private int duration;
	  private String singer;
	  private String album;
	  private String type;
	  private String size;
	  private String fileUrl;

	  public String getFileName() {
	    return fileName;
	  }

	  public void setFileName(String fileName) {
	    this.fileName = fileName;
	  }

	  public String getTitle() {
	    return title;
	  }

	  public void setTitle(String title) {
	    this.title = title;
	  }

	  public String getDuration() {
		  Date date = new Date();
		  SimpleDateFormat sdf = new SimpleDateFormat("mm£ºss");
		  date.setTime(duration);
		  String str = sdf.format(date);
	    return str;
	  }

	  public void setDuration(int duration) {
	    this.duration = duration;
	  }

	  public String getSinger() {
	    return singer;
	  }

	  public void setSinger(String singer) {
	    this.singer = singer;
	  }

	  public String getAlbum() {
	    return album;
	  }

	  public void setAlbum(String album) {
	    this.album = album;
	  }
	  
	  public String getType() {
	    return type;
	  }

	  public void setType(String type) {
	    this.type = type;
	  }

	  public String getSize() {
	    return size;
	  }

	  public void setSize(String size) {
	    this.size = size;
	  }

	  public String getFileUrl() {
	    return fileUrl;
	  }

	  public void setFileUrl(String fileUrl) {
	    this.fileUrl = fileUrl;
	  }

	  public Song() {
	    super();
	  }

	  public Song(String fileName, String title, int duration, String singer,
	      String album, String year, String type, String size, String fileUrl) {
	    super();
	    this.fileName = fileName;
	    this.title = title;
	    this.duration = duration;
	    this.singer = singer;
	    this.album = album;
	    this.type = type;
	    this.size = size;
	    this.fileUrl = fileUrl;
	  }

	  @Override
	  public String toString() {
	    return "Song [fileName=" + fileName + ", title=" + title
	        + ", duration=" + duration + ", singer=" + singer + ", album="
	        + album +", type=" + type + ", size="
	        + size + ", fileUrl=" + fileUrl + "]";
	  }

	}