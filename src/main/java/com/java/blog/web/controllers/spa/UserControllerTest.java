package com.java.blog.web.controllers.spa;


import java.security.Principal;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.java.blog.dto.spa.NewUserDTO;
import com.java.blog.dto.spa.UserInfoDTO;
import com.java.blog.entity.spa.UserTest;
import com.java.blog.service.spa.UserServiceTest;

/**
 *
 *  REST service for users.
 *
 */

@Controller
@RequestMapping("/user")
public class UserControllerTest {

    private static final Logger LOGGER = Logger.getLogger(UserControllerTest.class);

    @Autowired
    UserServiceTest userService;

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public UserInfoDTO getUserInfo(Principal principal) {
    	

        UserTest user = userService.findUserByUsername(principal.getName());
        Long todaysCalories = userService.findTodaysCaloriesForUser(principal.getName());

        return user != null ? new UserInfoDTO(user.getUsername(), user.getMaxCaloriesPerDay(), todaysCalories) : null;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT)
    public void updateUserMaxCaloriesPerDay(Principal principal, @RequestBody Long newMaxCalories) {
        userService.updateUserMaxCaloriesPerDay(principal.getName(), newMaxCalories);
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public void createUser(@RequestBody NewUserDTO user) {
        userService.createUser(user.getUsername(), user.getEmail(), user.getPlainTextPassword());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
