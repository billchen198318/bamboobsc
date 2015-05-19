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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.ImageIOUtil;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.exception.ServiceException;

public class PdfConvertUtils {
	
	public static List<String> toImageUpload(File pdfFile, int resolution, 
			String system, String uploadType, boolean isFile) throws ServiceException, Exception {
		List<String> oids = new LinkedList<String>();
		List<File> imageFiles = toImageFiles(pdfFile, resolution);
		for (File file : imageFiles) {
			oids.add(
					UploadSupportUtils.create(
							system, uploadType, isFile, file, file.getName())
			);
		}
		return oids;
	}
	
	@SuppressWarnings("unchecked")
	public static List<File> toImageFiles(File pdfFile, int resolution) throws Exception {
		PDDocument document = PDDocument.loadNonSeq(pdfFile, null);		
		List<PDPage> pages = document.getDocumentCatalog().getAllPages();
		File tmpDir = new File(Constants.getWorkTmpDir() + "/" + PdfConvertUtils.class.getSimpleName() 
				+ "/" + System.currentTimeMillis() + "/");
		FileUtils.forceMkdir( tmpDir );
		List<File> files = new LinkedList<File>();
		for (int i=0; i<pages.size(); i++) {
			BufferedImage bufImage = pages.get(i).convertToImage(BufferedImage.TYPE_INT_RGB, resolution);
			File imageFile = new File( tmpDir.getPath() + "/" + String.valueOf(i) + ".png" );
			FileOutputStream fos = new FileOutputStream(imageFile);
			ImageIOUtil.writeImage(bufImage, "png", fos, resolution);
			fos.flush();
			fos.close();
			files.add(imageFile);
		}
		document.close();
		tmpDir = null;
		return files;
	}
	
	/*
	public static void main(String args[]) throws Exception {
		// for test
		List<File> imageFiles = toImageFiles(new File("/home/git/bamboobsc/core-doc/install.pdf"), 300);
		for (File file : imageFiles) {
			System.out.println( file.getPath() );
		}
	}
	*/

}
