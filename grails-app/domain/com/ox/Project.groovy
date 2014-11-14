package com.ox

class Project {
	
	String name
	String description
	
	static hasMany = [stages: Stage]
	static belongsTo = [owner: User]
	
	static constraints = {}
}
