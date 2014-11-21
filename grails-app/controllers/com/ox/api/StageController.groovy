package com.ox.api

import grails.converters.JSON

import com.ox.CommitStage
import com.ox.Project
import com.ox.Stage
import com.ox.StageType
import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException

class StageController extends ProjectController{
	
	def stageService
	def stageBuilder
	
	def list(Long id){ render stageService.list(getProject(getUser().id, id).id) as JSON }
	
	def create(Long id){
		def stage = stageService.create(getProject(getUser().id, id), stageBuilder.create(request))
		response.setHeader("Location","$grailsApplication.config.grails.serverURL/me/project/${id}/stages/$stage.id")
		render (status: 201)
	}
	
	def get(Long id, Long stageId){
		render stageService.get(getProject(getUser().id, id).id, stageId) as JSON
	}
}