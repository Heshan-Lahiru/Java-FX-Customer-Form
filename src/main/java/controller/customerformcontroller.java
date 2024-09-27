
package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.customer;

import java.net.URL;
import java.sql.*;
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
    private TableColumn<?, ?> salarycolumn;

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
           txtcusid.getText();
           //txtcustitle.getText();
           txtcusname.getText();
           txtcussalary.getText();
          // txtcusdate.getText();
           txtcusaddress.getText();
          // txtcusdate.getDate();
           txtcuscity.getText();
           txtcusprovince.getText();
           txtcuspostalcodes.getText();




    }

    @FXML
    void btndeleteonaction(ActionEvent event) {

    }

    @FXML
    void btnsearchonaction(ActionEvent event) {

    }

    @FXML
    void btnupdateonaction(ActionEvent event) {

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
        addresscomumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        datecolumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        citycolumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        provincecolumn.setCellValueFactory(new PropertyValueFactory<>("province"));
        postalcodecolumn.setCellValueFactory(new PropertyValueFactory<>("postalcode"));



        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "root");
            String SQL = "SELECT * FROM Customer";
            ResultSet resultSet = connection.createStatement().executeQuery(SQL);
            while(resultSet.next()){
                customer customer = new customer(
                        resultSet.getString("CustID"),
                        resultSet.getString("CustTitle"),
                        resultSet.getString("CustName"),
                        resultSet.getDouble("salary"),
                        resultSet.getString("CustAddress"),
                        resultSet.getDate("dob").toLocalDate(),
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
    }
}






