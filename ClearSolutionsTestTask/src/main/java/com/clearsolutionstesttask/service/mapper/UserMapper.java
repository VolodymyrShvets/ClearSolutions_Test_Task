package com.clearsolutionstesttask.service.mapper;

import com.clearsolutionstesttask.model.User;
import com.clearsolutionstesttask.model.dto.UserRegistrationDTO;
import com.clearsolutionstesttask.model.dto.UserResponseDTO;
import com.clearsolutionstesttask.model.dto.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userRegistrationDTOToUser(UserRegistrationDTO dto);

    UserRegistrationDTO userToUserRegistrationDTO(User user);

    UserResponseDTO userToUserResponseDTO(User user);

    User populateUserWithPresentUserUpdateDTOFields(@MappingTarget User user, UserUpdateDTO userDto);
}
