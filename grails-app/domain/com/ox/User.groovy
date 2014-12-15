package com.ox

import restapidoc.annotations.ApiDescription
import restapidoc.annotations.ApiProperty

@ApiDescription(description = "A user representation")
class User {
	
	@ApiProperty(description = "A Name")
	String mail
    static hasMany = [projects: Project]
	static mapping = {
		table 'user'
		projects joinTable: [name: 'user_project', key: 'user_id', column: 'project_id']
	}
}
