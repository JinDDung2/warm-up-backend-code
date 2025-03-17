package com.example.practicalcode.studycafe.model.pass;

import com.example.practicalcode.studycafe.model.locker.StudyCafeLockerPass;

public class StudyCafeSeatPass implements StudyCafePass{

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final DiscountPolicy discountPolicy;

    private StudyCafeSeatPass(StudyCafePassType passType, int duration, int price, DiscountPolicy discountPolicy) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }

    public static StudyCafeSeatPass of(StudyCafePassType passType, int duration, int price, DiscountPolicy discountPolicy) {
        return new StudyCafeSeatPass(passType, duration, price, discountPolicy);
    }

    public boolean isSameDurationType(StudyCafeLockerPass lockerPass) {
        return lockerPass.isSamePassType(this.passType)
                && lockerPass.isSameDuration(this.duration);
    }

    public boolean isSameType(StudyCafePassType type) {
        return this.passType == type;
    }

    public boolean cannotUseLocker() {
        return this.passType.isNotLockerType();
    }

    public boolean isTwelveWeeksPass() {
        return this.duration == 12;
    }

    public boolean isWeeklyPassOverTwoWeeks() {
        return this.passType == StudyCafePassType.WEEKLY && this.duration >= 2;
    }

    public int getDiscountPrice() {
        return discountPolicy.applyDiscount(this);
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getPrice() {
        return price;
    }

}
