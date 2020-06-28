package com.brightcoding.ws.Repositeries;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.brightcoding.ws.Entities.UserEntity;

//public interface UserRepositery extends CrudRepository<UserEntity, Long> {
public interface UserRepositery extends PagingAndSortingRepository<UserEntity, Long> {	//Permet de récupérer une page de la base de donnée

	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String userId);
	
	@Query(value = "SELECT * FROM users",nativeQuery = true)	//Request sql pour récupérer touts les utilisateurs
	Page<UserEntity> findAllUsers(Pageable pageableRequest);
	
//	@Query("SELECT user FROM UserEntity user")	//Request JPQL parrail aux requetes sql. user est l'object de la classe pars la table!!
//	Page<UserEntity> findAllUsers(Pageable pageableRequest);
	
	@Query(value = "SELECT * FROM users u WHERE (u.first_name LIKE %:search% OR u.last_name LIKE %:search%) AND u.email_verification_status = :status ",nativeQuery = true)	// Cetet technique s'appelle NamedMarque , c'est recommandée par rapport à l'autre qui doit respecter l'ordre
	Page<UserEntity> findAllUsersByCritiria(Pageable pageableRequest, String search, int status);	

//	@Query(value = "SELECT * FROM users u WHERE (u.first_name = :search OR u.last_name = :search) AND u.email_verification_status = :status ",nativeQuery = true)	// Cetet technique s'appelle NamedMarque , c'est recommandée par rapport à l'autre qui doit respecter l'ordre
//	Page<UserEntity> findAllUsersByCritiria(Pageable pageableRequest, String search, int status);	//?1 concerne search et ?2 concerne status
	
	
	
//	@Query(value = "SELECT * FROM users u WHERE (u.first_name = ?1 OR u.last_name= ?1) AND u.email_verification_status = ?2 ",nativeQuery = true)	//La valeur search peut etre soit first_name or last_name
//	Page<UserEntity> findAllUsersByCritiria(Pageable pageableRequest, String search, int status);	//?1 concerne search et ?2 concerne status. Cetet methode s'appele QuestionMarque
	
	
	
	
//	@Query(value = "select * from users u where u.first_name)='walid'",nativeQuery = true)	//Request SQL
//	Page<UserEntity> findAllByFirstName(Pageable pageableRequest);
}
