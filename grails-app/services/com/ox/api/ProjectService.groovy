package com.ox.api

import grails.transaction.Transactional

import com.ox.Project
import com.ox.User

@Transactional
class ProjectService {

    def create(User user, def project) {
		project.owner = user
		user.projects << project
		project.save(flush:true)
		project
    }
	
	def list(User user){
		User.executeQuery("from Project as project where project.owner.id = $user.id")
	}
	
	def get(Long userId, Long id){
		Project.executeQuery("from Project as project where project.id = $id and project.owner.id = $userId")[0]
	}
}
