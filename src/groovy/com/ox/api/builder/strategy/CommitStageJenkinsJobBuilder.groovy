package com.ox.api.builder.strategy

import com.ox.CommitStage
import com.ox.Stage

class CommitStageJenkinsJobBuilder implements JenkinsJobBuilder {
	
	@Override
	public String build(Stage stage) {
		CommitStage commitStage = (CommitStage)stage
		String.format(getXML(), commitStage.branch,commitStage.url)
	}
	
	private String getXML(){
		URL uri = this.getClass().getClassLoader().getResource("resources/commit-stage.xml")
		new File(uri.getPath()).text
	}

}
