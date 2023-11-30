package com.learn.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.learn.rest.webservices.restfulwebservices.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
	private UserRepository repository;

	MessageSource messageSource;

	public UserJpaResource(MessageSource messageSource,
							UserRepository repository) {
		this.messageSource = messageSource;
		this.repository = repository;
	}

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return repository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable int id) {
		Optional<User> user = repository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("User not found! ID :: " + id);

		EntityModel<User> entityModel = EntityModel.of(user.get());

		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));

		return entityModel;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {

		User savedUser = repository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		repository.deleteById(id);
	}

	@GetMapping("/jpa/hello-world-internationalized")
	public String helloWorldInternationalized() {

		Locale locale = LocaleContextHolder.getLocale();

		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);

	}

}
