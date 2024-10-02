package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.customer.customercontroller;
import controller.item.itemController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.carttablemodel;
import model.customer;
import model.item;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class placeorderformcontroller implements Initializable {

    @FXML
    private TableColumn<?, ?> coldescription;

    @FXML
    private TableColumn<?, ?> colitemcode;

    @FXML
    private TableColumn<?, ?> colqty;

    @FXML
    private TableColumn<?, ?> coltotal;

    @FXML
    private TableColumn<?, ?> colunitprice;

    @FXML
    private Label date;

    @FXML
    private TableView <carttablemodel> tblid;

    @FXML
    private Label time;

    @FXML
    private Label total;

    @FXML
    private JFXTextField txtcity;

    @FXML
    private JFXComboBox<String> txtcusid;

    @FXML
    private JFXTextField txtdescription;

    @FXML
    private JFXComboBox<String> txtitemcode;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtorderid;

    @FXML
    private JFXTextField txtqty;

    @FXML
    private JFXTextField txtsalary;

    @FXML
    private JFXTextField txtstock;

    @FXML
    private JFXTextField txtunitprice;

    ObservableList<carttablemodel> cart = FXCollections.observableArrayList();
    @FXML
    void btnaddtocartonaction(ActionEvent event) {
        Double unitPrice = Double.parseDouble(txtunitprice.getText());
        Integer qtys = Integer.parseInt(txtqty.getText());

        Double totals = unitPrice * qtys;
        cart.add(
                new carttablemodel(
                        txtitemcode.getValue(),
                        txtdescription.getText(),
                        qtys,
                        unitPrice,
                        totals
                )
        );
        tblid.setItems(cart);
        calcNetTotal();
    }
    private void calcNetTotal() {
        Double totalm = 0.0;

        for (carttablemodel cartTM : cart) {
            totalm += cartTM.getTotal();
        }

        total.setText(totalm.toString());
 }
    @FXML
    void btnplaceorderonaction(ActionEvent event) {

    }

    Date todaydate;
    public void loadDateAndTime(){
        todaydate=  new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        date.setText(f.format(todaydate));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            time.setText(
                    now.getHour() + " : " + now.getMinute() + " : " + now.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadcustomersid();
        loaditemsid();

        txtitemcode.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newvalue) -> {
            System.out.println(newvalue);
            loadItemData(newvalue);
        });

        txtcusid.getSelectionModel().selectedItemProperty().addListener((observableValue, s, newValue) -> {
            loadCusData(newValue);
        });

        colitemcode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("total"));


    }

    private void loadcustomersid(){
           txtcusid.setItems(new customercontroller().getCustomerIds());
    }
    private void loaditemsid(){
        txtitemcode.setItems(new itemController().getitemIds());
    }

    private void loadItemData(String itemscode){
        item i = new itemController().searchItem(itemscode);

        txtdescription.setText(i.getDescription());
        txtunitprice.setText(i.getUnitPrice().toString());
        txtstock.setText(i.getPackSize());

        }

    private void loadCusData(String cusid){
        customer c = new customercontroller().searchCustomer(cusid);

        txtname.setText(c.getName());
        txtcity.setText(c.getCity());
        txtsalary.setText(c.getSalary()+"");

    }

}
