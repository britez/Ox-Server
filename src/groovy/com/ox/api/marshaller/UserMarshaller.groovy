package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.User

class UserMarshaller {
	
	def projectMarshaller
	
	def register() {
		JSON.registerObjectMarshaller(User) { User user ->;
			return [id : user.id, mail : user.mail,	projects: projectMarshaller.getListURL()]
		}
	}
}
