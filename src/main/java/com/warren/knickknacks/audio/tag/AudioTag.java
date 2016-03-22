package com.warren.knickknacks.audio.tag;

import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.AbstractID3Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;

import com.warren.knickknacks.audio.AudioFile;

public abstract class AudioTag {
	protected AudioFile audioFile;
	protected Tag tag;
	
	protected String title;
	protected String artist;
	protected String album;
	protected String year;
	protected String comment;
	protected String genre;
	
	public static AudioTag getInstance(final AudioFile audioFile) throws Exception {
		
		AudioTag audioTag = null;
		switch(audioFile.getAudioFileType()) {
			case MP3:
				audioTag = new MP3AudioTag((MP3File) audioFile.getAudioFile());
				break;
			default:
				throw new UnsupportedOperationException("TODO: Not done yet");
		}
			
		audioTag.audioFile = audioFile;
		audioTag.tag = audioFile.getAudioFile().getTag();
		
		if(audioTag.tag == null) {
			switch(audioFile.getAudioFileType()) {
				case MP3:
					// MP3File mp3 = new MP3File(audioFile.getFile());
					// audioTag.tag = mp3.getID3v1Tag();
					// break;
					throw new IllegalArgumentException("No ID3 tag has been set");
				default:
					org.jaudiotagger.audio.AudioFile audio = new org.jaudiotagger.audio.AudioFile();
					audio.setFile(audioFile.getFile());
					audioTag.tag = audio.getTagOrCreateDefault();
					break;
			}
		}
		return audioTag;
	}
	
	public abstract void save() throws Exception;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("tag=").append(tag.toString());

		return builder.toString();
	}

	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
		this.tag = tag;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public AudioFile getAudioFile() {
		return audioFile;
	}
}
