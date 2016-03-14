package com.warren.knickknacks.audio.task;

import java.io.File;
import java.io.FileFilter;
import java.io.OutputStream;

import com.warren.knickknacks.audio.AudioFile;
import com.warren.knickknacks.audio.tag.AudioTag;

public abstract class AbstractAudioTask {

	public void performTaskAll(File rootDir, FileFilter filter) throws Exception {
		File[] files = new File[0];
		
		if(filter == null) 
			files = rootDir.listFiles();
		else 
			files = rootDir.listFiles(filter);
		
		for(File f : files) {
			if(f.isDirectory()) {
				performTaskAll(f, filter);
			}
			else {
				AudioFile audioFile = new AudioFile(f);
				performTask(audioFile);
			}
		}
	}
	
	public abstract void performTask(AudioFile audioFile) throws Exception;
}
