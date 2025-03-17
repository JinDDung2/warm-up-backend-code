package com.example.practicalcode.studycafe.model.pass;

public class OpenEventDiscountPolicy implements DiscountPolicy {

    @Override
    public int applyDiscount(StudyCafeSeatPass pass) {
        int price = pass.getPrice();

        if (pass.isTwelveWeeksPass()) {
            return (int) (price * 0.85);
        }

        if (pass.isWeeklyPassOverTwoWeeks()) {
            return (int) (price * 0.9);
        }

        return price;
    }
}
