package com.ox.api

import grails.transaction.Transactional

import com.ox.Project
import com.ox.User
import com.ox.api.exception.BadRequestException
import com.ox.api.exception.ProjectNotFoundException

@Transactional
class ProjectService {
	
	def jenkinsService
	def stageService
	
    def create(User user, def project) {
		log.info("Attemp to create a project")
		project.owner = user
		user.projects << project
		if (!project.stages){
			project.stages = []
		}
		if(!(project.save(flush:true))){
			throw new BadRequestException()
		}
		log.info("Project created successfully")
		log.info("Attemp to create projct into jenkins instance")
		jenkinsService.create(project)
		project
    }
	
	def list(User user){
		User.executeQuery("from Project as project where project.owner.id = $user.id")
	}
	
	def get(Long userId, Long id){
		def result = Project.executeQuery("from Project as project where project.id = $id and project.owner.id = $userId")[0]
		if(!result){
			throw new ProjectNotFoundException(id:id)
		}
		if (jenkinsService.hasBuilds(result.getCode())){
			jenkinsService.get(result)
		}
		result
	}
	
	def delete(Project project){
		project.stages.each { stage ->
			stageService.delete(stage)
		}
		jenkinsService.delete(project)
		project.delete()
	}
}