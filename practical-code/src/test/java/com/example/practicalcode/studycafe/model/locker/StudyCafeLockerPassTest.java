package com.example.practicalcode.studycafe.model.locker;

import com.example.practicalcode.studycafe.model.pass.StudyCafePassType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

class StudyCafeLockerPassTest {

    @DisplayName("같은 유형의 패스인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HOURLY, FIXED, false",
            "WEEKLY, FIXED, false",
            "FIXED, FIXED, true",
    })
    void isSamePassType(StudyCafePassType passType, StudyCafePassType matchedType, boolean expected) {
        // given
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(matchedType, 1, 10000);

        // when
        boolean result = lockerPass.isSamePassType(passType);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("같은 기간인지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "4, 12, false",
            "12, 12, true",
    })
    void isSameDuration(int duration, int matchedDuration, boolean expected) {
        // given
        StudyCafeLockerPass pass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, matchedDuration, 10000);

        // when
        boolean result = pass.isSameDuration(duration);

        // then
        assertThat(result).isEqualTo(expected);
    }
}