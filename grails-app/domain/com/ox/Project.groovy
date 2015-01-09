package com.ox

class Project {
	
	String name
	String description
	Long time
	String status
	
	static hasMany = [stages: Stage, runs: Run]
	static belongsTo = [owner: User]
	
	static mapping = {
		table 'project'
		stages joinTable: [name: 'project_stage', key: 'project_id', column: 'stage_id']
	}
	
	static constraints = {
		name unique: true
		time nullable: true, blank: true
		status nullable: true, blank: true
	}
	
	Long runNumber(){
		runs.size()
	}
	
	String getCode(){
		name.replaceAll(" ","")
	}
}
