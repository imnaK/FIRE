package prv.imnak.fire.dialogs;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class Dialog extends JDialog {
	private static final long serialVersionUID = 1L;

	protected SpringLayout layout;
	
	protected JPanel contentPane;
	
	public Dialog(JFrame owner, String title, int width, int height) {
		super(owner, true);
		
		initFrame(title, width, height);
		initPanel();
	}

	private void initFrame(String title, int width, int height) {
//		setIconImage(App.iIconApp);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle(title);
		setSize(width, height);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
	}
	
	private void initPanel() {
		layout = new SpringLayout();
		
		contentPane = new JPanel();
		contentPane.setLayout(layout);
		setContentPane(contentPane);
	}
	
}