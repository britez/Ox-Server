package com.ox.api.builder.strategy;


public interface JenkinsJobBuilder<T> {
	
	String build(T stage);
}
