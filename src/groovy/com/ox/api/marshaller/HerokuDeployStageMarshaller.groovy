package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.CommitStage

class HerokuDeployStageMarshaller extends StageMarshaller{
	
	@Override
	def register() {
		JSON.registerObjectMarshaller(CommitStage) { CommitStage stage ->;
			return [id: stage.id, type: stage.type, 
					url: stage.url, branch: stage.branch,
					previous: stage.previous,
					next: stage.next]
		}
	}
	
	def getListURL(){
		"$grailsApplication.config.grails.serverURL$LIST_URL"
	}
}