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
package com.netsteadfast.greenstep.model;

import java.io.File;
import java.util.List;

import com.netsteadfast.greenstep.po.hbm.TbSysFtpTranSegm;
import com.netsteadfast.greenstep.vo.SysFtpTranVO;
import com.netsteadfast.greenstep.vo.SysFtpVO;

public class SystemFtpClientResultObj implements java.io.Serializable {	
	private static final long serialVersionUID = -1582579875875921071L;
	private SysFtpVO sysFtp;
	private SysFtpTranVO sysFtpTran;
	private List<TbSysFtpTranSegm> sysFtpTranSegms;
	private List<String> names; // 要抓取的檔案名稱	
	private List<File> files; // 抓在本機上的檔案	
	private List<SystemFtpClientData> datas; // 轉過的資料
	
	public SysFtpVO getSysFtp() {
		return sysFtp;
	}

	public void setSysFtp(SysFtpVO sysFtp) {
		this.sysFtp = sysFtp;
	}

	public SysFtpTranVO getSysFtpTran() {
		return sysFtpTran;
	}

	public void setSysFtpTran(SysFtpTranVO sysFtpTran) {
		this.sysFtpTran = sysFtpTran;
	}

	public List<TbSysFtpTranSegm> getSysFtpTranSegms() {
		return sysFtpTranSegms;
	}

	public void setSysFtpTranSegms(List<TbSysFtpTranSegm> sysFtpTranSegms) {
		this.sysFtpTranSegms = sysFtpTranSegms;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}
	
	public List<File> getFiles() {
		return files;
	}
	
	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<SystemFtpClientData> getDatas() {
		return datas;
	}

	public void setDatas(List<SystemFtpClientData> datas) {
		this.datas = datas;
	}
	
}
