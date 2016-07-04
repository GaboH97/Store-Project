package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import controller.Actions;
import controller.Controller;
import model.entity.Category;
import model.entity.Product;

public class AdministratorView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel, TopPanel;
	private final static String[] COLUMN_NAMES = { "ID", "Name", "Factory Price", "Category", "In Stock", "Quantity",
			"Delete" };
	private JTable tblElements;
	private Controller controller;
	private JTextField user, nameFilterTF;
	private JPasswordField pass;
	private DefaultTableModel model;
	private JPanel lateralPanel;
	private JSpinner minSpinner;
	private JSpinner maxSpinner;
	private JComboBox<Category> categoryJCB;

	public AdministratorView(Controller controller) {
		this.controller = controller;
		setLayout(new BorderLayout(5, 5));
		createMainPanel();
		createTopPanel();
		createLateralPanel();
		createFooterPanel();
	}

	public void createMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.WHITE);
		
		JPanel tablePanel = new JPanel(new BorderLayout());
		tablePanel.setBackground(Color.WHITE);
		tablePanel.setBorder(BorderFactory.createTitledBorder("PRODUCTS LIST"));

		model = new DefaultTableModel();
		model.setColumnIdentifiers(COLUMN_NAMES);

		tblElements = new JTable(model);
		tblElements.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		tblElements.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());
		modifyTableColumnsSize(tblElements);
		
		tablePanel.add(new JScrollPane(tblElements, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);

		mainPanel.add(tablePanel, BorderLayout.CENTER);
		add(mainPanel, BorderLayout.CENTER);
	}

	public void createTopPanel() {
		TopPanel = new JPanel(new BorderLayout());
		TopPanel.setBackground(new Color(0, 0, 102));

		JButton storeLogo = new JButton("PIRATE BAY", new ImageIcon(getClass().getResource("/img/storeLogo.png")));
		try {
			storeLogo.setFont(customFont(Font.BOLD, 16));
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		storeLogo.setHorizontalTextPosition(JButton.CENTER);
		storeLogo.setVerticalTextPosition(JButton.TOP);
		storeLogo.setForeground(Color.WHITE);
		storeLogo.setHorizontalAlignment(JButton.CENTER);
		storeLogo.setContentAreaFilled(false);
		storeLogo.setBorderPainted(false);
		storeLogo.addActionListener(controller);
		storeLogo.setActionCommand(Actions.GO_HOME_PAGE.name());
		TopPanel.add(storeLogo, BorderLayout.WEST);

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

		TopPanel.add(panelPageStart, BorderLayout.EAST);
		add(TopPanel, BorderLayout.NORTH);

	}

	private Font customFont(int fontType, int fontSize) throws FontFormatException, IOException {
		InputStream fontStream = getClass().getResourceAsStream("/fonts/ANGELINA.TTF");
		Font onePoint;
		onePoint = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		fontStream.close();
		return onePoint.deriveFont(fontType, fontSize);
	}

	private void modifyTableColumnsSize(JTable tblElements2) {
		tblElements2.getColumnModel().getColumn(0).setPreferredWidth(80);
		tblElements2.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblElements2.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblElements2.getColumnModel().getColumn(3).setPreferredWidth(100);
		tblElements2.getColumnModel().getColumn(4).setPreferredWidth(100);
		tblElements2.getColumnModel().getColumn(5).setPreferredWidth(100);
		tblElements2.setRowHeight(30);
	}

	public void addProduct(Product product) throws IOException {
		Object[] row = new String[tblElements.getModel().getColumnCount()];
		row = product.fieldsForColumns();
		// List<Object> auxRow = Arrays.asList(product.fieldsForColumns());
		// auxRow.add(6,new String("Edit"));
		// auxRow.add(new String("Delete"));
		row = addElement(row, new String("Delete"));
		JButton btn = new JButton();
		btn.setName("" + product.getId());
		tblElements.getColumnModel().getColumn(6).setCellEditor(new MyButtonEditor(new JCheckBox(), btn));
		model.addRow(row);
	}

	public void clearTable() {
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
	}

	private static Object[] addElement(Object[] a, Object e) {
		a = Arrays.copyOf(a, a.length + 1);
		a[a.length - 1] = e;
		return a;
	}

	private void createFooterPanel() {
	}

	private void createLateralPanel() {
		lateralPanel = new JPanel(new BorderLayout());
		lateralPanel.setBorder(BorderFactory.createTitledBorder("Manager Tools"));
		lateralPanel.setBackground(Color.WHITE);

		JPanel filtersPanel = new JPanel();
		filtersPanel.setBackground(Color.WHITE);
		filtersPanel.setLayout(new BoxLayout(filtersPanel, BoxLayout.Y_AXIS));
		filtersPanel.setBorder(BorderFactory.createTitledBorder("Filters"));
		nameFilterTF = new JTextField(15);
		nameFilterTF.setBorder(BorderFactory.createTitledBorder("By Name"));
		filtersPanel.add(nameFilterTF);

		categoryJCB = new JComboBox<>(Category.values());
		categoryJCB.setBorder(BorderFactory.createTitledBorder("By Category"));
		categoryJCB.setBackground(Color.WHITE);
		filtersPanel.add(categoryJCB);

		JPanel priceRangePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		priceRangePanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		priceRangePanel.setBackground(Color.WHITE);
		minSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5000, 10));
		priceRangePanel.add(minSpinner);
		priceRangePanel.add(new JLabel("to"));

		maxSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 5000, 10));
		priceRangePanel.add(maxSpinner);
		filtersPanel.add(priceRangePanel);

		JButton filter = new JButton("Filter");
		filter.setAlignmentX(JButton.CENTER_ALIGNMENT);
		filter.setBackground(new Color(111, 111, 255));
		filter.setForeground(Color.WHITE);
		filter.addActionListener(controller);
		filter.setActionCommand(Actions.FILTER.name());
		filter.setBorderPainted(false);
		filtersPanel.add(filter);

		lateralPanel.add(filtersPanel, BorderLayout.NORTH);

		JPanel toolsPanel = new JPanel();
		toolsPanel.setBackground(Color.WHITE);
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.Y_AXIS));

		JButton addProduct = new JButton();
		addProduct.setText("Add Product");
		addProduct.addActionListener(controller);
		addProduct.setActionCommand(Actions.ADD_PRODUCT.name());
		addProduct.setAlignmentX(JButton.CENTER_ALIGNMENT);
		addProduct.setBorderPainted(false);
		addProduct.setContentAreaFilled(false);
		toolsPanel.add(addProduct);

		JButton checkProductsList = new JButton();
		checkProductsList.setText("Check Products List");
		checkProductsList.addActionListener(controller);
		checkProductsList.setActionCommand(Actions.GET_FULL_LIST_OF_PRODUCTS.name());
		checkProductsList.setAlignmentX(JButton.CENTER_ALIGNMENT);
		checkProductsList.setBorderPainted(false);
		checkProductsList.setContentAreaFilled(false);
		toolsPanel.add(checkProductsList);

		JButton getQueriesGraphics = new JButton("Get queries Graphics");
		getQueriesGraphics.addActionListener(controller);
		getQueriesGraphics.setActionCommand(Actions.GET_QUERIES_GRAPHICS.name());
		getQueriesGraphics.setAlignmentX(JButton.CENTER_ALIGNMENT);
		getQueriesGraphics.setBorderPainted(false);
		getQueriesGraphics.setContentAreaFilled(false);

		toolsPanel.add(getQueriesGraphics);

		lateralPanel.add(toolsPanel, BorderLayout.CENTER);
		add(lateralPanel, BorderLayout.WEST);
	}

	public void deleteRow(int ID) {
		for (int i = 0; i < model.getRowCount(); i++) {
			System.out.println(model.getValueAt(i, 0));
			if ((int) model.getValueAt(i, 0) == ID) {
				model.removeRow(i);
			}
		}
	}

	private class ImageRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JLabel label = new JLabel();
			if (value != null) {
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setIcon(new ImageIcon((byte[]) value));
			}
			return label;
		}
	}

	public ArrayList<String> applyFilterParameters() {
		ArrayList<String> parameters = new ArrayList<String>();

		parameters.add((!nameFilterTF.getText().isEmpty()) ? nameFilterTF.getText() : "Empty");
		parameters.add(((int) minSpinner.getValue() != 0) ? (int) minSpinner.getValue() + "" : "Empty");
		parameters.add(((int) maxSpinner.getValue() != 0) ? (int) maxSpinner.getValue() + "" : "Empty");
		parameters.add((categoryJCB.getSelectedItem().toString() != "Categories")
				? categoryJCB.getSelectedItem().toString() : "Empty");
		return parameters;
	}

	public void addFilteredItemsToTable(ArrayList<Product> applyFilters) throws IOException {
		clearTable();
		for (Product product : applyFilters) {
			model.addRow(product.fieldsForColumns());
		}
	}

	class ButtonRenderer extends JButton implements TableCellRenderer {

		private static final long serialVersionUID = 1L;

		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	class MyButtonEditor extends DefaultCellEditor {

		private static final long serialVersionUID = 1L;
		protected JButton button;
		private String label;
		private boolean isPushed;

		public MyButtonEditor(JCheckBox checkBox, JButton btn) {
			super(checkBox);
			this.button = btn;
			button.setOpaque(true);
			button.addActionListener(controller);
			button.setActionCommand(Actions.DELETE_PRODUCT.name());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			isPushed = false;
			return label;
		}

		@Override
		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		@Override
		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}
}