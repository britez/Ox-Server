package com.ox.api.builder.template

import freemarker.template.Configuration
import freemarker.template.Template
import freemarker.template.TemplateExceptionHandler

class TemplateRepository {
	
	Configuration cfg
	
	TemplateRepository(){
		this.cfg = new Configuration(Configuration.VERSION_2_3_21)
		String url = this.getClass().getClassLoader().getResource("resources").getFile()
		File dir = new File(url)
		cfg.setDirectoryForTemplateLoading(dir)
		cfg.setDefaultEncoding("UTF-8")
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER)
	}
	
	public Template loadTemplate(String template){
		cfg.getTemplate(template)
	}
	
}
