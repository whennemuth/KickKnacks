package com.warren.knickknacks.bu.ist.kc.sql;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

import com.warren.knickknacks.Util;


/**
 * Run top level script as follows:
 *   cd C:\[path to kc workspace]\kc\coeus\coeus-db\coeus-db-sql\src\main\resources\co\kuali\coeus\data\migration\sql\mysql
 *   mysql --max_allowed_packet=64M -v -u root -p < [path to .sql file created by this program]
 *   Enter password: admin
 * 
 * @param args
 */
public class BuildKcSqlScript {
	
	private File rootDir;
	private String dbName;
	
	private BuildKcSqlScript() { /* Restrict private constructor */ }
	
	public static BuildKcSqlScript getInstance(String rootDirPath, String dbName) {
		
		BuildKcSqlScript script = new BuildKcSqlScript();
		script.rootDir = new File(rootDirPath);
		script.dbName = dbName;
		if(!script.rootDir.isDirectory()) {
			System.out.println("No such directory: " + rootDirPath);
			return null;
		}
		
		return script;
	}
	
	public void build(PrintWriter pw) {
		File[] files = rootDir.listFiles(new FileFilter(){

			public boolean accept(File f) {
				boolean ok = f.isFile() && f.getName().endsWith(".sql");
				ok &= f.getName().contains("_client_upgrade")==false;
				ok &= f.getName().matches("\\d+_.*");
				return ok;
			}});
		Arrays.sort(files, new Comparator<File>(){

			public int compare(File f1, File f2) {
				String[] order = new String[]{
						"kc_upgrade",
						"rice_server_upgrade",
						"kc_rice_server_upgrade",
						"kc_demo",
						"rice_demo",
				};
				String order1 = "";
				String order2 = "";
				Integer index1 = 0;
				Integer index2 = 0;
				Integer version1 = 0;
				Integer version2 = 0;
				for(int i=0; i<order.length; i++) {
					version1 = Integer.valueOf(f1.getName().split("_")[0]);
					version2 = Integer.valueOf(f2.getName().split("_")[0]);
					if(f1.getName().contains(order[i])) {
						if(order1.isEmpty() || order1.length() < order[i].length()) {
							order1 = order[i];
							index1 = i;
							version1 = Integer.valueOf(f1.getName().split("_")[0]);
						}
					}
					if(f2.getName().contains(order[i])) {
						if(order2.isEmpty() || order2.length() < order[i].length()) {
							order2 = order[i];
							index2 = i;
							version2 = Integer.valueOf(f2.getName().split("_")[0]);
						}
					}					
				}
				
				int compare = version1.compareTo(version2);
				return compare == 0 ? index1.compareTo(index2) : compare;
			}}
		);
		
		pw.println("use " + dbName + ";");
		pw.println("SET GLOBAL foreign_key_checks = 0;");
		pw.println("SET foreign_key_checks = 0;");
		pw.println("SET optimizer_switch = 'derived_merge=off';");

		pw.println();
		
		for(File f : files) {
			pw.println("source " + f.getName() + ";");
		}		
		
		//addLogStatements(rootDir);
	}

	public static void buildScript(String rootDirPath, String dbName, String scriptFileName) {
		
		PrintWriter pw = null;
		try {
			BuildKcSqlScript script = BuildKcSqlScript.getInstance(rootDirPath, dbName);
			if(script != null) {
				if(scriptFileName == null) {
					pw = new PrintWriter(System.out);
				}
				else {
					File f = new File(scriptFileName);
					if(f.getParentFile() != null && f.getParentFile().exists()) {
						pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream(f)));
					}
				}
			}
			
			script.build(pw);
		} 
		catch (Exception e) {
			e.printStackTrace(System.out);
		}
		finally {
			if(pw != null) {
				pw.close();
			}
			System.out.println("Finished!");
		}
		
	}
	
	/**
	 * Add a logging statement to the top of each ".sql" file in the specified directory. Recursively repeat for all subdirectories.
	 * When each script file is run, it will insert an entry into a log table indicating the time it was processed.
	 * You must create this log table first:
	 * 
	 * CREATE TABLE `kc`.`mylog` (
	 *   `script_name` VARCHAR(255) NOT NULL,
	 *     `created` DATETIME NOT NULL);
	 *     
	 * @param dir
	 */
	private void addLogStatements(File dir) {
		if(dir.isDirectory()) {
			File[] scripts = dir.listFiles(new FileFilter(){
				public boolean accept(File f) {
					return f.isFile() && f.getName().endsWith(".sql");
				}});
			
			for(File script : scripts) {
				String logStmt = "insert into mylog values ('" + script.getName() + "', current_timestamp());\r\n\r\n";
				if(!Util.fileStartsWith(script, logStmt)) {
					Util.prependFileContent(logStmt, script);
				}
			}
						
			File[] subdirs = dir.listFiles(new FileFilter(){
				public boolean accept(File dir) {
					return dir.isDirectory();
				}});
			
			for(File subdir : subdirs) {
				addLogStatements(subdir);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		final String DEFAULT_ROOT_DIR = 
				"C:/whennemuth/workspaces/kuali_workspace/kc/coeus-db/coeus-db-sql/src/main/resources/co/kuali/coeus/data/migration/sql/mysql";
		
		final String DEFAULT_DB_NAME = "kc";
		final String DEFAULT_BAT_NAME = "C:/Users/wrh/Desktop/KcSqlScript.sql";
		
		if(args == null || args.length == 0) {
			args = new String[] { DEFAULT_ROOT_DIR,	DEFAULT_DB_NAME, DEFAULT_BAT_NAME };
		}

		buildScript(
				args[0], 
				args.length < 2 ? args[1] : DEFAULT_DB_NAME, 
				args.length == 1 ? null : args[2]);
	}
}
