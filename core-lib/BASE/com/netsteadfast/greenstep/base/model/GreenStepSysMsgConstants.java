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

public interface GreenStepSysMsgConstants {
	// ?_BSE_? B=base S=system E=error 
	// ?_UOS_? UO=user-operation S=status-msg
	// ?_STD_? standard message
	// ?_DOS_? DO=data-operation S=status-msg
	
	// system error message
	public static final String NO_PERMISSION="MSG_BSE0001"; // No permission!
	public static final String NO_LOGIN_ACCESS_DENIED="MSG_BSE0002"; // No sign-on system access denied!
	public static final String DOZER_MAPPER_ID_BLANK = "MSG_BSE0003"; // error. dozer mapper id blank!
	
	// service or controller operation error message
	public static final String PARAMS_BLANK = "MSG_UOS0001"; // parameter cann't blank!
	public static final String PARAMS_INCORRECT = "MSG_UOS0002"; // parameter is incorrect
	public static final String OBJ_NULL = "MSG_UOS0003"; // object null!
	public static final String CONTROLLER_TRY_AGAIN = "MSG_UOS0004"; // Please try again!
	
	// data operation message
	public static final String DATA_NO_EXIST = "MSG_DOS0001"; // data no exist!
	public static final String DATA_IS_EXIST = "MSG_DOS0002"; // data is exist!
	public static final String UPDATE_SUCCESS = "MSG_DOS0003"; // update data success!
	public static final String UPDATE_FAIL = "MSG_DOS0004"; // update data fail!
	public static final String INSERT_SUCCESS = "MSG_DOS0005"; // insert data success!
	public static final String INSERT_FAIL = "MSG_DOS0006"; // insert data fail!
	public static final String DELETE_SUCCESS = "MSG_DOS0007"; // delete data success!
	public static final String DELETE_FAIL = "MSG_DOS0008"; // delete data fail!
	public static final String SEARCH_NO_DATA = "MSG_DOS0009"; // search no data!
	public static final String DATA_CANNOT_DELETE = "MSG_DOS0010"; // Data to be used, and can not be deleted!
	
	// standard message
	public static final String LOGIN_FAIL = "MSG_STD0001"; // Login fail!
	public static final String UPLOAD_FILE_TYPE_ERROR = "MSG_STD0002"; // Upload a file type error!
	public static final String UPLOAD_FILE_NO_SELECT="MSG_STD0003"; // Please select a file!
	public static final String UPLOAD_FILE_ONLY_IMAGE="MSG_STD0004"; // Please upload a image file!
	public static final String DATA_ERRORS = "MSG_STD0005"; // Data errors!
	
}
