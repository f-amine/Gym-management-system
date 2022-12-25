package appClasses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.scene.control.TextField;

public class Instructor extends Employee {
	  public Instructor(int id, String firstName, String lastName, String email, String phoneNumber, String address, String role, String paymentInformation) {
	    super(id, firstName, lastName, email, phoneNumber, address, role, paymentInformation);
	  }

	  public void assignProgram(Member member, Program program) {
		  member.setProgram(program);
	  }
	  public void addProgram(TextField description, TextField programName) {
		  Connection connection;
		  dbConnection handler;
		  PreparedStatement pst;
		  handler = new dbConnection();
		  connection = handler.getConnection();
		  String q1 = "INSERT INTO PROGRAM (programName,description) VALUES (?,?)";
		  try {
			pst=connection.prepareStatement(q1);
			pst.setString(1, description.getText());
			pst.setString(2, programName.getText());
			ResultSet rs = pst.executeQuery();
			JOptionPane.showMessageDialog(null, "Program added succesfully");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error Please try again");
			e.printStackTrace();
		}

	  }
	}

