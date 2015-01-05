package com.ox.api.builder

import com.ox.Project
import com.ox.Stage
import com.ox.api.StageType

class JobBuilder {
	
	def strategy
	
	def build(Stage stage){
		strategy.get(stage.getType()).build(stage)
	}
	
	def build(Project project){
		strategy.get(StageType.PROJECT).build(project)
	}
}