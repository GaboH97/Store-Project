package controller;

import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import model.dao.Store;
import model.entity.Product;
import models.exceptions.EmptyQueryWithGivenParametersException;
import myExeption.ExceptionVerifyId;
import myExeption.ProductNotFoundException;
import persistence.DataSaver;
import views.DialogCreateProduct;
import views.FullItemViewDialog;
import views.GraphicsDialog;
import views.MainWindow;
import views.OpeningDialog;

public class Controller implements ActionListener {

	private static final String FILTER_FAILED_MESSAGE = "<html>Filter failed for some reason<br>Please check input data</html>";
	private static final String DELETE_PRODUCT_MESSAGE = "<html>Do you really want to <br> delete this item?</html>";
	private MainWindow mainWindow;
	private DialogCreateProduct dialogCreateProduct;
	private OpeningDialog openingDialog;
	private FullItemViewDialog fullItemViewDialog;
	private GraphicsDialog graphicsDialog;
	private Store store;

	public Controller() {
		store = new Store();
		mainWindow = new MainWindow(this);
		dialogCreateProduct = new DialogCreateProduct(this, mainWindow);
		openingDialog = new OpeningDialog(this);
		fullItemViewDialog = new FullItemViewDialog();
		graphicsDialog = new GraphicsDialog(mainWindow);
		// dialogAdministrator = new DialogAdministrator(this, mainWindow);
		// dialogSignInAccount = new DialogSignInAccount(this, mainWindow);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (Actions.valueOf(e.getActionCommand())) {
		case ADD_PRODUCT:
			addProduct();
			break;
		case CREATE_PRODUCT:
			createProduct();
			break;
		case DELETE_PRODUCT:
			deleteProduct((JButton) e.getSource());
			break;
		case ACCEPT_ADD:
			acceptAdd();
			break;
		case ADD_TO_SHOPPING:
			addToShoppingCar((JButton)e.getSource());
			break;
		case CANCEL:
			break;
		case CHANGE_VIEW_PAGE:
			changeViewPage((JButton)e.getSource());
			break;
		case DISPLAY_CATEGORY_PRODUCTS:
			break;
		case FILTER:
			filter();
			break;
		case VIEW_DETAILS:
			viewDetails((JButton)e.getSource());
			break;
		case GO_HOME_PAGE:
			goHomePage();
			break;
		case LOG_IN_ADMIN:
			logInAdmin();
			break;
		case ENTER_ADMIN_MODE:
			enterAdminMode();
			break;
		case ENTER_USER_MODE:
			enterUserMode();
			break;
		case GET_FULL_LIST_OF_PRODUCTS:
			getFullListOfProducts();
			break;
		case GET_QUERIES_GRAPHICS:
			getQueriesGraphics();
			break;
		case ADD_TO_SHOPPING_CAR:
			break;
		case CLOSE_ADMIN_SESSION:
			closeAdminSession();
			break;
		case EDIT_PRODUCT:
			break;
		default:
			break;
		}
	}

	private void getQueriesGraphics() {
		graphicsDialog.getCategoriesFrequencies(store.getCategoriesFrequencies());
	}

	private void changeViewPage(JButton source) {
		try {
			System.out.println(source.getParent().getName());
			if (source.getParent().getName() == "Knobs Main") {
				mainWindow.displayComponentsForUserView(
						store.displayComponents(store.changeViewPage(source)));
			}

		} catch (FontFormatException | IOException e) {
			JOptionPane.showMessageDialog(mainWindow, "No items to show", mainWindow.getTitle(),
					JOptionPane.WARNING_MESSAGE);
		}
	}

	private void closeAdminSession() {
	
		mainWindow.closeAdminSession();
		openingDialog.setVisible(true);
	}

	private void addToShoppingCar(JButton source) {
		
	}

	private void viewDetails(JButton source) {
		try {
			System.out.println(source.getName());
			//System.out.println(product.toString());
			Product product = store.searchProduct(Integer.parseInt(source.getName()));
			System.out.println(product.toString());
			fullItemViewDialog.initComponents(product);
			
		} catch (NumberFormatException | ProductNotFoundException | FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void goHomePage() {
		// TODO Auto-generated method stub
		
	}

	private void enterUserMode() {
		openingDialog.setVisible(false);
		//mainWindow.clear();
		mainWindow.initComponents(false);
		mainWindow.setVisible(true);
		mainWindow.displayComponentsForUserView(store.getCurrentProducts());
	}

	private void logInAdmin() {
		// TODO Auto-generated method stub
		
	}

	private void enterAdminMode() {
		openingDialog.setVisible(false);
		//mainWindow.clear();
		mainWindow.initComponents(true);
		mainWindow.setVisible(true);
	}

	private void getFullListOfProducts() {
		
	}

	private void filter() {
		mainWindow.getAdministratorView().clearTable();
		try {
			mainWindow.getAdministratorView().addFilteredItemsToTable(
					store.applyFilters(mainWindow.getAdministratorView().applyFilterParameters()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, FILTER_FAILED_MESSAGE, mainWindow.getTitle(),
					JOptionPane.WARNING_MESSAGE, new ImageIcon(getClass().getResource("/img/sad.png")));
			e.printStackTrace();
		} catch (EmptyQueryWithGivenParametersException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), mainWindow.getTitle(), JOptionPane.WARNING_MESSAGE, new ImageIcon(getClass().getResource("/img/sad.png")));
		}
	}

	private void acceptAdd() {
		try {
			Product product = dialogCreateProduct.createProduct();
			store.addProduct(product);
			System.out.println("Sisa");
			mainWindow.getAdministratorView().addProduct(product);
			//mainWindow.displayComponentsForUserView(store.getCurrentProducts());
			//dialogCreateProduct.clearAndClose();
		} catch (NumberFormatException | ExceptionVerifyId | IOException e) {
			JOptionPane.showMessageDialog(mainWindow, "Couldn't create Product for some reason", mainWindow.getTitle(),
					JOptionPane.WARNING_MESSAGE, new ImageIcon(getClass().getResource("/img/Others.png")));
		}
	}

	private void createProduct() {
		dialogCreateProduct.setVisible(true);
	}

	//private void editProduct(JButton source) {
	//	// TODO Auto-generated method stub

	//}

	private void deleteProduct(JButton source) {
		System.out.println("Mi nombre es "+source.getName());
		int option = JOptionPane.showConfirmDialog(null,DELETE_PRODUCT_MESSAGE, mainWindow.getTitle(), JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		if(option==JOptionPane.YES_OPTION){
			int ID = Integer.parseInt(source.getName());
			store.deleteProduct(ID);
			mainWindow.getAdministratorView().deleteRow(ID);
		}
		
	}

	private void addProduct() {
		dialogCreateProduct.setVisible(true);
	}
}
