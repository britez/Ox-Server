package com.ox

class Stage {
	String type
	Long previous
	List next
	
	static belongsTo = [owner: Project]
	static hasMany = [next:Long]
	
	static constraints = {
		previous nullable: true
		next nullable: true
	}
	
	String getCode(){
		type.replaceAll(" ","")
	}
}