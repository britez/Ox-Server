package com.ox.api

import grails.converters.JSON

import com.ox.Project
import com.ox.User
import com.ox.api.exception.ProjectNotFoundException
import com.ox.api.response.ResponseBody

class ProjectController extends UserController{
	
	def projectService
	
    def create() {
		Project project = projectService.create(getUser(),new Project(JSON.parse(request)))
		response.setHeader("Location","$grailsApplication.config.grails.serverURL/me/projects/${project.id}")
		render (status: 201)
	}
	
	def list() { render projectService.list(getUser()) as JSON }
	
	def get(Long id){ render getProject(getUser().id, id) as JSON }
	
	def delete(Long id){
		projectService.delete(getUser().id, id)
		render(status: 204)
	}
	
	def projectNotFoundException(final ProjectNotFoundException e){
		render(status:404, contentType:"application/json"){
			new ResponseBody(message: "Project with id: $e.id not found")
		}
	}
	
	protected Project getProject(Long userId, Long projectId){
		return projectService.get(userId, projectId)
	}
}