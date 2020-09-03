package com.example.firebasedummyserver.model.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.example.firebasedummyserver.model.entities.EntityRole;

public interface RoleRepository extends PagingAndSortingRepository<EntityRole, Long> {

	/**
	 * Find a role instance by its name.
	 * 
	 * @param name String - A role's name
	 * @return EntityRole - An EntityRole object
	 */
	@Query(nativeQuery = true, value = "SELECT * FROM roles WHERE roles.role_name = :name")
	public EntityRole findByName(@Param("name") String name);

}
