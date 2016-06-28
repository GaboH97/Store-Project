package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.GregorianCalendar;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import controller.Actions;
import controller.Controller;
import model.entity.Category;

public class DialogAddProduct extends JDialog{

	private static final long serialVersionUID = 1L;
	private JPanel panelData, panelImage;
	JTextField txtId, txtName, txtFactoryPrice;
	JTextArea txtADescription, txtAQuality;
	MaskFormatter mskQuantity;
	JFormattedTextField txtQuantity;
	JLabel lblId, lblName, lblCategory, lblImage ,lblDescription,  lblIsInStock, lblQuantity, lblQuality, lblCover;
	JComboBox<Category> cboxCategory;
	JButton btnAccept, btnCancel;
	JScrollPane jScrollPane, jScrollPaneQuality;

	public DialogAddProduct(Controller controller, MainWindow mainWindow) {
		super(mainWindow);
		setTitle("Add Event");
		setSize(500, 600);
		setLocationRelativeTo(null);
		createPanel(controller);

	}
	public void createPanel(Controller controller) {
		panelData = new JPanel();
		panelData.setBackground(Color.WHITE);
		panelData.setLayout(new GridBagLayout());
		createlbl();
		createButton(controller);
		add(panelData);
		
	}

	public void clear() {
		txtId.setText("");
		txtName.setText(""); 
		txtFactoryPrice.setText(""); 
		txtADescription.setText("");
	}

	public void createlbl() {
		GridBagConstraints gConstraints=new GridBagConstraints();

		lblId = new JLabel("Id:");
		gConstraints.gridx = 0;
		gConstraints.gridy = 0; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0;
		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelData.add(lblId, gConstraints);

		txtId = new JTextField();
		gConstraints.gridx = 1; 
		gConstraints.gridy = 0; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1;
		gConstraints.weightx = 1.0;
		gConstraints.fill = GridBagConstraints.HORIZONTAL;
		panelData.add(txtId, gConstraints);

		lblName = new JLabel("Name: ");
		gConstraints.gridx = 0;
		gConstraints.gridy = 1; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0;
		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelData.add(lblName, gConstraints);

		txtName = new JTextField();
		gConstraints.gridx = 1; 
		gConstraints.gridy = 1; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1;
		gConstraints.weightx = 1.0;
		gConstraints.fill = GridBagConstraints.HORIZONTAL;
		panelData.add(txtName, gConstraints);

		lblCategory = new JLabel("Category: ");
		gConstraints.gridx = 0;
		gConstraints.gridy = 2; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0; 
		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelData.add(lblCategory, gConstraints);

		cboxCategory = new JComboBox<>(Category.values());
		gConstraints.gridx = 1; 
		gConstraints.gridy = 2; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1;
		gConstraints.weightx = 1.0;
		gConstraints.fill = GridBagConstraints.HORIZONTAL;
		panelData.add(cboxCategory, gConstraints);

		lblDescription = new JLabel("Description: ");
		gConstraints.gridx = 0;
		gConstraints.gridy = 3; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0; 
		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelData.add(lblDescription, gConstraints);

		txtADescription = new JTextArea();
		gConstraints.gridx = 1;
		gConstraints.gridy = 3; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0; 
		gConstraints.fill = GridBagConstraints.BOTH;
		jScrollPane = new JScrollPane(txtADescription);
		jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelData.add(jScrollPane, gConstraints);

		
		panelImage = new JPanel();
		panelImage.setLayout(new GridLayout(1, 2));
		gConstraints.gridx = 2;
		gConstraints.gridy = 2; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0;
		panelData.add(panelImage, gConstraints);
		
		lblCover = new JLabel("Cover: ");
//		gConstraints.gridx = 2;
//		gConstraints.gridy = 2; 
//		gConstraints.gridwidth = 1; 
//		gConstraints.gridheight = 1; 
//		gConstraints.weighty = 1.0;
//		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelImage.add(lblCover);
		
		lblImage("D:/NINI/Videos/img_monster/Musica/H.jpg");
		
//		lblImage = new JLabel(new ImageIcon(getClass().getResource("D:/NINI/Videos/img_monster/Musica/1.png")));
		gConstraints.gridx = 2;
		gConstraints.gridy = 1; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 6; 
		gConstraints.weighty = 1.0;
		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelData.add(lblImage, gConstraints);
		
		lblQuantity = new JLabel("Quantity: ");
		gConstraints.gridx = 0;
		gConstraints.gridy = 5; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0;
		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelData.add(lblQuantity, gConstraints);

		createTxtFieldQuantity();

		lblQuality = new JLabel("Quality:");
		gConstraints.gridx = 0;
		gConstraints.gridy = 6; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0;
		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelData.add(lblQuality, gConstraints);

		txtAQuality = new JTextArea();
		gConstraints.gridx = 1;
		gConstraints.gridy = 6; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1; 
		gConstraints.weighty = 1.0; 
		gConstraints.fill = GridBagConstraints.BOTH;
		jScrollPaneQuality = new JScrollPane(txtAQuality);
		jScrollPaneQuality.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelData.add(jScrollPaneQuality, gConstraints);
	}
	private void lblImage(String url){
		ImageIcon img = new ImageIcon(url);
		lblImage = new JLabel(img);
//		Icon icono = new ImageIcon(img.getImage().getScaledInstance(lblImage.getWidth(),
//				lblImage.getHeight(), Image.SCALE_DEFAULT));
//		lblImage.setIcon(icono);
//		gConstraints.gridx = 2;
//		gConstraints.gridy = 1; 
//		gConstraints.gridwidth = 1; 
//		gConstraints.gridheight = 6; 
//		gConstraints.weighty = 1.0;
//		gConstraints.fill = GridBagConstraints.NORTHEAST;
		panelImage.add(lblImage);
	}

	private void createTxtFieldQuantity() {
		GridBagConstraints gConstraints=new GridBagConstraints();
		try {
			mskQuantity = new MaskFormatter("#####");
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Ingrese solo números");
		}
		txtQuantity = new JFormattedTextField(mskQuantity);
		gConstraints.gridx = 1; 
		gConstraints.gridy = 5; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 1;
		gConstraints.weightx = 1.0;
		gConstraints.fill = GridBagConstraints.HORIZONTAL;
		panelData.add(txtQuantity, gConstraints);
	}

	public void createButton(Controller controller) {
		GridBagConstraints gConstraints=new GridBagConstraints();

		btnAccept = new JButton("Accept");
		gConstraints.gridx = 0;
		gConstraints.gridy = 7; 
		gConstraints.gridwidth = 2; 
		gConstraints.gridheight = 2; 
		gConstraints.weighty = 1.0; 
		gConstraints.fill = GridBagConstraints.EAST;
		btnAccept.setFont(new Font("Arial", Font.PLAIN, 14));
		btnAccept.setForeground(Color.BLACK);
		btnAccept.setBackground((Color.WHITE));
		btnAccept.setBounds(40, 360, 150, 30);
		btnAccept.addActionListener(controller);
		btnAccept.setActionCommand(Actions.ACCEPT_ADD.name());
		panelData.add(btnAccept, gConstraints);

		btnCancel = new JButton("Cancel");
		gConstraints.gridx = 2;
		gConstraints.gridy = 7; 
		gConstraints.gridwidth = 1; 
		gConstraints.gridheight = 2; 
		gConstraints.weighty = 1.0; 
		gConstraints.fill = GridBagConstraints.EAST;
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 14));
		btnCancel.setForeground(Color.BLACK);
		btnCancel.setBackground((Color.WHITE));
		btnCancel.setBounds(210, 360, 150, 30);
		btnCancel.addActionListener(controller);
		btnCancel.setActionCommand(Actions.CANCEL.name());
		panelData.add(btnCancel, gConstraints);
	}
}

