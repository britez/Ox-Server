package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.Project
import com.ox.User

class ProjectMarshaller {
	
	private static final String LIST_URL="/me/projects"
	
	def grailsApplication
	def stageMarshaller
	def runMarshaller
	
	def register() {
		JSON.registerObjectMarshaller(Project) { Project project ->;
			return [id: project.id, 
					name: project.name,	
					description: project.description,
					stages: String.format(stageMarshaller.getListURL(),project.id),
					runs: String.format(runMarshaller.getListURL(), project.id),
					statics: [
						number: "# ${project.runNumber()}",
						time: project.time
						]
					]
		}
	}
	
	def getListURL(){
		"$grailsApplication.config.grails.serverURL$LIST_URL"
	}
}
