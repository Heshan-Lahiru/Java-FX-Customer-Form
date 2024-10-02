package controller.item;

import javafx.collections.ObservableList;
import model.item;

public interface itemservice {
    boolean addItem(item item);
    boolean updateItem(item item);
    item searchItem(String itemCode);
    boolean deleteItem(String itemCode);
    ObservableList<item> getAllItem();

}
