package com.brightcoding.ws.ServicesImpl;

import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brightcoding.ws.Entities.AddressEntity;
import com.brightcoding.ws.Entities.UserEntity;
import com.brightcoding.ws.Repositeries.AddressRepositery;
import com.brightcoding.ws.Repositeries.UserRepositery;
import com.brightcoding.ws.Services.AddressService;
import com.brightcoding.ws.Shared.Utils;
import com.brightcoding.ws.Shared.Dato.AdressDto;
import com.brightcoding.ws.Shared.Dato.UserDto;

@Service
public class AdressServiceImpl implements AddressService {
	@Autowired
	AddressRepositery addressRepositery;
	@Autowired
	UserRepositery userRespositery;
	@Autowired
	Utils utils;

	@Override
	public List<AdressDto> getAllAdsresses(String email) {
		UserEntity currentUser = userRespositery.findByEmail(email);
		List<AddressEntity> addressEntity= currentUser.getAdmin()==true ?(List<AddressEntity>) addressRepositery.findAll():(List<AddressEntity>) addressRepositery.findByUser(currentUser) ;
		Type listType = new TypeToken<List<AdressDto>>() {}.getType();
		List<AdressDto> addressesDto = new ModelMapper().map(addressEntity, listType);
		return addressesDto;
	}

	@Override
	public AdressDto createAddress(AdressDto addressDto, String email) {
	
		UserEntity currentUser= userRespositery.findByEmail(email);
		ModelMapper modelMapper = new ModelMapper();
		
		UserDto userDto = modelMapper.map(currentUser, UserDto.class);
		addressDto.setAddressId(utils.generateStringId(30));
		addressDto.setUser(userDto);
		AddressEntity addressEntity = modelMapper.map(addressDto, AddressEntity.class);
		AddressEntity addressdEntity= addressRepositery.save(addressEntity);
		AdressDto NewaddressDto = modelMapper.map(addressdEntity, AdressDto.class);
		return NewaddressDto;
	}

	@Override
	public AdressDto getOneAddress(String addressId) {
		AddressEntity addressEntity = addressRepositery.findByAddressId(addressId);
		if (addressEntity == null) throw new RuntimeException("address not found");
		ModelMapper modelMapper = new ModelMapper();	
		AdressDto adressDto = modelMapper.map(addressEntity, AdressDto.class);
		return adressDto;
	}
	
	@Override
	public void deleteAddress(String adressId) {
		AddressEntity addressEntity = addressRepositery.findByAddressId(adressId);
		if (addressEntity == null) throw new RuntimeException("address not found");
		addressRepositery.delete(addressEntity);
	}
	
	@Override
	public AdressDto updateAddress(String addressId, AdressDto addressDto) {
		AddressEntity addressEntity = addressRepositery.findByAddressId(addressId);
		if (addressEntity == null) throw new RuntimeException("address not found");
		addressEntity.setCity(addressDto.getCity());
		addressEntity.setCountry(addressDto.getCounntry());
		AddressEntity updateAddress = addressRepositery.save(addressEntity);
		ModelMapper modelMapper= new ModelMapper();
		AdressDto user= modelMapper.map(updateAddress, AdressDto.class);
		return user;
	}
}
