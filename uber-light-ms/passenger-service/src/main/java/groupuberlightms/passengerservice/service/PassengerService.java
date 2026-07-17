package groupuberlightms.passengerservice.service;

import groupuberlightms.passengerservice.entity.Passenger;
import groupuberlightms.passengerservice.repository.PassengerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {

    private final PassengerRepository repository;

    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public List<Passenger> findAll() {
        return repository.findAll();
    }

    public Optional<Passenger> findById(Long id) {
        return repository.findById(id);
    }

    public Passenger create(Passenger passenger) {
        passenger.setId(null);
        return repository.save(passenger);
    }

    public Optional<Passenger> update(Long id, Passenger changes) {
        return repository.findById(id).map(existing -> {
            existing.setFirstName(changes.getFirstName());
            existing.setLastName(changes.getLastName());
            existing.setEmail(changes.getEmail());
            existing.setPhoneNumber(changes.getPhoneNumber());
            return repository.save(existing);
        });
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
