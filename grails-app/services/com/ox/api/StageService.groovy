package com.ox.api

import grails.transaction.Transactional

import com.ox.Stage
import com.ox.api.exception.JenkinsCommunicationException;
import com.ox.api.exception.StageNotFoundException

@Transactional
class StageService {
	
	def jenkinsService
	
	def list(def projectId){
		def result = Stage.executeQuery("from Stage as stage where stage.owner.id = $projectId")
		result
	}
	
	def get(def projectId, def id){
		def stage = Stage.executeQuery("from Stage as stage where stage.id = $id and stage.owner.id = $projectId")[0]
		if (stage == null){
			throw new StageNotFoundException(id: id)
		}
		stage
	}

	def create(def project, def stage){
		stage.owner = project
		stage.next = []
		if(!stage.save()){
			println stage.errors
		}
		if(stage.previous){
			setPrevious(project, stage.id, stage.previous)
		}
		jenkinsService.create(stage)
		stage
	}
	
	private setPrevious(def project, def currentId, def previousId){
		def previous = get(project.id, previousId)
		previous.next << currentId
		previous.save(flush:true)
	}
}