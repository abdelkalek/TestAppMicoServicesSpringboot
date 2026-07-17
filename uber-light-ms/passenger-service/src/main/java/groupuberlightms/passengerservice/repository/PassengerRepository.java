package groupuberlightms.passengerservice.repository;

import groupuberlightms.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Excluded from Spring Data REST auto-export so the explicit
 * {@code /api/passengers} controller is the single HTTP entry point.
 */
@RepositoryRestResource(exported = false)
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
}
