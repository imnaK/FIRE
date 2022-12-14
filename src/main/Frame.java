package main;

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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import dialogs.OptionsDialog;

@SuppressWarnings("serial")
public class Frame extends JFrame {

	final String appFrameTitle = Application.APPLICATION_NAME + " - " + Application.VERSION;
	final Dimension innerFrameSize = new Dimension(Util.rem(50), Util.rem(35));
	
	SpringLayout layout;
	JPanel contentPane;
	JScrollPane spFilesFound;
	JList<String> listFilesFound;
	
	JTextField	tfSearchInPath,
				tfSearchForString;
	JLabel		lblSearchIn,
				lblSearchFor;
	JButton		btnChangeSearchPath,
				btnOpenOptionsDialog,
				btnSearchForFiles,
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
		
		layout.putConstraint(SpringLayout.NORTH, btnSearchForFiles, Util.GAP, SpringLayout.SOUTH, btnOpenOptionsDialog);
		layout.putConstraint(SpringLayout.EAST, btnSearchForFiles, 0, SpringLayout.EAST, btnOpenOptionsDialog);
		layout.putConstraint(SpringLayout.SOUTH, btnSearchForFiles, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, btnOpenOptionsDialog);
		layout.putConstraint(SpringLayout.WEST, btnSearchForFiles, 0, SpringLayout.WEST, btnOpenOptionsDialog);

		layout.putConstraint(SpringLayout.NORTH, btnOpenFile, Util.GAP, SpringLayout.SOUTH, btnSearchForFiles);
		layout.putConstraint(SpringLayout.EAST, btnOpenFile, 0, SpringLayout.EAST, btnSearchForFiles);
		layout.putConstraint(SpringLayout.SOUTH, btnOpenFile, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, btnSearchForFiles);
		layout.putConstraint(SpringLayout.WEST, btnOpenFile, 0, SpringLayout.WEST, btnSearchForFiles);

		layout.putConstraint(SpringLayout.NORTH, btnOpenInExplorer, Util.GAP, SpringLayout.SOUTH, btnOpenFile);
		layout.putConstraint(SpringLayout.EAST, btnOpenInExplorer, 0, SpringLayout.EAST, btnOpenFile);
		layout.putConstraint(SpringLayout.SOUTH, btnOpenInExplorer, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, btnOpenFile);
		layout.putConstraint(SpringLayout.WEST, btnOpenInExplorer, 0, SpringLayout.WEST, btnOpenFile);
		
		layout.putConstraint(SpringLayout.NORTH, btnExitApplication, -(Util.HEIGHT + Util.GAP), SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, btnExitApplication, 0, SpringLayout.EAST, btnOpenFile);
		layout.putConstraint(SpringLayout.SOUTH, btnExitApplication, -Util.GAP, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, btnExitApplication, 0, SpringLayout.WEST, btnOpenFile);
		
		// textfield
		layout.putConstraint(SpringLayout.NORTH, tfSearchInPath, 0, SpringLayout.NORTH, lblSearchIn);
		layout.putConstraint(SpringLayout.EAST, tfSearchInPath, -Util.GAP, SpringLayout.WEST, btnChangeSearchPath);
		layout.putConstraint(SpringLayout.SOUTH, tfSearchInPath, 0, SpringLayout.SOUTH, lblSearchIn);
		layout.putConstraint(SpringLayout.WEST, tfSearchInPath, Util.GAP, SpringLayout.EAST, lblSearchIn);

		layout.putConstraint(SpringLayout.NORTH, tfSearchForString, 0, SpringLayout.NORTH, lblSearchFor);
		layout.putConstraint(SpringLayout.EAST, tfSearchForString, 0, SpringLayout.EAST, tfSearchInPath);
		layout.putConstraint(SpringLayout.SOUTH, tfSearchForString, 0, SpringLayout.SOUTH, lblSearchFor);
		layout.putConstraint(SpringLayout.WEST, tfSearchForString, 0, SpringLayout.WEST, tfSearchInPath);
		
		// list (scrollpane)
		layout.putConstraint(SpringLayout.NORTH, spFilesFound, Util.GAP, SpringLayout.SOUTH, tfSearchForString);
		layout.putConstraint(SpringLayout.EAST, spFilesFound, 0, SpringLayout.EAST, tfSearchForString);
		layout.putConstraint(SpringLayout.SOUTH, spFilesFound, -Util.GAP, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, spFilesFound, 0, SpringLayout.WEST, tfSearchForString);
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
		btnChangeSearchPath = new JButton("Select");
		btnChangeSearchPath.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openFileChooserDialog();
			}
		});
		contentPane.add(btnChangeSearchPath);
		
		btnOpenOptionsDialog = new JButton("Options");
		btnOpenOptionsDialog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openOptionsDialog();
			}
		});
		contentPane.add(btnOpenOptionsDialog);

		btnSearchForFiles = new JButton("Seach");
		btnSearchForFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startSearchProcess();
			}
		});
		btnSearchForFiles.setEnabled(false);
		contentPane.add(btnSearchForFiles);
		
		btnOpenFile = new JButton("Open selected");
		btnOpenFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.tryOpenSelectedListItem(listFilesFound);
			}
		});
		btnOpenFile.setEnabled(false);
		contentPane.add(btnOpenFile);
		
		btnOpenInExplorer = new JButton("Open in Explorer");
		btnOpenInExplorer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.tryOpenInExplorer(new File(listFilesFound.getSelectedValue()));
			}
		});
		btnOpenInExplorer.setEnabled(false);
		contentPane.add(btnOpenInExplorer);
		
		btnExitApplication = new JButton("Exit");
		btnExitApplication.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Application.exitApplication();
			}
		});
		contentPane.add(btnExitApplication);
		
		// textfield
		tfSearchInPath = new JTextField();
		tfSearchInPath.setText(Application.selectedSearchDirectory.getAbsolutePath());
		tfSearchInPath.setEditable(false);
		contentPane.add(tfSearchInPath);
		
		tfSearchForString = new JTextField();
		tfSearchForString.addKeyListener(new KeyAdapter() {	
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && !Application.inSearchProcess)
					startSearchProcess();
			}
		});
		contentPane.add(tfSearchForString);
		
		// list and scrollpane
		listFilesFound = new JList<String>();
		listFilesFound.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Application.tryOpenSelectedListItem(listFilesFound);
				}
			}
		});
		listFilesFound.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				btnOpenFile.setEnabled(!listFilesFound.isSelectionEmpty());
				btnOpenInExplorer.setEnabled(!listFilesFound.isSelectionEmpty());
			}
		});
		
		spFilesFound = new JScrollPane();
		spFilesFound.setViewportView(listFilesFound);
		contentPane.add(spFilesFound);
	}
	
	private void openFileChooserDialog() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fc.setCurrentDirectory(Application.selectedSearchDirectory);
		int option = fc.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			Application.selectedSearchDirectory = fc.getSelectedFile();
			fileChooserApprove();
		} else if (option != JFileChooser.CANCEL_OPTION) {
			fileChooserCancel();
		}
	}
	
	private void fileChooserApprove() {
		if (Application.selectedSearchDirectory.exists()) {				
			tfSearchInPath.setText(Application.selectedSearchDirectory.getAbsolutePath());
			btnSearchForFiles.setEnabled(true);
			tfSearchForString.requestFocus();
		} else {
			Application.selectedSearchDirectory = null;
		}
	}
	
	private void fileChooserCancel() {
		btnSearchForFiles.setEnabled(false);
		Application.selectedSearchDirectory = null;
		tfSearchInPath.setText("- Error selecting folder -");
		System.err.println("Error selecting folder.");
	}

	private void startSearchProcess() {
		btnSearchForFiles.setEnabled(false);
		Application.performSearch(listFilesFound, tfSearchForString.getText());
		btnSearchForFiles.setEnabled(true);
		btnOpenFile.setEnabled(!listFilesFound.isSelectionEmpty());
		btnOpenInExplorer.setEnabled(!listFilesFound.isSelectionEmpty());
	}
	
}
