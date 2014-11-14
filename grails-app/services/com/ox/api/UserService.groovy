package com.ox.api

import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder

import com.ox.User
import com.ox.api.exception.TokenExpiredException
import com.ox.api.exception.UserNotFoundException

@Transactional
class UserService {
	
	def tokenService
	
	def create(User user){
		if(!user.projects){
			user.projects = []
		}
		if(!(user.save(flush:true))){
			println user.errors
		}
		user
	}
	
	private User getByMail(String mail){
		def storedUser = User.executeQuery("from User as u where u.mail = '$mail'")[0]
		if(!storedUser){
			throw new UserNotFoundException()
		}
		storedUser
	}
	
    def get(String accessToken) {
		User result
		User authUser = tokenService.getUser(accessToken)
		try {
			result = this.getByMail(authUser.mail)
		} catch (UserNotFoundException e) {
			result = this.create(authUser)
		}
		result
    }
}