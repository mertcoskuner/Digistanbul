package com.example._proj;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FileRepository extends MongoRepository<FileData, String> {
    Optional<FileData> findByName(String fileName);
}
