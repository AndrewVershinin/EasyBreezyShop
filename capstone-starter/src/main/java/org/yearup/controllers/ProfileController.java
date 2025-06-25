package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProfileDao;
import org.yearup.data.UserDao;
import org.yearup.models.Profile;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/profile")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final ProfileDao profileDao;
    private final UserDao userDao;

    @Autowired
    public ProfileController(ProfileDao profileDao, UserDao userDao) {
        this.profileDao = profileDao;
        this.userDao = userDao;
    }

    // GET /profile - return the profile for the current user
    @GetMapping
    public Profile getProfile(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            return profileDao.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not retrieve profile.", e);
        }
    }

    // PUT /profile - update the current user's profile
    @PutMapping
    public Profile updateProfile(@RequestBody Profile profile, Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            profile.setUserId(user.getId());
            profileDao.update(profile);

            return profileDao.getByUserId(user.getId()); // return updated profile
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Could not update profile.", e);
        }
    }
}

