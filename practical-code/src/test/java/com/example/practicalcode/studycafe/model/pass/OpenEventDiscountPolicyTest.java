package com.example.practicalcode.studycafe.model.pass;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class OpenEventDiscountPolicyTest {

    private final DiscountPolicy discountPolicy = new OpenEventDiscountPolicy();

    @DisplayName("12주 이용권에 대해 15% 할인된 가격을 반환해야 한다.")
    @ParameterizedTest
    @CsvSource({
            "10000, 1500",   // 10000원 -> 1500원 (15% 할인)
            "50000, 7500",   // 50000원 -> 7500원 (15% 할인)
            "120000, 18000"  // 120000원 -> 18000원 (15% 할인)
    })
    void applyDiscount_TwelveWeeksPass(int price, int expectedDiscount) {
        // given
        StudyCafeSeatPass seatPass = createSeatPass(StudyCafePassType.WEEKLY, 12, price);

        // when
        int discount = discountPolicy.applyDiscount(seatPass);

        // then
        assertThat(discount).isEqualTo(expectedDiscount);
    }

    @DisplayName("2주 이상 주간 이용권에 대해 10% 할인된 가격을 반환해야 한다.")
    @ParameterizedTest
    @CsvSource({
            "10000, 1000",   // 10000원 -> 1000원 (10% 할인)
            "50000, 5000",   // 50000원 -> 5000원 (10% 할인)
            "120000, 12000"  // 120000원 -> 12000원 (10% 할인)
    })
    void applyDiscount_WeeklyPassOverTwoWeeks(int price, int expectedDiscount) {
        // given
        StudyCafeSeatPass seatPass = createSeatPass(StudyCafePassType.WEEKLY, 3, price);

        // when
        int discount = discountPolicy.applyDiscount(seatPass);

        // then
        assertThat(discount).isEqualTo(expectedDiscount);
    }

    @DisplayName("할인 대상이 아닌 이용권은 0원을 반환해야 한다.")
    @ParameterizedTest
    @CsvSource({
            "HOURLY, 1, 10000",  // 시간제 1시간 패스 (할인 없음)
            "WEEKLY, 1, 5000",    // 일일 이용 패스 (할인 없음)
    })
    void applyDiscount_NoDiscount(StudyCafePassType passType, int duration, int price) {
        // given
        StudyCafeSeatPass seatPass = createSeatPass(passType, duration, price);

        // when
        int discount = discountPolicy.applyDiscount(seatPass);

        // then
        assertThat(discount).isEqualTo(0);
    }

    private StudyCafeSeatPass createSeatPass(StudyCafePassType passType, int duration, int price) {
        return StudyCafeSeatPass.of(passType, duration, price, discountPolicy);
    }
}