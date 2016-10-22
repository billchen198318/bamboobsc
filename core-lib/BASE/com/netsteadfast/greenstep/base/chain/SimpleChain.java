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
package com.netsteadfast.greenstep.base.chain;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.apache.commons.lang3.StringUtils;

import com.netsteadfast.greenstep.base.ChainConstants;
import com.netsteadfast.greenstep.base.model.ChainResultObj;

public class SimpleChain extends ChainBase {
	
	public SimpleChain() {
		super();	 
	}
	
	public Catalog getCatalog(String resourceConfig, String catalogName) throws Exception {
		ConfigParser parser = new ConfigParser();
		parser.parse( this.getClass().getResource(resourceConfig) );
		Catalog catalog = CatalogFactoryBase.getInstance().getCatalog(catalogName);
		return catalog;
	}
	
	private ChainResultObj getResult(Context context) throws Exception {
		ChainResultObj resultObj = new ChainResultObj();
		resultObj.setValue( context.get(ChainConstants.CHAIN_RESULT) );
		resultObj.setMessage( StringUtils.defaultString((String)context.get(ChainConstants.CHAIN_MESSAGE)) );		
		return resultObj;		
	}
	
	public ChainResultObj getResultFromClass(Class<Command>[] clazz) throws Exception {
		Context context = new ContextBase();
		return this.getResultFromClass(clazz, context);
	}
	
	public ChainResultObj getResultFromResource(String commandName) throws Exception {	
		Context context = new ContextBase();
		return this.getResultFromResource(commandName, context);
	}
	
	public ChainResultObj getResultFromClass(Class<Command>[] clazz, Context context) throws Exception {
		for (int i=0; i<clazz.length; i++) {
			this.addCommand(clazz[i].newInstance());
		}						
		this.execute(context);
		return this.getResult(context);
	}
	
	public ChainResultObj getResultFromResource(String commandName, Context context) throws Exception {
		Catalog catalog = this.getCatalog( ChainConstants.CHAIN_RESOURCE_CONFIG, ChainConstants.CHAIN_CATALOG );		
		this.addCommand( catalog.getCommand(commandName) );				
		this.execute(context);
		return this.getResult(context);
	}	
	
}
