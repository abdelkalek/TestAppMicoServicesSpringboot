package groupuberlightms.passengerservice.service;

import groupuberlightms.passengerservice.dto.PassengerRequest;
import groupuberlightms.passengerservice.dto.PassengerResponse;
import groupuberlightms.passengerservice.entity.Passenger;
import groupuberlightms.passengerservice.exception.PassengerNotFoundException;
import groupuberlightms.passengerservice.mapper.PassengerMapper;
import groupuberlightms.passengerservice.repository.PassengerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    public static final String CACHE_NAME = "passengers";

    private final PassengerRepository repository;
    private final PassengerMapper mapper;

    public PassengerService(PassengerRepository repository, PassengerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PassengerResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Cacheable(cacheNames = CACHE_NAME, key = "#id")
    public PassengerResponse findById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new PassengerNotFoundException(id));
    }

    public PassengerResponse create(PassengerRequest request) {
        Passenger saved = repository.save(mapper.toEntity(request));
        return mapper.toResponse(saved);
    }

    @CachePut(cacheNames = CACHE_NAME, key = "#id")
    public PassengerResponse update(Long id, PassengerRequest request) {
        Passenger existing = repository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
        mapper.update(existing, request);
        return mapper.toResponse(repository.save(existing));
    }

    @CacheEvict(cacheNames = CACHE_NAME, key = "#id")
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new PassengerNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
