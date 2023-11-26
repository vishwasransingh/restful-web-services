package com.learn.rest.webservices.restfulwebservices.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;

@Service
public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	
	private static int id = 0;

	static {
		users.add(new User(++id, "Aniket", LocalDate.now().minusYears(26)));
		users.add(new User(++id, "Bipin", LocalDate.now().minusYears(20)));
		users.add(new User(++id, "Chetan", LocalDate.now().minusYears(28)));
		users.add(new User(++id, "Dev", LocalDate.now().minusYears(36)));
	}

	public List<User> findAll() {
		return users;
	}

//	public User findOneUser(int id) {
//		Iterator<User> iterator = users.iterator();
//		while (iterator.hasNext()) {
//			User user = iterator.next();
//			if (user.getId().equals(id)) {
//				return user;
//			}
//		}
//		return null;
//	}

	public User findOneUser(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}

	public User save(User user) {
		user.setId(++id);	
		users.add(user);
		return user;
	}

}
