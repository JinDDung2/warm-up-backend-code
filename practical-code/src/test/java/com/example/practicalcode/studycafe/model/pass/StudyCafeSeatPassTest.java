package com.example.practicalcode.studycafe.model.pass;

import com.example.practicalcode.studycafe.model.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassTest {

    @DisplayName("사물함 패스를 사용할 수 없는지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HOURLY, true",
            "WEEKLY, true",
            "FIXED, false",
    })
    void cannotUseLocker(StudyCafePassType type, boolean expected) {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(type, 1, 10000, new OpenEventDiscountPolicy());

        // when
        boolean result = seatPass.cannotUseLocker();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("사물함 패스권과 좌석권의 타입과 기간이 동일한지 확인한다.")
    @ParameterizedTest
    @CsvSource({
            "HOURLY, 6, FIXED, 12, false", // 둘다 다를경우
            "HOURLY, 6, FIXED, 6, false", // type만 다를경우
            "FIXED, 6, FIXED, 12, false", // duration만 다를 경우
            "FIXED, 12, FIXED, 12, true", // 둘이 같을 경우
    })
    void isSameDurationType(StudyCafePassType lockerType, int lockerDuration, StudyCafePassType matchedType, int matchedDuration, boolean expected) {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(matchedType, matchedDuration, 10000, new OpenEventDiscountPolicy());
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(lockerType, lockerDuration, 10000);

        // when
        boolean result = seatPass.isSameDurationType(lockerPass);

        // then
        assertThat(result).isEqualTo(expected);
    }
}