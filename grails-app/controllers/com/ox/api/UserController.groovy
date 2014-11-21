package com.ox.api

import grails.converters.JSON

import com.ox.User
import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException
import com.ox.api.response.ResponseBody

class UserController {
	
	protected static final String AUTHORIZATION = "Authorization"
			
	def grailsApplication
	def userService
	def tokenService
	
    def show() { render getUser() as JSON }
	
	def invalidTokenException(final InvalidTokenException e){
		render(status: 400, contentType:"application/json"){
			new ResponseBody(message: "Your request have a bad sintax")
		}
	}
	
	def tokenExpiredException(final TokenExpiredException e){
		render(status: 403, contentType: "application/json"){
			new ResponseBody(message: "Your token is expired or invalid")
		}
	}
	
	protected User getUser(){
		return userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION)))
	}
}