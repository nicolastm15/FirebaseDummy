package com.example.firebasedummyserver.service;

import java.util.List;

import com.example.firebasedummyserver.model.entities.EntityRole;
import com.example.firebasedummyserver.model.repositories.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

   @Autowired
   private RoleRepository repository;

   /**
    * This methods fetches all the user roles in the database.
    * 
    * @return ArrayList<EntityRole> - An array of EntityRole objects
    */
   public List<EntityRole> getAll() {
      return (List<EntityRole>) repository.findAll();
   }

   /**
    * This method saves a user role in the database, if one doesn't already exist.
    * 
    * @param EntityRole EntityRole - An EntityRole object
    * @return EntityRole - The recently saved EntityRole object, if creation was
    *         successful
    *         <li>null, if creation was not successful</li>
    */
   public EntityRole add(EntityRole role) throws IllegalArgumentException, DataIntegrityViolationException {
      try {
         return repository.save(role);
      } catch (DataIntegrityViolationException e) {
         throw new DataIntegrityViolationException("This role already exists in the database");
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException("The received parameter cannot be null");
      }
   }

   /**
    * This method fetches a user role by ID and edits its values based on the
    * EntityRole object received as parameter.
    * 
    * @param id    long - An user role's ID
    * @param place EntityRole - An EntityRole object
    * @return EntityRole - An EntityRole object
    */
   public EntityRole edit(long id, EntityRole role) throws IllegalArgumentException, DataIntegrityViolationException {

      if (role == null)
         throw new IllegalArgumentException("Role null");

      return repository.findById(id).map(record -> {

         try {
            record.setName(role.getName());
            return repository.save(record);
         } catch(DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("The edited role conflicts with a role object already in the database");
         } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("No role with this id was found");
         }

      }).orElse(null);
      
   }

   /**
    * This method tries to delete a user role by ID if one exists.
    * 
    * @param id long - An EntityRole's ID
    * @return String - A message saying which user role was deleted
    *         <li>null, if couldn't find the user role to delete</li>
    */
   public Boolean delete(long id) {
      return repository.findById(id).map(record -> {

         try {
            repository.deleteById(id);
            return true;

         } catch (Exception e) {
            e.printStackTrace();
            return false;
         }

      }).orElse(false);
   }

}
