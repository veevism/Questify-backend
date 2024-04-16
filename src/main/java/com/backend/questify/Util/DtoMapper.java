package com.backend.questify.Util;

import com.backend.questify.DTO.UserDto;
import com.backend.questify.Entity.User;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Mapper {
 Mapper INSTANCE = Mappers.getMapper(Mapper.class);

 UserDto UserDtoToUser (UserDto userDto);

 User UserToUserDto (User user);

}
