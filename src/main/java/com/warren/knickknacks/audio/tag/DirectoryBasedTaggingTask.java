package com.warren.knickknacks.audio.tag;

import java.io.File;
import java.io.FileFilter;

import com.warren.knickknacks.audio.AudioFile;
import com.warren.knickknacks.audio.task.AbstractAudioTask;

public class DirectoryBasedTaggingTask extends AbstractAudioTask {

	@Override
	public void performTask(AudioFile audioFile) throws Exception {
		AudioTag tag = audioFile.getTag();
		String artist = audioFile.getFile().getParentFile().getParentFile().getName();
		String title = audioFile.getFile().getParentFile().getName();
		tag.setArtist(artist);
		tag.setTitle(title);
		tag.save();
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
}
