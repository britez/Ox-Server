package com.ox

import com.ox.api.StageType;

class CommitStage extends Stage{
	
	String url
	String branch
	
	static belongsTo = [owner: Project]
	
	@Override
	String getType(){
		StageType.COMMIT_STAGE
	}
	
	static c
}
