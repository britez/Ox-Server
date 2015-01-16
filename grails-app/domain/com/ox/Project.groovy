package com.ox

class Project {
	
	String name
	String description
	Long time
	String status
	Long started
	Long estimatedTime
	Long number
	
	static hasMany = [stages: Stage]
	static belongsTo = [owner: User]
	
	static mapping = {
		table 'project'
		stages joinTable: [name: 'project_stage', key: 'project_id', column: 'stage_id']
	}
	
	static constraints = {
		name unique: true
		time nullable: true, blank: true
		status nullable: true, blank: true
		started nullable: true, blank: true
		estimatedTime nullable: true, blank: true
		number nullable:true, blank: true
	}
	
	String getCode(){
		name.replaceAll(" ","")
	}
	
	void remove(Stage stage){
		def it = stages.iterator()
		while (it.hasNext()) {
			if (it.next().equals(stage)) {
				it.remove()
				break
			}
		}
	}
}
