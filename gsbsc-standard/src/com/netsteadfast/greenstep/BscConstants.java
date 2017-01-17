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
package com.netsteadfast.greenstep;

public class BscConstants {
	
	/**
	 * 空值的 BB_ORGANIZATION.ORG_ID , 當沒有父組織的部門其 BB_ORGANIZATION_PAR.PAR_ID 為 0000000000
	 */
	public final static String ORGANIZATION_ZERO_ID="0000000000";
	
	/**
	 * 空值的 BB_EMPLOYEE.OID , 當沒有主管關聯時 BB_EMPLOYEE_HIER.SUP_OID 為 0000000000
	 */
	public final static String EMPLOYEE_HIER_ZERO_OID="0000000000";
	
	/**
	 * 部門選擇的checkbox 的 id 與 name , 其組成方式為 TREE_CBOX:加上OID , 如 TREE_CBOX:3ba52439-6756-45e8-8269-ae7b4fb6a3dc
	 */
	public final static String ORGA_SELECT_CHECKBOX_ID_DELIMITER = "TREE_CBOX:";
	
	/**
	 * 部門選擇的checkbox 的 javascript function
	 */
	@Deprecated
	public final static String ORGA_SELECT_CHECKBOX_FN = "BSC_PROG001D0002Q_S00_putValue(\"${checkBoxId}\", \"${oid}\")";
	
	/**
	 * bb_vision.VIS_ID 的開頭固定字串 
	 */
	public final static String HEAD_FOR_VIS_ID = "VIS";
	
	/**
	 * bb_perspective.PER_ID 的開頭固定字串
	 */
	public final static String HEAD_FOR_PER_ID = "PER";
	
	/**
	 * bb_objective.OBJ_ID 的開頭固定字串
	 */
	public final static String HEAD_FOR_OBJ_ID = "OBJ";
	
	/**
	 * 給KPI報表 opw , 參數 nextType 要用的
	 */
	public final static String HEAD_FOR_KPI_ID = "KPI";
	
	/**
	 * 員工grid選擇取checkbox 的 id 與 name , 其組成方式為 GRID_CBOX:加上OID , 如 GRID_CBOX:3ba52439-6756-45e8-8269-ae7b4fb6a3dc
	 */	
	public final static String EMPL_SELECT_CHECKBOX_ID_DELIMITER = "GRID_CBOX:";
	
	/**
	 * KPI tree樹狀選單, 非 KPI 項目的id開頭, 如 NOT-KPI:3ba52439-6756-45e8-8269-ae7b4fb6a3dc
	 */
	public final static String KPI_TREE_NOT_ITEM = "NOT-KPI:";
	
	/**
	 * 不區分員工的衡量資料
	 */
	public final static String MEASURE_DATA_EMPLOYEE_FULL = "*";
	
	/**
	 * 不區分部門的衡量資料
	 */
	public final static String MEASURE_DATA_ORGANIZATION_FULL = "*";
	
	/**
	 * 衡量數據(TARGET)輸入欄位的開頭
	 */
	public final static String MEASURE_DATA_TARGET_ID = "MEASURE_DATA_TARGET:";
	
	/**
	 * 衡量數據(ACTUAL)輸入欄位的開頭
	 */
	public final static String MEASURE_DATA_ACTUAL_ID = "MEASURE_DATA_ACTUAL:";
	
	/**
	 * 當前輸入衡量資料屬於不區分
	 */
	public static final String MEASURE_DATA_FOR_ALL = "all"; // 當前輸入衡量資料屬於不區分
	
	/**
	 * 當前輸入部門衡量資料
	 */
	public static final String MEASURE_DATA_FOR_ORGANIZATION = "organization"; // 當前輸入部門衡量資料
	
	/**
	 * 當前輸入員工衡量資料	
	 */
	public static final String MEASURE_DATA_FOR_EMPLOYEE = "employee"; // 當前輸入員工衡量資料	
	
	/**
	 * 權重perspective輸入欄位 id
	 */
	public static final String WEIGHT_PERSPECTIVE_ID = "PER:";
	
	/**
	 * 權重objective輸入欄位 id
	 */
	public static final String WEIGHT_OBJECTIVE_ID = "OBJ:";
	
	/**
	 * 權重KPI輸入欄位 id
	 */
	public static final String WEIGHT_KPI_ID = "KPI:";
	
	/**
	 * 最小的顏色分數
	 */
	public static final int SCORE_COLOR_MIN_VALUE = -999999999;
	
	/**
	 * 最大的顏色分數
	 */
	public static final int SCORE_COLOR_MAX_VALUE = 999999999;
	
	/**
	 * itext 產 pdf 用的字型檔
	 */
	public static final String PDF_ITEXT_FONT = "fonts/fireflysung.ttf";
	
	/**
	 * SWOT 頁面輸入方塊的id區別 BSC_PROG002D0008Q:TYPE:VIS_ID:PER_ID:ORG_ID
	 */
	public static final String SWOT_TEXT_INPUT_ID_SEPARATE = ":";
	
	/**
	 * 360 最多的評分等級 如: 1 - Very Bad , 2 - Bad , 3 - Normal , 4 - Good , 5 - Very Good , 6 - Extreme
	 */
	public static final int MAX_DEGREE_FEEDBACK_LEVEL_SIZE = 6;
	
	/**
	 * 360 最多的評分相目數量
	 */
	public static final int MAX_DEGREE_FEEDBACK_ITEM_SIZE = 10;
	
}
