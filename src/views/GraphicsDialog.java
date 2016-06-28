package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphicsDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private TreeMap<String, Integer> productsCategories;
	private GraphicsPanel graphicsPanel;
	private JComboBox<FontNames> fontsJCB;
	private JSpinner fontSizeSpinner;
	private int fontSize;
	private String fontFamily;

	public GraphicsDialog(MainWindow mainWindow) {
		super(mainWindow);
		productsCategories = new TreeMap<>();
		setTitle("Products Categories");
		setSize(600, 600);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout());

		/*GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		// gbc.ipady = graphicsPanel.getHeight()/2;*/
		graphicsPanel = new GraphicsPanel();
		add(graphicsPanel, BorderLayout.CENTER);

		JPanel toolsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		toolsPanel.setBackground(new Color(249, 58, 103));
		fontsJCB = new JComboBox<>(FontNames.values());
		fontsJCB.setSelectedIndex(14);
		fontsJCB.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				fontFamily = fontsJCB.getSelectedItem().toString();
				graphicsPanel.repaint();
			}
		});
		toolsPanel.add(fontsJCB);

		fontSizeSpinner = new JSpinner(new SpinnerNumberModel(12, 0, 24, 2));
		fontSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				fontSize = (int) fontSizeSpinner.getValue();
				graphicsPanel.repaint();
			}
		});

		toolsPanel.add(fontSizeSpinner);
		/**gbc.gridx =0;
		gbc.gridy=1;
		gbc.gridwidth =2;
		gbc.gridheight=1;*/
		add(toolsPanel, BorderLayout.SOUTH);

	}

	private class GraphicsPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		public GraphicsPanel() {
			setBackground(Color.WHITE);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			if (!productsCategories.isEmpty()) {
				int totalAppeareances = 0;
				int startAngle = 0;
				int spacing = 0;
				for (Map.Entry<String, Integer> entry : productsCategories.entrySet()) {
					totalAppeareances += entry.getValue();
				}
				for (Map.Entry<String, Integer> entry : productsCategories.entrySet()) {
					g.setColor(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), 0));

					double arcAngle = (entry.getValue() * 360) / totalAppeareances;
					g.fillArc((this.getWidth() / 2) - (this.getWidth() / 8),
							(this.getHeight() / 2) - (this.getHeight() / 8), this.getWidth() / 4, this.getHeight() / 4,
							startAngle, (int) arcAngle);

					g.setFont(new Font(fontFamily, Font.BOLD, fontSize));

					g.drawString(entry.getKey(), (int) ((3.5 * this.getWidth()) / 5),
							(this.getHeight() / 12) + spacing);
					spacing += 20;
					startAngle += (int) arcAngle;
				}
			}
		}
	}

	public Font customFont() throws FontFormatException, IOException {
		InputStream fontStream = getClass().getResourceAsStream("/fonts/ANGELINA.TTF");
		Font onePoint = Font.createFont(Font.TRUETYPE_FONT, fontStream);
		fontStream.close();
		Font font = onePoint.deriveFont(Font.BOLD, 16);
		return font;
	}

	public void getCategoriesFrequencies(TreeMap<String, Integer> awf) {
		for (Map.Entry<String, Integer> entry : awf.entrySet()) {
			System.out.println(entry.getKey()+" %%%% "+entry.getValue());
		}
		productsCategories = awf;
		graphicsPanel.repaint();
		setVisible(true);
	}
}
