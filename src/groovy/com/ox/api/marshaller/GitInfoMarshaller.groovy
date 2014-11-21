package com.ox.api.marshaller

import grails.converters.JSON

import com.ox.CommitStage
import com.ox.Git

class GitInfoMarshaller {
	
	def register() {
		JSON.registerObjectMarshaller(Git) { Git git ->;
			return [url: git.url, branch: git.branch]
		}
	}
}
