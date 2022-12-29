package application;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class MemberController {

    @FXML
    private TextField AddressE;

    @FXML
    private TextField ContactE;

    @FXML
    private TextField EmailE;

    @FXML
    private TextField FirstNameE;

    @FXML
    private TextField IdE;

    @FXML
    private TextField LastNameE;

    @FXML
    private TextField MembershipExpirationE;

    @FXML
    private TextField MembershipTypeE;

    @FXML
    private TextField PaymentE;

    @FXML
    private TextField PhoneE;

    @FXML
    private TextField ProgramE;

    @FXML
    private TableColumn<?, ?> col_Address;

    @FXML
    private TableColumn<?, ?> col_Contact;

    @FXML
    private TableColumn<?, ?> col_Email;

    @FXML
    private TableColumn<?, ?> col_FirstName;

    @FXML
    private TableColumn<?, ?> col_Id;

    @FXML
    private TableColumn<?, ?> col_LastName;

    @FXML
    private TableColumn<?, ?> col_Membership;

    @FXML
    private TableColumn<?, ?> col_MembershipEx;

    @FXML
    private TableColumn<?, ?> col_Payment;

    @FXML
    private TableColumn<?, ?> col_Phone;

    @FXML
    private TableColumn<?, ?> col_Program;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<?> test;

    @FXML
    void getSelectedData(MouseEvent event) {

    }

}
