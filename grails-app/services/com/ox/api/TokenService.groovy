package com.ox.api

import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*

import com.ox.User
import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException

@Transactional
class TokenService {
	
	def grailsApplication

    def getToken(def header) {
		if (!header){
			throw new InvalidTokenException();
		}
		header.replaceAll("Bearer\\s","")
    }
	
	def getUser(def token){
		def http = new HTTPBuilder()
		http.request("$grailsApplication.config.grails.oauth.base", GET, TEXT ) { req ->
			uri.path = "$grailsApplication.config.grails.oauth.context/token"
			headers.'Authorization' = "Bearer $token"
			headers.'Content-Type'= 'application/json'
		
			response.success = { resp, reader ->
				new User(grails.converters.JSON.parse(reader.text))
			}
		
			response.'403' = {
				throw new TokenExpiredException()
			}
		}
	}
}
