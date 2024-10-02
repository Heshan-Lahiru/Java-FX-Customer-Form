package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.customer;
import util.crudutill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class customercontroller implements customerservice {
    @Override
    public boolean addcustomer(customer customer) {
        return false;
    }

    @Override
    public customer searchCustomer(String id) {
        try {
            ResultSet resultSet = crudutill.execute("SELECT * FROM customer WHERE CustID=?",id);
            resultSet.next();
            return new customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4).toLocalDate(),
                    resultSet.getDouble(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }


    @Override
    public boolean updatecustomer(customer customer) {
        return false;
    }

    @Override
    public ObservableList<customer> getAllCustomers() {
        ObservableList<customer> observableList = FXCollections.observableArrayList();
        try {
            ResultSet resultSet = crudutill.execute("SELECT * FROM customer");
            while (resultSet.next()) {
                observableList.add(new customer(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDouble(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9)
                ));
            }
            return observableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getCustomerIds(){
        ObservableList<customer> allCustomers = getAllCustomers();
        ObservableList<String> idList = FXCollections.observableArrayList();

        allCustomers.forEach(customer->{
            idList.add(customer.getId());
        });

        return idList;
    }

}
