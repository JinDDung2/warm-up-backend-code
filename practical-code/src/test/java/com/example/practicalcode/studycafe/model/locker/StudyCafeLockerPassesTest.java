package com.example.practicalcode.studycafe.model.locker;

import com.example.practicalcode.studycafe.model.pass.OpenEventDiscountPolicy;
import com.example.practicalcode.studycafe.model.pass.StudyCafePassType;
import com.example.practicalcode.studycafe.model.pass.StudyCafeSeatPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeLockerPassesTest {

    @Test
    @DisplayName("일치하는 duration을 가진 패스를 찾는다.")
    void findLockerPassBy_WhenMatchingPassExists() {
        // given
        StudyCafeLockerPass fixedPass = createFixedLockerPass();
        StudyCafeLockerPass weeklyPass = createWeeklyLockerPass();
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(fixedPass, weeklyPass));

        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 30, 10000, new OpenEventDiscountPolicy()); // 같은 duration을 가지는 패스

        // when
        Optional<StudyCafeLockerPass> foundPass = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertThat(foundPass)
                .isPresent()
                .get()
                .isEqualTo(fixedPass);

    }

    @Test
    @DisplayName("일치하는 패스가 없을 경우 비어있는지 확인한다.")
    void testFindLockerPassBy_WhenNoMatchingPassExists() {
        // given
        StudyCafeLockerPass pass1 = createFixedLockerPass();
        StudyCafeLockerPass pass2 = createWeeklyLockerPass();
        StudyCafeLockerPasses lockerPasses = StudyCafeLockerPasses.of(List.of(pass1, pass2));

        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1000, new OpenEventDiscountPolicy()); // 같은 duration을 가지는 패스 // 일치하는 duration 없음

        // when
        Optional<StudyCafeLockerPass> foundPass = lockerPasses.findLockerPassBy(seatPass);

        // then
        assertThat(foundPass).isEmpty();
    }

    private StudyCafeLockerPass createFixedLockerPass() {
        return StudyCafeLockerPass.of(StudyCafePassType.FIXED, 30, 10000);
    }

    private StudyCafeLockerPass createWeeklyLockerPass() {
        return StudyCafeLockerPass.of(StudyCafePassType.WEEKLY, 7, 3500);
    }
}