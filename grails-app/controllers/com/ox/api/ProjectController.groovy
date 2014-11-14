package com.ox.api

import grails.converters.JSON

import com.ox.Project
import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException

class ProjectController {
	
	def tokenService
	def userService
	def projectService
	
	private static final String AUTHORIZATION_HEADER = "Authorization"

    def create() {
		def project = null
		try {
			def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION_HEADER)))
			project = projectService.create(user,new Project(JSON.parse(request)))
		} catch (InvalidTokenException e){
			render(status: 400, text: "{'message': 'Invalid token'}")
			return
		} catch (TokenExpiredException e){
			render(status: 403, text: "{'message': 'Forbidden'}")
			return
		}
		response.setHeader("Location","http://localhost:8080/Ox-Server/me/project/${project.id}")
		render (status: 201)
	}
	
	def list() {
		List<Project> result
		try {
			def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION_HEADER)))
			result = projectService.list(user)
		} catch (InvalidTokenException e){
			render(status: 400, text: "{'message': 'Invalid token'}")
			return
		} catch (TokenExpiredException e){
			render(status: 403, text: "{'message': 'Forbidden'}")
			return
		}
		render result as JSON
	}
	
	def get(Long id){
		Project result
		try {
			def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION_HEADER)))
			result = projectService.get(user.id, id)
		} catch (InvalidTokenException e){
			render(status: 400, text: "{'message': 'Invalid token'}")
			return
		} catch (TokenExpiredException e){
			render(status: 403, text: "{'message': 'Forbidden'}")
			return
		}
		render result as JSON
	}
}
