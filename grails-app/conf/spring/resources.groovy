import com.ox.api.builder.JobBuilder
import com.ox.api.builder.StageBuilder
import com.ox.api.marshaller.CommitStageMarshaller
import com.ox.api.marshaller.CustomMarshallers
import com.ox.api.marshaller.ProjectMarshaller
import com.ox.api.marshaller.RunMarshaller
import com.ox.api.marshaller.UserMarshaller

// Place your Spring DSL code here
beans = {
	
	stageBuilder(StageBuilder)
	jobBuilder(JobBuilder)
	
	customMarshallers(CustomMarshallers) {
		marshallers = [
			ref('userMarshaller'),
			ref('projectMarshaller'),
			ref('commitStageMarshaller'),
			ref('runMarshaller')
		]
	}
	
	userMarshaller(UserMarshaller){
		projectMarshaller = ref('projectMarshaller')
	}
	
	projectMarshaller(ProjectMarshaller){
		grailsApplication = ref('grailsApplication')
		stageMarshaller = ref('commitStageMarshaller')
		runMarshaller = ref('runMarshaller')
	}
	
	commitStageMarshaller(CommitStageMarshaller){
		grailsApplication = ref('grailsApplication')
	}
	
	runMarshaller(RunMarshaller){
		grailsApplication = ref('grailsApplication')
	}
}