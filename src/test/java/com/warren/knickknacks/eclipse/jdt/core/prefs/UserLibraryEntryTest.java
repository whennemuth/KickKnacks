package com.warren.knickknacks.eclipse.jdt.core.prefs;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserLibraryEntryTest extends AbstractTest{

	List<UserLibraryMember> members = new ArrayList<UserLibraryMember>();
	
	@Before
	public void setUp() {		
		addUserLibraryMemberMock("c:/mylib/lib1.jar");
		addUserLibraryMemberMock("c:/mylib/lib2.jar");
		addUserLibraryMemberMock("c:/mylib/libdir01");
	}
	
	private void addUserLibraryMemberMock(String pathname) {
		UserLibraryMember member = new UserLibraryMember();
		member.setJar(configureFile(pathname, Mockito.mock(File.class)));
		members.add(member);
	}
	
	@Test
	public void testPrefsFileLine() {
		UserLibraryString entry = new UserLibraryString(members);
		String prefsLine = entry.getPrefsFileLine("NEW_LIB");
		System.out.println(prefsLine);
		assertTrue(prefsLine.startsWith(
				"org.eclipse.jdt.core.userLibrary.NEW_LIB=<?xml version\\=\"1.0\" "
				+ "encoding\\=\"UTF-8\"?>\\r\\n<userlibrary systemlibrary\\=\"false\" version\\=\"2\">"));
		assertTrue(prefsLine.endsWith("\\r\\n</userlibrary>\\r\\n"));
		assertTrue(prefsLine.contains("<archive path\\=\"C\\:/mylib/lib1.jar\"/>"));
		assertTrue(prefsLine.contains("<archive path\\=\"C\\:/mylib/lib2.jar\"/>"));
		assertTrue(prefsLine.contains("<archive path\\=\"C\\:/mylib/libdir01\"/>"));
		int instances = findInstances("<archive[^>]+/>", prefsLine);
		assertEquals(3, instances);
		instances = findInstances("<userlibrary[^>]+>", prefsLine);
		assertEquals(1, instances);
		instances = findInstances("</userlibrary>", prefsLine);
		assertEquals(1, instances);
	}

}
