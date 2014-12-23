package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.Run

class RunMarshaller {
	
	protected static final String LIST_URL="/me/projects/%s/runs"
	
	def grailsApplication
	
	def register() {
		JSON.registerObjectMarshaller(Run) { Run run ->
			return [id: run.id]
		}
	}
	
	def getListURL(){
		"$grailsApplication.config.grails.serverURL$LIST_URL"
	}
}
