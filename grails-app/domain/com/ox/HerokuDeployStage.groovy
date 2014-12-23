package com.ox

import com.ox.api.StageType;

class HerokuDeployStage extends Stage{

	String url
	String username
	String password
	
	static belongsTo = [owner: Project]
	
	@Override
	String getType(){
		StageType.HEROKU_DEPLOY_STAGE
	}
}