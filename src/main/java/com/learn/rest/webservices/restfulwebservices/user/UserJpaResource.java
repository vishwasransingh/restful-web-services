package com.learn.rest.webservices.restfulwebservices.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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

import com.learn.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.learn.rest.webservices.restfulwebservices.jpa.UserRepository;
import com.learn.rest.webservices.restfulwebservices.post.Post;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	private UserRepository userRepository;
	private PostRepository postRepository;

	MessageSource messageSource;

	public UserJpaResource(MessageSource messageSource, UserRepository repository, PostRepository postRepository) {
		this.messageSource = messageSource;
		this.userRepository = repository;
		this.postRepository = postRepository;
	}

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retriveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (user.isEmpty())
			throw new UserNotFoundException("User not found! ID :: " + id);

		EntityModel<User> entityModel = EntityModel.of(user.get());

		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));

		return entityModel;
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {

		User savedUser = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}

	@GetMapping("/jpa/hello-world-internationalized")
	public String helloWorldInternationalized() {

		Locale locale = LocaleContextHolder.getLocale();

		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);

	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPostsForUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException("User not found! ID :: " + id);
		return user.get().getPosts();
	}

	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {

		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty())
			throw new UserNotFoundException("User not found! ID :: " + id);

		post.setUser(user.get());
		
		Post savedPost = postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedPost.getId())
				.toUri();

		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{userId}/posts/{postId}")
	public Post retriveOnePostForUser(@PathVariable int userId, @PathVariable int postId) {
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isEmpty())
			throw new UserNotFoundException("User not found! ID :: " + userId);
		
		List<Post> posts = user.get().getPosts();
		
		Iterator<Post> iterator = posts.iterator();
		
		while (iterator.hasNext()) {
			
			Post post = iterator.next();
			if (post.getId().equals(postId))
				return post;
		}
		
		return null;

	}

}
