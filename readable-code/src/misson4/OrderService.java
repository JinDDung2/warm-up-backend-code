package misson4;

import java.util.logging.Logger;

public class OrderService {

    private static final Logger log = Logger.getLogger(OrderService.class.getName());

    public boolean validateOrder(Order order) {
        if (order.getItems().isEmpty()) {
            log.info("주문 항목이 없습니다.");
            return false;
        } else {
            if (order.getTotalPrice() > 0) {
                if (!order.hasCustomerInfo()) {
                    log.info("사용자 정보가 없습니다.");
                    return false;
                } else {
                    return true;
                }
            } else if (!(order.getTotalPrice() > 0)) {
                log.info("올바르지 않은 총 가격입니다.");
                return false;
            }
        }
        return true;
    }

}
