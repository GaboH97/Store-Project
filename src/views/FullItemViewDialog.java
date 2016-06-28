package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import controller.Actions;
import controller.Controller;
import model.entity.Product;

public class FullItemViewDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JTextArea descriptionTxtA;
	private JTextArea reviewTxtA;

	public FullItemViewDialog() {
		try {
			UIManager.put("Label.Font", customFont(Font.BOLD, 10));
			SwingUtilities.updateComponentTreeUI(this);
		} catch (FontFormatException | IOException e) {

		}
		setSize(400, 400);
		//setTitle(mainWindow.getTitle());
		setLocationRelativeTo(null);
		setBackground(new Color(249, 210, 119));
		setLayout(new BorderLayout());
		this.controller = controller;
	}

	private Font customFont(int fontType, int fontSize) throws FontFormatException, IOException {
		InputStream fontStream = getClass().getResourceAsStream("/fonts/ANGELINA.TTF");
		Font onePoint = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		fontStream.close();
		Font font = onePoint.deriveFont(fontType, fontSize);
		return font;
	}

	public void initComponents(Product product) throws FontFormatException, IOException {
		JPanel upperPanel = new JPanel(new BorderLayout());
		upperPanel.setBackground(this.getBackground());
		JLabel titleLabel = new JLabel(product.getName(), JLabel.CENTER);
		titleLabel.setFont(customFont(Font.BOLD, 22));
		titleLabel.setForeground(Color.RED);
		upperPanel.add(titleLabel, BorderLayout.NORTH);

		JButton coverButton = new JButton(
				new ImageIcon(product.getIllustration().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		coverButton.setBorderPainted(false);
		coverButton.setName(""+product.getId());
		System.out.println("el nombre del chimbo es "+coverButton.getName());
		coverButton.setContentAreaFilled(false);
		coverButton.addActionListener(controller);
		coverButton.setActionCommand(Actions.VIEW_DETAILS.name());
		coverButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true));
		upperPanel.add(coverButton, BorderLayout.CENTER);

		JPanel infoPanel = new JPanel(new GridLayout(0, 1));
		infoPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));
		JLabel idlbl = new JLabel("ID " + product.getId(), JLabel.CENTER);
		// JLabel artistlbl = new JLabel("Artist: "+product.getArtistName(),
		// JLabel.CENTER);
		JLabel storePricelbl = new JLabel("Price: " + product.getStorePrice(), JLabel.CENTER);
		JLabel categorylbl = new JLabel("Category: " + product.getCategory(), JLabel.CENTER);
		JLabel currentStatelbl = new JLabel((product.isInStock()) ? "In Stock" : "Out of Stock", JLabel.CENTER);
		currentStatelbl.setForeground((currentStatelbl.getText() == "In Stock") ? Color.GREEN : Color.RED);

		infoPanel.add(idlbl);
		//infoPanel.add(categorylbl);
		infoPanel.add(storePricelbl);
		infoPanel.add(currentStatelbl);
		upperPanel.add(infoPanel, BorderLayout.SOUTH);

		JPanel container = new JPanel(new BorderLayout(3, 3));
		container.setBackground(Color.WHITE);
		container.setBorder(BorderFactory.createTitledBorder("Product Description"));
		descriptionTxtA = new JTextArea(5, 15);
		descriptionTxtA.setText(product.getDescription());
		descriptionTxtA.setFont(customFont(Font.PLAIN,14));
		descriptionTxtA.setEditable(false);
		container.add(descriptionTxtA,BorderLayout.CENTER);
		
		JPanel currentReviewsOfItem = new JPanel();
		currentReviewsOfItem.setLayout(new BoxLayout(currentReviewsOfItem, BoxLayout.Y_AXIS));
		JPanel clientRate = new JPanel();
		clientRate.setLayout(new BorderLayout(5,5));
		JPanel reviewPanel = new JPanel(new BorderLayout());
		reviewPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		
		StarsPanel starsPanel = new StarsPanel();
		starsPanel.setBackground(Color.WHITE);
		starsPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		//starsPanel.setSize(100,100);
		clientRate.add(starsPanel,BorderLayout.NORTH);
		
		reviewTxtA = new JTextArea(5,15);
		reviewTxtA.setText("Write a review of this product!");
		reviewPanel.add(reviewTxtA,BorderLayout.CENTER);
		reviewPanel.add(new JScrollPane(reviewTxtA,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),BorderLayout.EAST);
		
		clientRate.add(reviewPanel,BorderLayout.SOUTH);
		
		currentReviewsOfItem.add(clientRate);
		
		container.add(currentReviewsOfItem, BorderLayout.SOUTH);
	
		JPanel buyingOptionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

		buyingOptionsPanel.setBackground(this.getBackground());

		if (product.getQuantity() >= 1) {
			JButton addItemToCar = new JButton("Add to cart",
					new ImageIcon(getClass().getResource("/img/shoppingCar.png")));
			addItemToCar.addActionListener(controller);
			addItemToCar.setActionCommand(Actions.ADD_TO_SHOPPING_CAR.name());
			addItemToCar.setHorizontalTextPosition(JButton.CENTER);
			addItemToCar.setBorderPainted(false);
			addItemToCar.setContentAreaFilled(false);
			addItemToCar.setVerticalTextPosition(JButton.TOP);
			buyingOptionsPanel.add(addItemToCar);

			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, product.getQuantity(), 1);
			JSpinner spinner = new JSpinner(model);
			buyingOptionsPanel.add(spinner);

		} else {
			JLabel state = new JLabel((product.isInStock())?"In Stock":"Out Of Stock", JLabel.CENTER);
			state.setForeground((product.isInStock())?Color.GREEN:Color.RED);
			buyingOptionsPanel.add(state);
		}

		add(upperPanel, BorderLayout.NORTH);
		add(container, BorderLayout.CENTER);
		add(buyingOptionsPanel, BorderLayout.SOUTH);
		this.pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		FullItemViewDialog dialog = new FullItemViewDialog();
		/*try {
			dialog.initComponents(new Product(12321, "LA CHOTAAAAAA", 32432, Category.BEAUTY,"fsdfsd", 432432, ImageIO.read(new File("E:/catalogSales/src/img/administratorIcon.png"))));
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		dialog.setVisible(true);
		
	}
	
	private class StarsPanel extends JPanel implements MouseListener, MouseMotionListener{
		
		private static final long serialVersionUID = 1L;
		private int cordX;
		private int cordY;
		
		public StarsPanel() {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
		}

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println(e.getX());
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println(e.getX());
			
		}
		
	}
}
