package com.ox

class Project {
	
	String name
	String description
	
	static hasMany = [stages: Stage]
	static belongsTo = [owner: User]
	
	static mapping = {
		table 'project'
		stages joinTable: [name: 'project_stage', key: 'project_id', column: 'stage_id']
	}
}
