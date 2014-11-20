package com.ox

class CommitStage extends Stage{
	
	static hasOne = [gitInfo: Git]
	static belongsTo = [owner: Project]
	
    static constraints = {
		gitInfo nullable: true
	}
}
