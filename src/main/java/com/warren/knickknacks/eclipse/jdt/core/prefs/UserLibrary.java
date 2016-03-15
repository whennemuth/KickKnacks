package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class UserLibrary {
	
	public static final String ECLIPSE_SETTINGS_FILE = 
			".metadata/.plugins/org.eclipse.core.runtime/.settings/org.eclipse.jdt.core.prefs";
	
	private List<File> libEntries = new ArrayList<File>();
//	private File workspace;
//	private String name;
	private UserLibraryParms parms;
	
	public static UserLibrary getInstanceForJars(UserLibraryParms parms) {
		parms.setRegex(".*\\.jar");
		return getInstance(parms);
	}
	
	public static UserLibrary getInstanceForSourceJars(UserLibraryParms parms) {
		parms.setRegex(".*\\-((source)|(sources))\\.jar");
		return getInstance(parms);
	}
		
	public static UserLibrary getInstance(final UserLibraryParms parms) {
		if(!parms.isValid()) 
			throw new IllegalArgumentException(parms.getValidationMessage());
		
		UserLibrary lib = new UserLibrary();
		lib.parms = parms;
		lib.build(parms.getRootDir(), new FileFilter(){
			public boolean accept(File pathname) {
				if(parms.getIncludeSubDirs() && pathname.isDirectory())
					return true;
				Matcher matcher = parms.getMatcher(pathname.getAbsolutePath());
				if(matcher != null)
					return matcher.matches();
				return true;
			}});
		return lib;
	}

	private UserLibrary() { /* Restrict default constructor */ }
	
	private void build(File dir, FileFilter filter) {
		for(File f : dir.listFiles(filter)) {
			if(f.isDirectory()) {
				build(f, filter);
			}
			else {
				libEntries.add(f);
			}
		}
	}
	
	/**
	 * Build the pathname for the eclipse user library settings file
	 * 
	 * @param libName
	 * @return
	 */
	private String getPreferencesPathname() {
		String prefsPathname = parms.getWorkspaceDir().getAbsolutePath();
		if(!(prefsPathname.endsWith("\\") || prefsPathname.endsWith("/"))) {
			prefsPathname += "/";
		}
		return prefsPathname += ECLIPSE_SETTINGS_FILE;
	}
	
	/**
	 * Create a UserLibrary object and save its string output to the eclipse user library settings file
	 * @param libName
	 * @throws Exception
	 */
	public void save() throws Exception {
		UserLibraryEntry entry = new UserLibraryEntry(libEntries);
		PrefsFile prefsFile = new PrefsFile(getPreferencesPathname());
		String lineItem = entry.getPrefsFileLine(parms.getLibName());
		if(lineItem.isEmpty()) 
			return;
		prefsFile.addEntry(entry.getPrefsFileLine(parms.getLibName()));
	}

	public String getPreview() throws Exception {
		UserLibraryEntry entry = new UserLibraryEntry(libEntries);
		PrefsFile prefsFile = new PrefsFile(getPreferencesPathname());
		String lineItem = entry.getPrefsFileLine(parms.getLibName());
		if(lineItem.isEmpty()) 
			return "[NO CONTENT FOUND TO COMPOSE A LIBRARY FROM]";
		String preview = prefsFile.previewAddEntry(lineItem);
		return preview;
	}
	
	public void preview(OutputStream out) throws Exception {
		String preview = getPreview();
		out.write(preview.getBytes());
		out.flush();
	}
	
	public void preview() throws Exception {
		preview(System.out);
	}
	
//	public String getName() {
//		return name;
//	}
	
	public List<File> getLibEntries() {
		return libEntries;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("START!");
		
		UserLibraryParms parms = new UserLibraryParms();
		parms.setWorkspaceDir("C:/whennemuth/workspaces/kuali_workspace");
		parms.setRootDir("C:/Users/wrh/.m2/repository/org/kuali/rice");
		parms.setLibName("RICE_SOURCE_LIB_2.5.3.1509.0002");
		parms.setRegex(".*2\\.5\\.3\\.1509\\.0002\\-kualico\\-source(s)?.jar");
		parms.setIncludeSubDirs(true);
		
		UserLibrary lib = getInstance(parms);
		
		for(File f : lib.getLibEntries()) {
			System.out.println(f.getAbsolutePath());
		}
		
		if(true)
			lib.save();
		else
			lib.preview();
		
		System.out.println("FINISH!");
	}

}
