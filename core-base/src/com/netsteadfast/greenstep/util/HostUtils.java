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

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.ProtocolHandler;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.coyote.http11.Http11Protocol;

public class HostUtils {
	
	public static int getHttpPort() {
		int port = 8080;
		MBeanServer mBeanServer = MBeanServerFactory.findMBeanServer(null).get(0);
		ObjectName name;
		try {
			name = new ObjectName("Catalina", "type", "Server");
			try {
				Server server = (Server) mBeanServer.getAttribute(name, "managedResource");
				Service[] services = server.findServices();
				for (Service service : services) {
					for (Connector connector : service.findConnectors()) {
						ProtocolHandler protocolHandler = connector.getProtocolHandler();
			            if (protocolHandler instanceof Http11Protocol 
			            		|| protocolHandler instanceof Http11AprProtocol
			            		|| protocolHandler instanceof Http11NioProtocol) {
			            	port = connector.getPort();
			            }						
					}
				}
			} catch (AttributeNotFoundException e) {
				e.printStackTrace();
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (MBeanException e) {
				e.printStackTrace();
			} catch (ReflectionException e) {
				e.printStackTrace();
			}
		} catch (MalformedObjectNameException e) {
			e.printStackTrace();
		}
		return port;
	}
	
	public static String getHostAddress() {
		String hostAddress = "";		
		try {
			Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
			for ( ; nics.hasMoreElements() && "".equals(hostAddress); ) {
				NetworkInterface interfece = nics.nextElement();
				if ( interfece.getName().toLowerCase().startsWith("lo") ) {
					continue;
				}
				Enumeration<InetAddress> addrs = interfece.getInetAddresses();
				for ( ; addrs.hasMoreElements() && "".equals(hostAddress); ) {
					InetAddress addr = addrs.nextElement();
					if ( addr.getHostAddress().indexOf(":") > -1 ) {
						continue;
					}
					hostAddress = addr.getHostAddress();
				}					
			}			
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if (StringUtils.isBlank(hostAddress)) {
			hostAddress = "127.0.0.1";
		}
		return hostAddress;
	}
	
	public static void main(String args[]) throws Exception {
		Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
		for ( ; nics.hasMoreElements(); ) {
			System.out.println("--------------------------------------------------------------------------");
			NetworkInterface interfece = nics.nextElement();
			System.out.println( interfece.getName() );
			Enumeration<InetAddress> addrs = interfece.getInetAddresses();
			for ( ; addrs.hasMoreElements(); ) {
				InetAddress addr = addrs.nextElement();
				System.out.println( addr.getHostAddress() );
			}
		}
	}
	
}
