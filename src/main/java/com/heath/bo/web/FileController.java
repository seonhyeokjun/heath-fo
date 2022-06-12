package com.heath.bo.web;

import com.heath.bo.service.file.FileService;
import com.heath.bo.web.dto.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 파일 정보 가져오기
     * @param id
     * @return
     */
    @GetMapping("/api/file/{id}")
    public @ResponseBody List<FileDto> getFiles(@PathVariable Long id){
        return fileService.getFiles(id);
    }

    /**
     * 파일 다운로드
     * @param id
     * @return
     * @throws IOException
     */
    @GetMapping("/api/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") Long id) throws IOException {
        FileDto fileDto = fileService.getDownloadFile(id);
        Path path = Paths.get(fileDto.getFilePath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;" +
                        " filename=\"" + URLEncoder.encode(fileDto.getRealFilename(), StandardCharsets.UTF_8) + "\"")
                .body(resource);
    }
}
