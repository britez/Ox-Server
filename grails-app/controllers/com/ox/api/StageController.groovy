package com.ox.api

import grails.converters.JSON

import com.ox.api.exception.JenkinsCommunicationException;
import com.ox.api.exception.StageNotFoundException
import com.ox.api.response.ResponseBody

class StageController extends ProjectController{
	
	def stageService
	def stageBuilder
	
	def list(Long id){ render stageService.list(getProject(getUser().id, id).id) as JSON }
	
	def create(Long id){
		def stage = stageService.create(getProject(getUser().id, id), stageBuilder.create(request))
		response.setHeader("Location","$grailsApplication.config.grails.serverURL/me/projects/${id}/stages/$stage.id")
		render (status: 201)
	}
	
	def get(Long id, Long stageId){
		def result = stageService.get(getProject(getUser().id, id).id, stageId)
		render result as JSON
	}
	
	def stageNotFoundException(final StageNotFoundException e){
		render(status:404, contentType:"application/json"){
			new ResponseBody(message: "Stage with id: $e.id not found")
		}
	}
	
	def jenkinsCommunicationException(final JenkinsCommunicationException e){
		render(status:500, contentType:"application/json"){
			new ResponseBody(message: e.message)
		}
	}
}