package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

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
    private static void addReceiptHeader(PdfPTable table) {
        Image logo;
		try {
			logo = Image.getInstance("C:\\Users\\Smiloxham\\Desktop\\testGYM\\boba\\src\\Receipts\\gym-logo.png");
	        logo.scaleAbsolute(229, 56);
	        PdfPCell logoCell = new PdfPCell(logo, false);
	        logoCell.setBorder(Rectangle.NO_BORDER);
	        table.addCell(logoCell);

		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        PdfPCell titleCell = new PdfPCell(new Paragraph("Membership Payment Receipt"));
        titleCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        titleCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(titleCell);
        table.addCell("");
        table.addCell("");
    }
    private void addPaymentDetails(PdfPTable table) {
        // Add the payment details
    	Payment= test.getSelectionModel().getSelectedItem();
    	Date date = (Date) Payment.getDate();
    	LocalDate localDate = date.toLocalDate();
    	table.addCell("Receipt No:");
    	table.addCell(Integer.valueOf(Payment.getPaymentId()).toString());
        table.addCell("Payment Date:");
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        table.addCell(Date.valueOf(localDate).toString());
        table.addCell("Member Name :");
        table.addCell(Payment.getMemberName());
        table.addCell("Workout Type:");
        table.addCell("GYM");
        table.addCell("Payment Amount:");
        table.addCell(Double.valueOf(Payment.getAmount()).toString());
        table.addCell("Payment Method:");
        table.addCell(Payment.getPaymentMethod());
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell("");
    }
    private static void addReceiptFooter(PdfPTable table) {
        table.addCell("");
        table.addCell("");
        PdfPCell footerCell = new PdfPCell(new Paragraph("Receiver Signature"));
        footerCell.setColspan(2);
        footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        footerCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(footerCell);
    }
    @FXML
    void generateReceipt(MouseEvent event) {
        Document document = new Document();
        PdfPTable table = new PdfPTable(2);
            try {
				PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\Smiloxham\\Desktop\\testGYM\\boba\\src\\Receipts\\"+Payment.getMemberName()+" receipt.pdf"));
	            document.open();
	            addReceiptHeader(table);
	            addPaymentDetails(table);
	            addReceiptFooter(table);
	            document.add(table);
	            document.close();
	            JOptionPane.showMessageDialog(null, "Receipt Generated Succesfully");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
    }
    @FXML
    void getSelectedData(MouseEvent event) {
    	Payment= test.getSelectionModel().getSelectedItem();
    	if (Payment != null) {
    	IdE.setText(Integer.valueOf(Payment.getPaymentId()).toString());
    	MemberE.setValue(col_Name.getCellData(Payment).toString());
    	AmountE.setText(Double.valueOf(Payment.getAmount()).toString());
    	Date date = (Date) Payment.getDate();
    	LocalDate localDate = date.toLocalDate();
    	DateE.setValue(localDate);
    	PaymenE.setValue(col_Payment.getCellData(Payment).toString());
    	}
    }
    public void getHomeScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Home.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

    public void getMembersScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Member.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public void getReceptionistScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Receptionist.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public void getTrainorScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Trainor.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    
    public void getMembershipOfferScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Membership.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    
    public void getEquipmentsScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/equipments.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    

    public void getProgramScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Program.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
    

    public void getPaymentScene() {
		Main m = new Main();
		try {
			m.changeSceen("/UserInterface/Payment.fxml");
		} catch (IOException e) {
			
			e.printStackTrace();
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
