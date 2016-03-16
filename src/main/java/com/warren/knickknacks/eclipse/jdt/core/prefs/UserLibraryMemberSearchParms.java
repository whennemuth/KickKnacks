package com.warren.knickknacks.eclipse.jdt.core.prefs;

/**
 * This class represents a group of parameter sets for a single user library member.
 * Any member of a user library, like a jar file for example, has 5 separate properties offered by eclipse:
 *    1) The location of the jar
 *    2) The location of the source code jar
 *    3) The location of any native libraries
 *    4) Access rules.
 * @author wrh
 *
 */
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
