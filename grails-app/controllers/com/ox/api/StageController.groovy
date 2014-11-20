package com.ox.api

import grails.converters.JSON

import com.ox.CommitStage
import com.ox.Stage
import com.ox.api.exception.InvalidTokenException
import com.ox.api.exception.TokenExpiredException

class StageController {
	
	def userService
	def projectService
	def tokenService
	def stageService
	
	private static final String AUTHORIZATION_HEADER = "Authorization"

    def update(String projectId, String stageId) {
		Stage result
		try {
			def user = userService.get(tokenService.getToken(request.getHeader(AUTHORIZATION_HEADER)))
			def project = projectService.get(user.id, projectId)
			def stage = new CommitStage(JSON.parse(request))
			stage.id = stageId
			stageService.update(project.id, stage)
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
