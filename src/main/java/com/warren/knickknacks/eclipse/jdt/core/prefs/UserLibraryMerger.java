package com.warren.knickknacks.eclipse.jdt.core.prefs;

import java.io.File;
import java.util.Arrays;

/**
 * A user library can be related to another user library.
 * For example, user library A constitutes a collection of jars, and user library B constitutes a collection of source jars.
 * A relates to be in that each jar in A has a matching jar in B, where jar in B contains the source code for the jar in A.
 * This class "merges" one user library into another such that a pairing is set reflecting the relationship.
 * Before the merge, the following would be seen:
 *     A.getLibMembers().get(n).getJar() = [The main jar]
 *     A.getLibMembers().get(n).getSourceJar() = null
 * When the merge is complete, the following would be true of user library A:
 *     A.getLibMembers().get(n).getJar() = [The main jar]
 *     A.getLibMembers().get(n).getSourceJar() = [The corresponding source jar]
 *     
 * @author wrh
 *
 */
public abstract class UserLibraryMerger {

	public static enum ItemType {
		JAR, SRC_JAR, JAVADOC, NATIVE_LIB, ACCESS_RULES
	};
	
	private UserLibrary mainLib;
	private ItemType itemType;
	
	public abstract boolean isMatch(UserLibraryMember main, UserLibraryMember merge);
	
	public static UserLibraryMerger getInstance(UserLibrary mainLib, ItemType itemType) {
		UserLibraryMerger merger = null;
		if(itemType != null) {
			switch(itemType) {
				case JAR: /* this corresponds to mainLib, so ignore it. */ break;
				case SRC_JAR:
					merger = new UserLibraryMerger() {
						@Override public boolean isMatch(UserLibraryMember jarLibMbr, UserLibraryMember srcLibMbr) {
							String jarname = jarLibMbr.getJar().getName();
							int instr = jarname.lastIndexOf(".");
							if(instr >=0) {
								jarname = jarname.substring(0, instr);
							}
							return srcLibMbr.getJar().getName().startsWith(jarname);
						}};
					break;
				case JAVADOC:
					// TODO: Come up with criterion for a javadoc jar as identified with the jar it documents by name.
					break;
				case NATIVE_LIB:
					// TODO: Come up with criterion for a native library as identified with the jar it is a library for.
					break;
				case ACCESS_RULES:
					// TODO: May not apply. Figure this out.
					break;
			}
		}
		else {
			merger = new UserLibraryMerger() {
				@Override public boolean isMatch(UserLibraryMember jarLibMbr, UserLibraryMember srcLibMbr) {
					return false; // Default implementation. Will never get used.
				}};
		}
		merger.mainLib = mainLib;
		merger.itemType = itemType;
		
		return merger;
	}
	
	public void merge(UserLibrary libToMerge) {
		for(UserLibraryMember main : mainLib.getLibMembers()) {
			for(UserLibraryMember merge : libToMerge.getLibMembers()) {
				if(isMatch(main, merge)) {
					switch(itemType) {
						case JAR: /* this corresponds to mainLib, so ignore it. */ break;
						case SRC_JAR:
							if(isMergeable(main.getSourceJar(), merge.getJar())) {
								main.setSourceJar(merge.getJar());
							}
							break;
						case JAVADOC: 
							if(isMergeable(main.getJavadoc(), merge.getJar())) {
								main.setJavadoc(merge.getJar());
							} 
							break;
						case NATIVE_LIB: 
							if(isMergeable(main.getNativeLibrary(), merge.getJar())) {
								main.setNativeLibrary(merge.getJar());
							}
							break;
						case ACCESS_RULES: 
							// TODO: This is an arbitrary assignment. Not sure how access rules will be determined yet.
							main.setAccessRules(Arrays.asList(new String[] { merge.getJar().getAbsolutePath() })); 
							break;
					}
				}
			}
		}
	}

	/**
	 * The existing user library member may already have had an item - like source jar, javadoc, etc. - set.
	 * But this would have been based off that item starting with the same name as the main jar (minus the file extension).
	 * It is possible that the new candidate fits this same criteria with a shorter name, which would make it a more
	 * accurate match. In this case, the candidate replaces the existing (or null) and is therefore "mergeable".
	 *  
	 * @param existing
	 * @param anotherCandidate
	 * @return
	 */
	private boolean isMergeable(File existing, File anotherCandidate) {
		return existing == null || existing.getName().length() > anotherCandidate.getName().length();
	}
	
	public UserLibrary getMergedLib() {
		return mainLib;
	}

}
