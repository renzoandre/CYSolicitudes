package com.bootcamp.api.mapper;


import com.bootcamp.api.dto.ApplicationFilterDto;
import com.bootcamp.api.dto.ApplicationFilteredResponseDto;
import com.bootcamp.api.dto.ApplicationResponseDto;
import com.bootcamp.api.dto.CreateApplicationDto;
import com.bootcamp.model.application.Application;
import com.bootcamp.model.application.ApplicationFilter;
import com.bootcamp.model.application.ApplicationFilteredResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApplicationDtoMapper {
    ApplicationResponseDto toResponse(Application application);
    ApplicationResponseDto toResponseList(Application applications);
    Application toModel(CreateApplicationDto createUserDto);
    ApplicationFilter toModel(ApplicationFilterDto applicationFilterDto);
    ApplicationFilteredResponseDto toResponse(ApplicationFilteredResponse applicationFilteredResponse);
}
