package com.backend.questify.Util;

import com.backend.questify.DTO.UserDto;
import com.backend.questify.Entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DtoMapper {
 DtoMapper INSTANCE = Mappers.getMapper(DtoMapper.class);

 User UserDtoToUser (UserDto userDto);

 UserDto UserToUserDto (User user);

}
