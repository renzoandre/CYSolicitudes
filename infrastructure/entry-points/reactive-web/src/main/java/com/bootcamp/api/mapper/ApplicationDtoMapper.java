package com.bootcamp.api.mapper;


import com.bootcamp.api.dto.ApplicationResponseDto;
import com.bootcamp.api.dto.CreateApplicationDto;
import com.bootcamp.model.application.Application;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationDtoMapper {
    ApplicationResponseDto toResponse(Application application);
    ApplicationResponseDto toResponseList(Application applications);
    Application toModel(CreateApplicationDto createUserDto);
}
