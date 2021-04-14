
public class Run {
    public static void main(String[] args) {
        Warehouse warehouse = new Warehouse();
        warehouse.show();
        warehouse.sell_product("Dinning Table", 1);
        warehouse.show();
        warehouse.sell_product("Dining Chair", 1);
        warehouse.show();
        warehouse.sell_product("Dining Chair", 1);
        warehouse.show();
    }
}
