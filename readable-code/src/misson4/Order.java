package misson4;

import java.util.logging.Logger;

public class Order {

    private static final Logger log = Logger.getLogger(Order.class.getName());

    private final OrderItem items;
    private final Member member;
    private final int totalPrice;

    private Order(OrderItem items, Member member, int totalPrice) {
        this.items = items;
        this.member = member;
        this.totalPrice = totalPrice;
    }

    public boolean validate() {
        if (items.isEmpty()) {
            log.info("주문 항목이 없습니다.");
            return false;
        }

        if (totalPrice <= 0) {
            log.info("올바르지 않은 총 가격입니다.");
            return false;
        }

        if (member.hasNotInfo()) {
            log.info("사용자 정보가 없습니다.");
            return false;
        }

        return true;
    }
}
