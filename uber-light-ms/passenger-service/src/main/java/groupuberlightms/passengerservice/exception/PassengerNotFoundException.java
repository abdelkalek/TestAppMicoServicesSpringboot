package groupuberlightms.passengerservice.exception;

public class PassengerNotFoundException extends RuntimeException {

    private final Long id;

    public PassengerNotFoundException(Long id) {
        super("Passenger with id " + id + " not found");
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
