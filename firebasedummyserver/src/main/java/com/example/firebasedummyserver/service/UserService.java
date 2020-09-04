package com.example.firebasedummyserver.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.firebasedummyserver.model.entities.EntityRole;
import com.example.firebasedummyserver.model.entities.EntityUser;
import com.example.firebasedummyserver.model.repositories.RoleRepository;
import com.example.firebasedummyserver.model.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	/**
	 * This method fetches all EntityUser instances in the database and returns them
	 * in a Page instead of a List.
	 * 
	 * @param pageable Pageable - An object describing pagination properties
	 * @return Page<EntityUser> - A list of EntityUser objects
	 */
	public Page<EntityUser> getAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	/**
	 * This method fetches a user by ID.
	 * 
	 * @param id - A user's ID
	 * @return EntityUser - An EntityUser object
	 *         <li>null, if user doesn't exist</li>
	 * 
	 */
	public EntityUser getById(long id) {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * This method saves the user in the database, if one doesn't already exist.
	 * 
	 * @param user EntityUser - An EntityUser object
	 * @return EntityUser - The recently saved EntityUser object, if creation was
	 *         successful
	 *         <li>null, if user already exists</li>
	 * @throws IllegalArgumentException if the "user" parameter is null
	 * 
	 */
	public EntityUser addBasicUser(EntityUser user) throws IllegalArgumentException, DataIntegrityViolationException {
		if (user == null) {
			throw new IllegalArgumentException("received: null object as a parameter; expecting: An EntityUser object");
		}

		try {
			EntityRole basicRole = roleRepository.findByName("USER");
			user.setRoles(basicRole);

			return userRepository.save(user);

		} catch (IllegalArgumentException e) { //TODO exception nao esta sendo jogada
			throw e;
		} catch (DataIntegrityViolationException e){
			throw new DataIntegrityViolationException ("The user already exists");
		}
	}

	/**
	 * This method fetches a user by ID and edits its values based on the EntityUser
	 * object received as parameter.
	 * 
	 * @param id   long - The desired user's ID
	 * @param user EntityUser - An EntityUser object with the new values to insert
	 *             into the one in the database.
	 * @return EntityUser - The edited EntityUser object, if editing was successful
	 *         <li>null, if the desired object doesn't exist in the database</li>
	 * @throws IllegalArgumentException        if the "user" parameter is null
	 * @throws DataIntegrityViolationException if any of the edit user's attributes
	 *                                         conflict with another user
	 */
	public EntityUser edit(long id, EntityUser user) throws IllegalArgumentException, DataIntegrityViolationException {
		if (user == null) {
			throw new IllegalArgumentException("received: null object as a parameter; expecting: An EntityUser object");
		}

		Optional<EntityUser> userWithOldValues = userRepository.findById(id);
		if (userWithOldValues.isPresent()) {
			EntityUser modifiedUser = userWithOldValues.get();
			ReflectionUtils.doWithFields(modifiedUser.getClass(), field -> {
				field.setAccessible(true);
				if (field.get(user) != null && field.getName() != "id") {
					field.set(modifiedUser, field.get(user));
				}
			});
			return userRepository.save(modifiedUser);
		}

		return null;

	}

	/**
	 * This method tries to delete a user by ID if one exists.
	 * 
	 * @param id long - The desired user's ID
	 * @return true - if delete was successful
	 *         <li>false, if the desired user doesn't exist in the database</li>
	 */
	public boolean delete(long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			return true;
		}

		return false;

	}

	/**
	 * This method fetches a user by its username and then gives the user his/her
	 * deserved points for first access in a day.
	 * 
	 * @param username String - A user's username
	 * @return EntityUser - The desired EntityUser object
	 *         <li>null, if the desired user doesn't exist in the database</li>
	 * @throws IllegalArgumentException if the "username" parameter is null
	 * 
	 */
	public EntityUser getUserByUsername(String username) throws IllegalArgumentException {
		if (username == null) {
			throw new IllegalArgumentException("received: null object as a parameter; expecting: An EntityUser object");
		}

		EntityUser user = userRepository.findByUsername(username);

		if (user != null) {
			return user;
		}
		return null;
	}

	/**
	 * This method fetches a user by its email and then gives the user his/her
	 * deserved points for first access in a day.
	 * 
	 * @param email String - A user's email
	 * @return Optional<EntityUser> - The desired EntityUser object
	 *         <li>null, if the desired user doesn't exist in the database</li>
	 * @throws IllegalArgumentException if the "email" parameter is null
	 */
	public Optional<EntityUser> getUserByEmail(String email) throws IllegalArgumentException {
		if (email == null) {
			throw new IllegalArgumentException("received: null object as a parameter; expecting: An EntityUser object");
		}

		Optional<EntityUser> user = userRepository.findByEmail(email);

		if (user != null) {
			return user;
		}

		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

}
