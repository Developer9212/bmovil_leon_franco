package com.fenoreste.dao;
import org.springframework.data.repository.CrudRepository;
import com.fenoreste.entity.User;

public interface UserDao extends CrudRepository<User, Integer> {

	public User findUserByUsername(String username);
	
}
