package com.apress.ravi.Rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.MediaType;
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
	
	@GetMapping("/")
	public ResponseEntity<List<UsersDTO>> listAllUsers() {
		List<UsersDTO> users = userJpaRepository.findAll();
		return new ResponseEntity<List<UsersDTO>>(users, HttpStatus.OK);
	}
	
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsersDTO> createUser(@RequestBody final UsersDTO user) {
	    userJpaRepository.save(user);
	    return new ResponseEntity<UsersDTO>(user, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UsersDTO> getUserById(@PathVariable("id") final long id) {
		UsersDTO user = userJpaRepository.findOne(id);
		return new ResponseEntity<UsersDTO>(user, HttpStatus.OK);
	}	
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsersDTO> updateUser(@PathVariable("id") final Long id, @RequestBody UsersDTO user) {
		
	     // fetch user based on id and set it to currentUser object of type UserDTO
	     UsersDTO currentUser = userJpaRepository.findOne(id);
	        
	     // update currentUser object data  with user object data
	     currentUser.setName(user.getName());
	     currentUser.setAddress(user.getAddress());
	     currentUser.setEmail(user.getEmail());
	        
	     // save currentUser object
	     userJpaRepository.saveAndFlush(currentUser);
	        
	     //return ResponseEntity object
	     return new ResponseEntity<UsersDTO>(currentUser, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<UsersDTO> deleteUser(@PathVariable("id") final long id) {
		userJpaRepository.delete(id);
		return new ResponseEntity<UsersDTO>(HttpStatus.NO_CONTENT);
	}
}
