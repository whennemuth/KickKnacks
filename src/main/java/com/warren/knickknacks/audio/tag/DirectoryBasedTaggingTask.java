package com.warren.knickknacks.audio.tag;

import java.io.File;
import java.io.FileFilter;

import com.warren.knickknacks.audio.AudioFile;
import com.warren.knickknacks.audio.task.AbstractAudioTask;

public class DirectoryBasedTaggingTask extends AbstractAudioTask {

	@Override
	public void performTask(AudioFile audioFile) throws Exception {
		String artist = audioFile.getFile().getParentFile().getParentFile().getName();
		String album = audioFile.getFile().getParentFile().getName();
		if(!artist.matches("LRH \\d+: .*")) {
			if(artist.matches("\\d+\\s+.*")) {
				artist = "LRH " + artist.split("\\s+")[0] + ": " + artist.replaceFirst("\\d+\\s+", "");
			}			
		}
		audioFile.setArtist(artist);
		audioFile.setAlbum(album);
		audioFile.save();
	}

	public void tagAll(File rootDir) throws Exception {
		super.performTaskAll(rootDir, null);
	}

	public void tagAllMp3(File rootDir, final boolean includeSubDirs) throws Exception {
		super.performTaskAll(rootDir, new FileFilter(){
			public boolean accept(File pathname) {
				return (includeSubDirs && pathname.isDirectory()) || 
						pathname.getName().toLowerCase().endsWith(".mp3");
			}});
	}
	
	public static void main(String[] args) throws Exception {
		DirectoryBasedTaggingTask task = new DirectoryBasedTaggingTask();
		File rootDir = new File("C:/directory/with/mp3/files/in/it");
		task.tagAllMp3(rootDir, true);
	}
}
