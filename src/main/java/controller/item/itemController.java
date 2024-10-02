package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.customer;
import model.item;
import util.crudutill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class itemController  implements itemservice{
    @Override
    public boolean addItem(item item) {
        String SQl = "INSERT INTO item VALUES(?,?,?,?,?)";
        try {
            return crudutill.execute(
                    SQl,
                    item.getItemCode(),
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateItem(item item) {
        String SQL = "UPDATE item SET Description=?, PackSize=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?";
        try {
            return crudutill.execute(SQL,
                    item.getDescription(),
                    item.getPackSize(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getItemCode()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public item searchItem(String itemCode) {
        String SQL = "SELECT * FROM item WHERE ItemCode=?";
        ResultSet resultSet = null;
        try {
            resultSet = crudutill.execute(SQL, itemCode);
            resultSet.next();
            return new item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4),
                    resultSet.getInt(5)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean deleteItem(String itemCode) {
        String SQl = "DELETE FROM item WHERE ItemCode=?";
        try {
            return crudutill.execute(SQl, itemCode);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public ObservableList<item> getAllItem() {
        ObservableList<item> itemObservableList = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM item";

        try {
            ResultSet resultSet = crudutill.execute(SQL);

            while (resultSet.next()) {
                itemObservableList.add(new item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getInt(5)
                ));
            }
            return itemObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getitemIds(){
        ObservableList<item> allCustomers = getAllItem();
        ObservableList<String> idList = FXCollections.observableArrayList();

        allCustomers.forEach(customer->{
            idList.add(customer.getItemCode());
        });

        return idList;
    }
}