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
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

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
	
	public static List<File> toImageFiles(File pdfFile, int resolution) throws Exception {
		PDDocument document = PDDocument.load(pdfFile);		
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		/*
		List<PDPage> pages = new LinkedList<PDPage>();
		for (int i=0; i < document.getDocumentCatalog().getPages().getCount(); i++) {
			pages.add( document.getDocumentCatalog().getPages().get(i) );
		}
		*/
		File tmpDir = new File(Constants.getWorkTmpDir() + "/" + PdfConvertUtils.class.getSimpleName() 
				+ "/" + System.currentTimeMillis() + "/");
		FileUtils.forceMkdir( tmpDir );
		List<File> files = new LinkedList<File>();
		//int len = String.valueOf(pages.size()+1).length();
		int len = String.valueOf(document.getDocumentCatalog().getPages().getCount()+1).length();
		//for (int i=0; i<pages.size(); i++) {
		for (int i=0; i<document.getDocumentCatalog().getPages().getCount(); i++) {
			String name = StringUtils.leftPad(String.valueOf(i+1), len, "0");
			BufferedImage bufImage = pdfRenderer.renderImageWithDPI(i, resolution, ImageType.RGB);
			File imageFile = new File( tmpDir.getPath() + "/" + name + ".png" );
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
	
	public static void main(String args[]) throws Exception {
		if (args == null || args.length != 2) {
			System.out.println("PdfConvertUtils [FILE] [RESOLUTION]");
			System.out.println("Example: PdfConvertUtils /test.pdf 120");		
			System.exit(1);
		}		
		System.out.println("source document file: " + args[0]);		
		int res = NumberUtils.toInt(args[1], 300);
		List<File> imageFiles = toImageFiles(new File(args[0]), res);
		for (File file : imageFiles) {
			System.out.println( file.getPath() );
		}
	}

}
