package controller;

import com.jfoenix.controls.JFXTextField;
import database.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.customer;
import model.item;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class itemformcontroller implements Initializable {

    @FXML
    private TableColumn<?, ?> descriptioncol;

    @FXML
    private TableColumn<?, ?> itemcodecol;

    @FXML
    private TableColumn<?, ?> packsizecol;

    @FXML
    private TableColumn<Object, Object> qtycol;

    @FXML
    private TableView<item> table;

    @FXML
    private JFXTextField txtdescription;

    @FXML
    private JFXTextField txtitemid;

    @FXML
    private JFXTextField txtpacksize;

    @FXML
    private JFXTextField txtqty;

    @FXML
    private JFXTextField txtunitprice;

    @FXML
    private TableColumn<?, ?> unitpricecol;

    @FXML
    void btnaddonaction(ActionEvent event) {
        item cus= new item(txtitemid.getText(),txtdescription.getText(),txtpacksize.getText(),txtunitprice.getText(),txtqty.getText());
        try {
            String SQL = "INSERT INTO item VALUES(?,?,?,?,?)";
            Connection connection = dbconnection.getInstance().getConnection();
            PreparedStatement pre = connection.prepareStatement(SQL);

            pre.setObject(1,cus.getItemCode());
            pre.setObject(2,cus.getDescription());
            pre.setObject(3,cus.getPackSize());
            pre.setObject(4,cus.getUnitPrice());
            pre.setObject(5,cus.getQtyOnHand());


            boolean isadd = pre.executeUpdate()>0;
            System.out.println(isadd);
            if(isadd){
                new Alert(Alert.AlertType.INFORMATION,"Item Added").show();
                loarditemtable();
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Item Not Added").show();
            }


        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Item Not Added").show();
        }
    }

    @FXML
    void btndeleteonaction(ActionEvent event) {
        String SQL = "DELETE FROM item WHERE ItemCode='"+txtitemid.getText()+"'";

        try {
            Connection connection = dbconnection.getInstance().getConnection();
            boolean isdelete = connection.createStatement().executeUpdate(SQL)>0;
            if(isdelete){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted").show();
                loarditemtable();
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Item Not Deleted").show();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Item Not Deleted").show();
        }
    }

    @FXML
    void btnsearchonaction(ActionEvent event) {

        String SQL = "SELECT * FROM item WHERE ItemCode=?";
        try {
            Connection connection = dbconnection.getInstance().getConnection();
            PreparedStatement pre = connection.prepareStatement(SQL);
            pre.setObject(1,txtitemid.getText());
            ResultSet resultSet = pre.executeQuery();
            resultSet.next();
            item item = new item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5)

            );
            setValuetotext(item);
            System.out.println(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnupdateonaction(ActionEvent event) {
        item cus= new item(txtitemid.getText(),txtdescription.getText(),txtpacksize.getText(),txtunitprice.getText(),txtqty.getText());

        String SQL = "UPDATE item SET Description=?, PackSize=?,UnitPrice=?,QtyOnHand=? WHERE ItemCode=?";
        try {
            Connection connection = dbconnection.getInstance().getConnection();
            PreparedStatement pre = connection.prepareStatement(SQL);
            pre.setObject(1,cus.getDescription());
            pre.setObject(2,cus.getPackSize());
            pre.setObject(3,cus.getUnitPrice());
            pre.setObject(4,cus.getQtyOnHand());
            pre.setObject(5,cus.getItemCode());

            boolean isupdated = pre.executeUpdate() > 0;
            if(isupdated){
                new Alert(Alert.AlertType.INFORMATION,"item updated").show();
                loarditemtable();
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"item Not updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"item Not updated").show();
        }
    }
      public void loarditemtable(){
          ObservableList<item> itemobservablelist = FXCollections.observableArrayList();
          itemcodecol.setCellValueFactory(new PropertyValueFactory<>("ItemCode"));
          descriptioncol.setCellValueFactory(new PropertyValueFactory<>("Description"));
          packsizecol.setCellValueFactory(new PropertyValueFactory<>("PackSize"));
          unitpricecol.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
          qtycol.setCellValueFactory(new PropertyValueFactory<>("QtyOnHand"));


          try {
              Connection connection = dbconnection.getInstance().getConnection();
              String SQL = "SELECT * FROM item";
              ResultSet resultSet = connection.createStatement().executeQuery(SQL);
              while(resultSet.next()){
                  item items = new item(
                          resultSet.getString("ItemCode"),
                          resultSet.getString("Description"),
                          resultSet.getString("PackSize"),
                          resultSet.getString("UnitPrice"),
                          resultSet.getString("QtyOnHand")

                  );
                  itemobservablelist.add(items);
              }
              table.setItems(itemobservablelist);

          }
          catch (SQLException e){
              throw new RuntimeException(e);
          }
      }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loarditemtable();


        table.getSelectionModel().selectedItemProperty().addListener((observableValue, customer, newvalue) -> {
            if(newvalue != null){
                setValuetotext(newvalue);
            }
        });

    }
    private void setValuetotext(item newvalue) {
        txtitemid.setText(newvalue.getItemCode());
        txtdescription.setText(newvalue.getDescription());
        txtpacksize.setText(newvalue.getPackSize());
        txtunitprice.setText(newvalue.getUnitPrice());
        txtqty.setText(newvalue.getQtyOnHand());
    }
}
