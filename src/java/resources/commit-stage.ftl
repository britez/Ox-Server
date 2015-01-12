<?xml version='1.0' encoding='UTF-8'?>
<flow-definition plugin="workflow-job@1.1">
	<actions/>
	<description/>
	<keepDependencies>false</keepDependencies>
	<properties/>
	<definition class="org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition" plugin="workflow-cps@1.1">
		<script>
			node{
				dir ('${workspace}'){
					git branch: 'dev', changelog: true, poll: true, url: '${stage.url}' 
					def grails = tool 'grails'
					<#noparse> 
				    sh "${grails}/bin/grails test-app"
				    </#noparse>
				}
			}
		</script>
	<sandbox>false</sandbox>
	</definition>
	<triggers/>
</flow-definition>