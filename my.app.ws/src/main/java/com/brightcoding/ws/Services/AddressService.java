package com.brightcoding.ws.Services;

import java.util.List;

import com.brightcoding.ws.Shared.Dato.AdressDto;
import com.brightcoding.ws.Shared.Dato.UserDto;

public interface AddressService {
	
	List<AdressDto> getAllAdsresses(String email);
	AdressDto createAddress (AdressDto addressDto, String email);
	AdressDto getOneAddress(String addressId);
	void deleteAddress(String addressId);
	AdressDto updateAddress(String Id, AdressDto addressDto);

	
}
