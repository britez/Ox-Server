package com.ox.api.builder

import com.ox.Project
import com.ox.Stage
import com.ox.api.StageType
import com.ox.api.StatusType;

class StatusBuilder {
	
	String getStatus(def json){
		Boolean isBuilding = json.building
		String status = json.result
		if(isBuilding){
			return StatusType.BUILDING
		}
		if(StatusType.SUCCESS.toUpperCase().equals(status)){
			return StatusType.SUCCESS
		}
		return StatusType.FAILED
	}
	
}