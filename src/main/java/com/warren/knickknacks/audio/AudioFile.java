package com.warren.knickknacks.audio;

import java.io.File;

import com.warren.knickknacks.audio.tag.AudioTag;

import org.jaudiotagger.audio.mp3.MP3File;

public class AudioFile {

	private File file;
	private org.jaudiotagger.audio.AudioFile audioFile;
	private AudioFileType audioFileType;
	private AudioTag audioTag;

	public AudioFile(File file) throws Exception {
		this.file = file;
		audioFileType = AudioFileType.getType(file);
		switch(audioFileType) {
			case MP3:
				this.audioFile = new MP3File(file);
				break;
			default:
				// TODO: There are many other types supported by the jaudiotagger library.
				this.audioFile = (org.jaudiotagger.audio.AudioFile) audioFileType.getType().newInstance();
				this.audioFile.setFile(file);
		}
		getTag();
	}

	public AudioTag getTag() {
		try {
			if(audioTag == null)
				audioTag = AudioTag.getInstance(this);
			return audioTag;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public File getFile() {
		return file;
	}

	public AudioFileType getAudioFileType() {
		return audioFileType;
	}
	
	public void save() throws Exception {
		if(audioTag != null) {
			audioTag.save();
			audioFile.setTag(audioTag.getTag());
		}
		audioFile.commit();
	}

	public org.jaudiotagger.audio.AudioFile getAudioFile() {
		return audioFile;
	}

	public void setTitle(String title) {
		getTag().setTitle(title);
	}
	public void setArtist(String artist) {
		getTag().setArtist(artist);
	}
	public void setAlbum(String album) {
		getTag().setAlbum(album);
	}
	public void setYear(String year) {
		getTag().setYear(year);
	}
	public void setComment(String comment) {
		getTag().setComment(comment);
	}
	public void setGenre(String genre) {
		getTag().setGenre(genre);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AudioFile [file=").append(file.getAbsolutePath())
				.append("\r\n")
				.append("header=").append(audioFile.getAudioHeader())
				.append("\r\n")
				.append("getTag()=").append(getTag())
				.append("]");
		return builder.toString();
	}

	public static void main(String[] args) throws Exception {
		final String filepath = "C:/path/to/mp3/track1.mp3";
		System.out.println("Starting for \"" + filepath + "\"");
		File f = new File(filepath);
		AudioFile audioFile = new AudioFile(f);
//		System.out.println(audioFile);
//		System.out.println(audioFile.audioFile.displayStructureAsPlainText());
		
//		audioFile.setAlbum("ALBUM NAME");
		audioFile.setArtist("ARTIST NAME");
//		audioFile.setComment("MY COMMENT");
//		audioFile.setGenre("GENRE NAME");
//		audioFile.setTitle("TITLE ENTRY");
//		audioFile.setYear("YEAR ENTRY");
		audioFile.save();
		
		System.out.println("Finished");
	}
}
