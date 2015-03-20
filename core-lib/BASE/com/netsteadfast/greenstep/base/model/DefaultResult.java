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
package com.netsteadfast.greenstep.base.model;

public class DefaultResult <T extends Object> implements DefaultResultProvide<T>, java.io.Serializable {
	private static final long serialVersionUID = -6204858044830805447L;
	private SystemMessage systemMessage;
	private T value;
	
	@Override
	public void setValue(T value) {
		this.value=value;
	}

	@Override
	public T getValue() {
		return this.value;
	}

	@Override
	public SystemMessage getSystemMessage() {
		return this.systemMessage;
	}

	@Override
	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage=systemMessage;
	}

}
