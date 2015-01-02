package com.ox.api

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import com.ox.Project
import com.ox.Stage
import com.ox.api.exception.JenkinsCommunicationException

@Transactional
class JenkinsService {
	
	def jobBuilder
	def grailsApplication
	
	def create(Stage stage){
		perform("${stage.owner.name}-${stage.type}",jobBuilder.build(stage))
		update(stage.owner)
	}
	
	def create(Project project){
		perform("${project.name}",jobBuilder.build(project))
	}
	
	def get(Project project){
		def base = grailsApplication.config.grails.jenkins.base
		def context = grailsApplication.config.grails.jenkins.context
		def http = new HTTPBuilder(base)
		http.request(Method.GET,ContentType.JSON) {
		 uri.path = "$context/job/${project.name}/lastBuild/api/json"
		 headers.'Content-Type' = 'application/json'
		 response.success = { resp, json ->
			 project.time = json.duration.toLong()
			 project.save()
		 }
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
	
	private update(Project project){
		def base = grailsApplication.config.grails.jenkins.base
		def context = grailsApplication.config.grails.jenkins.context
		def aBody = jobBuilder.build(project)
		def http = new HTTPBuilder(base)
		http.request(Method.POST,ContentType.XML) {
		 uri.path = "$context/job/${project.name}/config.xml"
		 body = aBody
		 headers.'Content-Type' = 'application/xml'
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
	
    private perform(String name, String aBody){
		def base = grailsApplication.config.grails.jenkins.base
		def context = grailsApplication.config.grails.jenkins.context
		def http = new HTTPBuilder(base)
		http.request(Method.POST,ContentType.XML) {
		 uri.path = "$context/createItem"
		 uri.query = [name:"$name"]
		 body = aBody
		 headers.'Content-Type' = 'application/xml'
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
}