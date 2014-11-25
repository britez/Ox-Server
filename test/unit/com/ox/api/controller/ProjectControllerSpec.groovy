
package com.ox.api.controller


import org.junit.Test

import spock.lang.Specification

import com.ox.User
import com.ox.api.ProjectController
import com.ox.api.ProjectService
import com.ox.api.TokenService
import com.ox.api.UserService
import com.ox.api.exception.ProjectNotFoundException

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ProjectController)
class ProjectControllerSpec extends Specification {
	
	def mockedTokenService
	def mockedUserService
	def mockedProjectService
	def mockedUser

    def setup() {
		mockedTokenService = mockFor(TokenService)
		mockedUserService = mockFor(UserService)
		mockedProjectService = mockFor(ProjectService)
		mockedUser = new User(mail:"thisIsAMail")
		
		mockedTokenService.demand.getToken() {String header -> "thisIsAToken"}
		mockedUserService.demand.get() {String token -> mockedUser}
		mockedProjectService.demand.create() {def user, def project -> project}
		
		controller.tokenService = mockedTokenService.createMock()
		controller.userService = mockedUserService.createMock()
		controller.projectService = mockedProjectService.createMock()
    }
	
	@Test
	void "testCreate"() {
		when: controller.create()
		then: response.status == 201
	}

	@Test
    void "testProjectNotFound"() {
		when: controller.projectNotFoundException(new ProjectNotFoundException(id: 1))
		then: response.status == 404
			  response.json.message == "Project with id: 1 not found"
    }
}
