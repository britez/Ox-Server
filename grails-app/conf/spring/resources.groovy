import com.ox.StageBuilder
import com.ox.User;
import com.ox.api.marshaller.CommitStageMarshaller
import com.ox.api.marshaller.CustomMarshallers
import com.ox.api.marshaller.GitInfoMarshaller;
import com.ox.api.marshaller.ProjectMarshaller
import com.ox.api.marshaller.UserMarshaller

import grails.rest.render.hal.HalJsonCollectionRenderer
import grails.rest.render.hal.HalJsonRenderer

// Place your Spring DSL code here
beans = {
	
	stageBuilder(StageBuilder)
	
	customMarshallers(CustomMarshallers) {
		marshallers = [
			ref('userMarshaller'),
			ref('projectMarshaller'),
			ref('commitStageMarshaller'),
			ref('gitMarshaller')
		]
	}
	
	userMarshaller(UserMarshaller){
		projectMarshaller = ref('projectMarshaller')
	}
	
	projectMarshaller(ProjectMarshaller){
		grailsApplication = ref('grailsApplication')
		stageMarshaller = ref('commitStageMarshaller')
	}
	
	commitStageMarshaller(CommitStageMarshaller){
		grailsApplication = ref('grailsApplication')
	}
	
	gitMarshaller(GitInfoMarshaller)
	
	/* Swagger */
	halPCollectionRenderer(HalJsonCollectionRenderer, User)

	halPRenderer(HalJsonRenderer, User)
}