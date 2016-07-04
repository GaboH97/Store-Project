package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Toolkit;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.Actions;
import controller.Controller;

public class OpeningDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public OpeningDialog(Controller controller) {
		setLayout(new BorderLayout(5, 5));
		setBackground(Color.WHITE);
		setTitle("Treasure Vault");
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - getWidth()/2, (Toolkit.getDefaultToolkit().getScreenSize().height)/2 - getHeight()/2);

		try {
			createTopPanel(controller);
			createMainPanel(controller);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createFooterPanel(controller);
		pack();
		setVisible(true);
	}

	private void createTopPanel(Controller controller) throws FontFormatException, IOException {
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		JButton storeLogo = new JButton("Treasure Vault", new ImageIcon(getClass().getResource("/img/storeLogo.png")));
		storeLogo.setBorderPainted(false);
		storeLogo.setHorizontalTextPosition(JButton.CENTER);
		storeLogo.setPressedIcon(new ImageIcon(getClass().getResource("/img/sad.png")));
		storeLogo.setVerticalTextPosition(JButton.TOP);
		storeLogo.setAlignmentX(JButton.CENTER_ALIGNMENT);
		storeLogo.setContentAreaFilled(false);
		storeLogo.setFont(customFont(Font.PLAIN, 24));
		topPanel.add(storeLogo);

		add(topPanel, BorderLayout.NORTH);
	}

	private Font customFont(int fontType, int fontSize) throws FontFormatException, IOException {
		InputStream fontStream = getClass().getResourceAsStream("/fonts/ANGELINA.TTF");
		Font onePoint = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		fontStream.close();
		Font font = onePoint.deriveFont(fontType, fontSize);
		return font;
	}

	private void createMainPanel(Controller controller) throws FontFormatException, IOException {
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3, true));
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JLabel label = new JLabel("Welcome to the Treasure Vault!");
		Font font = customFont(Font.BOLD, 30);
		Map attributes = font.getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_DOTTED);
		label.setFont(font.deriveFont(attributes));
		label.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		mainPanel.add(label);

		JLabel explanationLabel = new JLabel("<html>First of all, choose which mode<br> do you want to log in</html>");
		explanationLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		explanationLabel.setHorizontalAlignment(JLabel.CENTER);
		mainPanel.add(explanationLabel);

		JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		optionsPanel.setBackground(Color.WHITE);
		optionsPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

		JButton administratorView = new JButton("Admin Mode",
				new ImageIcon(getClass().getResource("/img/administratorIcon.png")));
		administratorView.setHorizontalTextPosition(JButton.CENTER);
		administratorView.setFont(customFont(Font.BOLD, 16));
		administratorView.setVerticalTextPosition(JButton.TOP);
		administratorView.addActionListener(controller);
		administratorView.setActionCommand(Actions.ENTER_ADMIN_MODE.name());
		administratorView.setBorderPainted(false);
		administratorView.setContentAreaFilled(false);

		optionsPanel.add(administratorView);

		JButton userView = new JButton("User Mode", new ImageIcon(getClass().getResource("/img/userIcon.png")));
		userView.setHorizontalTextPosition(JButton.CENTER);
		userView.setFont(customFont(Font.BOLD, 16));
		userView.setVerticalTextPosition(JButton.TOP);
		userView.addActionListener(controller);
		userView.setActionCommand(Actions.ENTER_USER_MODE.name());
		userView.setBorderPainted(false);
		userView.setContentAreaFilled(false);

		optionsPanel.add(userView);
		
		mainPanel.add(optionsPanel);

		add(mainPanel, BorderLayout.CENTER);
	}

	private void createFooterPanel(Controller controller) {
		// TODO Auto-generated method stub

	}

}
