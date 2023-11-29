package com.learn.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionPersonController {

	@GetMapping("/v1/person")
	public PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Vishwas Ransingh");
	}

	@GetMapping("/v2/person")
	public PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Vishwas", "Ransingh"));
	}

	@GetMapping(path = "/person", params = "version=1")
	public PersonV1 getFirstVersionUsingRequestParam() {
		return new PersonV1("Vishwas Ransingh");
	}

	@GetMapping(path = "/person", params = "version=2")
	public PersonV2 getSecondVersionUsingRequestParam() {
		return new PersonV2(new Name("Vishwas", "Ransingh"));
	}
	
	@GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
	public PersonV1 getFirstVersionUsingRequestHeaders() {
		return new PersonV1("Vishwas Ransingh");
	}
	
	@GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
	public PersonV2 getSecondVersionUsingRequestHeaders() {
		return new PersonV2(new Name("Vishwas", "Ransingh"));
	}
	
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v1+json")
	public PersonV1 getFirstVersionUsingAcceptHeaders() {
		return new PersonV1("Vishwas Ransingh");
	}
	
	@GetMapping(path = "/person/accept", produces = "application/vnd.company.app-v2+json")
	public PersonV2 getSecondVersionUsingAcceptHeaders() {
		return new PersonV2(new Name("Vishwas", "Ransingh"));
	}

}
