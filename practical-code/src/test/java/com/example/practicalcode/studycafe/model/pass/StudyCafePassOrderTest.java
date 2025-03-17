package com.example.practicalcode.studycafe.model.pass;

import com.example.practicalcode.studycafe.model.locker.StudyCafeLockerPass;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StudyCafePassOrderTest {

    @DisplayName("사물함권이 없는 총 금액을 구한다.")
    @Test
    void getTotalPriceWithoutLockerPass() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.WEEKLY, 4, 10000, new OpenEventDiscountPolicy());
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // when
        int totalPrice = order.getTotalPrice();
        int expected = (int) (seatPass.getPrice() * 0.9);

        // then
        assertThat(totalPrice).isEqualTo(expected);
    }

    @DisplayName("할인이 없는 총 금액을 구한다.")
    @Test
    void getTotalPriceWithoutDiscount() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.HOURLY, 1, 1000, new OpenEventDiscountPolicy());
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, null);

        // when
        int totalPrice = order.getTotalPrice();
        int expected = seatPass.getPrice();

        // then
        assertThat(totalPrice).isEqualTo(expected);
    }

    @DisplayName("고정석과 라커패스를 구매한 금액을 구한다.")
    @Test
    void getTotalPrice() {
        // given
        StudyCafeSeatPass seatPass = StudyCafeSeatPass.of(StudyCafePassType.FIXED, 12, 100000, new OpenEventDiscountPolicy());
        StudyCafeLockerPass lockerPass = StudyCafeLockerPass.of(StudyCafePassType.FIXED, 12, 20000);
        StudyCafePassOrder order = StudyCafePassOrder.of(seatPass, lockerPass);

        // when
        int totalPrice = order.getTotalPrice();
        int expected = (int) (seatPass.getPrice() * 0.85) + lockerPass.getPrice();

        // then
        assertThat(totalPrice).isEqualTo(expected);
    }

}