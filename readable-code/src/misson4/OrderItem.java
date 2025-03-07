package misson4;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {

    private final List<Item> items = new ArrayList<>();

    public boolean isEmpty() {
        return items.isEmpty();
    }

}
