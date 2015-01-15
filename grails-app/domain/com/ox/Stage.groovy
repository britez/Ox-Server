package com.ox

class Stage {
	String type
	Long previous
	List next
	Long time
	String status
	Long started
	Long number
	Long estimatedTime
	
	static belongsTo = [owner: Project]
	static hasMany = [next:Long]
	
	static constraints = {
		previous nullable: true
		next nullable: true
		time nullable: true, blank: true
		status nullable: true, blank: true
		number nullable: true, blank: true
		estimatedTime nullable: true, blank: true
		started nullable:true, blank: true
	}
	
	String getCode(){
		type.replaceAll(" ","")
	}
}