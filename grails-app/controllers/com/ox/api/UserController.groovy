package com.ox.api

import com.ox.User
import com.ox.api.exception.InvalidTokenException;

class UserController {
	
	def userService
	def tokenService
	
	static final String AUTHORIZATION = "Authorization"

    def show() {
		def token = null
		try {
			token = tokenService.getToken(request.getHeader(AUTHORIZATION))
		} catch (InvalidTokenException e){
			render(status: 400, text: "{message: 'Invalid token'}")
			return
		}
		def User user = userService.get(token)
		render(status: 200, text: "Token $token")
		return
	}
}
