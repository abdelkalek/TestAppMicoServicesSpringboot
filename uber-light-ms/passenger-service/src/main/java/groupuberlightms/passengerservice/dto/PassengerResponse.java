package groupuberlightms.passengerservice.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Serializable so it can be stored in the Redis cache with the default
 * JDK serializer.
 */
public record PassengerResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        Double rating,
        LocalDateTime createdAt
) implements Serializable {
}
