package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.File;
import java.util.List;

import com.warren.knickknacks.Util;

public class UserLibraryString {

	private List<UserLibraryMember> members;
	
	public UserLibraryString(List<UserLibraryMember> members) {
		this.members = members;
	}

	/**
	 * Create a line item to be inserted into the eclipse meta data file that contains user-defined libraries 
	 * that have been added for the workspace.
	 * 
	 * @param libName
	 * @return
	 */
	public String getPrefsFileLine(String libName) {
		if(members == null || members.isEmpty()) {
			return "";
		}
		
		StringBuilder s = new StringBuilder("org.eclipse.jdt.core.userLibrary.")
				.append(libName)
				.append("=<?xml version\\=\"1.0\" encoding\\=\"UTF-8\"?>\\r\\n")
				.append("<userlibrary systemlibrary\\=\"false\" version\\=\"2\">");
		
		for(UserLibraryMember m : members) {
			s.append("\\r\\n\\t<archive path\\=\"");
			String path = getPath(m.getJar());
			s.append(path);
			s.append("\"/>");
		}
		
		s.append("\\r\\n</userlibrary>\\r\\n");
		return s.toString();
	}

	/**
	 * Get the pathname portion of a single item for the user-library entry (ie: a jar file)
	 * @param f
	 * @return
	 */
	private String getPath(File f) {
		String path = f.getAbsolutePath();
		String[] parts = path.split("[\\\\/:]+");
		StringBuilder s = new StringBuilder();
		s.append(parts[0].toUpperCase()).append("\\:");
		String reassembled = Util.concat(1, parts.length-1, "/", parts);
		s.append("/").append(reassembled);
		return s.toString();
	}

}
