package com.brightcoding.ws.Repositeries;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.brightcoding.ws.Entities.AddressEntity;
import com.brightcoding.ws.Entities.UserEntity;

@Repository
public interface AddressRepositery extends CrudRepository<AddressEntity, Long> {

	List<AddressEntity> findByUser(UserEntity currentUser);
	AddressEntity findByAddressId(String addressId);

}
