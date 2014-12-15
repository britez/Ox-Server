package com.ox

class User {
	
	String mail
    static hasMany = [projects: Project]
	static mapping = {
		table 'user'
		projects joinTable: [name: 'user_project', key: 'user_id', column: 'project_id']
	}
}
