package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import appClasses.Payment;
import appClasses.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class PaymentController implements Initializable {

    @FXML
    private TextField AmountE;

    @FXML
    private DatePicker DateE;

    @FXML
    private TextField IdE;

    @FXML
    private ComboBox<String> MemberE;

    @FXML
    private ComboBox<String> PaymenE;

    @FXML
    private TableColumn<Payment, String> col_Amount;

    @FXML
    private TableColumn<Payment, String> col_Date;

    @FXML
    private TableColumn<Payment, String> col_Id;

    @FXML
    private TableColumn<Payment, String> col_Name;

    @FXML
    private TableColumn<Payment, String> col_Payment;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Payment> test;

    private Connection connection;
	private dbConnection handler;
	private PreparedStatement pst;
	Payment Payment  =null;
	public int index =-1;
    ObservableList<Payment> dataList;
    public void choiceBoxMemberFill() {
    	connection = handler.getConnection();
    	ObservableList<String> options = FXCollections.observableArrayList();
    	String q1 = "SELECT firstname from member";
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    options.add(rs.getString("firstname"));
			  }
			MemberE.setItems(options);

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
    }
    public void choiceBoxPaymentMethodFill() {
    	ObservableList<String> items = FXCollections.observableArrayList("Credit Card", "Cash", "Cheque");
    	PaymenE.setItems(items);
    }
    
    public ObservableList<Payment> loadData() {
    	connection = handler.getConnection();
    	String q1 = "SELECT * from payment";
    	ObservableList<Payment> list = FXCollections.observableArrayList();
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
			    list.add(new Payment(rs.getInt("paymentId"),rs.getString("member") ,rs.getDouble("amount"),rs.getDate("date"),rs.getString("paymentMethod")));	    
			   
			  }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
    	
    	}
    
    
    @FXML
    void confirmPayment(MouseEvent event) {
    	connection = handler.getConnection();
		String q1 = "INSERT INTO payment VALUES (?,?,?,?,?)";
		  try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, IdE.getText());
			pst.setString(2, MemberE.getValue());
			pst.setString(3, AmountE.getText());
			pst.setString(4, String.valueOf(DateE.getValue()));
			pst.setString(5, PaymenE.getValue());
			pst.execute();
			test.setItems(loadData());
			//search_Trainor();
			JOptionPane.showMessageDialog(null, "Payment added succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
		}
    }
    
    @FXML
    void generateReceipt(MouseEvent event) {
    	try {
    		File file = new File("template.pdf");
			PDDocument template = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			String templateText = stripper.getText(template);
			template.close();
	    	Payment= test.getSelectionModel().getSelectedItem();
	    	String name = Integer.valueOf(Payment.getPaymentId()).toString();
	    	String amount = Double.valueOf(Payment.getAmount()).toString();
	    	Date date = (Date) Payment.getDate();
	    	LocalDate localDate = date.toLocalDate();
	    	String datePayment = Date.valueOf(localDate).toString();
	    	String PaymentMethod = Payment.getPaymentMethod();
	    	String modifiedText = templateText.replace("{name}", name).replace("{amount}", amount).replace("{date}", datePayment).replace("{paymentMethod}", PaymentMethod).replace("\n", "").replace("\r", "");
	    	PDDocument document = new PDDocument();
	    	PDPage page = new PDPage();
	    	document.addPage(page);
	    	PDPageContentStream contentStream = new PDPageContentStream(document, page);
	    	contentStream.beginText();
	    	contentStream.newLineAtOffset(25, 700);
	    	contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
	    	contentStream.showText(modifiedText);
	    	contentStream.endText();
	    	contentStream.close();
	    	document.save("output.pdf");
	    	document.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
    }
    
    
    
    
    @FXML
    void getMembersScene(MouseEvent event) {

    }

    @FXML
    void getProgramScene(MouseEvent event) {

    }

    @FXML
    void getSelectedData(MouseEvent event) {
    	Payment= test.getSelectionModel().getSelectedItem();
    	if (Payment != null) {
    	IdE.setText(Integer.valueOf(Payment.getPaymentId()).toString());
    	MemberE.setValue(col_Name.getCellData(Payment).toString());
    	AmountE.setText(Double.valueOf(Payment.getAmount()).toString());;
    	Date date = (Date) Payment.getDate();
    	LocalDate localDate = date.toLocalDate();
    	DateE.setValue(localDate);
    	PaymenE.setValue(col_Payment.getCellData(Payment).toString());
    	}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		handler = new dbConnection();
		col_Id.setCellValueFactory(new PropertyValueFactory<Payment, String>("paymentId"));
		col_Name.setCellValueFactory(new PropertyValueFactory<Payment, String>("memberName"));
		col_Amount.setCellValueFactory(new PropertyValueFactory<Payment, String>("amount"));
		col_Date.setCellValueFactory(new PropertyValueFactory<Payment, String>("date"));
		col_Payment.setCellValueFactory(new PropertyValueFactory<Payment, String>("paymentMethod"));
		choiceBoxPaymentMethodFill();
		choiceBoxMemberFill();
		test.setItems(loadData());
	}

}
