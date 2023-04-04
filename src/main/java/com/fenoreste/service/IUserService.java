package com.fenoreste.service;

import java.util.List;

import com.fenoreste.entity.User;


public interface IUserService {
   
	public List<User>findAll();	
	public void save(User user);	
	public User findById(Integer id);
}
