package com.seon.springvueproject.service.file;

import com.seon.springvueproject.domain.file.FileLoad;
import com.seon.springvueproject.domain.file.FileRepository;
import com.seon.springvueproject.web.dto.FileDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileService {
    private FileRepository fileRepository;

    @Transactional
    public FileDto getFiles(Long id){
        FileLoad fileLoad = fileRepository.findByBoardId(id);
    }
}
