package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single member of an eclipse user library - a jar file, for example.
 * @author wrh
 *
 */
public class UserLibraryMember {

	private File jar;
	private File sourceJar;
	private File javadoc;
	private File nativeLibrary;
	private List<String> accessRules = new ArrayList<String>();
	private File directory;
	private UserLibrary parent;
	
	public File getJar() {
		return jar;
	}
	public void setJar(File jar) {
		this.jar = jar;
	}
	public File getSourceJar() {
		return sourceJar;
	}
	public void setSourceJar(File sourceJar) {
		this.sourceJar = sourceJar;
	}
	public File getJavadoc() {
		return javadoc;
	}
	public void setJavadoc(File javadoc) {
		this.javadoc = javadoc;
	}
	public File getNativeLibrary() {
		return nativeLibrary;
	}
	public void setNativeLibrary(File nativeLibrary) {
		this.nativeLibrary = nativeLibrary;
	}
	public List<String> getAccessRules() {
		return accessRules;
	}
	public void setAccessRules(List<String> accessRules) {
		this.accessRules = accessRules;
	}
	public File getDirectory() {
		return directory;
	}
	public void setDirectory(File directory) {
		this.directory = directory;
	}
	public UserLibrary getParent() {
		return parent;
	}
	public void setParent(UserLibrary parent) {
		this.parent = parent;
	}
}
