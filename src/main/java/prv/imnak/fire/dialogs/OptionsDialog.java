package prv.imnak.fire.dialogs;

import prv.imnak.fire.Application;
import prv.imnak.fire.DateType;
import prv.imnak.fire.Search;
import prv.imnak.fire.Util;

import java.awt.Font;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class OptionsDialog extends Dialog {

	JLabel		lblHeadingSearchOptions,
				lblHeadingFilterOptions,
				lblFilterStartDate,
				lblFilterEndDate;
	JCheckBox	cbCaseSensitive,
				cbRegularExpression,
				cbShowFullPath,
				cbFilterByDate;
	JButton		btnOk,
				btnCancel;
	JComboBox<DateType>	coDateType;
	JTextField	tfFilterStartDate,
				tfFilterEndDate;
	JSeparator	seOptions;
	
	public OptionsDialog(JFrame owner) {
		super(owner, Application.APPLICATION_NAME + " - Options", Util.rem(18), Util.rem(29));
		
		initComponents();
		initLayoutConstraints();
		
		setResizable(false);
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
		
		layout.putConstraint(SpringLayout.NORTH, lblFilterStartDate, Util.GAP, SpringLayout.SOUTH, coDateType);
		layout.putConstraint(SpringLayout.EAST, lblFilterStartDate, Util.WIDTH_SMALL + Util.GAP, SpringLayout.WEST, coDateType);
		layout.putConstraint(SpringLayout.SOUTH, lblFilterStartDate, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, coDateType);
		layout.putConstraint(SpringLayout.WEST, lblFilterStartDate, 0, SpringLayout.WEST, coDateType);
		
		layout.putConstraint(SpringLayout.NORTH, lblFilterEndDate, Util.GAP, SpringLayout.SOUTH, lblFilterStartDate);
		layout.putConstraint(SpringLayout.EAST, lblFilterEndDate, 0, SpringLayout.EAST, lblFilterStartDate);
		layout.putConstraint(SpringLayout.SOUTH, lblFilterEndDate, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, lblFilterStartDate);
		layout.putConstraint(SpringLayout.WEST, lblFilterEndDate, 0, SpringLayout.WEST, lblFilterStartDate);
		
		//checkbox
		layout.putConstraint(SpringLayout.NORTH, cbCaseSensitive, Util.GAP, SpringLayout.SOUTH, lblHeadingSearchOptions);
		layout.putConstraint(SpringLayout.EAST, cbCaseSensitive, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, cbCaseSensitive, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, lblHeadingSearchOptions);
		layout.putConstraint(SpringLayout.WEST, cbCaseSensitive, 0, SpringLayout.WEST, seOptions);

		layout.putConstraint(SpringLayout.NORTH, cbRegularExpression, Util.GAP, SpringLayout.SOUTH, cbCaseSensitive);
		layout.putConstraint(SpringLayout.EAST, cbRegularExpression, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, cbRegularExpression, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, cbCaseSensitive);
		layout.putConstraint(SpringLayout.WEST, cbRegularExpression, 0, SpringLayout.WEST, seOptions);
		
		layout.putConstraint(SpringLayout.NORTH, cbShowFullPath, Util.GAP, SpringLayout.SOUTH, cbRegularExpression);
		layout.putConstraint(SpringLayout.EAST, cbShowFullPath, 0, SpringLayout.EAST, cbRegularExpression);
		layout.putConstraint(SpringLayout.SOUTH, cbShowFullPath, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, cbRegularExpression);
		layout.putConstraint(SpringLayout.WEST, cbShowFullPath, 0, SpringLayout.WEST, cbRegularExpression);

		layout.putConstraint(SpringLayout.NORTH, cbFilterByDate, Util.GAP, SpringLayout.SOUTH, lblHeadingFilterOptions);
		layout.putConstraint(SpringLayout.EAST, cbFilterByDate, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, cbFilterByDate, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, lblHeadingFilterOptions);
		layout.putConstraint(SpringLayout.WEST, cbFilterByDate, 0, SpringLayout.WEST, seOptions);
		
		// combo-box
		layout.putConstraint(SpringLayout.NORTH, coDateType, Util.GAP, SpringLayout.SOUTH, cbFilterByDate);
		layout.putConstraint(SpringLayout.EAST, coDateType, 0, SpringLayout.EAST, seOptions);
		layout.putConstraint(SpringLayout.SOUTH, coDateType, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, cbFilterByDate);
		layout.putConstraint(SpringLayout.WEST, coDateType, 0, SpringLayout.WEST, seOptions);
		
		// text-field
		layout.putConstraint(SpringLayout.NORTH, tfFilterStartDate, 0, SpringLayout.NORTH, lblFilterStartDate);
		layout.putConstraint(SpringLayout.EAST, tfFilterStartDate, 0, SpringLayout.EAST, coDateType);
		layout.putConstraint(SpringLayout.SOUTH, tfFilterStartDate, 0, SpringLayout.SOUTH, lblFilterStartDate);
		layout.putConstraint(SpringLayout.WEST, tfFilterStartDate, Util.GAP, SpringLayout.EAST, lblFilterStartDate);
		
		layout.putConstraint(SpringLayout.NORTH, tfFilterEndDate, 0, SpringLayout.NORTH, lblFilterEndDate);
		layout.putConstraint(SpringLayout.EAST, tfFilterEndDate, 0, SpringLayout.EAST, tfFilterStartDate);
		layout.putConstraint(SpringLayout.SOUTH, tfFilterEndDate, 0, SpringLayout.SOUTH, lblFilterEndDate);
		layout.putConstraint(SpringLayout.WEST, tfFilterEndDate, 0, SpringLayout.WEST, tfFilterStartDate);
		
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
		layout.putConstraint(SpringLayout.NORTH, seOptions, Util.GAP, SpringLayout.SOUTH, cbShowFullPath);
		layout.putConstraint(SpringLayout.EAST, seOptions, -Util.GAP, SpringLayout.EAST, contentPane);
		layout.putConstraint(SpringLayout.SOUTH, seOptions, Util.HEIGHT + Util.GAP, SpringLayout.SOUTH, cbShowFullPath);
		layout.putConstraint(SpringLayout.WEST, seOptions, Util.GAP, SpringLayout.WEST, contentPane);
	}

	private void initComponents() {
		// label
		lblHeadingSearchOptions = new JLabel("Search Options");
		lblHeadingSearchOptions.setHorizontalAlignment(JLabel.CENTER);
		lblHeadingSearchOptions.setFont(lblHeadingSearchOptions.getFont().deriveFont(Font.BOLD));
		contentPane.add(lblHeadingSearchOptions);

		lblHeadingFilterOptions = new JLabel("Filter Options");
		lblHeadingFilterOptions.setHorizontalAlignment(JLabel.CENTER);
		lblHeadingFilterOptions.setFont(lblHeadingFilterOptions.getFont().deriveFont(Font.BOLD));
		contentPane.add(lblHeadingFilterOptions);
		
		lblFilterStartDate = new JLabel("Start Date:");
		contentPane.add(lblFilterStartDate);
		
		lblFilterEndDate = new JLabel("End Date:");
		contentPane.add(lblFilterEndDate);
		
		// checkbox
		cbCaseSensitive = new JCheckBox("Case Sensitive");
		cbCaseSensitive.setSelected(Search.isCaseSensitiveSearch);
		contentPane.add(cbCaseSensitive);
		
		cbRegularExpression = new JCheckBox("Regular Expression");
		cbRegularExpression.setSelected(Search.isRegularExpressionSearch);
		contentPane.add(cbRegularExpression);
		
		cbShowFullPath = new JCheckBox("Show Full Path");
		cbShowFullPath.setSelected(Search.isShowFullPath);
		contentPane.add(cbShowFullPath);
		
		cbFilterByDate = new JCheckBox("Date Filter");
		cbFilterByDate.setSelected(Search.filterByDate);
		cbFilterByDate.addActionListener(e -> {
            coDateType.setEnabled(cbFilterByDate.isSelected());
            tfFilterStartDate.setEnabled(cbFilterByDate.isSelected());
            tfFilterEndDate.setEnabled(cbFilterByDate.isSelected());
        });
		contentPane.add(cbFilterByDate);
		
		// combo-box
		coDateType = new JComboBox<>(DateType.values());
		coDateType.setEnabled(cbFilterByDate.isSelected());
		contentPane.add(coDateType);

		// text-field
		tfFilterStartDate = new JTextField(Util.DATE_FORMAT.format(Search.filterStartDate));
		tfFilterStartDate.setEnabled(cbFilterByDate.isSelected());
		contentPane.add(tfFilterStartDate);
		
		tfFilterEndDate = new JTextField(Util.DATE_FORMAT.format(Search.filterEndDate));
		tfFilterEndDate.setEnabled(cbFilterByDate.isSelected());
		contentPane.add(tfFilterEndDate);
		
		// button
		btnOk = new JButton("Ok");
		btnOk.addActionListener(e -> optionOk());
		contentPane.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(e -> optionCancel());
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
		try {
			Search.filterStartDate = Util.DATE_FORMAT.parse(tfFilterStartDate.getText()).getTime();
			Search.filterEndDate = Util.DATE_FORMAT.parse(tfFilterEndDate.getText()).getTime();
			Search.dateType = (DateType) coDateType.getSelectedItem();
		} catch (ParseException ex) {
			cbFilterByDate.setSelected(false);
			//noinspection CallToPrintStackTrace
			ex.printStackTrace();
		}
		Search.isCaseSensitiveSearch = cbCaseSensitive.isSelected();
		Search.isRegularExpressionSearch = cbRegularExpression.isSelected();
		Search.isShowFullPath = cbShowFullPath.isSelected();
		Search.filterByDate = cbFilterByDate.isSelected();
		
		Application.loadListData();
	}
	
	private void optionCancel() {
		closeDialog();
	}
	
	private void closeDialog() {
		this.dispose();
	}

}
