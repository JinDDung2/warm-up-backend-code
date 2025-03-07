package misson4;

import java.util.List;

public class Order {

    private final List<Item> items;
    private final Member member;
    private final int totalPrice;

    private Order(List<Item> items, Member member, int totalPrice) {
        this.items = items;
        this.member = member;
        this.totalPrice = totalPrice;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public boolean hasCustomerInfo() {
        return member != null;
    }
}
