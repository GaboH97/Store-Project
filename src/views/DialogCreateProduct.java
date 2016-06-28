package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import controller.Actions;
import controller.Controller;
import model.dao.Store;
import model.entity.Category;
import model.entity.Product;

public class DialogCreateProduct extends JDialog {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	JTextField txtName, txtFactoryPrice;
	JTextArea txtADescription;
	JSpinner spinnerQuantity;
	MaskFormatter mskQuantity;
	JTextField txtQuantity;
	JComboBox<Category> cboxCategory;
	JButton btnAccept, btnCancel;
	private JLabel labelCover;

	public DialogCreateProduct(Controller controller, MainWindow mainWindow) {
		super(mainWindow);
		setTitle(mainWindow.getTitle());
		setSize(300, 300);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		createPanel(controller);
		pack();
	}

	public void createPanel(Controller controller) {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		initComponents(controller);
	}

	public void initComponents(Controller controller) {

		JPanel textData = new JPanel();
		textData.setLayout(new BoxLayout(textData, BoxLayout.Y_AXIS));
		
		txtName = new JTextField(15);
		txtName.setName("Name");
		addBorder(txtName);
		textData.add(txtName);

		cboxCategory = new JComboBox<>(Category.values());
		cboxCategory.setName("Category");
		addBorder(cboxCategory);
		textData.add(cboxCategory);

		txtFactoryPrice = new JTextField(15);
		txtFactoryPrice.setName("Factory Price");
		addBorder(txtFactoryPrice);
		textData.add(txtFactoryPrice);

		spinnerQuantity = new JSpinner(new SpinnerNumberModel(1, 0, 10000, 1));
		spinnerQuantity.setName("Quantity");
		addBorder(spinnerQuantity);
		textData.add(spinnerQuantity);

		txtADescription = new JTextArea();
		txtADescription.setName("Description");
		addBorder(txtADescription);
		textData.add(txtADescription);

		mainPanel.add(textData);
		add(mainPanel, BorderLayout.CENTER);

		JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton accept = new JButton("Accept");
		accept.setBackground(new Color(111, 111, 255));
		accept.setForeground(Color.WHITE);
		accept.setBorderPainted(false);
		accept.addActionListener(controller);
		accept.setActionCommand(Actions.ACCEPT_ADD.name());
		footer.add(accept);

		JButton cancel = new JButton("Cancel");
		cancel.setBackground(new Color(111, 111, 255));
		cancel.setForeground(Color.WHITE);
		cancel.setBorderPainted(false);
		cancel.addActionListener(new ActionListener() {

			private static final String CANCEL_MESSAGE = "Do you really want to cancel add operation?";

			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(null, CANCEL_MESSAGE, "Store", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					DialogCreateProduct.close();
				}
			}
		});

		footer.add(cancel);
		add(footer, BorderLayout.SOUTH);

		JPanel coverPanel = new JPanel(new BorderLayout(5, 5));
		coverPanel.setBackground(Color.WHITE);
		coverPanel.setName("Illustration");
		addBorder(coverPanel);

		labelCover = new JLabel("<html> Drag a Picture of<br>the product</html>");
		labelCover.setBackground(Color.WHITE);
		labelCover.setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;

			@Override
			public synchronized void drop(DropTargetDropEvent dtde) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY);
				try {
					String cosito = dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)
							.toString().substring(1, dtde.getTransferable()
									.getTransferData(DataFlavor.javaFileListFlavor).toString().length() - 1)
							.replace('\\', '/');
					System.out.println(cosito);
					System.out.println(dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
					labelCover.setIcon(new ImageIcon(
							new ImageIcon(cosito).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
					labelCover.setText("");
					labelCover.setName(cosito);
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		coverPanel.add(labelCover, BorderLayout.CENTER);
		add(coverPanel, BorderLayout.EAST);
	}

	public static void close() {
		close();
	}

	public void addBorder(JComponent component) {
		component
				.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.BLACK, 1, true), component.getName()));
	}

	public Product createProduct() throws NumberFormatException, MalformedURLException, IOException {
		return Store.createProduct(txtName.getText(),
				Double.parseDouble(txtFactoryPrice.getText()), (Category) cboxCategory.getSelectedItem(),
				txtADescription.getText(), (int) spinnerQuantity.getValue(), toBufferedImage(
						ImageIO.read(new File(labelCover.getName())).getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
	}

	public void clearAndClose() {
		txtADescription.setText("");
		txtFactoryPrice.setText("");
		txtName.setText("");
		spinnerQuantity.setValue(1);
		labelCover.setText("Vergaaaaa");
		labelCover.setIcon(new ImageIcon(getClass().getResource("/img/unknownItem.png")));
		dispose();
		setVisible(false);
	}

	public BufferedImage toBufferedImage(Image img) {

		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
}
