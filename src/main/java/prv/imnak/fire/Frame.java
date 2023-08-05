package prv.imnak.fire;

import prv.imnak.fire.dialogs.OptionsDialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class Frame extends JFrame {

	final String appFrameTitle = Application.APPLICATION_NAME + " - " + Application.VERSION;
	final Dimension innerFrameSize = new Dimension(Util.rem(50), Util.rem(35));
	
	SpringLayout			layout;
	JPanel					contentPane;
	JScrollPane				spFilesFound;
	public JList<String>	listFilesFound;

	JTextField				tfSearchInPath,
							tfSearchForString;
	JLabel					lblSearchIn,
							lblSearchFor;
	public JButton			btnChangeSearchPath,
							btnOpenOptionsDialog,
							btnStartSearchProcess,
							btnOpenFile,
							btnOpenInExplorer,
							btnExitApplication;
	
	/**
	 * Create the frame
	 */
	public Frame() {
		initFrame();
		initComponents();
		initLayoutConstraints();
		
		fileChooserApprove();
	}

	private void openOptionsDialog() {
		new OptionsDialog(this);
	}
	
	private void initLayoutConstraints() {
		// label
		layout.putConstraint(SpringLayout.NORTH, lblSearchIn, Util.GAP, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, lblSearchIn, Util.WIDTH_SMALL + Util.GAP, SpringLayout.WEST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, lblSearchIn, Util.HEIGHT + Util.GAP, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, lblSearchIn, Util.GAP, SpringLayout.WEST, contentPane);

		layout.putConstraint(SpringLayout.NORTH, lblSearchFor, Util.GAP, SpringLayout.SOUTH, lblSearchIn);
		layout.putConstraint(SpringLayout.EAST, lblSearchFor, 0, SpringLayout.EAST, lblSearchIn);
		layout.putConstraint(SpringLayout.SOUTH, lblSearchFor, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, lblSearchIn);
		layout.putConstraint(SpringLayout.WEST, lblSearchFor, 0, SpringLayout.WEST, lblSearchIn);
		
		// button
		layout.putConstraint(SpringLayout.NORTH, btnChangeSearchPath, 0, SpringLayout.NORTH, lblSearchIn);
		layout.putConstraint(SpringLayout.EAST, btnChangeSearchPath, -Util.GAP, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, btnChangeSearchPath, 0, SpringLayout.SOUTH, lblSearchIn);
		layout.putConstraint(SpringLayout.WEST, btnChangeSearchPath, -(Util.WIDTH + Util.GAP), SpringLayout.EAST, contentPane);
		
		layout.putConstraint(SpringLayout.NORTH, btnOpenOptionsDialog, Util.GAP, SpringLayout.SOUTH, btnChangeSearchPath);
		layout.putConstraint(SpringLayout.EAST, btnOpenOptionsDialog, 0, SpringLayout.EAST, btnChangeSearchPath);
		layout.putConstraint(SpringLayout.SOUTH, btnOpenOptionsDialog, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, btnChangeSearchPath);
		layout.putConstraint(SpringLayout.WEST, btnOpenOptionsDialog, 0, SpringLayout.WEST, btnChangeSearchPath);
		
		layout.putConstraint(SpringLayout.NORTH, btnStartSearchProcess, Util.GAP, SpringLayout.SOUTH, btnOpenOptionsDialog);
		layout.putConstraint(SpringLayout.EAST, btnStartSearchProcess, 0, SpringLayout.EAST, btnOpenOptionsDialog);
		layout.putConstraint(SpringLayout.SOUTH, btnStartSearchProcess, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, btnOpenOptionsDialog);
		layout.putConstraint(SpringLayout.WEST, btnStartSearchProcess, 0, SpringLayout.WEST, btnOpenOptionsDialog);

		layout.putConstraint(SpringLayout.NORTH, btnOpenFile, Util.GAP, SpringLayout.SOUTH, btnStartSearchProcess);
		layout.putConstraint(SpringLayout.EAST, btnOpenFile, 0, SpringLayout.EAST, btnStartSearchProcess);
		layout.putConstraint(SpringLayout.SOUTH, btnOpenFile, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, btnStartSearchProcess);
		layout.putConstraint(SpringLayout.WEST, btnOpenFile, 0, SpringLayout.WEST, btnStartSearchProcess);

		layout.putConstraint(SpringLayout.NORTH, btnOpenInExplorer, Util.GAP, SpringLayout.SOUTH, btnOpenFile);
		layout.putConstraint(SpringLayout.EAST, btnOpenInExplorer, 0, SpringLayout.EAST, btnOpenFile);
		layout.putConstraint(SpringLayout.SOUTH, btnOpenInExplorer, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, btnOpenFile);
		layout.putConstraint(SpringLayout.WEST, btnOpenInExplorer, 0, SpringLayout.WEST, btnOpenFile);
		
		layout.putConstraint(SpringLayout.NORTH, btnExitApplication, -(Util.HEIGHT + Util.GAP), SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, btnExitApplication, 0, SpringLayout.EAST, btnOpenFile);
		layout.putConstraint(SpringLayout.SOUTH, btnExitApplication, -Util.GAP, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, btnExitApplication, 0, SpringLayout.WEST, btnOpenFile);
		
		// text-field
		layout.putConstraint(SpringLayout.NORTH, tfSearchInPath, 0, SpringLayout.NORTH, lblSearchIn);
		layout.putConstraint(SpringLayout.EAST, tfSearchInPath, -Util.GAP, SpringLayout.WEST, btnChangeSearchPath);
		layout.putConstraint(SpringLayout.SOUTH, tfSearchInPath, 0, SpringLayout.SOUTH, lblSearchIn);
		layout.putConstraint(SpringLayout.WEST, tfSearchInPath, Util.GAP, SpringLayout.EAST, lblSearchIn);

		layout.putConstraint(SpringLayout.NORTH, tfSearchForString, 0, SpringLayout.NORTH, lblSearchFor);
		layout.putConstraint(SpringLayout.EAST, tfSearchForString, 0, SpringLayout.EAST, tfSearchInPath);
		layout.putConstraint(SpringLayout.SOUTH, tfSearchForString, 0, SpringLayout.SOUTH, lblSearchFor);
		layout.putConstraint(SpringLayout.WEST, tfSearchForString, 0, SpringLayout.WEST, tfSearchInPath);
		
		// list (scroll-pane)
		layout.putConstraint(SpringLayout.NORTH, spFilesFound, Util.GAP, SpringLayout.SOUTH, tfSearchForString);
		layout.putConstraint(SpringLayout.EAST, spFilesFound, 0, SpringLayout.EAST, tfSearchForString);
		layout.putConstraint(SpringLayout.SOUTH, spFilesFound, -Util.GAP, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, spFilesFound, 0, SpringLayout.WEST, lblSearchFor);
	}
	
	private void initFrame() {
		setIconImage(Application.APPLICATION_ICON);
		setTitle(appFrameTitle);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(innerFrameSize.width, innerFrameSize.height);
		setMinimumSize(innerFrameSize);
		setLocationRelativeTo(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Application.exitApplication();
			}
		});
		
		layout = new SpringLayout();
		
		contentPane = new JPanel();
		contentPane.setLayout(layout);
		setContentPane(contentPane);
	}

	private void initComponents() {
		// label
		lblSearchIn = new JLabel("Search in:");
		contentPane.add(lblSearchIn);
		
		lblSearchFor = new JLabel("Search for:");
		contentPane.add(lblSearchFor);
		
		// button
		btnChangeSearchPath = new JButton("Choose Path");
		btnChangeSearchPath.addActionListener(e -> openFileChooserDialog());
		contentPane.add(btnChangeSearchPath);
		
		btnOpenOptionsDialog = new JButton("Options");
		btnOpenOptionsDialog.addActionListener(e -> openOptionsDialog());
		contentPane.add(btnOpenOptionsDialog);

		btnStartSearchProcess = new JButton("Search");
		btnStartSearchProcess.addActionListener(e -> startSearchProcess());
		btnStartSearchProcess.setEnabled(false);
		contentPane.add(btnStartSearchProcess);
		
		btnOpenFile = new JButton("Open File");
		btnOpenFile.addActionListener(e -> Application.tryOpenSelectedListItem());
		btnOpenFile.setEnabled(false);
		contentPane.add(btnOpenFile);
		
		btnOpenInExplorer = new JButton("Open Path");
		btnOpenInExplorer.addActionListener(e -> Application.tryOpenInExplorer());
		btnOpenInExplorer.setEnabled(false);
		contentPane.add(btnOpenInExplorer);
		
		btnExitApplication = new JButton("Exit");
		btnExitApplication.addActionListener(e -> Application.exitApplication());
		contentPane.add(btnExitApplication);
		
		// text-field
		tfSearchInPath = new JTextField();
		tfSearchInPath.setText(Search.selectedSearchDirectory.getAbsolutePath());
		tfSearchInPath.setEditable(false);
		contentPane.add(tfSearchInPath);
		
		tfSearchForString = new JTextField();
		tfSearchForString.addKeyListener(new KeyAdapter() {	
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !Search.isSearching)
					startSearchProcess();
			}
		});
		contentPane.add(tfSearchForString);
		
		// list and scroll-pane
		listFilesFound = new JList<>();
		listFilesFound.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Application.tryOpenSelectedListItem();
				}
			}
		});
		listFilesFound.addPropertyChangeListener(evt -> {
            btnOpenFile.setEnabled(!listFilesFound.isSelectionEmpty());
            btnOpenInExplorer.setEnabled(!listFilesFound.isSelectionEmpty());
        });
		
		spFilesFound = new JScrollPane();
		spFilesFound.setViewportView(listFilesFound);
		contentPane.add(spFilesFound);
	}
	
	private void openFileChooserDialog() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(Search.selectedSearchDirectory);
		int option = fc.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			Search.selectedSearchDirectory = fc.getSelectedFile();
			fileChooserApprove();
		} else if (option != JFileChooser.CANCEL_OPTION) {
			fileChooserCancel();
		}
	}
	
	private void fileChooserApprove() {
		if (Search.selectedSearchDirectory.exists()) {				
			tfSearchInPath.setText(Search.selectedSearchDirectory.getAbsolutePath());
			btnStartSearchProcess.setEnabled(true);
			tfSearchForString.requestFocus();
		} else {
			Search.selectedSearchDirectory = null;
		}
	}
	
	private void fileChooserCancel() {
		btnStartSearchProcess.setEnabled(false);
		Search.selectedSearchDirectory = null;
		tfSearchInPath.setText("- Error selecting folder -");
		System.err.println("Error selecting folder.");
	}

	private void startSearchProcess() {
		Application.performSearch(tfSearchForString.getText());
	}
	
}
