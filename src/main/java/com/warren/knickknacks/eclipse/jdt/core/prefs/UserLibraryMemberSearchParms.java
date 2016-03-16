package com.warren.knickknacks.eclipse.jdt.core.prefs;


public class UserLibraryMemberSearchParms {

	private UserLibraryMemberItemSearchParms jarParms;
	private UserLibraryMemberItemSearchParms srcJarParms;
	private UserLibraryMemberItemSearchParms javaDocParms;
	private UserLibraryMemberItemSearchParms nativeLibraryParms;
	private UserLibraryMemberItemSearchParms accessRulesParms;
	
	public UserLibraryMemberItemSearchParms getJarParms() {
		return jarParms;
	}
	public void setJarParms(UserLibraryMemberItemSearchParms jarParms) {
		this.jarParms = jarParms;
	}
	public UserLibraryMemberItemSearchParms getSrcJarParms() {
		return srcJarParms;
	}
	public void setSrcJarParms(UserLibraryMemberItemSearchParms srcJarParms) {
		this.srcJarParms = srcJarParms;
	}
	public UserLibraryMemberItemSearchParms getJavaDocParms() {
		return javaDocParms;
	}
	public void setJavaDocParms(UserLibraryMemberItemSearchParms javaDocParms) {
		this.javaDocParms = javaDocParms;
	}
	public UserLibraryMemberItemSearchParms getNativeLibraryParms() {
		return nativeLibraryParms;
	}
	public void setNativeLibraryParms(
			UserLibraryMemberItemSearchParms nativeLibraryParms) {
		this.nativeLibraryParms = nativeLibraryParms;
	}
	public UserLibraryMemberItemSearchParms getAccessRulesParms() {
		return accessRulesParms;
	}
	public void setAccessRulesParms(
			UserLibraryMemberItemSearchParms accessRulesParms) {
		this.accessRulesParms = accessRulesParms;
	}
}
