package com.ox.api.builder.strategy

import com.ox.HerokuDeployStage
import com.ox.api.builder.template.TemplateRepository

import freemarker.template.Template

class HerokuDeployStageBuilder implements JenkinsJobBuilder<HerokuDeployStage> {

	static final String HEROKU_DEPLOY_STAGE = "heroku-deploy-stage.ftl"
	
	TemplateRepository repository
	
	@Override
	public String build(HerokuDeployStage stage) {
		Map model = [:]
		model.put("stage",stage)
		model.put("workspace", "/home/Ox-Server/workspace/${stage.owner.getCode()}")
		Writer out = new StringWriter()
		this.getXML().process(model, out)
		out.toString()
	}
	
	private Template getXML(){
		repository.loadTemplate(HEROKU_DEPLOY_STAGE)
	}

}
