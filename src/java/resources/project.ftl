<?xml version='1.0' encoding='UTF-8'?>
<flow-definition plugin="workflow-job@1.1">
	<actions/>
	<description/>
	<keepDependencies>false</keepDependencies>
	<properties/>
	<definition class="org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition" plugin="workflow-cps@1.1">
		<script>
			node {
				sh "mkdir -p ${workspace}"
				<#list stages as stage> 
				build '${project.getCode()}-${stage.getCode()}'
				</#list> 
			}
		</script>
		<sandbox>false</sandbox>
	</definition>
	<triggers/>
</flow-definition>