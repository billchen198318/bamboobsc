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

import java.util.ArrayList;
import java.util.List;

import com.netsteadfast.greenstep.po.hbm.BbPerspective;
import com.netsteadfast.greenstep.po.hbm.BbSwot;
import com.netsteadfast.greenstep.vo.OrganizationVO;
import com.netsteadfast.greenstep.vo.VisionVO;

/**
 * 要產生 SWOT 頁面資料 or 報表資料用的 
 *
 */
public class SwotDataVO implements java.io.Serializable {
	private static final long serialVersionUID = 1865055649360283753L;
	
	/**
	 * 願景
	 */
	private VisionVO vision;
	
	/**
	 * 部門
	 */
	private OrganizationVO organization;
	
	/**
	 * BB_PERSPECTIVE 資料
	 */
	private List<BbPerspective> perspectives = new ArrayList<BbPerspective>();
	
	/**
	 * BB_SWOT 資料
	 */
	private List<BbSwot> contents = new ArrayList<BbSwot>();
	
	/**
	 * 要顯示出來的內容
	 */
	private List<SwotIssuesVO> issues = new ArrayList<SwotIssuesVO>();

	public VisionVO getVision() {
		return vision;
	}

	public void setVision(VisionVO vision) {
		this.vision = vision;
	}

	public OrganizationVO getOrganization() {
		return organization;
	}

	public void setOrganization(OrganizationVO organization) {
		this.organization = organization;
	}

	public List<BbPerspective> getPerspectives() {
		return perspectives;
	}

	public void setPerspectives(List<BbPerspective> perspectives) {
		this.perspectives = perspectives;
	}

	public List<BbSwot> getContents() {
		return contents;
	}

	public void setContents(List<BbSwot> contents) {
		this.contents = contents;
	}

	public List<SwotIssuesVO> getIssues() {
		return issues;
	}

	public void setIssues(List<SwotIssuesVO> issues) {
		this.issues = issues;
	}
	
}
