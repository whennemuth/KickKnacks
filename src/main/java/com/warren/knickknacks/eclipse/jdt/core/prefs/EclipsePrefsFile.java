package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.warren.knickknacks.Util;

public class EclipsePrefsFile {

	private File prefsFile;
	
	public EclipsePrefsFile(File prefsFile) {
		this.prefsFile = prefsFile;
	}
	
	public EclipsePrefsFile(String pathname) {
		this(new File(pathname));
	}

	public void addEntry(String entry) throws Exception {
		FileOutputStream out = null;
		out = new FileOutputStream(prefsFile, true);
		addEntry(out, entry);
	}

	/**
	 * Get a look at what the contents of the eclipse user library settings file would look like if 
	 * the new entry were to be added.
	 * 
	 * @param entry
	 * @return
	 * @throws Exception
	 */
	public String previewAddEntry(String entry) throws Exception {
		FileInputStream in = new FileInputStream(prefsFile);
		String prefsFile = Util.getFileContent(in);
		String retval = null;
		
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			out.write(prefsFile.getBytes());
			addEntry(out, entry);
			retval = out.toString("UTF-8");
		} 
		finally {
			if(out != null) {
				out.close();
			}
		}
		return retval;
	}
	
	public void addEntry(OutputStream out, String entry) throws Exception {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(
				new BufferedOutputStream(out));
			pw.println(entry);
		} 
		finally {
			if(pw != null) {
				pw.close();
			}
		}
	}

	public static void main(String[] args) {
		
	}
}
