package com.heath.bo.user.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER","일반 사용자"),
    TRAINER("ROLE_TRAINER", "트레이너"),
    GYM("ROLE_GYM", "헬스장");

    private final String key;
    private final String title;
}
