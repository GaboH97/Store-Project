package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Controller;

public class DialogSignInAccount extends JDialog{

	private static final long serialVersionUID = 1L;
	
	JLabel lblMail, lblPassword, lblSignIn;
	JTextField txtMail;
	JPasswordField pswPassword;
	JButton btnSignIn;
	JPanel panelComponents, panelTxt, panelLbl;

	public DialogSignInAccount(Controller controller, MainWindow mainWindow) {
		super(mainWindow);
		setTitle("Add Event");
		setSize(500, 600);
		setLocationRelativeTo(null);
		initComponents(controller);
		setVisible(true);
	}
	
	public void initComponents(Controller controller) {
		panelComponents = new JPanel();
		panelComponents.setLayout(new BorderLayout());
		add(panelComponents);
		
		panelLbl = new JPanel();
		panelComponents.add(panelLbl, BorderLayout.WEST);
		
		panelTxt = new JPanel();
		panelTxt.setLayout(new GridLayout(1, 3));
		panelComponents.add(panelTxt, BorderLayout.EAST);
		
		lblMail = new JLabel("E-Mail: ");
		panelLbl.add(lblMail);
		
		lblPassword = new JLabel("Password: ");
		panelLbl.add(lblPassword);
		
		txtMail = new JTextField("oooooooooooooooooooo");
		panelTxt.add(txtMail);
		
		pswPassword = new JPasswordField();
		panelTxt.add(pswPassword);
	}


}
