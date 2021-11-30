package com.seon.springvueproject.service.file;

import com.seon.springvueproject.domain.file.FileLoad;
import com.seon.springvueproject.domain.file.FileRepository;
import com.seon.springvueproject.web.dto.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public FileDto getFiles(Long id){
        FileLoad fileLoad = fileRepository.findByBoardId(id);

        return FileDto.builder()
                .id(id)
                .realFilename(fileLoad.getRealFilename())
                .filename(fileLoad.getFilename())
                .filePath(fileLoad.getFilePath())
                .build();
    }
}
