package com.ox.api.builder

import com.ox.Stage
import com.ox.api.StageType
import com.ox.api.builder.strategy.CommitStageJenkinsJobBuilder
import com.ox.api.builder.strategy.JenkinsJobBuilder

class JobBuilder {
	
	Map<StageType, JenkinsJobBuilder> strategy = [:]
	
	JobBuilder(){
		strategy.put(StageType.COMMIT_STAGE, new CommitStageJenkinsJobBuilder())
	}
	
	def build(Stage stage){
		strategy.get(stage.getType()).build(stage)
	}
}