<?xml version='1.0' encoding='UTF-8'?>
<flow-definition plugin="workflow-job@1.1">
	<actions/>
	<description/>
	<keepDependencies>false</keepDependencies>
	<properties/>
	<definition class="org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition" plugin="workflow-cps@1.1">
		<script>
			import java.io.FileNotFoundException
			import java.lang.reflect.UndeclaredThrowableException
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
                    } catch (UndeclaredThrowableException ex) {
                    	if(!(ex.getCause() instanceof FileNotFoundException)){
                    		throw ex.getCause()
                    	}
                    }
                    try {
	                      readFile 'pom.xml'
                          echo "Maven app detected..."
                          echo "Building maven app..."
					      def mvn = tool 'mvn'
						  <#noparse> 
					      sh "${mvn}/bin/mvn test"
					      </#noparse>
                    } catch (UndeclaredThrowableException ex) {
                    	if(!(ex.getCause() instanceof FileNotFoundException)){
                    		throw ex.getCause()
                    	}
                    }
                    try {
	                      readFile 'Gruntfile.json'
                          echo "Grunt app detected..."
                          echo "Building grunt app..."
					      sh "grunt build"
                    } catch (UndeclaredThrowableException ex) {
                    	if(!(ex.getCause() instanceof FileNotFoundException)){
                    		throw ex.getCause()
                    	}
                    }
				}
			}
		</script>
		<sandbox>false</sandbox>
	</definition>
	<triggers/>
</flow-definition>