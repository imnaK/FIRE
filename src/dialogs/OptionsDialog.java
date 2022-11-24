package dialogs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.SpringLayout;

import main.Application;
import main.Util;

@SuppressWarnings("serial")
public class OptionsDialog extends Dialog {

	JLabel	lblHeadingSearchOptions,
			lblHeadingFilterOptions;
	JCheckBox	cbCaseSensitive,
				cbRegularExpression,
				cbDateFilter;
	JButton	btnOk,
			btnCancel;
	JComboBox<String>	coDateType;
	JSeparator seOptions;
	
	public OptionsDialog(JFrame owner) {
		super(owner, Application.APPLICATION_NAME + " | Options", Util.rem(18), Util.rem(24));
		
		initComponents();
		initLayoutConstraints();
		
		setVisible(true);
	}

	private void initLayoutConstraints() {
		// label
		layout.putConstraint(SpringLayout.NORTH, lblHeadingSearchOptions, Util.GAP, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, lblHeadingSearchOptions, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, lblHeadingSearchOptions, Util.HEIGHT + Util.GAP, SpringLayout.NORTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, lblHeadingSearchOptions, 0, SpringLayout.WEST, seOptions);

		layout.putConstraint(SpringLayout.NORTH, lblHeadingFilterOptions, Util.GAP, SpringLayout.NORTH, seOptions);
		layout.putConstraint(SpringLayout.EAST, lblHeadingFilterOptions, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, lblHeadingFilterOptions, Util.HEIGHT + Util.GAP, SpringLayout.NORTH, seOptions);
		layout.putConstraint(SpringLayout.WEST, lblHeadingFilterOptions, 0, SpringLayout.WEST, seOptions);
		
		//checkbox
		layout.putConstraint(SpringLayout.NORTH, cbCaseSensitive, Util.GAP, SpringLayout.SOUTH, lblHeadingSearchOptions);
		layout.putConstraint(SpringLayout.EAST, cbCaseSensitive, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, cbCaseSensitive, Util.HEIGHT, SpringLayout.SOUTH, lblHeadingSearchOptions);
		layout.putConstraint(SpringLayout.WEST, cbCaseSensitive, 0, SpringLayout.WEST, seOptions);

		layout.putConstraint(SpringLayout.NORTH, cbRegularExpression, Util.GAP, SpringLayout.SOUTH, cbCaseSensitive);
		layout.putConstraint(SpringLayout.EAST, cbRegularExpression, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, cbRegularExpression, Util.HEIGHT, SpringLayout.SOUTH, cbCaseSensitive);
		layout.putConstraint(SpringLayout.WEST, cbRegularExpression, 0, SpringLayout.WEST, seOptions);

		layout.putConstraint(SpringLayout.NORTH, cbDateFilter, Util.GAP, SpringLayout.SOUTH, lblHeadingFilterOptions);
		layout.putConstraint(SpringLayout.EAST, cbDateFilter, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, cbDateFilter, Util.HEIGHT, SpringLayout.SOUTH, lblHeadingFilterOptions);
		layout.putConstraint(SpringLayout.WEST, cbDateFilter, 0, SpringLayout.WEST, seOptions);
		
		// combobox
		layout.putConstraint(SpringLayout.NORTH, coDateType, Util.GAP, SpringLayout.SOUTH, cbDateFilter);
		layout.putConstraint(SpringLayout.EAST, coDateType, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, coDateType, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, cbDateFilter);
		layout.putConstraint(SpringLayout.WEST, coDateType, 0, SpringLayout.WEST, seOptions);
		
		// button
		layout.putConstraint(SpringLayout.NORTH, btnCancel, -(Util.HEIGHT + Util.GAP), SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.EAST, btnCancel, -Util.GAP, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, btnCancel, -Util.GAP, SpringLayout.SOUTH, contentPane);
		layout.putConstraint(SpringLayout.WEST, btnCancel, -(Util.WIDTH_SMALL + Util.GAP), SpringLayout.EAST, contentPane);

		layout.putConstraint(SpringLayout.NORTH, btnOk, 0, SpringLayout.NORTH, btnCancel);
		layout.putConstraint(SpringLayout.EAST, btnOk, -Util.GAP, SpringLayout.WEST, btnCancel);
		layout.putConstraint(SpringLayout.SOUTH, btnOk, 0, SpringLayout.SOUTH, btnCancel);
		layout.putConstraint(SpringLayout.WEST, btnOk, -(Util.WIDTH_SMALL + Util.GAP), SpringLayout.WEST, btnCancel);
		
		// separator
		layout.putConstraint(SpringLayout.NORTH, seOptions, Util.GAP, SpringLayout.SOUTH, cbRegularExpression);
		layout.putConstraint(SpringLayout.EAST, seOptions, -Util.GAP, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, seOptions, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, cbRegularExpression);
		layout.putConstraint(SpringLayout.WEST, seOptions, Util.GAP, SpringLayout.WEST, contentPane);
	}

	private void initComponents() {
		// label
		lblHeadingSearchOptions = new JLabel("Search Options");
		contentPane.add(lblHeadingSearchOptions);

		lblHeadingFilterOptions = new JLabel("Filter Options - (Currently disabled)");
		contentPane.add(lblHeadingFilterOptions);
		
		// checkbox
		cbCaseSensitive = new JCheckBox("Case Sensitive");
		cbCaseSensitive.setSelected(Application.caseSensitiveSearch);
		contentPane.add(cbCaseSensitive);
		
		cbRegularExpression = new JCheckBox("Regular Expression");
		cbRegularExpression.setSelected(Application.regularExpressionSearch);
		contentPane.add(cbRegularExpression);
		
		cbDateFilter = new JCheckBox("Date Filter");
		cbDateFilter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coDateType.setEnabled(cbDateFilter.isSelected());
			}
		});
		cbDateFilter.setEnabled(false);
		contentPane.add(cbDateFilter);
		
		// combobox
		String[] dateTypes = {"Date created", "Date modified", "Date accessed"};
		coDateType = new JComboBox<String>(dateTypes);
		coDateType.setEnabled(cbDateFilter.isSelected());
		contentPane.add(coDateType);
		
		// button
		btnOk = new JButton("Ok");
		btnOk.addActionListener((e) -> {
			optionOk();
		});
		contentPane.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener((e) -> {
			optionCancel();
		});
		contentPane.add(btnCancel);
		
		// separator
		seOptions = new JSeparator();
		contentPane.add(seOptions);
	}
	
	private void optionOk() {
		optionSave();
		closeDialog();
	}
	
	private void optionSave() {
		Application.caseSensitiveSearch = cbCaseSensitive.isSelected();
		Application.regularExpressionSearch = cbRegularExpression.isSelected();
	}
	
	private void optionCancel() {
		closeDialog();
	}
	
	private void closeDialog() {
		this.dispose();
	}

}
