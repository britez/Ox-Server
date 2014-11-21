package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.CommitStage

class CommitStageMarshaller extends StageMarshaller{
	
	@Override
	def register() {
		JSON.registerObjectMarshaller(CommitStage) { CommitStage stage ->;
			return [id: stage.id, type: stage.type, gitInfo: stage.gitInfo]
		}
	}
	
	def getListURL(){
		"$grailsApplication.config.grails.serverURL$LIST_URL"
	}
}
