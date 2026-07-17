package groupuberlightms.passengerservice.mapper;

import groupuberlightms.passengerservice.dto.PassengerRequest;
import groupuberlightms.passengerservice.dto.PassengerResponse;
import groupuberlightms.passengerservice.entity.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Passenger toEntity(PassengerRequest request);

    PassengerResponse toResponse(Passenger entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void update(@MappingTarget Passenger entity, PassengerRequest request);
}
