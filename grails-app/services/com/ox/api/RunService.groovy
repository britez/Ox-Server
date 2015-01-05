package com.ox.api

import grails.transaction.Transactional

import com.ox.Project
import com.ox.Run

@Transactional
class RunService {
	
	def jenkinsService

    def create(Project project) {
		jenkinsService.run(project)
		Run run = new Run(owner:project)
		project.runs << run
		project.save()
    }
}
