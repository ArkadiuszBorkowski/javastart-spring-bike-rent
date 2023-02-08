package pl.javastart.bikerent;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
class BikeRepository {
    private final EntityManager entityManager;

    public BikeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public void save(Bike bike) {
            entityManager.persist(bike);
    }

    private boolean exist(Bike bike) {
        return entityManager.find(Bike.class, bike.getId()) != null;
    }

    public Optional<Bike> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Bike.class, id));
    }

    public void deleteById(Long id){
        findById(id).ifPresent(entityManager::remove);
    }


}
