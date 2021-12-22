package com.seon.springvueproject.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class accountTest {

    @Test
    @DisplayName("롬복_기능_테스트")
    public void lombokTest(){
        // given
        String name = "test";
        int amount = 1000;

        // when
        account dto = new account(name, amount);

        // then
        assertThat(dto.getName()).isEqualTo(name);
        assertThat(dto.getAmount()).isEqualTo(amount);
    }

}