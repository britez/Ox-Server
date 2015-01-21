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
	
	void merge(CommitStage stage){
		this.url = stage.url
		this.branch = stage.branch
	}
}
