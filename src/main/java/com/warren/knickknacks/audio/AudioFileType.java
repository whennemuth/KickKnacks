package com.warren.knickknacks.audio;

import java.io.File;

public enum AudioFileType {

	MP3(org.jaudiotagger.audio.mp3.MP3File.class),
	MP4(org.jaudiotagger.audio.AudioFile.class),
	OGG(org.jaudiotagger.audio.AudioFile.class),
	FLAC(org.jaudiotagger.audio.AudioFile.class),
	WMA(org.jaudiotagger.audio.AudioFile.class);
	
	private Class clazz;
	private AudioFileType(Class clazz) {
		this.clazz = clazz;
	}
	
	public static AudioFileType getType(File f) {
		String ext = f.getName().split("\\.")[f.getName().split("\\.").length-1].toUpperCase();
		AudioFileType t = AudioFileType.valueOf(ext);
		return t;
	}
	
	public Class getType() {
		return clazz;
	}
}
