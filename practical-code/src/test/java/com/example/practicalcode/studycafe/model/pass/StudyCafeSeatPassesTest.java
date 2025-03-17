package com.example.practicalcode.studycafe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafeSeatPassesTest {

    @DisplayName("타입으로 좌석권을 찾는다.")
    @Test
    void findPassBy() {
        // given
        List<StudyCafeSeatPass> list = List.of(
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 3, 30000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 8, 8000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy())
        );
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(list);

        // when
        List<StudyCafeSeatPass> result = seatPasses.findPassBy(StudyCafePassType.FIXED);

        // then
        assertThat(result).hasSize(4);
        assertThat(result).extracting(StudyCafeSeatPass::getPassType).allMatch(StudyCafePassType.FIXED::equals);
    }

    @DisplayName("타입으로 좌석권을 찾는다.")
    @Test
    void notFindPassBy() {
        // given
        List<StudyCafeSeatPass> list = List.of(
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 8, 8000, new OpenEventDiscountPolicy()),
                StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy())
        );
        StudyCafeSeatPasses seatPasses = StudyCafeSeatPasses.of(list);

        // when
        List<StudyCafeSeatPass> result = seatPasses.findPassBy(StudyCafePassType.WEEKLY);

        // then
        assertThat(result).isEmpty();
    }
}