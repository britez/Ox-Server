package com.ox.api.builder

import org.springframework.beans.factory.annotation.Autowired

import com.ox.Project
import com.ox.Stage
import com.ox.api.StageType
import com.ox.api.builder.strategy.CommitStageBuilder
import com.ox.api.builder.strategy.JenkinsJobBuilder
import com.ox.api.builder.strategy.ProjectBuilder
import com.ox.api.builder.template.TemplateRepository

class JobBuilder {
	
	def strategy
	
	def build(Stage stage){
		strategy.get(stage.getType()).build(stage)
	}
	
	def build(Project project){
		strategy.get(StageType.PROJECT).build(project)
	}
}