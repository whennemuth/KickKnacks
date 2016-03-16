package com.warren.knickknacks.eclipse.jdt.core.prefs;

public class UserLibraryMerger {

	public static enum ItemType {
		JAR, SRC_JAR, JAVADOC, NATIVE_LIB, ACCESS_RULES
	};
	
	private UserLibrary lib;
	
	public UserLibraryMerger(UserLibrary lib) {
		this.lib = lib;
	}

	public void merge(UserLibrary libToMerge, ItemType itemType) {
		switch(itemType) {
		case JAR: /* this corresponds to the lib provided by the constructor, so break */ break;
		case SRC_JAR:
			// TODO: figure out how to do this: lib.getLibMembers().get(0).setSourceJar(sourceJar);
			break;
		case JAVADOC:
			break;
		case NATIVE_LIB:
			break;
		case ACCESS_RULES:
			break;
		}
	}

	public UserLibrary getMergedLib() {
		return lib;
	}

}
