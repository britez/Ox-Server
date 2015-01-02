package com.ox.api.builder.strategy

import org.springframework.beans.factory.annotation.Autowired

import com.ox.CommitStage
import com.ox.api.builder.template.TemplateRepository

import freemarker.template.Template

class CommitStageBuilder implements JenkinsJobBuilder<CommitStage> {

	static final String COMMIT_STAGE = "commit-stage.ftl"
	
	TemplateRepository repository
	
	@Override
	public String build(CommitStage stage) {
		Map model = [:]
		model.put("stage", stage)
		model.put("grails", '${grails}')
		Writer out = new StringWriter() 
		this.getXML().process(model, out)
		out.toString()
	}
	
	private Template getXML(){
		repository.loadTemplate(COMMIT_STAGE)
	}

}
