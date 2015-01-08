package com.ox.api

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import com.ox.Project
import com.ox.Stage
import com.ox.api.exception.JenkinsBussinessException
import com.ox.api.exception.JenkinsCommunicationException

@Transactional
class JenkinsService {
	
	def jobBuilder
	def statusBuilder
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
		String name = getId(project.name)
		http.request(Method.GET,ContentType.JSON) {
		 uri.path = "$context/job/${name}/lastBuild/api/json"
		 headers.'Content-Type' = 'application/json'
		 response.success = { resp, json ->
			 project.time = json.duration.toLong()
			 project.status = statusBuilder.getStatus(json) 
			 project.save()
		 }
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
	
	def run(Project project){
		def base = grailsApplication.config.grails.jenkins.base
		def context = grailsApplication.config.grails.jenkins.context
		def aBody = jobBuilder.build(project)
		def http = new HTTPBuilder(base)
		def name = getId(project.name)
		http.request(Method.POST,ContentType.XML) {
		 uri.path = "$context/job/${name}/build"
		 headers.'Content-Type' = 'application/xml'
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
	
	def delete(Project project){
		performDelete(project.name)
	}
	
	def delete(Stage stage){
		performDelete("${stage.owner.name}-${stage.type}")
	}
	
	private performDelete(String name){
		def base = grailsApplication.config.grails.jenkins.base
		def context = grailsApplication.config.grails.jenkins.context
		def http = new HTTPBuilder(base)
		def code = getId(name)
		http.request(Method.POST,ContentType.XML) {
		 uri.path = "$context/job/${code}/doDelete"
		 headers.'Content-Type' = 'application/xml'
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
	
	private update(Project project){
		def base = grailsApplication.config.grails.jenkins.base
		def context = grailsApplication.config.grails.jenkins.context
		def aBody = jobBuilder.build(project)
		def http = new HTTPBuilder(base)
		def name = getId(project.name)
		http.request(Method.POST,ContentType.XML) {
		 uri.path = "$context/job/${name}/config.xml"
		 body = aBody
		 headers.'Content-Type' = 'application/xml'
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
	
    private perform(String name, String aBody){
		log.info("Attempt to create job: $name")
		log.info("Attempt to create body: $aBody")
		def base = grailsApplication.config.grails.jenkins.base
		def context = grailsApplication.config.grails.jenkins.context
		log.info("URL: $base$context/createItem")
		def code = getId(name)
		def http = new HTTPBuilder(base)
		http.request(Method.POST,ContentType.XML) {
		 uri.path = "$context/createItem"
		 uri.query = [name:"$code"]
		 body = aBody
		 headers.'Content-Type' = 'application/xml'
		 response.'404' = { resp -> throw new JenkinsBussinessException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") } 
		 response.failure = { resp -> throw new JenkinsCommunicationException(message: "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}") }
	   }
	}
	
	private String getId(String name){
		name.replace(" ","")
	}
}