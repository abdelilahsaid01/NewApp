package com.brightcoding.ws.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brightcoding.ws.Exceptions.UserException;
import com.brightcoding.ws.Request.UserRequest;
import com.brightcoding.ws.Response.ErroMessages;
import com.brightcoding.ws.Response.UserResponse;
import com.brightcoding.ws.Services.UserService;
import com.brightcoding.ws.Shared.Dato.UserDto;

//@CrossOrigin("*")	//Autoriser le Client (Angulat/VusJs/autresBAcEnd pour executer toutes les methodes. Pour spécifier il faut copier au sessus de la methode et monsiennet le nom domaine (http://localhhost:4200 or hhtp://java.com
@RestController
@RequestMapping("/users")

public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})//Produce car elle va produire des donnée la sérialisation	//Envois des donnée en format xml pas Json 
	public ResponseEntity<UserResponse> getUser(@PathVariable String id){	 
		UserDto userDto=userService.getUserByUserId(id);
		UserResponse userResponse= new UserResponse();
		BeanUtils.copyProperties(userDto, userResponse);
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.OK);	//status 200
			}
	
	
	@GetMapping	//Réception d'une list des users. C'est ps la peine d'indiquer le Id
	public ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "4") int limit,
			@RequestParam(value = "search", defaultValue = "") String search,
	@RequestParam(value = "status", defaultValue = "0") int status){	//Réception des valeurs des paramètres de URL
		List<UserResponse> usersResponse = new ArrayList<>();
		List<UserDto> users = userService.getUsers(page, limit,search,status);
		
		for (UserDto userDto:users) {
			 ModelMapper modelMapper = new ModelMapper();
			UserResponse userResponse = modelMapper.map(userDto, UserResponse.class);
//			 UserResponse user = new UserResponse();
//			BeanUtils.copyProperties(userDto, user);
			usersResponse.add(userResponse);
		 }
		return new ResponseEntity<List<UserResponse>>(usersResponse, HttpStatus.OK);
}
	
	
	
	@PostMapping(	produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}, 
					consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}) 	//Envois et la réception en format xml
	
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) throws Exception {	//Type de retour UserResponse. RequestBody permet de récupérer un object. @Valid permet permet de vérifier les contrainte mentionnée au niveau de Response (NotNull/Size..)
		
		if(userRequest.getFirstName().isEmpty()) throw new UserException(ErroMessages.MISSING_REQUIERED_FIELD.getErrorMessage());	//Personnaliser les exception les messages d'exceptions 
		
//		UserDto userDao = new UserDto();
//		BeanUtils.copyProperties(userRequest, userDao);
		
		ModelMapper modelMapper = new ModelMapper();
		UserDto userDao = modelMapper.map(userRequest, UserDto.class);	//mapping une instance d'une classe vers une autres, mieux que d'utiliser CopyProperties
		
		UserDto createUser= userService.createUser(userDao);
//		UserResponse userResponse = new UserResponse();
//		BeanUtils.copyProperties(createUser, userResponse);
		UserResponse userResponse = modelMapper.map(createUser, UserResponse.class);	//Cette écriture est équivalente aux deux lignes au-dessus
		
		
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.CREATED); // Status 201
	}
	
	
	
	@PutMapping(path="/{id}" ,  produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}, //Réception(Déserialisation) soit xml ou Json
								consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})  //Envois(Sérialisation) soit xml ou Json
	public ResponseEntity<UserResponse> updateUser(@PathVariable String id,@RequestBody UserRequest userRequest){
		UserDto userDao = new UserDto();
		BeanUtils.copyProperties(userRequest, userDao);
		UserDto updateUser= userService.updateUser(id, userDao);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(updateUser, userResponse);
		return new ResponseEntity<UserResponse>(userResponse, HttpStatus.ACCEPTED);	//Status 202
	}
	
	@DeleteMapping(path="/{id}") 
	public ResponseEntity<Object> deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);	//Type de retours Status 204
	}
	// testtttttttt
	// hhhhhh

}
