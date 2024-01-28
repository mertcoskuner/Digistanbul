package com.example._proj;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

@Entity
@Table(name = "FILE_DATA")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String type;
    private String filePath;
    private String name;



    public FileData(String name, String type, String filePath) {

        super();
        this.name = name;
        this.type = type;
        this.filePath = filePath;
    }


}
