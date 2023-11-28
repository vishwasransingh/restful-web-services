package com.learn.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {

	UserDaoService service;

	MessageSource messageSource;

	public UserResource(UserDaoService service, MessageSource messageSource) {
		this.service = service;
		this.messageSource = messageSource;
	}

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return service.findAll();
	}

	@GetMapping("/users/{id}")
	public User retriveUser(@PathVariable int id) {
		User user = service.findOneUser(id);

		if (user == null)
			throw new UserNotFoundException("User not found! ID :: " + id);

		return user;
	}

	@PostMapping("/users")
	public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {

		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}

	@GetMapping("/hello-world-internationalized")
	public String helloWorldInternationalized() {

		Locale locale = LocaleContextHolder.getLocale();

		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);

	}

}
