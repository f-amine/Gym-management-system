package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

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
    void MemberDataSheet(MouseEvent event) {
    	connection = handler.getConnection();
    	String q1 = "Select firstname,lastname,email,phoneNumber,address,paymentInformation,emergencyContactInfo,membershipExpiration from member ";

        try {
    		pst=connection.prepareStatement(q1);
    		ResultSet rs = pst.executeQuery();
			FileWriter writer = new FileWriter("MemberData.csv");
            // Create Header
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                sb.append(rs.getMetaData().getColumnName(i));
                sb.append(",");
            }
            writer.append(sb.toString());
            writer.append("\n");
            // Fill Data
            while (rs.next()) {
                sb.setLength(0);
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    sb.append(rs.getString(i));
                    sb.append(",");
                }
                writer.append(sb.toString());
                writer.append("\n");
            }
            //Close file
            writer.flush();
            writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    }
 /*   @FXML
   void MemberDataSheet(MouseEvent event) {
    	connection = handler.getConnection();
    	String q1 = "Select firstname,lastname,email,phoneNumber,address,paymentInformation,emergencyContactInfo,membershipExpiration from member ";
        try {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("MemberData");
			try {
				pst=connection.prepareStatement(q1);
				ResultSet rs = pst.executeQuery();
	            int rowCount = 0;
	            while (rs.next()) {
	                XSSFRow row = sheet.createRow(rowCount++);
	                XSSFCell cell0 = row.createCell(0);
	                cell0.setCellValue(rs.getString("firstname"));
	                XSSFCell cell1 = row.createCell(0);
	                cell1.setCellValue(rs.getString("lastname"));
	                XSSFCell cell2 = row.createCell(0);
	                cell2.setCellValue(rs.getString("email"));
	                XSSFCell cell3 = row.createCell(0);
	                cell3.setCellValue(rs.getString("phoneNumber"));
	                XSSFCell cell4 = row.createCell(0);
	                cell4.setCellValue(rs.getString("address"));
	                XSSFCell cell5 = row.createCell(0);
	                cell5.setCellValue(rs.getString("paymentInformation"));
	                XSSFCell cell6 = row.createCell(0);
	                cell6.setCellValue(rs.getString("emergencyContactInfo"));
	                XSSFCell cell7 = row.createCell(0);
	                cell7.setCellValue(rs.getString("membershipExpiration"));
	            }}catch (SQLException e) {

				e.printStackTrace();
			}
	        
            FileOutputStream out;
				out = new FileOutputStream("MyFile.xlsx");
				workbook.write(out);
		        out.close();
		        workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} */
    /*@FXML
    void MemberDataSheet(MouseEvent event) throws IOException, RowsExceededException, WriteException {
    	connection = handler.getConnection();
    	String q1 = "Select firstname,lastname,email,phoneNumber,address,paymentInformation,emergencyContactInfo,membershipExpiration from member ";
    	try {
			pst=connection.prepareStatement(q1);
			ResultSet rs = pst.executeQuery();
            File file = new File("data.xlsx");
            WritableWorkbook workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet("Sheet1", 0);
            int columnCount = rs.getMetaData().getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = rs.getMetaData().getColumnName(i + 1);
                Label label = new Label(i, 0, columnName);
                sheet.addCell(label);
            }
         // Write the data to the sheet
            int rowCount = 1;
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    String value = rs.getString(i + 1);
                    Label label = new Label(i, rowCount, value);
                    sheet.addCell(label);
                }
                rowCount++;
            }
            workbook.write();
            workbook.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }*/
    
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
    @FXML
    void  Search(KeyEvent event) {
    try {
    	connection = handler.getConnection();
    	String q1 = "SELECT * FROM payment where (paymentId like '$"+filterField.getText()+"%' or member like '%"+filterField.getText()+"%'  or amount like '%"+filterField.getText()+"%' or paymentMethod like '%"+filterField.getText()+"%') ";
    	dataList=FXCollections.observableArrayList();
    	pst= connection.prepareStatement(q1);
   
    ResultSet rs = pst.executeQuery();
    while(rs.next())
    {
    	dataList.add(new Payment(rs.getInt("paymentId"),rs.getString("member") ,rs.getDouble("amount"),rs.getDate("date"),rs.getString("paymentMethod")));	
    }
    }catch(Exception e)
    {
    e.printStackTrace();
    }
	col_Id.setCellValueFactory(new PropertyValueFactory<Payment, String>("paymentId"));
	col_Name.setCellValueFactory(new PropertyValueFactory<Payment, String>("memberName"));
	col_Amount.setCellValueFactory(new PropertyValueFactory<Payment, String>("amount"));
	col_Date.setCellValueFactory(new PropertyValueFactory<Payment, String>("date"));
	col_Payment.setCellValueFactory(new PropertyValueFactory<Payment, String>("paymentMethod"));
    test.setItems(dataList);
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
