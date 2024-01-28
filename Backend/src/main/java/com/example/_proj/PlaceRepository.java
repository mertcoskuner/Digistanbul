package com.example._proj;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends MongoRepository<Place, String> {
    Optional<Place> findPlaceByPlaceName(String placeName);

    Optional<Place> deletePlaceByPlaceName(String placeName);

    List<Place> findPlacesByAddress(String address);
}
