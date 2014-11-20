package com.ox.api

import grails.converters.JSON

import com.ox.Project
import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.ProjectNotFoundException
import com.ox.api.exception.TokenExpiredException
import com.ox.api.response.ResponseBody

class ProjectController extends SessionController{
	
	def tokenService
	def userService
	def projectService
	
    def create() {
		def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION)))
		def project = projectService.create(user,new Project(JSON.parse(request)))
		response.setHeader("Location","$grailsApplication.config.grails.serverURL/me/project/${project.id}")
		render (status: 201)
	}
	
	def list() {
		def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION)))
		render projectService.list(user) as JSON
	}
	
	def get(Long id){
		def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION)))
		render projectService.get(user.id, id) as JSON
	}
	
	def delete(Long id){
		def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION)))
		projectService.delete(user.id, id)
		render(status: 204)
	}
	
	def projectNotFoundException(final ProjectNotFoundException e){
		render(status:404, contentType:"application/json"){
			new ResponseBody(message: "Project with id: $e.id not found")
		}
	}
}
