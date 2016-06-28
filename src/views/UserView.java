package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import controller.Actions;
import controller.Controller;

public class UserView extends JPanel {

	private static final long serialVersionUID = 1L;
	private MerchandisePanel merchandisePanel;
	private FooterPanel footerPanel;
	private LateralPanel lateralPanel;
	private JTextField user;
	private JPasswordField pass;

	public UserView(Controller controller, MainWindow mainWindow) {
		merchandisePanel = new MerchandisePanel(controller);
		footerPanel = new FooterPanel(controller);
		lateralPanel = new LateralPanel(controller);
		setLayout(new BorderLayout());
		//add(merchandisePanel, BorderLayout.CENTER);
		//add(footerPanel, BorderLayout.SOUTH);
		add(lateralPanel, BorderLayout.WEST);
		
		JScrollPane scroll = new JScrollPane();
		scroll.setBounds(132, 155, 502, 311);
		scroll.setViewportView(merchandisePanel);
		scroll.getViewport().setView(merchandisePanel);
		add(scroll, BorderLayout.CENTER);
		setTopPanel(controller);

	}

	public void setTopPanel(Controller controller) {
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.setBackground(new Color(0, 0, 102));

		JButton storeLogo = new JButton("PIRATE BAY", new ImageIcon(getClass().getResource("/img/storeLogo.png")));
		try {
			storeLogo.setFont(customFont(Font.BOLD, 16));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		storeLogo.setHorizontalTextPosition(JButton.CENTER);
		storeLogo.setVerticalTextPosition(JButton.TOP);
		storeLogo.setHorizontalAlignment(JButton.LEFT);
		storeLogo.setContentAreaFilled(false);
		storeLogo.setBorderPainted(false);
		storeLogo.addActionListener(controller);
		storeLogo.setActionCommand(Actions.GO_HOME_PAGE.name());
		topPanel.add(storeLogo, BorderLayout.WEST);

		JPanel panelPageStart = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelPageStart.setBackground(new Color(0, 0, 102));

		AvatarButton adminButton = new AvatarButton();
		adminButton.setIcon(new ImageIcon(new ImageIcon(getClass().getResource("/img/administratorIcon.png")).getImage()
				.getScaledInstance(45, 45, Image.SCALE_SMOOTH)));
		adminButton.addActionListener(controller);
		adminButton.setActionCommand(Actions.LOG_IN_ADMIN.name());
		panelPageStart.add(adminButton);

		user = new JTextField(15);
		user.setBorder(BorderFactory.createTitledBorder("User Name"));
		panelPageStart.add(user);
		pass = new JPasswordField(15);
		pass.setBorder(BorderFactory.createTitledBorder("Password"));
		panelPageStart.add(pass);

		JPanel options = new JPanel();
		options.setBackground(new Color(0, 0, 102));
		options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

		JButton login = new JButton("Login");
		login.setActionCommand(Actions.LOG_IN_ADMIN.name());
		login.addActionListener(controller);
		login.setBackground(new Color(73, 73, 186));
		login.setAlignmentX(JButton.CENTER_ALIGNMENT);
		login.setForeground(Color.white);
		options.add(login);

		JButton closeSession = new JButton("Close Session");
		closeSession.setActionCommand(Actions.CLOSE_ADMIN_SESSION.name());
		closeSession.addActionListener(controller);
		closeSession.setAlignmentX(JButton.CENTER_ALIGNMENT);
		closeSession.setBackground(new Color(252, 78, 78));
		closeSession.setForeground(Color.white);
		options.add(closeSession);

		panelPageStart.add(options);

		topPanel.add(panelPageStart, BorderLayout.EAST);
		add(topPanel, BorderLayout.NORTH);
	}

	private Font customFont(int fontType, int fontSize) throws FontFormatException, IOException {
		InputStream fontStream = getClass().getResourceAsStream("/fonts/ANGELINA.TTF");
		Font onePoint;
		onePoint = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		fontStream.close();
		return onePoint.deriveFont(fontType, fontSize);
	}

	public MerchandisePanel getMerchandisePanel() {
		return merchandisePanel;
	}

}
