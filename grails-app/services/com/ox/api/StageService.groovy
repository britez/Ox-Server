package com.ox.api

import grails.transaction.Transactional

@Transactional
class StageService {

    def update(def projectId, def stage) {
		def result = Stage.executeQuery("from Stage as s where s.id = $stage.id and s.owner.id = $projectId")[0]
		result
    }
}
