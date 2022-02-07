package com.seon.springvueproject.domain.file;

import com.seon.springvueproject.domain.board.Board;
import com.seon.springvueproject.domain.board.BoardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class FileRepositoryTest {
    @Autowired private FileRepository fileRepository;

    @Test
    @DisplayName("저장된 파일을 불러온다.")
    void 파일_불러오기() throws Exception{
        // given
        String realFilename = "testFile";
        UUID uuid = UUID.randomUUID();
        String filename = uuid + "_" + realFilename;
        String filePath = System.getProperty("user.dir") + "/src/main/resources/static/files" + "/" + filename;

        FileLoad fileLoad = new FileLoad(realFilename, filename, filePath);

        // when
        fileRepository.save(fileLoad);
        List<FileLoad> byBoardId = fileRepository.findAll();

        // then
        FileLoad findFile = byBoardId.get(0);
        assertThat(findFile.getFilename()).isEqualTo(filename);
    }
}