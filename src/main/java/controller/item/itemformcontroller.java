package controller.item;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.item;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class itemformcontroller implements Initializable {

    @FXML
    private TableColumn<?, ?> CodeLite;

    @FXML
    private TableColumn<?, ?> descriptioncol;

    @FXML
    private TableColumn<?, ?> packsizecol;

    @FXML
    private TableColumn<?, ?> qtycol;

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
        item item = new item(
                txtitemid.getText(),
                txtdescription.getText(),
                txtpacksize.getText(),
                Double.parseDouble(txtunitprice.getText()),
                Integer.parseInt(txtqty.getText())
        );
        Boolean isAdd = itemController.addItem(item);
        if (isAdd) {
            new Alert(Alert.AlertType.INFORMATION, "Item Added!!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Added!!").show();
        }
    }


    @FXML
    void btndeleteonaction(ActionEvent event) {
        if (itemController.deleteItem(txtitemid.getText())) {
            new Alert(Alert.AlertType.INFORMATION, txtitemid.getText() + " Item Deleted").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, txtitemid.getText() + " Item Not Deleted").show();
        }
    }


    @FXML
    void btnsearchonaction(ActionEvent event) {
        setValueToText(itemController.searchItem(txtitemid.getText()));
    }

    @FXML
    void btnupdateonaction(ActionEvent event) {
        item item = new item(
                txtitemid.getText(),
                txtdescription.getText(),
                txtpacksize.getText(),
                Double.parseDouble(txtunitprice.getText()),
                Integer.parseInt(txtqty.getText())
        );
        if (itemController.updateItem(item)) {
            new Alert(Alert.AlertType.INFORMATION, "Item Updated!").show();
            loadTable();
        } else {
            new Alert(Alert.AlertType.ERROR, "Item Not Updated ! ").show();
        }
    }


    public void nextonaction(ActionEvent actionEvent) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/customerform.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    itemservice itemController = new itemController() ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeLite.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        packsizecol.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        unitpricecol.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        qtycol.setCellValueFactory(new PropertyValueFactory<>("qty"));

        loadTable();

        table.getSelectionModel().selectedItemProperty().addListener((observableValue, item, newValue) -> {
            if (newValue != null) {
                setValueToText(newValue);
            }
        });
    }

    private void setValueToText(item newValue) {
        txtitemid.setText(newValue.getItemCode());
        txtdescription.setText(newValue.getDescription());
        txtpacksize.setText(newValue.getPackSize());
        txtunitprice.setText(newValue.getUnitPrice().toString());
        txtqty.setText(newValue.getQtyOnHand().toString());
    }

    private void loadTable() {
        ObservableList<item> items = itemController.getAllItem();
        table.setItems(items);
    }
}
