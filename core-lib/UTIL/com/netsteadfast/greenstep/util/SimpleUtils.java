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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

public class SimpleUtils {
	public static final int IS_YEAR=1;
	public static final int IS_MONTH=2;
	public static final int IS_DAY=3;	
	private final static Random rnd=new Random();		
	private final static String sourceStr="abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final static char sourceStrArray[]=sourceStr.toCharArray();
	
	public static boolean checkBeTrueOf_azAZ09(
			final int minLength, final int maxLength, final String sourceValue) {
		boolean accept=true;
		if (sourceValue==null || sourceValue.length()<minLength || sourceValue.length()>maxLength || minLength<0 ) {
			accept=false;
			return accept;
		}
		accept=SimpleUtils.checkBeTrueOf_azAZ09(sourceValue);
		return accept;
	}
	
	public static boolean checkBeTrueOf_09(final String sourceValue) {
		boolean isNormal=true;
		if (sourceValue!=null && sourceValue.length()>0) {
			int val=0;
			char tmp[]=sourceValue.toCharArray();
			for (int ix=0; ix<tmp.length && isNormal; ix++) {
				val=(int)tmp[ix];
				if (! (val>=48 && val<=57) )
					isNormal=false;
			}
		} else {
			isNormal=false;
		}
		return isNormal;
	}	
	
	public static boolean checkBeTrueOf_azAZ09(final String sourceValue) {
		boolean isNormal=true;
		if (sourceValue!=null && sourceValue.length()>0) {
			int val=0;
			char tmp[]=sourceValue.toCharArray();
			for (int ix=0; ix<tmp.length && isNormal; ix++) {
				val=(int)tmp[ix];
				if (!( (val>=48 && val<=57) || (val>=65 && val<=90) || (val>=97 && val<=122) ) )
					isNormal=false;
			}
		} else {
			isNormal=false;
		}
		return isNormal;
	}	
	
	public static boolean checkBeTrueOfNumber(final String sourceValue) {
		return org.apache.commons.lang3.math.NumberUtils.isCreatable(sourceValue);
	}	
	
	public static String createRandomString(final int length) {
		if (length<1)
			return "";
		
		StringBuffer retValue=new StringBuffer();
		for (int ix=0; ix<length; ix++) {
			retValue.append(SimpleUtils.sourceStrArray[SimpleUtils.rnd.nextInt(SimpleUtils.sourceStrArray.length)] );
		}
		return retValue.toString();
	}
	
	public static String getStr(String value) {
		return getStr(value, "");
	}
	
	public static String getStr(final String value, final String defaultValue) {		
		return org.apache.commons.lang3.StringUtils.defaultString(value, defaultValue);
	}
	
	public static int getInt(final String value, final int defaultValue) { 
		int val=defaultValue;
		if (value!=null ) {
			val=org.apache.commons.lang3.math.NumberUtils.toInt(value, defaultValue);
		}
		return val;
	}

	public static long getLong(final String value, final long defaultValue) {
		long val=defaultValue;
		if (value!=null ) {
			val=org.apache.commons.lang3.math.NumberUtils.toLong(value, defaultValue);
		}
		return val;
	}	
	
	public static float getFloat(final String value, final float defaultValue) {
		float val=defaultValue;
		if (value!=null ) {
			val=org.apache.commons.lang3.math.NumberUtils.toFloat(value, defaultValue);
		}
		return val;
	}
	
	public static final String getStrYMD(final int type) {
		Calendar calendar=Calendar.getInstance();
		if (type==SimpleUtils.IS_YEAR) {
			return calendar.get(Calendar.YEAR)+"";
		}
		if (type==SimpleUtils.IS_MONTH) {
			return StringUtils.leftPad((calendar.get(Calendar.MONTH)+1 )+"", 2, "0");
		}
		if (type==SimpleUtils.IS_DAY) {
			return StringUtils.leftPad(calendar.get(Calendar.DAY_OF_MONTH)+"", 2, "0");
		}
		return calendar.get(Calendar.YEAR)+"";
	}	
	
	public static boolean isDate(final String yyyymmdd) {
		boolean accept=false;
		if (yyyymmdd==null )
			return accept;
		int y=0;
		int m=0;
		int d=0;
		if (yyyymmdd.indexOf("/")>-1 ) {
			String tmp[]=yyyymmdd.split("/");
			if (tmp.length==3 ) {
				y=SimpleUtils.getInt(tmp[0], 0 );
				m=SimpleUtils.getInt(tmp[1], 0 );
				d=SimpleUtils.getInt(tmp[2], 0 );
			}
			tmp=null;
		}
		if (yyyymmdd.indexOf("-")>-1 ) {
			String tmp[]=yyyymmdd.split("-");
			if (tmp.length==3 ) {
				y=SimpleUtils.getInt(tmp[0], 0 );
				m=SimpleUtils.getInt(tmp[1], 0 );
				d=SimpleUtils.getInt(tmp[2], 0 );
			}
			tmp=null;
		}		
		if (yyyymmdd.length()==8 ) {
			y=SimpleUtils.getInt(yyyymmdd.substring(0, 4), 0 );
			m=SimpleUtils.getInt(yyyymmdd.substring(4, 6), 0 );
			d=SimpleUtils.getInt(yyyymmdd.substring(6, 8), 0 );
		}
		if (y!=0 && m!=0 && d!=0 && y>=1900 && y<=9999 && m>=1 && m<=12 && d>=1) {
			if (d<=SimpleUtils.getMaxDayOfMonth(y, m) ) {
				accept=true;
			}
		}
		return accept;
	}
	
	public static int getDaysBetween(String startDate, String endDate) {
		DateTime s = new DateTime( getStrYMD(startDate.replaceAll("/", "-"), "-") );
		DateTime e = new DateTime( getStrYMD(endDate.replaceAll("/", "-"), "-") );
		return Days.daysBetween(s, e).getDays();
	}
	
	public static int getDaysBetween(Date startDate, Date endDate) {
		return getDaysBetween( getStrYMD(startDate, "-"), getStrYMD(endDate, "-") );
	}
	
	public static int getYearsBetween(String startDate, String endDate) {
		DateTime s = new DateTime( startDate.length()==4 ? startDate + "-01-01" : getStrYMD(startDate.replaceAll("/", "-"), "-") ); 
		DateTime e = new DateTime( endDate.length()==4 ? endDate + "-01-01" : getStrYMD(endDate.replaceAll("/", "-"), "-") );		
		return Years.yearsBetween(s, e).getYears();
	}	
	
	public static int getYearsBetween(Date startDate, Date endDate) {
		return getYearsBetween( getStrYMD(startDate, "").substring(0, 4), getStrYMD(endDate, "").substring(0, 4) );
	}
	
	public static int getMonthsBetween(String startDate, String endDate) {		
		DateTime s = new DateTime( getStrYMD(startDate.replaceAll("/", "-"), "-") ); 
		DateTime e = new DateTime( getStrYMD(endDate.replaceAll("/", "-"), "-") );		
		return Months.monthsBetween(s, e).getMonths();
	}		
	
	public static int getMonthsBetween(Date startDate, Date endDate) {
		return getMonthsBetween( getStrYMD(startDate, "-"), getStrYMD(endDate, "-") );
	}	
	
	public static final String getStrYMD(String splitStr) {
		StringBuilder sb=new StringBuilder();
		Calendar calendar=Calendar.getInstance();
		sb.append(calendar.get(Calendar.YEAR) ).append(splitStr);
		sb.append( StringUtils.leftPad((calendar.get(Calendar.MONTH)+1)+"", 2, "0") ).append(splitStr);
		sb.append( StringUtils.leftPad(calendar.get(Calendar.DAY_OF_MONTH)+"", 2, "0") );
		return sb.toString();
	}
	
	public static final String getStrYMD(Date date, String splitStr) {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR) + splitStr
			+ org.apache.commons.lang3.StringUtils.leftPad((calendar.get(Calendar.MONTH)+1)+"", 2, "0") + splitStr
			+ org.apache.commons.lang3.StringUtils.leftPad(calendar.get(Calendar.DAY_OF_MONTH)+"", 2, "0");
	}
	
	public static final String getStrYMD(String yyyymmdd, String splitStr) {
		if (StringUtils.isBlank(splitStr) || !isDate(yyyymmdd)) {
			return yyyymmdd;
		}
		if (yyyymmdd.length()!=8) {
			return yyyymmdd;
		}
		return yyyymmdd.substring(0, 4) + splitStr + yyyymmdd.substring(4, 6) + splitStr + yyyymmdd.substring(6, 8);
	}
	
	public static int getMaxDayOfMonth(final int year, final int month) {
		int max=28;		
		Calendar calendar=Calendar.getInstance();		
		if (year>=1900 && year<=3000) {
			calendar.set(Calendar.YEAR, year);
		} else {
			calendar.set(Calendar.YEAR, 1900);		
		}
		if (month>=1 && month<=12) {
			calendar.set(Calendar.MONTH, (month-1) );
		} else {
			calendar.set(Calendar.MONTH, 0 );		
		}
		calendar.set(Calendar.DATE, 1);
		max=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return max;
	}
	
	public static int getDayOfWeek(final int year, final int month) {
		int dayofweek=28;		
		Calendar calendar=Calendar.getInstance();		
		if (year>=1900 && year<=3000) {
			calendar.set(Calendar.YEAR, year);
		} else {
			calendar.set(Calendar.YEAR, 1900);		
		}
		if (month>=1 && month<=12) {
			calendar.set(Calendar.MONTH, (month-1) );
		} else {
			calendar.set(Calendar.MONTH, 0 );		
		}
		calendar.set(Calendar.DATE, 1);
		dayofweek=calendar.get(Calendar.DAY_OF_WEEK);
		return dayofweek;
	}	
	
	/**
	 * yyyyMMdd 
	 * 2013/01/01
	 * 20130101
	 * 
	 * @param yyyyMMdd
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getQuarterlyStartEnd(String yyyyMMdd) throws Exception {
		Map<String, String> dateMap=new HashMap<String, String>();
		if (yyyyMMdd==null) {
			throw new java.lang.IllegalArgumentException("yyyyMMdd error.");
		}
		yyyyMMdd=yyyyMMdd.replaceAll("/", "");
		if (yyyyMMdd.length()!=8 || !org.apache.commons.lang3.math.NumberUtils.isCreatable(yyyyMMdd)) {
			throw new java.lang.IllegalArgumentException("yyyyMMdd error.");
		}
		int yyyy=Integer.parseInt(yyyyMMdd.substring(0, 4));
		int mm = Integer.parseInt(yyyyMMdd.substring(4, 6));
		String start="";
		String end="";
		if (mm>=1 && mm<=3) { // Q1
			start=yyyy+"/01/01";
			end=yyyy+"/03/" + getMaxDayOfMonth(yyyy, 3);
		}
		if (mm>=4 && mm<=6) { // Q2
			start=yyyy+"/04/01";
			end=yyyy+"/06/" + getMaxDayOfMonth(yyyy, 6);
		}
		if (mm>=7 && mm<=9) { // Q3
			start=yyyy+"/07/01";
			end=yyyy+"/09/" + getMaxDayOfMonth(yyyy, 9);			
		}
		if (mm>=10 && mm<=12) { // Q4
			start=yyyy+"/10/01";
			end=yyyy+"/12/" + getMaxDayOfMonth(yyyy, 12);			
		}		
		if ("".equals(start) || "".equals(end)) {
			throw new java.lang.IllegalArgumentException("yyyyMMdd error.");
		}
		dateMap.put("date1", start);
		dateMap.put("date2", end);
		return dateMap;
		
	}
	
	public static String toHex(final String sourceValue) {
		String value="";
		value=org.apache.commons.codec.binary.Hex.encodeHexString(SimpleUtils.getStr(sourceValue, "").getBytes() );
		return value;
	}
	
	public static String deHex(final String sourceValue) {
		String value="";
		try {
			value=new String(org.apache.commons.codec.binary.Hex.decodeHex( SimpleUtils.getStr(sourceValue, "").toCharArray() ) );
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static String toB64(final String sourceValue) {		
		String value="";
		value=org.apache.commons.codec.binary.Base64.encodeBase64String(SimpleUtils.getStr(sourceValue, "").getBytes() );	
		return value;
	}
	
	public static String deB64(final String sourceValue) {
		String value="";
		value=new String(org.apache.commons.codec.binary.Base64.decodeBase64(SimpleUtils.getStr(sourceValue, "") ) );
		return value;
	}
	
	public static String getUUIDStr() {
		//return UUID.randomUUID().toString();
		return com.datastax.driver.core.utils.UUIDs.timeBased().toString();
	}
	
	public static String getRandomUUIDStr() {
		return com.datastax.driver.core.utils.UUIDs.random().toString();
	}
	
	public static byte[] toMD5(final String sourceValue) {
		return org.apache.commons.codec.digest.DigestUtils.md5(SimpleUtils.getStr(sourceValue, "") );
	}
	
	public static String toMD5Hex(final String sourceValue) {
		return org.apache.commons.codec.digest.DigestUtils.md5Hex(SimpleUtils.getStr(sourceValue, "") );
	}
	
	public static String joinString(Object... values) {
		if (values==null ) {
			return "";
		}
		StringBuilder sb=new StringBuilder();
		for (Object o: values) {
			if (o!=null) {
				sb.append((String)o );
			}			
		}
		return sb.toString();
	}
	
	public static String toUnicode(String strValue) {
		if (strValue==null ) {
			return "";
		}
		StringBuilder sb=new StringBuilder();
		char chr[]=strValue.toCharArray();
		for (int ix=0; ix<chr.length; ix++ ) {
			sb.append("&#").append(((int)chr[ix]) ).append(";");
		}
		return sb.toString();
	}
	
	public static <T extends java.io.Serializable> List<T> getListHashSet(List<T> list) throws Exception {
		if (list==null) {
			return list;
		}
		return new ArrayList<T>(new HashSet<T>(list));
	}
	
    /**
     * Decode string to image
     * @param imageString The string to decode
     * @return decoded image
     */
    public static BufferedImage decodeToImage(String imageString) {
        BufferedImage image = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(imageString));
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Encode image to string
     * @param image The image to encode
     * @param type jpeg, bmp, ...
     * @return encoded string
     */
    public static String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();
            imageString = DatatypeConverter.printBase64Binary(imageBytes);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageString;
    }
    
    public static String getPNGBase64Content(String imgStr) throws Exception {
    	if (!isPNGBase64Content(imgStr)) {
    		//imgStr = IOUtils.toString(SimpleUtils.class.getClassLoader().getResource("META-INF/resource/nofound-icon.html").openStream());
    		imgStr = new String( IOUtils.toByteArray(SimpleUtils.class.getClassLoader().getResource("META-INF/resource/nofound-icon.html").openStream()) );
    	}
    	imgStr = org.apache.commons.lang3.StringUtils.replaceOnce(imgStr, "<img src=\"data:image/png;base64,", "");
    	imgStr = org.apache.commons.lang3.StringUtils.replaceOnce(imgStr, "\">", "");
    	imgStr = org.apache.commons.lang3.StringUtils.replaceOnce(imgStr, "data:image/png;base64,", "");    	
    	return imgStr;
    }		
    
    public static boolean isPNGBase64Content(String imgStr) throws Exception {
    	if (imgStr==null || imgStr.indexOf("image/png;base64,")==-1 ) {
    		return false;
    	}
    	if (imgStr.length()<35) {
    		return false;
    	}
    	return true;
    }
    
    /**
     * http://stackoverflow.com/questions/12593752/why-do-i-failed-to-read-excel-2007-using-poi
     * 
     * @param inp
     * @return
     * @throws IOException
     * @throws InvalidFormatException
     */
    public static Workbook createPOIWorkbook(InputStream inp) throws IOException, InvalidFormatException {
    	// If clearly doesn't do mark/reset, wrap up
    	if(!inp.markSupported()) {
    		inp = new PushbackInputStream(inp, 8);
    	}
    	if(POIFSFileSystem.hasPOIFSHeader(inp)) {
    		return new HSSFWorkbook(inp);
    	}
    	if(DocumentFactoryHelper.hasOOXMLHeader(inp)) {
    		return new XSSFWorkbook(OPCPackage.open(inp));
        }
    	throw new IllegalArgumentException("Your InputStream was neither an OLE2 stream, nor an OOXML stream");
    }    
    
	public static void setCellPicture(XSSFWorkbook wb, XSSFSheet sh, byte[] iconBytes, int row, int col) throws Exception {
        int myPictureId = wb.addPicture(iconBytes, XSSFWorkbook.PICTURE_TYPE_PNG);
        
        XSSFDrawing drawing = sh.createDrawingPatriarch();
        XSSFClientAnchor myAnchor = new XSSFClientAnchor();
       
        myAnchor.setCol1(col);
        myAnchor.setRow1(row);
        
        XSSFPicture myPicture = drawing.createPicture(myAnchor, myPictureId);
        myPicture.resize();
	}
	
	public static byte[] getColorRGB(String rgb) throws Exception {
		if (StringUtils.isEmpty(rgb) || rgb.length()!=6) {
			return new byte[]{ (byte)00, (byte)00, (byte)00 };
		}
		int red = Integer.parseInt(rgb.substring(0,2), 16);
		int green = Integer.parseInt(rgb.substring(2,4), 16);
		int blue = Integer.parseInt(rgb.substring(4,6), 16);
		return new byte[]{ (byte)red, (byte)green, (byte)blue };
	}
	
	public static byte[] getColorRGB4POIColor(String srcRGB) throws Exception {
		if (StringUtils.isEmpty(srcRGB) || (srcRGB.length()!=6 && srcRGB.length()!=7) ) {
			return new byte[]{ (byte)-1, (byte)00, (byte)00, (byte)00 };
		}
		String rgb=srcRGB;
		if (rgb.length()==7) { // #FFFFFF to FFFFFF
			rgb=rgb.substring(1, rgb.length());
		}
		int red = Integer.parseInt(rgb.substring(0,2), 16);
		int green = Integer.parseInt(rgb.substring(2,4), 16);
		int blue = Integer.parseInt(rgb.substring(4,6), 16);
		return new byte[]{ (byte)-1, (byte)red, (byte)green, (byte)blue };
	}		
	
	public static int[] getColorRGB2(String color) throws Exception {
		if (StringUtils.isEmpty(color) || color.length()!=7) {
			return new int[] {0, 0, 0};
		}
		Color c = Color.decode(color);
		return new int[]{ c.getRed(), c.getGreen(), c.getBlue() };
	}		
	
    public static String getHttpRequestUrl(HttpServletRequest request) {
    	StringBuilder url = new StringBuilder(); 
    	url.append( request.getRequestURL().toString() );
    	Enumeration<String> paramNames = request.getParameterNames();
    	for (int i=0; paramNames.hasMoreElements(); i++) {
    		String paramName = paramNames.nextElement();    		
    		if ( i == 0 ) {
    			url.append("?");
    		} else {
    			url.append("&");
    		}
    		url.append(paramName).append("=").append( request.getParameter(paramName) );
    	}
    	return url.toString();
    }	
	
	public static String escapeCsv(String str) throws Exception {
		if ( null == str ) {
			return str;
		}		
		str = str.replaceAll("\r\n", "#GS_NRNL#");
		str = str.replaceAll("\r", "#GS_NR#");
		str = str.replaceAll("\n", "#GS_NL#");
		str = str.replaceAll("\t", "#GS_TAB#");
		str = str.replaceAll(";", "#GS_SEMICOLON#");
		str = str.replaceAll(",", "#GS_COMMA#");
		return str;
	}
	
	public static String unEscapeCsv(String str) throws Exception {
		if ( null == str ) {
			return str;
		}		
		str = str.replaceAll("#GS_NRNL#", "\r\n");
		str = str.replaceAll("#GS_NR#", "\r");
		str = str.replaceAll("#GS_NL#", "\n");
		str = str.replaceAll("#GS_TAB#", "\t");
		str = str.replaceAll("#GS_SEMICOLON#", ";");
		str = str.replaceAll("#GS_COMMA#", ",");
		return str;		
	}    
    
	public static String unEscapeCsv2(String str) throws Exception {
		if ( null == str ) {
			return str;
		}
		str = unEscapeCsv(str);
		if (str.startsWith("\"") && str.endsWith("\"")) {
			str = str.substring(1, str.length());
			str = str.substring(0, str.length()-1);				
		}
		return str;		
	}  	
	
	public static int getAvailableProcessors(int maxSize) {
		int processors = Runtime.getRuntime().availableProcessors() - 1;
		if (processors > maxSize) {
			processors = maxSize;
		}
		if (processors < 1) {
			processors = 1;
		}		
		return processors;
	}
	
}
