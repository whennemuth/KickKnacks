package com.warren.knickknacks.audio.tag;

import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.id3.ID3v1Tag;

import com.warren.knickknacks.audio.AudioFile;

public abstract class AudioTag {
	private AudioFile audioFile;
	private Tag tag;
	
	private String title;
	private String artist;
	private String album;
	private String year;
	private String comment;
	private String genre;
	
	private AudioTag() { /* Restrict default constructor */ }
	
	public static AudioTag getInstance(final AudioFile audioFile) throws Exception {
		AudioTag audioTag = new AudioTag(){
			@Override public void save(AudioTag audioTag) throws Exception {
				switch(audioFile.getAudioFileType()) {
					case MP3:
						ID3v1Tag mp3Tag = (ID3v1Tag) audioTag.getTag();
						mp3Tag.setAlbum(audioTag.album);
						mp3Tag.setArtist(audioTag.artist);
						mp3Tag.setComment(audioTag.comment);
						mp3Tag.setGenre(audioTag.genre);
						mp3Tag.setTitle(audioTag.title);
						mp3Tag.setYear(audioTag.year);
						//mp3Tag.createStructure();
						audioFile.save();
						break;
					default:
						throw new UnsupportedOperationException("TODO: Not done yet");
				}
			}};
			
		audioTag.audioFile = audioFile;
		
		switch(audioFile.getAudioFileType()) {
			case MP3:
				org.jaudiotagger.audio.mp3.MP3File mp3 = new org.jaudiotagger.audio.mp3.MP3File(audioFile.getFile());
				audioTag.tag = mp3.getID3v1Tag();
				break;
			default:
				org.jaudiotagger.audio.AudioFile audio = new org.jaudiotagger.audio.AudioFile();
				audio.setFile(audioFile.getFile());
				audioTag.tag = audio.getTagOrCreateDefault();
				break;
		}
		return audioTag;
	}
	
	public void save() throws Exception {
		save(this);
	}
	
	abstract void save(AudioTag tagToSave) throws Exception;
	
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
