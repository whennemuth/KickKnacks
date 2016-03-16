package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLibraryMemberItemSearchParms {

	private String libName;
	private String workspace;
	private File workspaceDir;
	private String rootDirPathname;
	private File rootDir;
	private Boolean includeSubDirs;	
	private Pattern pattern;
	
	public static final String VALID = "All parameters are valid";
	
	/**
	 * All parameter except for regex must be present.
	 * @return
	 */
	public String getValidationMessage() {
		StringBuilder s = new StringBuilder();
		if(workspace == null || !workspaceDir.isDirectory())
			s.append("Invalid workspace directory: " + workspace + "\r\n");
		if(rootDirPathname == null || !rootDir.isDirectory())
			s.append("Invalid root directory: " + rootDirPathname + "\r\n");
		if(includeSubDirs == null)
			s.append("Include subdirectories not specified\r\n");
		if(libName == null)
			s.append("Library name not specified\r\n");
		if(s.length() == 0) {
			s.append(VALID);
		}		
		else {
			s.insert(0, "WARNING - INVALID PARAMETERS: \r\n");
		}
		return s.toString();
	}
	
	public boolean isValid() {
		return VALID.equals(getValidationMessage());
	}
	
	public File getWorkspaceDir() {
		return workspaceDir;
	}
	public void setWorkspaceDir(String workspace) {
		this.workspace = workspace;
		this.workspaceDir = new File(workspace);
	}
	public File getRootDir() {
		return rootDir;
	}
	public void setRootDir(String root) {
		this.rootDirPathname = root;
		this.rootDir = new File(root);
	}
	public Boolean getIncludeSubDirs() {
		return includeSubDirs;
	}
	public void setIncludeSubDirs(Boolean includeSubDirs) {
		this.includeSubDirs = includeSubDirs;
	}
	public Matcher getMatcher(String s) {
		if(pattern == null)
			return null;
		return pattern.matcher(s);
	}
	public void setRegex(String regex) {
		this.pattern = Pattern.compile(regex);
	}
	public String getLibName() {
		return libName;
	}
	public void setLibName(String libName) {
		this.libName = libName;
	}
}
