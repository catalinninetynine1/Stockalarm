package com.projectdevm8.stockalarm.controller;

import com.projectdevm8.stockalarm.ConfigEx.exception.UserException;
import com.projectdevm8.stockalarm.model.User;
import com.projectdevm8.stockalarm.service.implementation.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

  private static Logger logger = LogManager.getLogger(HomeController.class);
  private final UserService userService;

  public HomeController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/")
  public String getHomePage() {
    try {
      User loggedInUser = userService.getLoggedInUser();
      logger.info(String.format("User-ul %s este logat", loggedInUser));
      return "redirect:/alarms";
    } catch (UserException e) {
      return "redirect:/login";
    }
  }
}