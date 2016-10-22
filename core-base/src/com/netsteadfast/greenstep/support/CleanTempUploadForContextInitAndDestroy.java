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
package com.netsteadfast.greenstep.support;

import javax.servlet.ServletContextEvent;

import com.netsteadfast.greenstep.base.Constants;
import com.netsteadfast.greenstep.base.model.ContextInitializedAndDestroyedBean;
import com.netsteadfast.greenstep.util.UploadSupportUtils;

public class CleanTempUploadForContextInitAndDestroy extends ContextInitializedAndDestroyedBean {
	private static final long serialVersionUID = 4978459389696832232L;

	@Override
	public void execute(ServletContextEvent event) throws Exception {
		UploadSupportUtils.cleanTempUpload();
		// 需要獨立處理的部份 tb_sys 沒有加入的系統
		if (Constants.getMainSystem().equals(Constants.getSystem())) {
			/**
			 * BSC-MOBILE 必需與 gsbsc-mobile-web 的 applicationContext-appSettings.properties 設定檔 base.system 的 id 一樣
			 */
			UploadSupportUtils.cleanTempUpload("BSC-MOBILE");
		}
	}

}
