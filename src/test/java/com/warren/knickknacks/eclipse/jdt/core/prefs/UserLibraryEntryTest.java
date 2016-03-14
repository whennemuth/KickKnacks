package com.warren.knickknacks.eclipse.jdt.core.prefs;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class UserLibraryEntryTest extends AbstractTest{

	List<File> items = new ArrayList<File>();
	
	@Before
	public void setUp() {		
		items.add(configureFile("c:/mylib/lib1.jar", Mockito.mock(File.class)));
		items.add(configureFile("c:/mylib/lib2.jar", Mockito.mock(File.class)));
		items.add(configureDirectory("c:/mylib/libdir01", Mockito.mock(File.class)));
	}
	
	@Test
	public void testPrefsFileLine() {
		UserLibraryEntry entry = new UserLibraryEntry(items);
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
