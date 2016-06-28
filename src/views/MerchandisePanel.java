package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.plaf.BorderUIResource.LineBorderUIResource;
import controller.Actions;
import controller.Controller;
import model.entity.Product;

public class MerchandisePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Controller controller;
	private ArrayList<SingleItemPanel> productsPanel;
	//private ArrayList<Product> totalSearchedItems;
	private ArrayList<Product> itemResults;
	//private TreeMap<String, Integer> itemsWithQuantities;
	private int pageCount;

	public MerchandisePanel(Controller controller) {

		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("All Items Available"));
		setBackground(Color.WHITE);

		this.controller = controller;
		//itemsWithQuantities = new TreeMap<>();
		itemResults = new ArrayList<>();
		//totalSearchedItems = new ArrayList<>();
		productsPanel = new ArrayList<>(9);
		pageCount = 0;

		JPanel itemsPanel = new JPanel(new GridLayout(4, 4, 3, 3));
		itemsPanel.setBorder(LineBorderUIResource.createGrayLineBorder());

		for (int i = 0; i < 16; i++) {
			SingleItemPanel sip = new SingleItemPanel();
			productsPanel.add(sip);
			itemsPanel.add(sip);
		}

		add(itemsPanel, BorderLayout.CENTER);

		JPanel knobsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		knobsPanel.setPreferredSize(new Dimension((int)knobsPanel.getPreferredSize().getWidth(),20));
		knobsPanel.setName("Knobs Main");
		JButton back = new JButton(new ImageIcon(getClass().getResource("/img/back.png")));
		back.setName("back");
		back.setBorderPainted(false);
		back.setContentAreaFilled(false);
		back.addActionListener(controller);
		back.setActionCommand(Actions.CHANGE_VIEW_PAGE.name());

		JButton forward = new JButton(new ImageIcon(getClass().getResource("/img/forward.png")));
		forward.setName("forward");
		forward.setBorderPainted(false);
		forward.setContentAreaFilled(false);
		forward.setActionCommand(Actions.CHANGE_VIEW_PAGE.name());
		forward.addActionListener(controller);
		knobsPanel.add(back);
		knobsPanel.add(forward);

		add(knobsPanel, BorderLayout.SOUTH);
		pageCount = 0;
	}

	public void displayComponents(int page) throws FontFormatException, IOException {
		for (int i = 16 * page, aux = 0; i < 16 * (page + 1); i++, aux++) {
			productsPanel.get(aux).initComponents(controller, itemResults.get(i));
		}
	}

	public void displayComponents2(ArrayList<Product> products) {
		System.out.println("Changing the mf page!!!!!!!!!!!");
		removePanelsContent();
		if (products.size() == 16) {
			for (int i = 0; i < products.size(); i++) {
				productsPanel.get(i).initComponents(controller, products.get(i));
			}
		} else {
			for (int i = 0; i < productsPanel.size(); i++) {
				if (i < products.size()) {
					productsPanel.get(i).initComponents(controller, products.get(i));
				} else {
					productsPanel.get(i).setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
					productsPanel.get(i).removeAll();
				}
			}
		}
	}

	public void addItems(ArrayList<Product> newProducts) {
		itemResults.addAll(newProducts);
		System.out.println("polla");
	}

	public void removePanelsContent() {
		for (SingleItemPanel singleItemPanel : productsPanel) {
			singleItemPanel.removeAll();
			singleItemPanel.revalidate();
		}
		repaint();
	}
}
