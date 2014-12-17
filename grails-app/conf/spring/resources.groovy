import com.ox.StageBuilder
import com.ox.User;
import com.ox.api.marshaller.CommitStageMarshaller
import com.ox.api.marshaller.CustomMarshallers
import com.ox.api.marshaller.ProjectMarshaller
import com.ox.api.marshaller.UserMarshaller

// Place your Spring DSL code here
beans = {
	
	stageBuilder(StageBuilder)
	
	customMarshallers(CustomMarshallers) {
		marshallers = [
			ref('userMarshaller'),
			ref('projectMarshaller'),
			ref('commitStageMarshaller')
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
}