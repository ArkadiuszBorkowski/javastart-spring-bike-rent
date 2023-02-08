package pl.javastart.bikerent;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Transactional
    public void add(NewBikeDto newBike)    {
        Bike bike = new Bike(newBike.getId(),
                newBike.getModel(),
                newBike.getSerialNo(),
                newBike.getHourPrice(),
                newBike.getDayPrice());
        bikeRepository.save(bike);
    }
    @Transactional
    public void deleteByID(Long bikeId) {
        bikeRepository.deleteById(bikeId);
    }
    @Transactional
    public double rentForHours(Long bikeId, int hours, String borrowerId) {
        LocalDateTime dateOfReturn = LocalDateTime.now().plusHours(hours);
        Bike bike = updateBike(bikeId, borrowerId, dateOfReturn);
        return bike.getHourPrice() * hours;
    }
    @Transactional
    public double rentForDay(Long bikeId, String borrowerId) {
        LocalDateTime dateOfReturn = LocalDateTime.now().withHour(23).withMinute(59);
        Bike bike = updateBike(bikeId, borrowerId, dateOfReturn);
        return bike.getDayPrice();
    }
    @Transactional
    public void returnBike(Long bikeId) {
        updateBike(bikeId, null, null);
    }

    private Bike updateBike(Long bikeId, String borrowerId, LocalDateTime dateOfReturn) {
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(BikeNotFoundException::new);
        bike.setDateOfReturn(dateOfReturn);
        bike.setBorrowerId(borrowerId);
        bikeRepository.save(bike);
        return bike;
    }


}
