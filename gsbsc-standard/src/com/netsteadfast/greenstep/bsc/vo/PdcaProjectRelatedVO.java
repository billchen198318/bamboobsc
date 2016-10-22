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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.netsteadfast.greenstep.vo.PdcaVO;

public class PdcaProjectRelatedVO implements java.io.Serializable {
	private static final long serialVersionUID = -1945657965873731112L;
	
	private PdcaVO project;
	private List<PdcaVO> parent = new LinkedList<PdcaVO>();
	private List<PdcaVO> child = new LinkedList<PdcaVO>();
	
	public PdcaVO getProject() {
		return project;
	}
	public void setProject(PdcaVO project) {
		this.project = project;
	}
	public List<PdcaVO> getParent() {
		return parent;
	}
	public void setParent(List<PdcaVO> parent) {
		this.parent = parent;
	}
	public List<PdcaVO> getChild() {
		return child;
	}
	public void setChild(List<PdcaVO> child) {
		this.child = child;
	}
	
	/**
	 * 給 PDCA report orgChart 用的資料
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrgData() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> nowNode = null;
		for (PdcaVO pdca : this.parent) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("name", pdca.getTitle());
			node.put("title", pdca.getStartDateDisplayValue() + " - " + pdca.getEndDateDisplayValue());
			node.put("children", new ArrayList<Map<String, Object>>() );
			if (data.size() == 0) {
				data.putAll(node);
			} else {
				List<Map<String, Object>> children = (List<Map<String, Object>>) nowNode.get("children");
				children.add(node);
			}
			nowNode = node;
		}
		if (nowNode == null) {
			data.put("name", "<font color='#8A0808'>" + project.getTitle() + "</font>");
			data.put("title", "<font color='#8A0808'>" + project.getStartDateDisplayValue() + " - " + project.getEndDateDisplayValue() + "</font>");
			data.put("children", new ArrayList<Map<String, Object>>() );			
			nowNode = data;
		} else {
			List<Map<String, Object>> children = (List<Map<String, Object>>) nowNode.get("children");
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("name", "<font color='#8A0808'>" + project.getTitle() + "</font>");
			node.put("title", "<font color='#8A0808'>" + project.getStartDateDisplayValue() + " - " + project.getEndDateDisplayValue() + "</font>");
			node.put("children", new ArrayList<Map<String, Object>>() );
			children.add(node);		
			nowNode = node;
		}
		for (PdcaVO pdca : this.child) {
			Map<String, Object> node = new HashMap<String, Object>();
			node.put("name", pdca.getTitle());
			node.put("title", pdca.getStartDateDisplayValue() + " - " + pdca.getEndDateDisplayValue());
			node.put("children", new ArrayList<Map<String, Object>>() );
			if (nowNode == null) {
				data.putAll(node);
			} else {
				List<Map<String, Object>> children = (List<Map<String, Object>>) nowNode.get("children");
				children.add(node);
			}
			nowNode = node;
		}
		return data;
	}
	
}
