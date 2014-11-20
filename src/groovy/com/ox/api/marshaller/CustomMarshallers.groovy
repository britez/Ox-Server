package com.ox.api.marshaller

class CustomMarshallers {
	
	List marshallers = []

	def register() {
		marshallers.each{ it.register() }
	}

}
