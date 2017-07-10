package com.netsteadfast.greenstep.test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class StrategyMapBackgroundImageTest {
	
	public static void main(String args[]) throws Exception {
		int width = 800, height = 600;

		// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
		// into integer pixels
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bi.createGraphics();
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		int fontLeft = 3;
		int fontAddY = 150;
		int fontNowY = 80;
		int lineAddY = 60;
		
		// Financial
		Font font = new Font("", Font.BOLD, 14);
		g2.setFont(font);
		String message = "Financial";
		
		// 原本純白的底
		//g2.setColor(Color.WHITE);
		//g2.fillRect(0, 0, width, height);
		
		// 填滿 grid 圖片的底
		BufferedImage bg = ImageIO.read( new File("/home/git/bamboobsc/gsbsc-web/WebContent/images/s-map-bg-grid.png") );
		for (int y=0; y<height; y+=bg.getHeight()) {
			for (int x=0; x<width; x+=bg.getWidth()) {
				g2.drawImage(bg, x, y, null);
			}
		}
		
		g2.setPaint( new Color(64, 64, 64) );
		g2.drawString(message, fontLeft, fontNowY);
		
		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2.setStroke(dashed);
		g2.drawLine(0, fontNowY+lineAddY, width, fontNowY+lineAddY);
		
		fontNowY = fontNowY + fontAddY; 
		
		
		// Customer
		message = "Customer";
		g2.drawString(message, fontLeft, fontNowY); 
		
		g2.setStroke(dashed);
		g2.drawLine(0, fontNowY+lineAddY, width, fontNowY+lineAddY);				
		
		fontNowY = fontNowY + fontAddY;
		
		
		// Internal business processes
		message = "Internal business processes";
		g2.drawString(message, fontLeft, fontNowY);
		
		g2.setStroke(dashed);
		g2.drawLine(0, fontNowY+lineAddY, width, fontNowY+lineAddY);				
		
		fontNowY = fontNowY + fontAddY; 
		
		
		// Learning and growth
		message = "Learning and growth";
		g2.drawString(message, fontLeft, fontNowY);
		
		g2.setStroke(dashed);
		g2.drawLine(0, fontNowY+lineAddY, width, fontNowY+lineAddY);				
		
		fontNowY = fontNowY + fontAddY; 
		
		
		ImageIO.write(bi, "PNG", new File("/tmp/ex/strategy-map-background-"+System.currentTimeMillis()+".png"));		
	}
	
}
