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
					git branch: '${stage.branch}', changelog: true, poll: true, url: '${stage.url}'
					echo "Finding app nature..."
                    try {
                          readFile 'grailsw'
                          echo "Grails app detected..."
                          echo "Building grails app..."
					      def grails = tool 'grails'
					      <#noparse> 
					      sh "${grails}/bin/grails test-app"
					      </#noparse>
                    } catch (def ex) {
                          //ex
                    }
                    try {
	                      readFile 'pom.xml'
                          echo "Maven app detected..."
                          echo "Building maven app..."
					      def mvn = tool 'mvn'
						  <#noparse> 
					      sh "${mvn}/bin/mvn test"
					      </#noparse>
                    } catch (def ex) {
                          //ex
                    }
				}
			}
		</script>
		<sandbox>false</sandbox>
	</definition>
	<triggers/>
</flow-definition>