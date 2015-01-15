package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.HerokuDeployStage
import com.ox.api.StatusType

class HerokuDeployStageMarshaller extends StageMarshaller{
	
	@Override
	def register() {
		JSON.registerObjectMarshaller(HerokuDeployStage) { HerokuDeployStage stage ->;
			Map result = [:]
			result.put('id',stage.id)
			result.put('type',stage.type)
			result.put('url',stage.url)
			result.put('previous', stage.previous)
			result.put('next', stage.next)
			result.put('status', stage.status)
			
			Map statics = [:]
			statics.put('number',"# ${stage.number?stage.number:0}")
			if (StatusType.BUILDING.equals(stage.status)){
				def time = new Date().getTime() - stage.started
				Double progress = (time*100/stage.estimatedTime)
				statics.put('time', time)
				statics.put('progress', progress<100?progress.round(2):100)
			}else{
				statics.put('time',stage.time?stage.time:0)
				statics.put('progress', 100)
			}
			result.put('statics',statics)
			result
		}
	}
	
	def getListURL(){
		"$grailsApplication.config.grails.serverURL$LIST_URL"
	}
}