package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class PrefsFile {

	private File prefsFile;
	
	public PrefsFile(File prefsFile) {
		this.prefsFile = prefsFile;
	}

	public void addEntry(String entry) throws Exception {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(
				new BufferedOutputStream(
					new FileOutputStream(prefsFile, true)));
			pw.println(entry);
		} 
		finally {
			if(pw != null) {
				pw.close();
			}
		}
	}

}
