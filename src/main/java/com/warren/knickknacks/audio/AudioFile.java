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
	}

	public AudioTag getTag() {
		try {
			if(audioTag == null)
				audioTag = AudioTag.getInstance(this);
			return audioTag;
		} catch (Exception e) {
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
		audioFile.commit();
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
		File f = new File("C:/myNonTechnicalStuff/multimedia/Audio/lectures/01 Science of Survival/08 Chart of Human Evaluation, Part 1/19. CD Track 19.mp3");
		AudioFile audioFile = new AudioFile(f);
		System.out.println(audioFile);
		System.out.println(audioFile.audioFile.displayStructureAsPlainText());
	}
}
