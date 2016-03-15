package com.warren.knickknacks.eclipse.jdt.core.prefs;

import static org.mockito.Mockito.when;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mockito.Mockito;

import com.warren.knickknacks.Util;

public abstract class AbstractTest {

	public File configureFile(String pathname, File f) {
		return configureFile(pathname, f, 0);
	}

	private File configureFile(String pathname, File f, int indent) {
		StringBuilder s = new StringBuilder();
		when(f.isFile()).thenReturn(true);
		s.append(getIndent(indent)).append("isFile").append(" = true").append("\r\n");
		when(f.isDirectory()).thenReturn(false);
		s.append(getIndent(indent)).append("isDirectory").append(" = false").append("\r\n");
		configureItem(pathname, f, s, indent);
		return f;
	}

	public File configureDirectory(String pathname, File f) {
		return configureDirectory(pathname, f, 0);
	}
	
	public File configureDirectory(String pathname, File f, int indent) {
		StringBuilder s = new StringBuilder();
		when(f.isFile()).thenReturn(false);
		s.append(getIndent(indent)).append("isFile").append(" = false").append("\r\n");
		when(f.isDirectory()).thenReturn(true);
		s.append(getIndent(indent)).append("isDirectory").append(" = true").append("\r\n");
		configureItem(pathname, f, s, indent);
		return f;
	}
	
	private File configureItem(String pathname, File f, StringBuilder s, int indent) {
		pathname = pathname.replaceAll("\\\\", "/");
		String[] parts = pathname.split("/");
		when(f.getAbsolutePath()).thenReturn(pathname);
		s.append(getIndent(indent)).append("getAbsolutePath").append(" = " + pathname).append("\r\n");
		when(f.exists()).thenReturn(true);
		s.append(getIndent(indent)).append("exists").append(" = true").append("\r\n");
		String name = parts[parts.length-1];
		when(f.getName()).thenReturn(name);
		s.append(getIndent(indent)).append("getName").append(" = " + name).append("\r\n");
		int index = parts.length > 1 ? parts.length-2 : 0;
		if(parts.length == 1) {
			when(f.getParent()).thenReturn(null);
			s.append(getIndent(indent)).append("getParent").append(" = null").append("\r\n");
			when(f.getParentFile()).thenReturn(null);
			s.append(getIndent(indent)).append("getParentFile").append(" = null").append("\r\n");
		}
		else {
			File parentFile = Mockito.mock(File.class);		
			String parent = Util.concat(index, "/", parts);
			parentFile = configureDirectory(parent, parentFile, indent+1);
			when(f.getParent()).thenReturn(parent);
			s.append(getIndent(indent)).append("getParent").append(" = " + parent).append("\r\n");
			when(f.getParentFile()).thenReturn(parentFile);			
			s.append(getIndent(indent))
				.append("getParentFile")
				.append(" = ")
				.append("[")
				.append("\r\n" + parentFile.toString())
				.append(getIndent(indent)+"]")
				.append("\r\n");
		}
		when(f.toString()).thenReturn(s.toString());
		return f;
	}
	
	private String getIndent(int indent) {
		StringBuilder s = new StringBuilder();
		for(int i=0; i<= indent; i++) {
			s.append("   ");
		}
		return s.toString();
	}
	
	protected int findInstances(String regex, String s) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		int counter = 0;
		while(m.find()) {
			counter++;
		}
		return counter;
	}
}
