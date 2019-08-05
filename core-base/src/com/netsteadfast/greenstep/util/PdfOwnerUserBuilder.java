/* 
 * Copyright 2012-2019 bambooCORE, greenstep of copyright Chen Xin Nien
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

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PdfOwnerUserBuilder {
	private String pdfFileFullPath = "";
	private String destEncryptionPdfPath = "";
	private String owner = "PdfOwnerUserBuilder";
	private String user = "PdfOwnerUserBuilder";
	private String watermarkText = "";
	
	public static PdfOwnerUserBuilder build() {
		PdfOwnerUserBuilder pdfOwnerUserBuilder = new PdfOwnerUserBuilder();
		return pdfOwnerUserBuilder;
	}
	
	public PdfOwnerUserBuilder watermarkText(String watermarkText) {
		if (!StringUtils.isBlank(watermarkText)) {
			this.watermarkText = watermarkText;
		}
		return this;
	}
	
	public PdfOwnerUserBuilder owner(String owner) {
		if (!StringUtils.isBlank(owner)) {
			this.owner = owner;
		}
		return this;
	}
	
	public PdfOwnerUserBuilder user(String user) {
		if (!StringUtils.isBlank(user)) {
			this.user = user;
		}
		return this;
	}
	
	public PdfOwnerUserBuilder destFile(String fullPath) {
		this.destEncryptionPdfPath = fullPath;
		return this;
	}
	
	public PdfOwnerUserBuilder destFileToTmpdir() {
		String fullPath = System.getProperty("java.io.tmpdir") + "/" + PdfOwnerUserBuilder.class.getSimpleName() + "/" + System.currentTimeMillis() + "/" + System.currentTimeMillis()+".pdf";
		this.destFile(fullPath);
		return this;
	}
	
	public PdfOwnerUserBuilder sourceFile(String fullPath) throws Exception {
		File f = new File(fullPath);
		if (!f.exists()) {
			f = null;
			throw new Exception("no exists file : " + fullPath);
		}
		f = null;
		this.pdfFileFullPath = fullPath;
		return this;
	}
	
	public PdfOwnerUserBuilder process() throws Exception {
		if (StringUtils.isBlank(this.destEncryptionPdfPath) || StringUtils.isBlank(this.pdfFileFullPath)) {
			throw new Exception("no exists dest file : " + destEncryptionPdfPath + " or source file : " + this.pdfFileFullPath);
		}
		File destEncryptionPdfFile = new File(this.destEncryptionPdfPath);
		if (!destEncryptionPdfFile.getParentFile().exists()) {
			FileUtils.forceMkdir(destEncryptionPdfFile.getParentFile());
		}
		destEncryptionPdfFile = null;
		FileOutputStream destEncryptionPdfFileOs = new FileOutputStream(this.destEncryptionPdfPath);
		PdfReader reader = null;
		PdfStamper stamper = null;
		try {
			reader = new PdfReader(this.pdfFileFullPath);
			stamper = new PdfStamper(reader, destEncryptionPdfFileOs);
			stamper.setEncryption(
					this.user.getBytes(), 
					this.owner.getBytes(), 
					PdfWriter.ALLOW_PRINTING, 
					PdfWriter.ENCRYPTION_AES_128);
			if (!StringUtils.isBlank(this.watermarkText)) {
				Font font = FontFactory.getFont("fonts/fireflysung.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
				PdfWatermarkUtils.addWatermark(stamper, font.getBaseFont(), Color.RED, this.watermarkText);
			}
			stamper.close();
			reader.close();
		} catch (Exception e) {
			throw e;
		} finally {
			if (stamper != null) {
				stamper = null;
			}
			if (reader != null) {
				reader = null;
			}
			if (destEncryptionPdfFileOs != null) {
				destEncryptionPdfFileOs.close();
				destEncryptionPdfFileOs = null;
			}
		}
		return this;
	}
	
	public String destFileFullPath() {
		return this.destEncryptionPdfPath;
	}	
	
	/*
	public static void main(String args[]) throws Exception {
		String path = PdfOwnerUserBuilder.build()
			.user("password123")
			.owner("password123")
			.sourceFile("/Pilot_Learn_English_Now_Eng.pdf")
			.destFileToTmpdir()
			.watermarkText("This is a TEST!")
			.process().destFileFullPath();
		System.out.println(path);
	}	
	*/
	
}
