package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class UserLibrary {
	
	private List<File> libEntries = new ArrayList<File>();
	private File workspace;
	
	public static void addJarsFromDirectory(File workspace, File rootDir, final boolean includeSubdirs) {
		UserLibrary lib = new UserLibrary();
		lib.workspace = workspace;
		lib.collect(rootDir, new FileFilter(){
			public boolean accept(File pathname) {
				if(includeSubdirs && pathname.isDirectory())
					return true;
				return pathname.getName().endsWith(".jar");
			}});
		lib.build();
	}
	
	private UserLibrary() { /* Restrict default constructor */ }
	
	private void collect(File dir, FileFilter filter) {
		for(File f : dir.listFiles(filter)) {
			if(f.isDirectory()) {
				collect(f, filter);
			}
			else {
				libEntries.add(f);
			}
		}
	}
	
	private void build() {
		String prefsPathname = workspace.getAbsolutePath();
		if(!(prefsPathname.endsWith("\\") || prefsPathname.endsWith("/"))) {
			prefsPathname += "/";
		}
		prefsPathname += ".metadata/.plugins/"; // TODO: resume
	}
}
