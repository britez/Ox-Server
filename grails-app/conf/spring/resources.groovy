import com.ox.api.marshaller.CustomMarshallers
import com.ox.api.marshaller.ProjectMarshaller;
import com.ox.api.marshaller.StageMarshaller;
import com.ox.api.marshaller.UserMarshaller

// Place your Spring DSL code here
beans = {
	customMarshallers(CustomMarshallers) {
		marshallers = [
			ref('userMarshaller'),
			ref('projectMarshaller')
		]
	}
	
	userMarshaller(UserMarshaller){
		projectMarshaller = ref('projectMarshaller')
	}
	
	projectMarshaller(ProjectMarshaller){
		grailsApplication = ref('grailsApplication')
		stageMarshaller = ref('stageMarshaller')
	}
	
	stageMarshaller(StageMarshaller){
		grailsApplication = ref('grailsApplication')
	}
}