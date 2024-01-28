package com.example._proj;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/place")
@AllArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/allplaces")
    public List<Place> fetchAllPlaces() {
        return placeService.getAllPlaces();
    }

    @GetMapping("/place-with-placename/{placeName}")
    public Optional<Place> getPlace(@PathVariable String placeName) {
        return placeService.getPlaceByPlacename(placeName);
    }

    @DeleteMapping("/delete-with-placename/{placeName}")
    public Optional<Place> deletePlaceName(@PathVariable String placeName) {
        return placeService.deletePlace(placeName);
    }

    @GetMapping("/get-place-with-adress/{address}")
    public List<Place> getWithAddress(@PathVariable String address) {
        return placeService.getPlacesByAdress(address);
    }

    @GetMapping("/get-comment-of-place/{placeName}")
    public List<Comment> getCommentsWithPlaceName(@PathVariable String placeName) {
        return placeService.getPlaceByPlacename(placeName).get().getComments();
    }

    @PostMapping("/add-place")
    public ResponseEntity<Place> addPlace(@RequestBody Place newPlace) {
        Place savedPlace = placeService.addPlace(newPlace);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPlace);
    }
}
