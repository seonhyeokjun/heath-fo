package com.seon.springvueproject.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileDto {
    private Long id;
    private String realFilename;
    private String filename;
    private String filePath;

    @Builder
    public FileDto(Long id, String realFilename, String filename, String filePath){
        this.id = id;
        this.realFilename = realFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
