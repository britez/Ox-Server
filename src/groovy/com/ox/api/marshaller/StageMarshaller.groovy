package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.Stage

class StageMarshaller {
	
	protected static final String LIST_URL="/me/projects/%s/stages"
	
	def grailsApplication
	
	def register() {
		JSON.registerObjectMarshaller(Stage) { Stage stage ->;
			return [id: stage.id]
		}
	}
	
	def getListURL(){
		"$grailsApplication.config.grails.serverURL$LIST_URL"
	}
}
