package org.example.springbootxml_json.repository;

import org.example.springbootxml_json.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsersRepository extends MongoRepository<User, String> {
}
