package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.entity.Category;
import controller.Controller;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public MainWindow() {
		setTitle("Catalog Sales");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setIconImage(new ImageIcon(getClass().getResource("/img/4.png")).getImage());
		setLayout(new BorderLayout());
		JPanel panel  = new JPanel(new GridLayout(4, 4));
		for (Category category : Category.values()) {
			panel.add(new JButton(new ImageIcon(getClass().getResource("/img/"+category.toString()+".png"))));
		}
		add(panel, BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public static void main(String[] args) {
		MainWindow main = new MainWindow();
	}
}
