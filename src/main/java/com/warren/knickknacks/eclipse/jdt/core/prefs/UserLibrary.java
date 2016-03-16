package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.File;
import java.io.FileFilter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.warren.knickknacks.eclipse.jdt.core.prefs.UserLibraryMerger.ItemType;

public class UserLibrary {
	
	public static final String ECLIPSE_SETTINGS_FILE = 
			".metadata/.plugins/org.eclipse.core.runtime/.settings/org.eclipse.jdt.core.prefs";
	
	private List<UserLibraryMember> libMembers = new ArrayList<UserLibraryMember>();
	private UserLibraryMemberItemSearchParms parms;
	
	public static UserLibrary getInstance(UserLibraryMemberSearchParms parms) {
		
		if(parms.getJarParms() == null) {
			throw new IllegalArgumentException("Jar search parameters required!");
		}

		UserLibrary jarLib = getInstance(parms.getJarParms());
		UserLibraryMerger merger = new UserLibraryMerger(jarLib);
		
		if(parms.getSrcJarParms() != null) {
			UserLibrary srcJarLib = getInstance(parms.getSrcJarParms());
			merger.merge(srcJarLib, ItemType.SRC_JAR);
		}
		
		if(parms.getJavaDocParms() != null) {
			UserLibrary javaDocLib = getInstance(parms.getJavaDocParms());
			merger.merge(javaDocLib, ItemType.JAVADOC);
		}
		
		if(parms.getNativeLibraryParms() != null) {
			UserLibrary nativeLibLib = getInstance(parms.getNativeLibraryParms());
			merger.merge(nativeLibLib, ItemType.NATIVE_LIB);
		}
		
		if(parms.getAccessRulesParms() != null) {
			UserLibrary accessRulesLib = getInstance(parms.getAccessRulesParms());
			merger.merge(accessRulesLib, ItemType.ACCESS_RULES);
		}

		return merger.getMergedLib();
	}
	
	public static UserLibrary getInstanceForJars(UserLibraryMemberItemSearchParms parms) {
		parms.setRegex(".*\\.jar");
		return getInstance(parms);
	}
	
	public static UserLibrary getInstanceForSourceJars(UserLibraryMemberItemSearchParms parms) {
		parms.setRegex(".*\\-((source)|(sources)|(src)\\.jar");
		return getInstance(parms);
	}
		
	public static UserLibrary getInstance(final UserLibraryMemberItemSearchParms parms) {
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
				UserLibraryMember member = new UserLibraryMember();
				member.setJar(f);
				libMembers.add(member);
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
		UserLibraryString entry = new UserLibraryString(libMembers);
		EclipsePrefsFile prefsFile = new EclipsePrefsFile(getPreferencesPathname());
		String lineItem = entry.getPrefsFileLine(parms.getLibName());
		if(lineItem.isEmpty()) 
			return;
		prefsFile.addEntry(entry.getPrefsFileLine(parms.getLibName()));
	}

	public String getPreview() throws Exception {
		UserLibraryString entry = new UserLibraryString(libMembers);
		EclipsePrefsFile prefsFile = new EclipsePrefsFile(getPreferencesPathname());
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
	
	public List<UserLibraryMember> getLibMembers() {
		return libMembers;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("START!");
		
		UserLibraryMemberItemSearchParms parms = new UserLibraryMemberItemSearchParms();
		parms.setWorkspaceDir("C:/whennemuth/workspaces/kuali_workspace");
		parms.setRootDir("C:/Users/wrh/.m2/repository/org/kuali/rice");
		parms.setLibName("RICE_SOURCE_LIB_2.5.3.1509.0002");
		parms.setRegex(".*2\\.5\\.3\\.1509\\.0002\\-kualico\\-source(s)?.jar");
		parms.setIncludeSubDirs(true);
		
		UserLibrary lib = getInstance(parms);
		
		for(UserLibraryMember m : lib.getLibMembers()) {
			System.out.println(m.getJar().getAbsolutePath());
		}
		
		if(true)
			lib.save();
		else
			lib.preview();
		
		System.out.println("FINISH!");
	}

}
