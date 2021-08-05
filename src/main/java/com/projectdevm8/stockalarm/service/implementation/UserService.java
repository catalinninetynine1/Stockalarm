package com.projectdevm8.stockalarm.service.implementation;

import com.projectdevm8.stockalarm.ConfigEx.exception.UserException;
import com.projectdevm8.stockalarm.DTobjects.UserDto;
import com.projectdevm8.stockalarm.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
  UserDto save(UserDto user) throws UserException;

  void validate(String email, String password);

  User getLoggedInUser();
}