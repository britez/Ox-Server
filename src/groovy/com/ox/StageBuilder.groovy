package com.ox

import grails.converters.JSON

class StageBuilder {
	
	Map<StageType, Class> strategy = [:]
	
	StageBuilder(){
		strategy.put(StageType.COMMIT_STAGE, { req -> new CommitStage(JSON.parse(req)) })
	}
	
	def create(def req){
		strategy.get(new Stage(JSON.parse(req)).type)(req)
	}

}
