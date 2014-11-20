package com.ox.api

import com.ox.CommitStage;

import grails.transaction.Transactional

@Transactional
class JobService {
	
	def jenkinsService
	
	def createCommitStage(def project){
		def commitStage = new CommitStage() 
		jenkinsService.createJob("$project.name-COMMIT-STAGE")
		if (!project.stages){
			project.stages = []
		}
		project.stages << commitStage
		commitStage.owner = project
		project.save(flush:true)
		project		
	}

}
