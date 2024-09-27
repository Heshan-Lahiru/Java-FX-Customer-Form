
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import database.dbconnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.customer;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class customerformcontroller implements Initializable {



    @FXML
    private TableColumn<?, ?> addresscomumn;

    @FXML
    private TableColumn<?, ?> citycolumn;

    @FXML
    private TableColumn<?, ?> datecolumn;

    @FXML
    private TableColumn<?, ?> idcolumn;

    @FXML
    private TableColumn<?, ?> namecolumn;

    @FXML
    private TableColumn<?, ?> postalcodecolumn;

    @FXML
    private TableColumn<?, ?> provincecolumn;

    @FXML
    private TableColumn salarycolumn;

    @FXML
    private TableView<customer> tblcustomersid;

    @FXML
    private TableColumn<?, ?> titlecolumn;

    @FXML
    private JFXTextField txtcusaddress;

    @FXML
    private JFXTextField txtcuscity;

    @FXML
    private DatePicker txtcusdate;

    @FXML
    private JFXTextField txtcusid;

    @FXML
    private JFXTextField txtcusname;

    @FXML
    private JFXTextField txtcuspostalcodes;

    @FXML
    private JFXTextField txtcusprovince;

    @FXML
    private JFXTextField txtcussalary;

    @FXML
    private JFXComboBox<String> txtcustitle;

    @FXML
    void btnaddonaction(ActionEvent event) {

         customer cus= new customer(txtcusid.getText(),txtcustitle.getValue(),txtcusname.getText(),txtcusdate.getValue(),Double.parseDouble(txtcussalary.getText()),txtcusaddress.getText(),txtcuscity.getText(),txtcusprovince.getText(),txtcuspostalcodes.getText());
        try {
            String SQL = "INSERT INTO customer VALUES(?,?,?,?,?,?,?,?,?)";
            Connection connection = dbconnection.getInstance().getConnection();
            PreparedStatement pre = connection.prepareStatement(SQL);

            pre.setObject(1,cus.getId());
            pre.setObject(2,cus.getTitle());
            pre.setObject(3,cus.getName());
            pre.setObject(4,cus.getDate());
            pre.setObject(5,cus.getSalary());
            pre.setObject(6,cus.getAddress());
            pre.setObject(7,cus.getCity());
            pre.setObject(8,cus.getProvince());
            pre.setObject(9,cus.getPostalcode());

            boolean isadd = pre.executeUpdate()>0;
            System.out.println(isadd);
            if(isadd){
                new Alert(Alert.AlertType.INFORMATION,"Customer Added").show();
                loardtable();
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Customer Not Added").show();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btndeleteonaction(ActionEvent event) {

        String SQL = "DELETE FROM customer WHERE CustID='"+txtcusid.getText()+"'";

        try {
            Connection connection = dbconnection.getInstance().getConnection();
            boolean isdelete = connection.createStatement().executeUpdate(SQL)>0;
       if(isdelete){
           new Alert(Alert.AlertType.INFORMATION,"Customer Deleted").show();
           loardtable();
       }
       else{
           new Alert(Alert.AlertType.INFORMATION,"Customer Not Deleted").show();
       }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Customer Not Deleted").show();
        }


    }

    @FXML
    void btnsearchonaction(ActionEvent event) {

        String SQL = "SELECT * FROM customer WHERE CustID=?";
        try {
            Connection connection = dbconnection.getInstance().getConnection();
            PreparedStatement pre = connection.prepareStatement(SQL);
            pre.setObject(1,txtcusid.getText());
            ResultSet resultSet = pre.executeQuery();
            resultSet.next();
            customer customer = new customer(
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
           setValuetotext(customer);
            System.out.println(customer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnupdateonaction(ActionEvent event) {
        customer cus= new customer(txtcusid.getText(),txtcustitle.getValue(),txtcusname.getText(),txtcusdate.getValue(),Double.parseDouble(txtcussalary.getText()),txtcusaddress.getText(),txtcuscity.getText(),txtcusprovince.getText(),txtcuspostalcodes.getText());

        String SQL = "UPDATE customer SET CustTitle=?, CustName=?,DOB=?,salary=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=?";
        try {
            Connection connection = dbconnection.getInstance().getConnection();
            PreparedStatement pre = connection.prepareStatement(SQL);
            pre.setObject(1,cus.getTitle());
             pre.setObject(2,cus.getName());
            pre.setObject(3,cus.getDate());
            pre.setObject(4,cus.getSalary());
            pre.setObject(5,cus.getAddress());
            pre.setObject(6,cus.getCity());
            pre.setObject(7,cus.getProvince());
            pre.setObject(8,cus.getPostalcode());
            pre.setObject(9,cus.getId());
            boolean isupdated = pre.executeUpdate() > 0;
            if(isupdated){
                new Alert(Alert.AlertType.INFORMATION,"Customer updated").show();
                loardtable();
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Customer Not updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Customer Not updated").show();
        }

    }


    @FXML
    void reloardonaction(ActionEvent event) throws SQLException {
        loardtable();

    }

    public void loardtable(){
        ObservableList<customer> customerobservablelist = FXCollections.observableArrayList();
        idcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        namecolumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        salarycolumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        addresscomumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        citycolumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        provincecolumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        postalcodecolumn.setCellValueFactory(new PropertyValueFactory<>("postalcode"));



        try {
            Connection connection = dbconnection.getInstance().getConnection();
            String SQL = "SELECT * FROM Customer";
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            while(resultSet.next()){
                customer customer = new customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDate("dob").toLocalDate(),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("PostalCode")
                );
                customerobservablelist.add(customer);
            }
            tblcustomersid.setItems(customerobservablelist);

        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> custitle = FXCollections.observableArrayList();
        custitle.add("Mr");
        custitle.add("Ms");
        custitle.add("Miss");
        custitle.add("Mrs");
        txtcustitle.setItems(custitle);
        loardtable();

        tblcustomersid.getSelectionModel().selectedItemProperty().addListener((observableValue, customer, newvalue) -> {
           if(newvalue != null){
               setValuetotext(newvalue);
           }
        });
    }

    private void setValuetotext(customer newvalue) {
        txtcusid.setText(newvalue.getId());
        txtcustitle.setValue(newvalue.getTitle());
        txtcusname.setText(newvalue.getName());
        txtcusdate.setValue(newvalue.getDate());
        txtcussalary.setText(String.valueOf(newvalue.getSalary()));
       txtcusaddress.setText(newvalue.getAddress());
        txtcuscity.setText(newvalue.getCity());
        txtcusprovince.setText(newvalue.getProvince());
        txtcuspostalcodes.setText(newvalue.getPostalcode());
    }
}






