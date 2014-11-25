package com.ox.api.controller

import grails.test.mixin.web.ControllerUnitTestMixin

import org.junit.Before
import org.junit.Test

import spock.lang.Specification

import com.ox.User
import com.ox.api.TokenService
import com.ox.api.UserController
import com.ox.api.UserService

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(UserController)
@TestMixin(ControllerUnitTestMixin)
class UserControllerSpec extends Specification {
	
	def mockedTokenService
	def mockedUserService
	def mockedUser
	
	@Before
	def setup() {
		mockedTokenService = mockFor(TokenService)
		mockedUserService = mockFor(UserService)
		mockedUser = new User(mail:"thisIsAMail")
		
		mockedTokenService.demand.getToken() {String header -> "thisIsAToken"}
		mockedUserService.demand.get() {String token -> mockedUser}
		
		controller.tokenService = mockedTokenService.createMock()
		controller.userService = mockedUserService.createMock()
    }

	@Test
	void "testShow"(){
		when: controller.show()
		then: response.status == 200
			  response.json.mail == "thisIsAMail"
	}

	@Test
    void "testInvalidTokenException"() {
        when: controller.invalidTokenException()
        then: response.status == 400
			  response.json.message == "Your request have a bad sintax"
    }
	
	@Test
	void "tokenExpiredException"() {
		when: controller.tokenExpiredException()
		then: response.status == 403
			  response.json.message == "Your token is expired or invalid"
	}
}
