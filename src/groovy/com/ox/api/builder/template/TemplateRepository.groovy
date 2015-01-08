package com.ox.api.builder.template

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler

class TemplateRepository {
	
	Configuration cfg
	
	def grailsResourceLocator
	
	TemplateRepository(){
		this.cfg = new Configuration(Configuration.VERSION_2_3_21)
		Resource resource = new ClassPathResource("resources/commit-stage.ftl", getClass().classLoader)
		cfg.setDirectoryForTemplateLoading(resource.getFile().getParentFile())
		cfg.setDefaultEncoding("UTF-8")
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
	}
	
	public Template loadTemplate(String template){
		cfg.getTemplate(template)
	}
	
}
