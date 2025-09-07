package by.task.userservice.mapper;

import by.task.userservice.dto.UserRequestDTO;
import by.task.userservice.dto.UserResponseDTO;
import by.task.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserRequestDTO dto);

    UserResponseDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromDTO(UserRequestDTO dto, @MappingTarget User user);
}