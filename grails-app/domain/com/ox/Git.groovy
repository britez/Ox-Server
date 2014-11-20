package com.ox

class Git {
	
	String url
	String branch
	
	static belongsTo = [owner: CommitStage]

    static constraints = {}
}
