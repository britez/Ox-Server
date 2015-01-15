package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.Project
import com.ox.api.StatusType

class ProjectMarshaller {
	
	private static final String LIST_URL="/me/projects"
	
	def grailsApplication
	def stageMarshaller

	def register() {
		JSON.registerObjectMarshaller(Project) { Project project ->;
			Map result = [:]
			result.put('id',project.id)
			result.put('name',project.name)
			result.put('description',project.description)
			result.put('stages',String.format(stageMarshaller.getListURL(),project.id))
			result.put('status', project.status)
			
			Map statics = [:]
			statics.put('number',"# ${project.number?project.number:0}")
			if (StatusType.BUILDING.equals(project.status)){
				def time = new Date().getTime() - project.started
				Double progress = (time*100/project.estimatedTime)
				statics.put('time', time)
				statics.put('progress', progress<100?progress.round(2):100)
			}else{
				statics.put('time',project.time?project.time:0)
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
