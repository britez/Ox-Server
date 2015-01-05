package com.ox.api

import grails.converters.JSON

import com.ox.Project
import com.ox.api.exception.JenkinsBussinessException
import com.ox.api.exception.JenkinsCommunicationException
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
		projectService.delete(getProject(getUser().id, id))
		render(status: 204)
	}
	
	def projectNotFoundException(final ProjectNotFoundException e){
		render(status:404, contentType:"application/json"){
			new ResponseBody(message: "Project with id: $e.id not found")
		}
	}
	
	def jenkinsCommunicationException(final JenkinsCommunicationException e){
		render(status:500, contentType:"application/json"){
			new ResponseBody(message: e.message)
		}
	}
	
	def jenkinsBussinessException(final JenkinsBussinessException e){
		render(status:400, contentType:"application/json"){
			new ResponseBody(message: e.message)
		}
	}
	
	protected Project getProject(Long userId, Long projectId){
		return projectService.get(userId, projectId)
	}
}