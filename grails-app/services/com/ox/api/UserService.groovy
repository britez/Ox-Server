package com.ox.api

import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.Method.GET

@Transactional
class UserService {
	
	def oauth = new HTTPBuilder()

    def get(String accessToken) {
		
		oauth.request("http://localhost:9090", GET) { req ->
			uri.path = '/Ox-Oauth/token'
			headers.'Authorization' = "Bearer $accessToken"
		   
			response.success = { resp, reader ->
				assert resp.statusLine.statusCode == 200
				println "Got response: ${resp.statusLine}"
				println "Content-Type: ${resp.headers.'Content-Type'}"
				println reader.text
			}
		   
			response.'403' = {
				println 'Forbidden'
			}
		  }
		
    }
}
