package com.ox.api

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

import com.ox.Stage
import com.ox.api.exception.JenkinsCommunicationException

@Transactional
class JenkinsService {
	
	def jobBuilder
	def grailsApplication
	
	def create(Stage stage){
		perform("${stage.owner.name}-${stage.type}",jobBuilder.build(stage))
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
	
	def String getSSHPub(){
		URL uri = this.getClass().getClassLoader().getResource("/home/maxi/.ssh/id_rsa.pub")
		new File(uri.getPath()).text
	}
}