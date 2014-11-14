package com.ox.api

import grails.transaction.Transactional
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.Method

@Transactional
class JenkinsService {
	
	private static final String BASE_URL = "http://localhost:8888"

    def createJob(){
		HTTPBuilder builder = new HTTPBuilder(BASE_URL)
		builder.request( Method.POST ) { 
			uri.path = '/jenkins/createItem=?test'
			requestContentType = ContentType.XML
			body = "<project />"
			response.success = { HttpResponseDecorator resp, xml ->
				xmlResult = xml
			}
		}
	}
}
