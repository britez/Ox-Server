package com.ox.api

import grails.transaction.Transactional

import com.ox.Project
import com.ox.User
import com.ox.api.exception.ProjectNotFoundException

@Transactional
class ProjectService {
	
	def jobService
	
    def create(User user, def project) {
		project.owner = user
		user.projects << project
		if (!project.stages){
			project.stages = []
		}
		//TODO: Implementar la creación en jenkins
		//project.stages << this.jobService.createCommitStage(project)
		if(!(project.save(flush:true))){
			println project.errors
		}
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
		result
	}
	
	def delete(Long userId, Long id){
		Project.executeUpdate("delete Project as project where project.id = $id and project.owner.id = $userId")
	}
}