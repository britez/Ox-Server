package com.ox.api

class RunController extends ProjectController{
	
	def runService

    def create(Long id) {
		runService.create(getProject(getUser().id, id))
		render (status: 201)
	}
}
