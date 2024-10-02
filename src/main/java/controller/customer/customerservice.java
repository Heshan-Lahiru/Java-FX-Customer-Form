package controller.customer;

import javafx.collections.ObservableList;
import model.customer;

public interface customerservice {
    boolean addcustomer(customer customer);
    customer searchCustomer(String id);
    boolean deleteCustomer(String id);
    boolean updatecustomer(customer customer);
    ObservableList<customer> getAllCustomers();
}
