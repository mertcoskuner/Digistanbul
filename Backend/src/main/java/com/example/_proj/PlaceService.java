package com.example._proj;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PlaceService {
    private final PlaceRepository placeRepository;
    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public Optional<Place> getPlaceByPlacename(String placeName) {
        return placeRepository.findPlaceByPlaceName(placeName);
    }

    public Optional<Place> deletePlace(String placeName) {
        return placeRepository.deletePlaceByPlaceName(placeName);
    }

    public List<Place> getPlacesByAdress(String address) {
        return placeRepository.findPlacesByAddress(address);
    }

    public Place addPlace(Place newPlace) {
        // You can perform any validation or business logic here before saving the place
        return placeRepository.save(newPlace);
    }

}
