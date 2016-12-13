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
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.DecoderException;
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
		return org.apache.commons.lang3.math.NumberUtils.isNumber(sourceValue);
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
		if (yyyyMMdd.length()!=8 || !org.apache.commons.lang3.math.NumberUtils.isNumber(yyyyMMdd)) {
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
		return UUID.randomUUID().toString();
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
    		return "iVBORw0KGgoAAAANSUhEUgAAAdwAAAEoCAMAAADFfrDIAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAMAUExURfHx8fjeg/nja9y5U/nkcmldJ+3MPeHh4frme/jaXP789vjgW/788vPRNO7u7op+Uf/++nNzcz08PPfotpaWl/rmgfjbavjXLfjccuTk5fjbY/jXMcSuLuTCTPz8/MPDw6WlpWtmZ76+vvfcM/jde/jhlPbULkdFRfjXNffbK1tbWykoKfb29mliTOnp6dLR0vfaHPTy7XJqax8fH/fZE/fcOffZCvfbIvjim/fYAPjXKqKRVvvpjPjko4iIiPnYPP79+GVjY/j4+Pjlq/r6+uvJQZGKcP/88P78+PT09PnjZfnhYOTFXPnYOtnY2np6evjfVvjeSdzCE/jfUPjfTfjeRYaFhf778d3d3mtqa2JeUfjYRbSrivjZVaurqxIQE/vpkfjeQfjgjvDOOfjZUOnHRfjZTP3tnP/yto2Gh6GWc/nYQbS0tPjZSffYBlVUVOC+UMzLzsOzYfrnh+rReevQavnXOIN7Zd7e4OrUim5naP3uo1tVQ3pzdD03J/vmYPb18HNtYPrjSOPj4+rOYf79+ffYAv38+uLi4vfdPtXV12xrdObm5+3s6vjfiXRzevPTRK6usOzNMYSEi6KOQOrMTtra2vHSS7SeUu/RB/3qgvXZW87O0PvofPXda/7wrvr6+cfHyPzndVpaZLW1uv379J2covDv7PvnaY+PkPTaY/n49/HXY/b29PfhbsC/wj43HezPW/v7++no5k1GKGJiavTz8vn5+FRUXvf39vHZcaalq+vr6RsZG+Df33p6goF/fyMfIP///f/0wP///v/+/P789f789P7+/v/99v/98+vr7P///Pb19ci/mP/883pxZMrHx9zb3Pf39/Pz8//99/Dw8FlXWPX19eTZrP799lFQUWBfX+bESDEsKvz7/P7+/f799NbMoi8vL7i0tfndJP/89f/+/rm4u9jX2fb19v/+/U1MTHNvcPfZDvfaF/n5+dza2hcVF/znkPDTWB8dInh3f/nhePTTOaKiou/WcPngPf7++/39/byqa////+S96PYAABzFSURBVHja7N0LfBTVvQfwubwSETAJGy0qeXEFDEu2mqpNbkSJAQxWwkOoqZrGJ1SjheZWuYKKrA8UrAVr6m3xEerj0ltB4+tarcrDELpUe02IUQGtW6vcxprWWi8tuXvPc+acmTOzk2yAPWfOb/1Idmd2ZjPf/M+cMzO7ayR0lI2hN4HG1dG4OorhNpSCkJ/za+Ol8dp8l6kopVaK2fvFUzISovswkSnFYLnFUyL2hbAzOZfN/yNePftwyLpTLF5zcSkbr4WKXnKx7UfRSvFS4sW1oYw0wO2MW7/nFPIbThFO9cYFKRDdB9vJ2qTFEcdCCgYON55hc3Cs2S+u+CULcW0rZReVf8Rxp1i/Z4P5uhoEU5PjluYL7icK4swj8YKE6EkDg0sfoT851+wT1+UlC3FtK+VWUHCEcfOZ3zMH/FAbqgX/zxFMtX6znBAOrdTu/Pz8ENweNYL7EbShcmpCNTloU0VETxIv2wWXWz3P08BuZ8GaC+Dz4D20ALeFur1kMS6/UrKo2jhfJEcEtzPH4oOFWwv+rTVfFTtVsO/h7ueTvwn7/Rzrjx8VRI7oSeJlu+AWC34PWmWdzDzCNZPydTybXajnS3bicis1Z0PPxJOOFC5oduM15JeF/8IdSIZZTuzUZLgJ8ldgu19g/unTkigQPGkgcLvjtLeA5xGv2QdukpfM4dpXyszGrvKI4OajFor8suCXjqNH40xdmVOT4cI/ibjzfi3ToUwkQqRxsD9pIHCLQ8xuv9htzT5wvV8yj2tfKTsbs8ojgQub3e4E5aOmVJmfKtg/5TO7z4IcayOw9+E/EWtMZLXd3JOcy04k3+fm23mgWrf5BPGa3XCZ9bq+ZBGubaXsbBn8Pudw405BvwblK2VwSx1TBT3LkI/esm2XbbXdSXrLieS95ZAdN0IexU8Qr9kNl1mv60sW4vIr5V5sqWNFhxE3H78sF1zbVB+4onFuUtyCxIDhotcKxp2HFZdfafrgduNmw6VZtk1NilvLH6Ei913bOO5JA4SLevfF/WqWmfX2rVnmV8odHTuSzXJ+Kd868h0q+1TPDlWGbTBv3ffonWQ4Okepdajor9RwqDpU3aSnwuNyK02bDpWdjx8K9QkXPbdAdN9rXFFjHyukjItg4odkKNRNtg6ePsVlpWkzFLLz8Qcx+oabYRvMW/c9jghk2If5qeN2xq1jggN7EAN2L7s7KVqD20r5gxgptcoDcsov5H74UbzPpcMGdOKD/DY1tj9n877Xsbwa7iSFB641ULGv3v7MkLWdhWv2gyt8Ygb6sQZtHrJ9RCvlDj/GI+mDKzhx4NmhYgfv6HePCO57HYW3ZkqCa3V33AdRxexZPdfj/35wxU8MOdYsWin7+lK0HVhcwSk/37ioCosF9z3Pn9Xwm3UgcCPeZ+584YqfaOrG891Xyry82lTP6A4sruBkvW/cDH4wb973PPOdwQ9pBgIXG7iec/eHK35iRqi4u7S7NtTpsVL6J5EeJ+t10jYaV+PqaFwdjaujcXU0ro7G1bg6GldH4+poXB2Nq6NxdTSuxj3kyYbZ2BBOJIqys9nHsrPxWdqm7OwoebwoBB5sRnOiNOGHQ9nmfby0unB4QXZ2LIHmLDJXxSwp0XSgJnvBgYh9ApodLp482XqVjsVYa2VeDvrXXK7GxdnoiluXnV2HHz6AHq2JJsMFmzfchJ41qiabuTTZWhL8EaXINsHEPeCOS+Z2xwVp1rh0s8XA9opwuHVNIKiawnBbheFPEbDJmyIbm9BWh9Obwnj2aFNTHXokjJYWLoIb9wDc2HVMrTJLggvY2NxUVBOyTzBxs5vccOnc1lqZl4Pmiy7IXqBxzc0Whv9nca06KTILzJrObHXbI3hW+H9YtNHs7IaEYEntYBrEjIVtE0xcVPFiXGZuMoNjPufrCzJuwo5bZxbmguwDpBCaQeVG6UY1K1uAiyoX/h88MWzNYy0pAhsK0QTz+WgRYlxmbguXvhxcuRt15fJ7uYhzn9uEHZobiMVGuMclXS9rn2zDxfvlMJmdMWSWhOdGS7Gtgr6aBPy7EOKyc1u49OVkc/tyjUu6QAkxLth1hqO4fwPmgF4bw0lxD8TQrpg+zeyO0SXxuOwqTFzQISsS4rJzu+HWpEt/Ki1wa+piCeE+N4Y2IxnXwPsHbO2ls1kGO9Q6566bWxJulmOwQ2RbhYkLOsM1DQJcbm7xPtd6tRrXFBDhNrDtXDjh2DuL9rl1dONyuOySwmSEhJ7FrcLCjWaTwuYXxc0txm2yRlwal8GF/ZKY2aECzXIN3ZY1gGRjKNKERjh07BET4sboxuVw2SXRodAC+CxugoWLh8IJvovnXAzzwvErL0Ij4JjGdeCSkqAbEHaQi8hxg2Z64KHOOkJVJO4t09JlZ+CWZB3EsK2CxQ3XcLiiufl9Ln7lRYl0Kt10xg3hfi8sxlAiDPtToaJEUtwY2ztK0KNYzJLI4ceaUFHMNoHBRWux4/Jzu+DC0o1qXB2Nq6NxdTSuxtXRuDoaV0fj6mhcHY1rSzjye5A/a1wFaQtuOhXltd9rXMXyZ0IL8517NK5K+e9T2dwU1biq2gZJ1wicbYB0jeDY3vDboOkaQbE9Zu4dZSf+Nli6RlBs//2OsrKyucHSNYJkGzRdIyC2ZSSB0jWCVLdB0zUCVbcB0zWCVbfB0jUCVreB0jWCVrdB0jUCV7ec7mthjatU3XK63wlrXKXqNjC6RhDrNii6RiDrNiC6RjDrNhi6yuFG/NVtIHRVw43e5LNug6BrKGtb5iOK6xrBrVv1dY0A163yukaQ61Z1XSPQdau4rhHsukW6p6qqawS8bmFOVFXXUMb2xlPRra91q7KuIrirsO2N/ahbhXUNVeoWpz91q66uErjRT03bsn7mxFNJs66SrqGW7R1l/da9ker+RuOmoe2t/a5boouz4H80bvrZzi0r07pK4UY/LSz0YfvZyltgVn4WJF3ZcVd9emMhiqftyoXbaBau9NAtvBH/oSz4jcZNj7qF8azbjyxaxPtRstotVETXUL9uxy3axmfRRwGpXUP9uiW2o994442kunecSJZYqIKuoXrdlqE2efT0CVtgll+MW2Yh7HXXnX76n4huoQq6huJ1W7YSYn6Ys4Vm+Wj4wEoX2q997TOFateQuG4LfdRtWT2sW8t2y5bJEPcnDlogC2m/+tUzTrxRldo1FLc9HVJO38IG7XlPZ2eiRQtpzzh5nDK1a6htWzYOSuZwuLfCh8Y522Moe8bJIOO+p8h+V1LcqE/bstmwVeZst0yAuLP59vgzhnbcuJO/p0btGkrXbVnZLQDyDR53C8S9hStakxbIjjvttLtOU6N2DaXr1hMX0v6Jb4+h7Gl33XWpIrqG0nXrgXudqD1GspfOnz9nznwVdA21bd1xne3xaRbtnAsumKOArqG2rSvuf3xN2B4T2XnzZs+eLb+uobatO66jE2UVLaKdffTR0usaatu640JZjhbJzgGy85Ds0Uefd57suobatq64G7j22FG0kPa8mTMl1zUks51aiG6+r5dyx3W2x/NM2vMQ7cxBg+TWNdS2dccVdKLmsUULaQcNGSK1riGXLU4frnN0w33QpRNFixbSAlugWyuvrkS4Gf2wdcf1ao8HoapFtpm5EteuPLirTu2HrTuubVBra48J7YYhmZmZVu1Wa9xDZFvbH9vr3HB/4tkeI9khUDYzMzf3/wrJrl622pUFN6N26h5wmzq10L8tPDMAcd/hbXNQ5c63D2ptshswbWZu5t8HPyCpriGL7R6Eu8d/3eIztf+AkjzucvjQTLf2mBTtBiKbm/v3wYOhLsKduqBL4x4CWxy/dWueqb0USu7jcNFFVIMEg1qrObZoc/++GGTwA8+RnYJUn75tSGU7eW7faD/76hlQcjmHOx0+5NUebyANMkjjLxfjPEBwC1/r0rgDaltIbKfO9d8e09N5UHIyh3s1uszG2T92FG1uY+Mvxy6GN0Z3qkS6EuC+UrinGN181a39ypmFwqsfl9kGtcQ2c4hZtI2AtvG2sTSLx0qom/64r0wtxpk89w4/7bHtyhmIe/EXLO47ENdtUEurFsg25j69d+zYveCGI5+uIY3tt+b2rT0mp/MedAx0Yatc79Y/xs0xtr1/LxugS3rs0ugaaW/7Nk4yW/5KxpPNM7Ub7ANdNMytOM9lUEuKtrGx4ssrrRDfB8i+f48kummO+8oef7bs20H4i6JusQ900TD3QZdBLSnaxoqKh668chq6scaS6RppbvtdHE9b18uP4ZmB86DlBAb3BvjAEHH/GNNWVFQ0/nUaF4ps6VZr3NRSXejDlh3U2q5kRIeP7QNdNMwVDWrNoq2o/3IYG4Z42gOg146yUeOmlo3v4XjY8u2xWbTMlTMPA8sbGFz4Dt1F3K6WDmoJbUXjzy8fRm8OY6ALs6d4icZNJSXEdvlcv4Na25Xl8MzA7GW2gS4c5j7MdKL4ooVl+8PLHaHM04YNe4D03ms0bipZ243iZit4e57oyhk00GVw4Zuvlwn7x1C2vvGnI7lcPtJOTHVLNG4qrTLGnet/UCu6cqbCNtBFH5vgGNQS2vpN/ztSHAb5R7gDv1njppCbJnSD2+S+9o9tV848yI+F0EioXlC0kDbzmpHjHRk5niGGmYZx6zRuCsmeANI9NHknyvNKxkwBboVjVwtk6xsvop7l5CY2vhx24N/+rsZNGXfCclEn6k8enSjblTOD+LEQGgnl5pr94wqTdl65ayxnSHwiHp9p3FRyICdnArgd43dQ63JlOX/S72qM29gHWhvx5ZNxJ/5ujZtC8nNwjulLe+y8cuZhbiyERkK29rg+c36534xfR2yL9VAopXwX2d781DHiQe24cX6uLEe4V5u4HyJctmgrZp5f7j/jJ8Me/Hvd723UuCllMynd+FBbe3xykrfncafzFnJjIdgqLyNFC22HWO3xca6xbNdNxuOz7rerNW6KI914PAf8l/PsUJf2+FLPK8vxQcaF7Ek/dMJvGS3a3Nnne6vaiddNhh14eGvWx5ZTzWtxxBsH+13Pg4xeVzJWsGMhNBJaCGkrhsw7wT8sDrJF+SShcQdEF2TfzUN9DWptRZsJT/o0sp8zhj5hbGHjkNlnCWFPENwY26FS2ab/ZTY1+ybF98UnxeNDP/oo+aDWdiUjOoCcy46F0EhoorMt5jzN8Mjrlk/Ag7MJjyY07sDoTkIBtev8zBmPt+dtsK6vWGSdOtiHro7jZJ2kgmBb0r/LkcRWhktbb5q0BepumTT0H8kHtc4rGTM3wQ9u3XYxfNtBzofojdcOWT+Rz1aKi9Lv3VIKAoSHJh/UMm/P27Tpp0f98IcjR468Bn9W+odvvIM/J/0uVtZv1i2/OSeObBsSGncgDzJ/UfoF9C0duvLSJINaVLSbfv7TH/94GDyBA2jHjx8/iPuKg0FeshMdN2qLu+3xnLUJjTug+fRXOKVDkw1qH/rFX++fNm3asGHDTNrx5eUPMra5Ylmb6UT+/shnUacd8EpkK8tbOFf/hWSo+6B20y+evn/v3iuvRLaYdiSiLS/fYH1ByaINDlnT0zXrsO2+eLwhoXEHPLXf/vZfwH9AV9geA9ixY8dCWtYWl235/GVcs7xsTp9gkS3psU+SqW4l+tgEoIsz9Gj7oPahp7+xeCyxtTXJsGznO75XaA4rmzzUdtKkuoTGPdS67NvzHroNvsGSs+Voy+8ithdPnz79Q6J7jYfs9VwmXg9swSCqFI6SJbOV6aOKLN2ZtGiB7OAktuX4u96m43fX55DvFRLAXu+SdU+Vkr66bLZSfcgYqwtkN31/8eDB1HYvb2vSls9E3xm1nH83ybZBvlxhLn+K9NR/JZ2tXB8PaOkOGfTQNwYPZm1RV8pRtuXHPSx8O8nDnrDnk6C6pf30lxIa9zDpYlrWdprQFr1V6Gru/bloxzv7LBgiyJOyGfYU6qSD/woSGvdw6S53t2Vojztug8un2Qw5i8v5Z1mgzMPAlkRGW+k+TNvUfdaX7XH1zk+Q24e6VGf5yDq5beX7GHyrZUa47rboAPJE5+ed4Asg/eBKXrcyfoGF1TI7bEdytifAzpIb7jKAdw28MUEPqFO3Un71DFO7HrYn4D6S88NsyMfZIM6LrrmICbiLkdWoWzm/NMrSddrytOef3+jywZ4V2PPai661Au4RZQCsgK2cX/dm6S4W2R430RzZnDXI5eMBhyDOq+zByCDTFLCV9IsaLd29DluramHjes02e3d5H/rq60sg5RX2XHUFQVbCVtavWGV0bbYcLUj9NsHHJmyrR5aXXHEJ/I8G3sfI9ythK+2XI1u6nK3VItM+7wXbuGNU+/CJoeMvcc8VlyhiK+/XmjO6lq29bGHQZcvb3rkVXf04HbXJ2yqA4VHwZgt6UBlbeXEZXXzsgpStjRZ0fOvJOfp33hhNflpoUz0e3Kzcf7MithLjMrrE1lm2cCh70UX1tisxFpqix9sCH3palbqVGpfRhccuaJPMlS0cyl51VS57oc2ixqN41mPJDedpZepWblxGF+9u7WWLaUHv96jPKe+iz49lSB05/jaFbOXGZXRPsJctQ4v6wF82fv75541fUlguXwE3HKVsJcdldCdytmhna9HinSzvCkW/Ysv3lbKVHZfR5W2FtKask1VFW+lxGd3ruRaZo8VFK3L9Z+ammq38uIyuvWwJLe4aO2gBJ5ev/Ktqtgrgsi0zsb32WlK2ZnvMwdpZcdSzVQGXq122bLkG2aI180/wRqOgrRK4rC7Z2zqqlodlUBW2VQOX0XWULVO0gnr9Ab79QElbRXAt3R9di2zZsuWKlmW18m9K2qqCy+gyZWu1yBwt63rZZT+4TFVbZXBZXYaW7R0TWaoKXElUtVUHl9G12TppqepJl50EoqytQriMLu1Iucti1pPUtlUJl9Xldra4d8zUrAn79ZO+rrCtUriMrqNsrZo1WVFUtlUL19L9l2M5WxstlQWZrLCtYriMroCWyJo58+tnKm2rGi6jS21daM+EUdtWOVxOl5StSWtV7JlBsFUPl9G1ypajNaO6rYK4jK6TlrqeA6K8rYq4iWJGl9g6aQNgqyRuZ46py9CSPe05JAGwVRKX1bVsWdrRgbBVE5fRvRrbckU7+pzRgbBVFJfVtar2TCIbFFtVcdmW2dYgjx4dFFtlcdnatWxHowTFVl1cTpeRZWxrEhpXAV2rRWZsaxMaVw1dWrajvxUcW6VxWd3R9v1tAGzVxuV0UaYGyVZt3LY2m65Vt21tGldOUpre3t5Rpu6E6TfETVswxZpN40rjCtx682BaUGKmrpVaPAnN1UucNW66y0LWlpaqqsrK9vb2cDjc1dXRscqhO7W6o6sLTG1vr6zsqaqCyhBY46azbW9eS1VPZXu4q7qjw+gEGTUqVrImY4lNd090TUls1Cg4Q0dHR3VXuB0Qt+QpqGuoZFvV017dAUljJSVrQMasWnXh3evz13K6xc3r9y/NiI4B00tKYrEYUO7oquxp6VWvbTaUsW3Lq6rsArSgWMdEMzKWLr3wwgub9q9v/qTu8Y3dJu1fVh9YW7Qksr8JTF2asSoaXQPLuLOjvQfWbpvGTU9c0Ca3VxujYmuiq4DrW02zZs3af3dkyeZHHw/dd+9zOV9A2i0TVt8XeunRfKC7f/+sWW8h4DFrYqB226vyejVu2rbKBBcULirbt5qagO76zY++FLrv1dU/+y+Qn/3sudV/C9V90rz+7lmzmpreunApsi0BuOFKjZvWzXJLTztql4EvaJmjq1ZlLIW7XNAu3/fqvfeuXr363r+9et+U0Nrm9bNAxYI2Ge134V6X2mrc9B0Igc5yexh1lWGnCnWroktnRZrXHnj88RDM44+/9NLaR5uXjiF9KdCZMkCHOQy7y226Q5Xm/WU4yu2prAQj3GqAjJhLxjRv3vwJSVHR5vzmJU2QFKBWg7EuHOqika4e56a/b1teLz44VQXSA4a9XbMikfVL1i+BWb9+fSQSufvuaE8PnIwPU/XmtfXqI1TyNNDWYcjeqgzYsQI95/2vPLPmLfBTE8wa87CjPrYscS8r+hbJE4+BPEPvleizQtKnd8yFJE/8obW1dfjwEnq/RONKb7uU5JQdrTDDW0voIzGNq4wtCOY1dV/WuFLbvkKCbAnu8JfpozGNK6/tGtP2sRGWbmurqTtK48pvO2IEh2vVbrXGld32m4+NsPEGRNdQ1jaDBNiCYFtLl06t1rjy2ZasIjnlmwCXFi61bR1+D51erXFltgWx2mWTNwi6hpq2URJsO2KEE7f1HjpPl8aVKTHellTuCNt+V31dFXFHjSGhthwtU7t0vi6NK62tg3dHQHSNANiOMGMvXnPesMaVwnYNySkcq7Bdbm19gs4d1riS2dp1rXZ5RyB0FcPtpFYrBLSM7Y5A1K6hpu0pM0SFy/ti4MdeVldXKdzOEpxnVohlhaX72MvkWSXtGjeNfxeqtGLGjBk+aaHuParqGoraCnX5VnmH8rpGkGx3uNXuKDV1DUVt/VSuRWzpVmrcNPw9YiQrZvSxUSa69PmVGjfd0kFtTvEl2+rIY08oqGsoaOvG61G5auoaKtr2o3KV1FUBt9pet34K1ymsnq4CuNWjSExbge6OZIULdTvpkio1bvraJjtA5SKsmq70uF1C2zc98oc/7HRLq6GUrqGM7ZszZvjlhcBi4R1K6RqK2K5Iovnmr3fuhtmZJFbt9mjcI5twJ8mKEVjQLTuzzCTxbX2eLrNH46aJ7YgRb3rw7s5iszsoujLjhg2SFTsA7q/dk2VPEl263B6Ne6TSbtrioU5S29vvPNhH3SqNe4Rtn0TD1t/9zt32dZDbX3wefmjcCwfhnde9dYdXq6FryG/busNTdyfSfKGD5Pk70f0kvSo1dGXFraRYT+KDS++++y7VtUGhun2h2sqdyUt35/AuuvwqjXvkbIcPT6ILKe+sZnMweenubFVBV07cSur04nCUd0kESmiP+yKH+4JVurvtMZ/37pN09iqNeyRtvXSh7UHOtjrso11mdVs07mG07SIhtmeTiHB3Z+3atavlN3wOgseydifL2WG6nhaNe9hswyT/ee65557Nx0mUtXXr1kfCfO4EjyXH3X32i/QJLRr38KSHtU2umwKu9LqG5LYO3bMFuHfytu1bfeLKrisbbk87yQvnnns7E6FtFrxBycp2Nj0IN2u3j9vZ5jPzNO6hTpVpe/u5vO7tvC4tzCyE28Lh9uLKVV9XLtyqSpJH4HkAt+DzevS2C1I+UmnlffjAriyfuf0F+rw8jXvYbEEOinF3c7qodP9o6bZsxa1yAHQNGW1brE1v9z140KGDSndrXg/KI6hN9l+4MI/0kORp3EOVFoGt01eguxUHfh/2B+TnrKwg6MqD20K3sM0WHkrMOmhGhLN1O58+2mZloW+ggunVuIfTFvOywEl1+2wrq64suHl06+a97hkXnF2M7a6sfuT9FpJejXtobOFX75ml2ifhXakVLkiehLpy4JpbNo9tif0Lv841y68HRVcK3DyaXqfhLpitLqEzYdS2Dz4muv2K9So07uGxTcaLA0U/yHv//ffz2lDDnKJum8YdqPR62/rgRbbvo+R93P/SlU43/XF7zSSvUA9c+ifyASrd/i3oj+ZLadO4A2/bL97tLG4v6TJv3bpLeV1DFtsPXNmSh10QUjFHRX1OmxmNm2raPG39+kJO86+Exe2Hr0y6huS2vnjBkj52w+2zr8YdYNwPfOxUvXHNP5Pe7Q7cPvLKo5vmuDgfb08pqFa9Sfq0PI07gLgp2m5n/kxAtrusq++6GncAcLcfAtztKfFKgvv/AgwAFMs/i24U9LAAAAAASUVORK5CYII=";
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
