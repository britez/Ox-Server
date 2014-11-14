package com.ox.api

import grails.converters.JSON;

import com.ox.User
import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException

class UserController {
	
	def userService
	def tokenService
	
	static final String AUTHORIZATION = "Authorization"

    def show() {
		def token = null
		try {
			render userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION))) as JSON
			//render(contentType: 'text/json', text: result as JSON)
			return
		} catch (InvalidTokenException e){
			render(status: 400, text: "{'message': 'Invalid token'}")
			return
		} catch (TokenExpiredException e){
			render(status: 403, text: "{'message': 'Forbidden'}")
			return
		}
	}
}
