package com.ox.api

import com.ox.CommitStage;

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method

@Transactional
class JenkinsService {
	
	private static final String BASE_URL = "http://localhost:8888"
	
    def createJob(String name){
		def http = new HTTPBuilder(BASE_URL)
		http.request(Method.POST,ContentType.XML) {
		 uri.path = '/jenkins/createItem'
		 uri.query = [name:"$name"]
		 body = '<project />'
		 headers.'Content-Type' = 'application/xml'
		 // response handler for a success response code:
		 response.success = { resp, json ->
		   println resp.status
		 }
		 // handler for any failure status code:
		 response.failure = { resp ->
		   println "Unexpected error: ${resp.status} : ${resp.statusLine.reasonPhrase}"
		 }
	   }
	}
}
