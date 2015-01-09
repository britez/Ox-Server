package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.CommitStage
import com.ox.HerokuDeployStage

class HerokuDeployStageMarshaller extends StageMarshaller{
	
	@Override
	def register() {
		JSON.registerObjectMarshaller(HerokuDeployStage) { HerokuDeployStage stage ->;
			return [id: stage.id, type: stage.type, 
					url: stage.url,
					previous: stage.previous,
					next: stage.next]
		}
	}
	
	def getListURL(){
		"$grailsApplication.config.grails.serverURL$LIST_URL"
	}
}