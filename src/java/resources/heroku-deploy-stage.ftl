<?xml version='1.0' encoding='UTF-8'?>
<flow-definition plugin="workflow-job@1.1">
	<actions/>
	<description/>
	<keepDependencies>false</keepDependencies>
	<properties/>
	<definition class="org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition" plugin="workflow-cps@1.1">
		<script>
			node { 
			    dir('${workspace}'){
					def git = tool 'git'
					<#noparse>
					sh "${git} init"
					try { 
						sh "${git} </#noparse>remote add heroku ${stage.url}"
					} catch(def ex) { 
						//ex 
					} 
					<#noparse>
					sh "${git} checkout master" 
					sh "${git} merge origin/dev" 
					sh "${git} push heroku master"
					</#noparse>
				} 
			}
		</script>
		<sandbox>false</sandbox>
	</definition>
	<triggers/>
</flow-definition>