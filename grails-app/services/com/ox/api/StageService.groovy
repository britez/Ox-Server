package com.ox.api

import grails.transaction.Transactional

import com.ox.Project
import com.ox.Stage

@Transactional
class StageService {
	
	def list(def projectId){
		def result = Stage.executeQuery("from Stage as stage where stage.owner.id = $projectId")
		result
	}
	
	def get(def projectId, def id){
		Stage.executeQuery("from Stage as stage where stage.id = $id and stage.owner.id = $projectId")[0]
	}

	def create(def project, def stage){
		stage.owner = project
		if(!stage.save(flush:true)){
			println stage.errors
		}
		stage
	}
}