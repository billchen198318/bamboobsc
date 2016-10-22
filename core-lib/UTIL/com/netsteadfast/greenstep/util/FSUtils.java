/* 
 * Copyright 2012-2016 bambooCORE, greenstep of copyright Chen Xin Nien
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * -----------------------------------------------------------------------
 * 
 * author: 	Chen Xin Nien
 * contact: chen.xin.nien@gmail.com
 * 
 */
package com.netsteadfast.greenstep.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;

public class FSUtils {
	
	public static void mv(final String sourceFile, final String destFile) throws IOException, Exception {		
		File srcFile=new File(sourceFile);
		File desFile=new File(destFile);	
		org.apache.commons.io.FileUtils.moveFile(srcFile, desFile);			
		srcFile=null;
		desFile=null;		
	}
	
	public static void cp(final String sourceFile, final String destFile) throws IOException, Exception {		
		File srcFile=new File(sourceFile);
		File desFile=new File(destFile);		
		org.apache.commons.io.FileUtils.copyFile(srcFile, desFile);
		srcFile=null;
		desFile=null;		
	}
	
	public static void rm(final String removeFile) throws IOException, Exception {
		File rmFile=new File(removeFile);
		org.apache.commons.io.FileUtils.forceDelete(rmFile);
		rmFile=null;
	}
	
	public static String[] getList(final String dir) {
		File directory=new File(dir);
		return directory.list();
	}
	
	public static String readStr(final String fileFullPath) {
		String v="";	
		File f=new File(fileFullPath);
		try {
			if (f!=null && f.exists()) {
				byte b[]=org.apache.commons.io.FileUtils.readFileToByteArray(f);
				v=new String(b);				
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		f=null;
		return v;
	}
	
	public static boolean writeStr(final String fileFullPath, final String content) {
		boolean s=false;
		File f=new File(fileFullPath);		
		try {
			if (f!=null && f.exists()) {
				org.apache.commons.io.FileUtils.writeByteArrayToFile(f, SimpleUtils.getStr(content, "").getBytes() );
				s=true;				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		f=null;
		return s;
	}
	
	public static boolean writeStr2(final String fileFullPath, final String content) {
		boolean s=false;
		File f=new File(fileFullPath);
		try {
			if (f!=null) {
				org.apache.commons.io.FileUtils.writeByteArrayToFile(f, SimpleUtils.getStr(content, "").getBytes() );
				s=true;				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		f=null;
		return s;
	}
	
	public static List<String> findFileListByCondition(
			final String directory, final String sub_name[], final String content) {
		List<String> list1=new ArrayList<String>();
		List<String> findList=new ArrayList<String>();
		File dir=new File(directory);
		if (dir!=null ) {
			if (dir.isDirectory() ) {
				findFileNext(directory, findList);
				String f[]=null;
				f=new String[findList.size()];
				for (int ix=0; ix<findList.size(); ix++ ) {
					f[ix]=findList.get(ix);
				}
				for (int ix=0; f!=null && ix<f.length; ix++ ) {	
					boolean found_sub=false;
					for (int jx=0; jx<sub_name.length && !found_sub; jx++ ) {
						if (f[ix].toUpperCase().indexOf(sub_name[jx].toUpperCase() )>-1 ) {
							found_sub=true;
						}
					}
					if (found_sub) {
						if (readStr(f[ix]).indexOf(content)>-1 ) {
							list1.add(f[ix] );
						}
					}

				}				
				f=null;
			}
		}		
		dir=null;
		findList.clear();		
		return list1;
	}
	
	private static void findFileNext(final String f, List<String> findList) {
		System.out.println("find: " +  f);
		File file=new File(f);
		if (file!=null) {
			if (file.isDirectory() ) {
				File l[]=file.listFiles();
				for (int ix=0; l!=null && ix<l.length; ix++ ) {
					findFileNext(l[ix].getPath(), findList);
				}				
			} else {
				findList.add(f);
			}			
		}
		file=null;
	}
	
	/**
	 * get MIME-TYPE 
	 * Using javax.activation.MimetypesFileTypeMap
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String getMimeType(File file) throws Exception {		
		String mimeType="";
		if (file==null || !file.exists() || file.isDirectory() ) {
			return mimeType;
		}
		/*
		mimeType=new MimetypesFileTypeMap().getContentType(file);
		return mimeType;
		*/
		return Files.probeContentType(file.toPath());
	}
	
	public static String getMimeType(String filename) throws Exception {
		ConfigurableMimeFileTypeMap mfm = new ConfigurableMimeFileTypeMap();		
		return mfm.getContentType(filename);
	}	
	
	/**
	 * get MIME-TYPE 
	 * Using java.net.URL
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */	
	public static String getMimeType4URL(File file) throws IOException, MalformedURLException, Exception {
		String mimeType="";
		if (file==null || !file.exists() || file.isDirectory() ) {
			return mimeType;
		}		
		URL url=new URL(file.getPath());
		URLConnection urlConnection=url.openConnection();
		mimeType=urlConnection.getContentType();
		return mimeType;
	}
	
}
