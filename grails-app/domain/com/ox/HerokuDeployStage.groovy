package com.ox

class HerokuDeployStage extends Stage{

	String url
	
	static belongsTo = [owner: Project]
	
	@Override
	String getType(){
		StageType.HEROKU_DEPLOY_STAGE
	}
}