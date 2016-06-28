package views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;

import controller.Actions;
import controller.Controller;
import model.entity.Product;

public class SingleItemPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton car;
	private JSpinner amountSpinner;

	public SingleItemPanel() {
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
	}

	public void initComponents(Controller controller, Product product) {
		product.checkAvailability();
		JPanel coverPanel = new JPanel(new BorderLayout());
		coverPanel.setSize(100, 100);
		coverPanel.setBackground(this.getBackground());
		JButton coverButton = new JButton(
				new ImageIcon(product.getIllustration().getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
		coverButton.setName(""+product.getId());
		coverButton.setContentAreaFilled(false);
		coverButton.setBorderPainted(false);
		coverButton.setToolTipText("Explore item");
		coverButton.setActionCommand(Actions.VIEW_DETAILS.name());
		coverButton.addActionListener(controller);
		coverPanel.add(coverButton, BorderLayout.CENTER);

		JPanel inferiorPanel = new JPanel(new BorderLayout());
		inferiorPanel.setBackground(this.getBackground());

		JPanel infoPanel = new JPanel(new GridLayout(3, 1));
		infoPanel.setBackground(this.getBackground());

		JLabel titleLabel = new JLabel(product.getName(), JLabel.CENTER);
		infoPanel.add(titleLabel);
		JLabel isInStockLabel = new JLabel((product.isInStock())?"In Stock":"Out Of Stock", JLabel.CENTER);
		isInStockLabel.setForeground((product.isInStock())?Color.GREEN:Color.RED);
		infoPanel.add(isInStockLabel);
		JLabel priceLabel = new JLabel("" + product.getStorePrice(), JLabel.CENTER);
		infoPanel.add(priceLabel);

		inferiorPanel.add(infoPanel, BorderLayout.CENTER);
		if(product.isInStock()){
			JPanel buyingParametersPanel = new JPanel();
			buyingParametersPanel.setBackground(new Color(123,193,240));

			car = new JButton(new ImageIcon(getClass().getResource("/img/shoppingCar.png")));
			car.setName(product.getName());
			//car.addActionListener(controller);
			car.setActionCommand(Actions.ADD_TO_SHOPPING.name());
			car.setContentAreaFilled(false);
			car.setBorderPainted(false);

			SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1);
			amountSpinner = new JSpinner(model);
			buyingParametersPanel.add(car);
			buyingParametersPanel.add(amountSpinner);

			buyingParametersPanel.add(car);
			buyingParametersPanel.add(amountSpinner);

			inferiorPanel.add(buyingParametersPanel, BorderLayout.SOUTH);
		}else{
			JLabel labelOOS = new JLabel("Out Of Stock");
			labelOOS.setHorizontalAlignment(JLabel.CENTER);
			labelOOS.setAlignmentX(JLabel.CENTER_ALIGNMENT);
			labelOOS.setForeground(Color.RED);
			inferiorPanel.add(labelOOS);
		}
		
		add(coverPanel, BorderLayout.CENTER);
		add(inferiorPanel, BorderLayout.SOUTH);
		repaint();
	}

	public void initComponentsFooterPanel(Controller controller, Product product, int quantity) {
		JPanel coverPanel = new JPanel(new BorderLayout(5,5));
		coverPanel.setSize(100, 100);
		coverPanel.setBackground(this.getBackground());
		
		JButton coverButton = new JButton(
				new ImageIcon(product.getIllustration().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		coverButton.setContentAreaFilled(false);
		coverButton.setBorderPainted(false);
		coverButton.setToolTipText("Explore item");
		coverButton.setActionCommand(Actions.VIEW_DETAILS.name());
		coverButton.addActionListener(controller);
		coverPanel.add(coverButton, BorderLayout.CENTER);
		
		coverPanel.add(new JLabel(product.getName()), BorderLayout.SOUTH);
		add(coverPanel,BorderLayout.CENTER);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.black);
		g2d.drawRoundRect(0, 0, this.getWidth(), this.getHeight(), 10, 10);
		super.paintComponent(g);
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		SingleItemPanel sip = new SingleItemPanel();
		//try {
			//sip.initComponents(new Product(12321, "LA CHOTAAAAAA", 32432, Category.BEAUTY,"fsdfsd", 432432, ImageIO.read(new File("E:/catalogSales/src/img/administratorIcon.png"))));
	//	} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}
		frame.add(sip);
		frame.pack();
		frame.setVisible(true);
		
	}
}
