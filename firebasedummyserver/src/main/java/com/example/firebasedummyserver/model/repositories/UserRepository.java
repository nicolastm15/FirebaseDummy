package com.example.firebasedummyserver.model.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.firebasedummyserver.model.entities.EntityUser;

public interface UserRepository extends PagingAndSortingRepository<EntityUser, Long> {

	/**
	 * Find a user instance by its username.
	 * 
	 * @param username String - A user's username
	 * @return EntityUser - An EntityUser object
	 */
	@Query("select u from EntityUser u where u.username like :username")
	public EntityUser findByUsername(@Param("username") String username);

	/**
	 * Find a user instance by its email.
	 * 
	 * @param email String - A user's email
	 * @return EntityUser - An EntityUser object
	 */
	@Query("select u from EntityUser u where u.email like :email%")
	public Optional<EntityUser> findByEmail(@Param("email") String email);

}
