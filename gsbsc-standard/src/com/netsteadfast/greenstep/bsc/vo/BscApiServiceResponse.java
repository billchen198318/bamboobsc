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
package com.netsteadfast.greenstep.bsc.vo;

import com.netsteadfast.greenstep.base.model.YesNo;
import com.netsteadfast.greenstep.vo.VisionVO;

public class BscApiServiceResponse implements java.io.Serializable {
	private static final long serialVersionUID = 3784147952012520482L;
	private String success = YesNo.NO;
	private String message = "";	
	private VisionVO vision;
	private String htmlBodyUrl = ""; // 產生一組 url , 顯示HTML報表內容 
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public VisionVO getVision() {
		return vision;
	}
	public void setVision(VisionVO vision) {
		this.vision = vision;
	}
	public String getHtmlBodyUrl() {
		return htmlBodyUrl;
	}
	public void setHtmlBodyUrl(String htmlBodyUrl) {
		this.htmlBodyUrl = htmlBodyUrl;
	}
	
}
