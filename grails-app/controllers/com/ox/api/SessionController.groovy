package com.ox.api

import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException
import com.ox.api.response.ResponseBody

class SessionController {
	
	protected static final String AUTHORIZATION = "Authorization"
	
	def grailsApplication
	
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

}
