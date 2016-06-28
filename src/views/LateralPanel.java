package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;
import controller.Actions;
import controller.Controller;
import model.entity.Category;
import model.entity.Product;
import myExeption.ProductNotFoundException;

public class LateralPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JComboBox<Category> categoriesJCB;
	private JSpinner minPrice, maxPrice;
	private JTable filteredItems;
	private DefaultTableModel tableModel;

	private JScrollPane tableScroller;
	public static final String[] COLUMN_NAMES = { "ID", "name", "author", "Factory Price", "Item Type", "musicGenre",
			"Quantity" };

	public LateralPanel(Controller controller) {
		setLayout(new BorderLayout());
		setBackground(new Color(255, 229, 204));

		JPanel filtersPanel = new JPanel(new GridLayout(4, 1));
		filtersPanel.setBackground(this.getBackground());
		filtersPanel.setBackground(Color.WHITE);

		categoriesJCB = new JComboBox<>(Category.values());
		categoriesJCB.setBackground(new Color(200,76,60));

		filtersPanel.add(categoriesJCB);

		JPanel prices = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		minPrice = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 10));
		maxPrice = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 10));
		prices.add(minPrice);
		prices.add(new JLabel("to"));
		prices.add(maxPrice);
		filtersPanel.add(prices);

		JButton filter = new JButton("Filter");
		filter.setActionCommand(Actions.FILTER.name());
		filter.addActionListener(controller);
		filtersPanel.add(filter);

		add(filtersPanel, BorderLayout.NORTH);

		JPanel tablePanel = new JPanel();
		tablePanel.setBackground(Color.LIGHT_GRAY);

		tableModel = new DefaultTableModel();
		tableModel.setColumnIdentifiers(COLUMN_NAMES);
		filteredItems = new JTable(tableModel);
		tablePanel.add(filteredItems);

		tableScroller = new JScrollPane(filteredItems);
		add(tablePanel, BorderLayout.CENTER);

		add(tableScroller, BorderLayout.WEST);
	}

	public void addItems(ArrayList<Product> products) throws IOException {
		for (Product product : products) {
			Object[] row = new String[tableModel.getColumnCount()];
			row = product.fieldsForColumns();
			tableModel.addRow(row);
		}
	}

	public void updateValues(ArrayList<HashSet<String>> genresAndArtists) {
		
	}

	public int goToItemRow(Product product) throws ProductNotFoundException {
		for (int i = 1; i < filteredItems.getRowCount(); i++) {
			int ID = (int) filteredItems.getValueAt(i, 0);
			if (ID == product.getId()) {
				return i;
			}
		}
		throw new ProductNotFoundException();
	}
}

	//public String applyFilters() {
		/**
		 * if((String)(artistsJCB.getSelectedItem())=="" &&
		 * (String)(musicGenresJCB.getSelectedItem())=="" && "yes"){ return 1;
		 * }else if ((String)(artistsJCB.getSelectedItem())=="" &&
		 * (String)(musicGenresJCB.getSelectedItem())==""&& "no" ) { return 2;
		 * }else if ((String)(artistsJCB.getSelectedItem())=="" &&
		 * (String)(musicGenresJCB.getSelectedItem())!=""&& "no" ) { return 3;
		 * }else if ((String)(artistsJCB.getSelectedItem())=="" &&
		 * (String)(musicGenresJCB.getSelectedItem())!=""&& "yes" ) { return 4;
		 * }else if ((String)(artistsJCB.getSelectedItem())!="" &&
		 * (String)(musicGenresJCB.getSelectedItem())==""&& "no" ) { return 5;
		 * }else if ((String)(artistsJCB.getSelectedItem())!="" &&
		 * (String)(musicGenresJCB.getSelectedItem())==""&& "yes" ) { return 6;
		 * }else if ((String)(artistsJCB.getSelectedItem())!="" &&
		 * (String)(musicGenresJCB.getSelectedItem())!=""&& "yes" ) { return 7;
		 * }else{ throw new NoFiltersSelectedException(); }
		 **/
		// System.out.println(artistsJCB.getSelectedItem().toString());
	
		/*String categories = ((String)categoriesJCB.getSelectedItem() == "artists") ? "empty" : categoriesJCB.getSelectedItem().toString();
		return categories + "," + (Integer) minPrice.getValue() + "," + (Integer) maxPrice.getValue();
	}

	public static void main(String[] args) {
	}

}*/
