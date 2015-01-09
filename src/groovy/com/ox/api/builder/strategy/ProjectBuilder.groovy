package com.ox.api.builder.strategy

import org.springframework.beans.factory.annotation.Autowired;

import com.ox.Project
import com.ox.api.builder.template.TemplateRepository;

import freemarker.template.Template

class ProjectBuilder implements JenkinsJobBuilder<Project> {
	
	private static final String PROJECT = "project.ftl"
	
	TemplateRepository repository
	
	@Override
	public String build(Project project) {
		Map model = [:]
		model.put("project", project)
		model.put("stages",project.stages)
		model.put("workspace", project.getCode())
		Writer out = new StringWriter()
		this.getXML().process(model, out)
		out.toString()
	}
	
	private Template getXML(){
		repository.loadTemplate(PROJECT)
	}

}
