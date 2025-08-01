package com.edu.school.management.service;

import com.edu.school.management.entity.UserProfileEntity;

public interface UserProfileService {
	
	//get all user by id
    UserProfileEntity getUserById(Long id);
    
    //save the profile
    UserProfileEntity saveUserProfile(UserProfileEntity userProfile);
}
