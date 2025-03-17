package com.example.practicalcode.studycafe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassTypeTest {
    @DisplayName("사물함 패스를 구매 할 수 있는 타입들을 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HOURLY, false",
            "WEEKLY, false",
            "FIXED, true",
    })
    void isLockerType(StudyCafePassType type, boolean expected) {
        // when
        boolean result = type.isLockerType();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("사물함 패스를 구매 불가한 타입인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HOURLY, true",
            "WEEKLY, true",
            "FIXED, false",
    })
    void isNotLockerType(StudyCafePassType type, boolean expected) {
        // when
        boolean result = type.isNotLockerType();

        // then
        assertThat(result).isEqualTo(expected);
    }
}