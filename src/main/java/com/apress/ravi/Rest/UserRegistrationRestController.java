package com.apress.ravi.Rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apress.ravi.Exception.CustomErrorType;
import com.apress.ravi.repository.UserJpaRepository;
import com.apress.ravi.dto.UsersDTO;

@RestController
@RequestMapping("/api/user")
public class UserRegistrationRestController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserRegistrationRestController.class);

	private UserJpaRepository userJpaRepository;
	
	@Autowired
	public void setUserJpaRepository(UserJpaRepository userJpaRepository) {
		this.userJpaRepository = userJpaRepository;
	}
	
	// Method to get list of users
	@GetMapping("/")
	public ResponseEntity<List<UsersDTO>> listAllUsers() {
		List<UsersDTO> users = userJpaRepository.findAll();
		
		//check users is empty or not
		if (users.isEmpty()) {
			return new ResponseEntity<List<UsersDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UsersDTO>>(users, HttpStatus.OK);
	}
	
	// Method to create an user
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsersDTO> createUser(@RequestBody final UsersDTO user) {
		// check whether username exists
		if (userJpaRepository.findByName(user.getName()) != null) {
			return new ResponseEntity<UsersDTO>(new CustomErrorType(
					"Unable to create new user. A User with name "
					+ user.getName() + " already exist."), HttpStatus.CONFLICT);
		}
	    userJpaRepository.save(user);
	    return new ResponseEntity<UsersDTO>(user, HttpStatus.CREATED);
	}
	
	// Method to get user by id
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") final long id) {
		UsersDTO user = userJpaRepository.findOne(id);
		
		// check user object
		if (user == null) {
			return new ResponseEntity<UsersDTO>(
				   new CustomErrorType("User with id " + id + " not found "), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<UsersDTO>(user, HttpStatus.OK);
	}	
	
	// Method to update an existing user
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsersDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UsersDTO user) {
		
	     // fetch user based on id and set it to currentUser object of type UserDTO
	     UsersDTO currentUser = userJpaRepository.findOne(id);
	     
	     // check if currentUser whether it exists
	     if (currentUser == null) {
	    	 	return new ResponseEntity<UsersDTO>(
	    	 		   new CustomErrorType("Unable to update. User with id "
	    	 				   + id + " not found."), HttpStatus.NOT_FOUND);
	     }
	        
	     // update currentUser object data  with user object data
	     currentUser.setName(user.getName());
	     currentUser.setAddress(user.getAddress());
	     currentUser.setEmail(user.getEmail());
	        
	     // save currentUser object
	     userJpaRepository.saveAndFlush(currentUser);
	        
	     //return ResponseEntity object
	     return new ResponseEntity<UsersDTO>(currentUser, HttpStatus.OK);
	}
	
	// delete an existing user
	@DeleteMapping("/{id}")
	public ResponseEntity<UsersDTO> deleteUser(@PathVariable("id") final long id) {
		UsersDTO user = userJpaRepository.findOne(id);
		
		// check wheter user exists
		if (user == null) {
			return new ResponseEntity<UsersDTO>(
				   new CustomErrorType("Unable to delete. User with id " 
						   + id + " not found."), HttpStatus.NOT_FOUND);
		}
		userJpaRepository.delete(id);
		return new ResponseEntity<UsersDTO>(HttpStatus.NO_CONTENT);
	}
}
