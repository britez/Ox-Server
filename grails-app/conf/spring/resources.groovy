import com.ox.api.StageType
import com.ox.api.builder.JobBuilder
import com.ox.api.builder.StageBuilder
import com.ox.api.builder.strategy.CommitStageBuilder
import com.ox.api.builder.strategy.HerokuDeployStageBuilder
import com.ox.api.builder.strategy.ProjectBuilder
import com.ox.api.builder.template.TemplateRepository
import com.ox.api.marshaller.CommitStageMarshaller
import com.ox.api.marshaller.CustomMarshallers
import com.ox.api.marshaller.ProjectMarshaller
import com.ox.api.marshaller.RunMarshaller
import com.ox.api.marshaller.UserMarshaller

// Place your Spring DSL code here
beans = {
	
	
	//Builder Strategies
	stageBuilder(StageBuilder)
	
	def map = [:]
	map.put(StageType.COMMIT_STAGE, ref('commitStageBuilder'))
	map.put(StageType.HEROKU_DEPLOY_STAGE, ref('herokuDeployStageBuilder'))
	map.put(StageType.PROJECT, ref('projectBuilder'))
	
	jobBuilder(JobBuilder){
		strategy = map
	}
	
	commitStageBuilder(CommitStageBuilder){
		repository = ref('templateRepository')
	}
	
	herokuDeployStageBuilder(HerokuDeployStageBuilder){
		repository = ref('templateRepository')
	}
	
	projectBuilder(ProjectBuilder){
		repository = ref('templateRepository')
	}
	
	templateRepository(TemplateRepository)
	
	//Marshallers
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