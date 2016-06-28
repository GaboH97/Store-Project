package views;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import controller.Controller;
import model.entity.Product;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private UserView userView;
	private AdministratorView administratorView;

	public MainWindow(Controller controller) {

		for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(laf.getName()))
				try {
					UIManager.setLookAndFeel(laf.getClassName());
					//UIManager.put("Label.font", customFont());
					UIManager.put("Button.background", new Color(117, 149, 255));
					SwingUtilities.updateComponentTreeUI(this);
				} catch (Exception ex) {
				}
		}
		setTitle("Store");
		setSize(WIDTH, HEIGHT);
		setIconImage(new ImageIcon(getClass().getResource("/img/storeLogo.png")).getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		
		administratorView = new AdministratorView(controller);
		userView = new UserView(controller, this);
		
		
		
		/*model.entity.Category[] values = model.entity.Category.values();
		
		JPanel categoriesPanel = new JPanel(new GridLayout(4,4,5,5));
		categoriesPanel.setBackground(new Color(148,191,255));
		categoriesPanel.setBorder(BorderFactory.createEmptyBorder(110, 350, 110, 350));
		for (int i = 0; i < 16; i++) {
			//JButton category = new JButton(values[i].toString());
		
			System.out.println(values[i].toString());
		
			JButton category = new JButton(values[i].toString(),new ImageIcon(new ImageIcon(getClass().getResource("/img/"+values[i].toString()+".png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			category.setContentAreaFilled(false);
			category.setPreferredSize(new Dimension(100,100));
			category.setBorderPainted(true);
			category.setName(values[i].toString());
			category.setHorizontalTextPosition(JButton.CENTER);
			category.setVerticalTextPosition(JButton.TOP);
			category.addActionListener(controller);
			category.setActionCommand(Actions.DISPLAY_CATEGORY_PRODUCTS.name());
			categoriesPanel.add(category);
		}
		add(categoriesPanel,BorderLayout.CENTER);*/
		//add(administratorView, BorderLayout.CENTER);
	}
	
	public void initComponents(boolean isAdministrator){
		if(isAdministrator){
			System.out.println("La drogaa");
			add(administratorView, BorderLayout.CENTER);
		}else{
			add(userView, BorderLayout.CENTER);
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		pack();
	}

	public Font customFont() throws FontFormatException, IOException {
		InputStream fontStream = getClass().getResourceAsStream("/fonts/ANGELINA.TTF");
		Font onePoint;
		onePoint = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		fontStream.close();
		return onePoint.deriveFont(Font.PLAIN, 20);
	}

	public AdministratorView getAdministratorView() {
		return administratorView;
	}
	
	public void displayComponentsForUserView(ArrayList<Product> products) {
		System.out.println("el chimbo mide "+products.size());
		userView.getMerchandisePanel().displayComponents2(products);
		repaint();
	}

	public void clear() {
		removeAll();
	}

	public void closeAdminSession() {
		System.out.println(getComponentCount());
		getContentPane().removeAll();
		revalidate();
		setVisible(false);
	}
}
