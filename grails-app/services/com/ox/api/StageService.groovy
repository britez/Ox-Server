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
		result.each { it ->
			if (jenkinsService.hasBuilds("${it.owner.getCode()}-${it.getCode()}")){
				jenkinsService.get(it)
			}
		}
		result
	}
	
	def get(def projectId, def id){
		def stage = Stage.executeQuery("from Stage as stage where stage.id = $id and stage.owner.id = $projectId")[0]
		if (stage == null){
			throw new StageNotFoundException(id: id)
		}
		if (jenkinsService.hasBuilds("${stage.owner.getCode()}-${stage.getCode()}")){
			jenkinsService.get(stage)
		}
		stage
	}

	def create(def project, def stage){
		stage.owner = project
		stage.next = []
		if(!stage.save()){
			println stage.errors
		}
		jenkinsService.create(stage)
		if(stage.previous){
			setPrevious(project, stage.id, stage.previous)
		}
		stage
	}
	
	def update(def project, def stage){
		def master = get(project.id, stage.id)
		master.merge(stage)
		master.save()
		jenkinsService.updateStage(master)
		master
	}
	
	def delete(Stage stage){
		jenkinsService.delete(stage)
		stage.owner.remove(stage)
		stage.delete()
		jenkinsService.updateProject(stage.owner)
	}
	
	private setPrevious(def project, def currentId, def previousId){
		def previous = get(project.id, previousId)
		previous.next << currentId
		previous.save(flush:true)
		jenkinsService.updateProject(project)
	}
}