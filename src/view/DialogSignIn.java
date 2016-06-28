package view;

import javax.swing.JDialog;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Controller;

public class DialogSignIn extends JDialog{
	
	JTextField txtName, txtLastName, txtMail;
	JPasswordField pswPassword, pswPasswordAgain;
	
	public DialogSignIn(Controller controller, MainWindow mainWindow) {
		super(mainWindow);
		setTitle("Add Event");
		setSize(500, 600);
		setLocationRelativeTo(null);
	}
	
	

}
