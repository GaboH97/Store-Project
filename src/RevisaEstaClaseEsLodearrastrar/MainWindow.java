package RevisaEstaClaseEsLodearrastrar;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class MainWindow extends JFrame{
	private JTextArea area;

	public MainWindow() {
		setSize(500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		area = new JTextArea();
		area.setSize(200, 200);
		area.setLocation(20, 30);
//		area.setEnabled(false); 
		area.setDropTarget(new DropTarget(){
			private static final long serialVersionUID = 1L;
			@Override
			public synchronized void drop(DropTargetDropEvent dtde) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY);
				try {
					System.out.println(dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		add(area);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainWindow();
	}



}
