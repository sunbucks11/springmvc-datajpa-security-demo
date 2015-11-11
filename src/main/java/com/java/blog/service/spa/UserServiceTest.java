package com.java.blog.service.spa;

import static com.java.blog.service.ValidationUtils.assertMatches;
import static com.java.blog.service.ValidationUtils.assertMinimumLength;
import static com.java.blog.service.ValidationUtils.assertNotBlank;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.java.blog.entity.spa.UserTest;
import com.java.blog.repository.spa.UserRepositoryTest;

/**
 *
 * Business service for User entity related operations
 *
 */
@Service
public class UserServiceTest {

    private static final Logger LOGGER = Logger.getLogger(UserServiceTest.class);
    private static final Long DEFAULT_MAX_CAL_PER_DAY = 2000L;

    private static final Pattern PASSWORD_REGEX = Pattern.compile("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}");

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    @Autowired
    private UserRepositoryTest userRepository;

    /**
     *
     * updates the maximum calories of a given user
     *
     * @param username - the currently logged in user
     * @param newMaxCalories - the new max daily calories for the user
     */
    @Transactional
    public void updateUserMaxCaloriesPerDay(String username, Long newMaxCalories) {
        UserTest user = userRepository.findUserByUsername(username);

        if (user != null) {
            user.setMaxCaloriesPerDay(newMaxCalories);
        } else {
            LOGGER.info("User with username " + username + " could not have the max calories updated.");
        }
    }

    /**
     *
     * creates a new user in the database
     *
     * @param username - the username of the new user
     * @param email - the user email
     * @param password - the user plain text password
     */
    @Transactional
    public void createUser(String username, String email, String password) {

        assertNotBlank(username, "Username cannot be empty.");
        assertMinimumLength(username, 6, "Username must have at least 6 characters.");
        assertNotBlank(email, "Email cannot be empty.");
        assertMatches(email, EMAIL_REGEX, "Invalid email.");
        assertNotBlank(password, "Password cannot be empty.");
        assertMatches(password, PASSWORD_REGEX, "Password must have at least 6 characters, with 1 numeric and 1 uppercase character.");

        if (!userRepository.isUsernameAvailable(username)) {
            throw new IllegalArgumentException("The username is not available.");
        }
        UserTest user = new UserTest(username, new BCryptPasswordEncoder().encode(password), email, DEFAULT_MAX_CAL_PER_DAY);
        

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserTest findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Transactional(readOnly = true)
    public Long findTodaysCaloriesForUser(String username) {
        return userRepository.findTodaysCaloriesForUser(username);
    }

}
