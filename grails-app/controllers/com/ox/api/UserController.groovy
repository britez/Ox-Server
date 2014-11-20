package com.ox.api

import grails.converters.JSON

import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException
import com.ox.api.response.ResponseBody

class UserController extends SessionController{
	
	def userService
	def tokenService
	
    def show() {
		render userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION))) as JSON
	}
}
