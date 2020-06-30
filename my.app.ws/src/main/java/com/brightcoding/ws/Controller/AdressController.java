package com.brightcoding.ws.Controller;

import java.lang.reflect.Type;
import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
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

import com.brightcoding.ws.Request.AdressRequest;
import com.brightcoding.ws.Request.UserRequest;
import com.brightcoding.ws.Response.AddressResponse;
import com.brightcoding.ws.Response.UserResponse;
import com.brightcoding.ws.Services.AddressService;
import com.brightcoding.ws.Shared.Dato.AdressDto;
import com.brightcoding.ws.Shared.Dato.UserDto;

@RestController
@RequestMapping("/addresses")
public class AdressController {

	@Autowired
	AddressService addressService;

	@GetMapping
	public ResponseEntity<List<AddressResponse>> getAddresses(Principal principal) {
		List<AdressDto> addresses = addressService.getAllAdsresses(principal.getName()); // Récupérer par defaut l'émail
		Type listType = new TypeToken<List<AddressResponse>>() {}.getType();
		List<AddressResponse> addressesResponse = new ModelMapper().map(addresses, listType);
		return new ResponseEntity<List<AddressResponse>>(addressesResponse, HttpStatus.OK);
	}

	@PostMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, 
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE ,MediaType.MULTIPART_FORM_DATA_VALUE})	//uplouad

	public ResponseEntity<AddressResponse> StoreAddress(@RequestBody @Valid AdressRequest addressRequest, Principal principal ) throws Exception {
		ModelMapper modelMapper = new ModelMapper();
		AdressDto addressDto = modelMapper.map(addressRequest, AdressDto.class);
		AdressDto createAdress=addressService.createAddress(addressDto,principal.getName());	
		AddressResponse newAddress = modelMapper.map(createAdress, AddressResponse.class);
		return new ResponseEntity<AddressResponse>(newAddress, HttpStatus.CREATED);
	}
	
	@GetMapping(path="/{id}",produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})//Produce car elle va produire des donnée la sérialisation	//Envois des donnée en format xml pas Json 
	public ResponseEntity<AddressResponse> getOneAddress(@PathVariable(name="id") String addressId){	 
		AdressDto addressDto=addressService.getOneAddress(addressId);
		ModelMapper modelMapper = new ModelMapper();
		AddressResponse addressResponse = modelMapper.map(addressDto, AddressResponse.class);
		return new ResponseEntity<AddressResponse>(addressResponse, HttpStatus.OK);	//status 200
			}
	
	@DeleteMapping(path="/{id}") 
	public ResponseEntity<Object> deleteAddress(@PathVariable(name="id") String addressId) {
		addressService.deleteAddress(addressId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);	//Type de retours Status 204
	}
	
	@PutMapping(path="/{id}" ,  produces = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE}, //Réception(Déserialisation) soit xml ou Json
			consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})  //Envois(Sérialisation) soit xml ou Json
public ResponseEntity<AddressResponse> updateAddress(@PathVariable String id,@RequestBody AdressRequest addressRequest){
ModelMapper modelMapper = new ModelMapper();
AdressDto addressDao = modelMapper.map(addressRequest, AdressDto.class);
AdressDto newAddress= addressService.updateAddress(id, addressDao);
AddressResponse userResponse = modelMapper.map(newAddress, AddressResponse.class);
return new ResponseEntity<AddressResponse>(userResponse, HttpStatus.ACCEPTED);	//Status 202
}
	
}
