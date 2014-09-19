package com.ox.api

import grails.transaction.Transactional

import com.ox.api.exception.InvalidTokenException

@Transactional
class TokenService {

    def getToken(def header) {
		if (!header){
			throw new InvalidTokenException();
		}
		header.replaceAll("Bearer\\s","")
    }
}
