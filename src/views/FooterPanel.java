package views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import controller.Controller;
import model.entity.Product;

public class FooterPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;

	public FooterPanel(Controller controller) {
		this.controller = controller;
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		setBorder(BorderFactory.createTitledBorder("Top Sold Items"));
		setBackground(new Color(250,213,138));
	}

	public void updateTopSoldItems(TreeMap<Product, Integer> topSoldItems) {
		removeAll();
		for (Entry<Product, Integer> entry : topSoldItems.entrySet()) {
			System.out.println("El item se llama"+entry.getKey()+" y su cantidad es "+entry.getValue());
			SingleItemPanel sip = new SingleItemPanel();
			sip.initComponentsFooterPanel(controller, entry.getKey(), entry.getValue());
			add(sip);
		}
		repaint();
	}

}
