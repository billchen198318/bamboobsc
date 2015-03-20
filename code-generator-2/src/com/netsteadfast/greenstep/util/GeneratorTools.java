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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GeneratorTools extends JFrame {
	private static final long serialVersionUID = -3369949055691031244L;
	private JLabel label1;
	private JLabel label2;
	private JTextField textField1;
	private JTextField textField2;
	private JButton button1;
	private JButton button2;
	private ButtonActionListener buttonActionListener;
	
	public GeneratorTools() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.setTitle("Code Generator Tools");
		this.setSize(400, 200);
		this.getContentPane().setLayout(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.init();
		this.setVisible(true);
	}
	
	private void init() {
		this.buttonActionListener = new ButtonActionListener();
		
		this.label1=new JLabel("package:");
		this.label1.setBounds(5, 5, 80, 25);
		this.textField1=new JTextField("com.netsteadfast.greenstep");
		this.textField1.setBounds(85, 3, 250, 30);
		
		this.label2=new JLabel("head name:");
		this.label2.setBounds(5, 45, 80, 25);
		this.textField2=new JTextField("SysProg");
		this.textField2.setBounds(85, 42, 250, 30);
		
		this.button1=new JButton();
		this.button1.setText("OK");
		this.button1.setActionCommand(ButtonActionListener.BUTTON_AC_1);
		this.button1.addActionListener(this.buttonActionListener);
		this.button1.setBounds(85, 90, 100, 35);
		
		this.button2=new JButton();
		this.button2.setText("EXIT");
		this.button2.setActionCommand(ButtonActionListener.BUTTON_AC_2);
		this.button2.addActionListener(this.buttonActionListener);		
		this.button2.setBounds(190, 90, 100, 35);
		
		this.getContentPane().add(this.label1);
		this.getContentPane().add(this.label2);
		this.getContentPane().add(this.textField1);
		this.getContentPane().add(this.textField2);
		this.getContentPane().add(this.button1);
		this.getContentPane().add(this.button2);
	}
	
	private void doGenerator() throws Exception {
		CodeGeneratorUtil.execute(this.textField1.getText().trim(), this.textField2.getText().trim());
		JOptionPane.showMessageDialog(
				this, 
				"OK! please look generator source out directory\n" + System.getProperty("user.dir") + "/out", 
				this.getTitle(), 
				JOptionPane.INFORMATION_MESSAGE);		
	}
	
	private class ButtonActionListener implements ActionListener {
		public static final String BUTTON_AC_1 = "BTN01";
		public static final String BUTTON_AC_2 = "BTN02";
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (BUTTON_AC_1.equals(e.getActionCommand())) {
				try {
					GeneratorTools.this.doGenerator();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(
							GeneratorTools.this, 
							ex.getMessage().toString(), 
							GeneratorTools.this.getTitle(), 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			if (BUTTON_AC_2.equals(e.getActionCommand())) {
				System.exit(1);
			}
		}
		
	}
	
	public static void main(String args[]) throws Exception {
		new GeneratorTools();
	}
	
}
