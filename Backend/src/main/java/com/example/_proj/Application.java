package com.example._proj;

//import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class Application {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepo, MongoTemplate mongoTemplate){
		List<Comment> comments2 = new ArrayList<Comment>();
		List<Comment> comments3 = new ArrayList<Comment>();
		List<Comment> comments4 = new ArrayList<Comment>();
		List<Comment> comments5 = new ArrayList<Comment>();
		return args -> {
			/*
			Place place1 = new Place(
					"Maiden's Tower",
					"Historical",
					4.5,
					"",
					"tower",
					"wifi",
					100.0,
					"Uskudar",
					100,
					comments2
			);
			Place place2 = new Place(
					"Topkapı Palace",
					"Historical",
					4.7,
					"",
					"palace",
					"",
					150.0,
					"Fatih",
					157,
					comments3
			);
			Place place3 = new Place(
					"Sultanahmet Mosque",
					"Religious",
					4.3,
					"",
					"mosque",
					"",
					0,
					"Sultanahmet",
					157,
					comments4
			);
			Place place4 = new Place(
					"Galata Tower",
					"Historical",
					4.6,
					"",
					"a cylindrical tower made from stone and it is approximately 67 meters long",
					"restaurant",
					241,
					"galata",
					143,
					comments5
			);
			*/
			String email = "baturkarakaya@sabanciuniv.edu";

			User user1 = new User(
					"batur",
					email,
					"batur!123",
					"+905063634407"
			);

			User user2 = new User(
					"demirhan",
					"demirhanizer@sabanciuniv.edu",
					"demo.123",
					"+905379585147"
			);

			User user3 = new User(
					"mert",
					"mertcoskuner@sabanciuniv.edu",
					"mert,123",
					"+905353795866"
			);

			//userRepo.insert(user1);
			//userRepo.insert(user2);
			//userRepo.insert(user3);


			//mongowithquery(userRepo, mongoTemplate, email, user1);

			userRepo.findUserByEmail(email).ifPresentOrElse(u -> {
				System.out.println(u + " already exists");
			}, ()-> {
				System.out.println("Registering user: " + user1);
				userService.registerUser(user1);
			});
			//List<User> users = mongoTemplate.find()
		};
	}

	private static void mongowithquery(UserRepository userRepo, MongoTemplate mongoTemplate, String email, User user1) throws IllegalAccessException {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<User> users = mongoTemplate.find(query, User.class);
		if (users.size() > 1) {
			throw new IllegalAccessException("found many users with same email");
		}

		if (users.isEmpty()) {
			System.out.println("Inserting user" + user1);
			userRepo.insert(user1);
		}
		else {
			System.out.println(user1 + " already exists");
		}
	}
}