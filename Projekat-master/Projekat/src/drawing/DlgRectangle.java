package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Point;
import geometry.Rectangle;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUpperLeftX;
	private JTextField txtUpperLeftY;
	private JTextField txtHeight;
	private JTextField txtWidth;
	public boolean isOk;
	public Rectangle rect;
	private JButton btnInnerColor;
	private JButton btnOutlineColor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setTitle("Add or modify rectangle");
		setModal(true);
		setResizable(false);
		setLocationRelativeTo(null);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.PINK);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblUpperLeftX = new JLabel("UpperLeft X:");
			GridBagConstraints gbc_lblUpperLeftX = new GridBagConstraints();
			gbc_lblUpperLeftX.insets = new Insets(0, 0, 5, 5);
			gbc_lblUpperLeftX.gridx = 2;
			gbc_lblUpperLeftX.gridy = 1;
			contentPanel.add(lblUpperLeftX, gbc_lblUpperLeftX);
		}
		{
			JLabel lblUpperLeftY = new JLabel("UpperLeft Y:");
			GridBagConstraints gbc_lblUpperLeftY = new GridBagConstraints();
			gbc_lblUpperLeftY.insets = new Insets(0, 0, 5, 5);
			gbc_lblUpperLeftY.gridx = 8;
			gbc_lblUpperLeftY.gridy = 1;
			contentPanel.add(lblUpperLeftY, gbc_lblUpperLeftY);
		}
		{
			txtUpperLeftX = new JTextField();
			GridBagConstraints gbc_txtUpperLeftX = new GridBagConstraints();
			gbc_txtUpperLeftX.insets = new Insets(0, 0, 5, 5);
			gbc_txtUpperLeftX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftX.gridx = 2;
			gbc_txtUpperLeftX.gridy = 2;
			contentPanel.add(txtUpperLeftX, gbc_txtUpperLeftX);
			txtUpperLeftX.setColumns(10);
		}
		{
			txtUpperLeftY = new JTextField();
			GridBagConstraints gbc_txtUpperLeftY = new GridBagConstraints();
			gbc_txtUpperLeftY.insets = new Insets(0, 0, 5, 5);
			gbc_txtUpperLeftY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftY.gridx = 8;
			gbc_txtUpperLeftY.gridy = 2;
			contentPanel.add(txtUpperLeftY, gbc_txtUpperLeftY);
			txtUpperLeftY.setColumns(10);
		}
		{
			JLabel lblHeight = new JLabel("Height:");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.insets = new Insets(0, 0, 5, 5);
			gbc_lblHeight.gridx = 2;
			gbc_lblHeight.gridy = 4;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			JLabel lblWidth = new JLabel("Width:");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.insets = new Insets(0, 0, 5, 5);
			gbc_lblWidth.gridx = 8;
			gbc_lblWidth.gridy = 4;
			contentPanel.add(lblWidth, gbc_lblWidth);
		}
				{
					txtHeight = new JTextField();
					GridBagConstraints gbc_txtHeight = new GridBagConstraints();
					gbc_txtHeight.insets = new Insets(0, 0, 5, 5);
					gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtHeight.gridx = 2;
					gbc_txtHeight.gridy = 5;
					contentPanel.add(txtHeight, gbc_txtHeight);
					txtHeight.setColumns(10);
				}
				{
					txtWidth = new JTextField();
					GridBagConstraints gbc_txtWidth = new GridBagConstraints();
					gbc_txtWidth.insets = new Insets(0, 0, 5, 5);
					gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtWidth.gridx = 8;
					gbc_txtWidth.gridy = 5;
					contentPanel.add(txtWidth, gbc_txtWidth);
					txtWidth.setColumns(10);
				}

		btnOutlineColor = new JButton("Outline Color");
		btnOutlineColor.setForeground(Color.PINK);
		btnOutlineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color outlineColor = JColorChooser.showDialog(null, "Choose outline color",
						btnOutlineColor.getBackground());
				if (outlineColor != null)
					btnOutlineColor.setBackground(outlineColor);

			}
		});
		
		btnInnerColor = new JButton("Inner Color");
		btnInnerColor.setForeground(Color.PINK);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color innerColor = JColorChooser.showDialog(null, "Choose inner color!", btnInnerColor.getBackground());
				if (innerColor != null)
					btnInnerColor.setBackground(innerColor);
			}
		});
		
				btnInnerColor.setBackground(Color.BLACK);
				GridBagConstraints gbc_btnInnerColor = new GridBagConstraints();
				gbc_btnInnerColor.anchor = GridBagConstraints.SOUTH;
				gbc_btnInnerColor.insets = new Insets(0, 0, 5, 5);
				gbc_btnInnerColor.gridx = 5;
				gbc_btnInnerColor.gridy = 7;
				contentPanel.add(btnInnerColor, gbc_btnInnerColor);

		btnOutlineColor.setBackground(Color.BLACK);
		GridBagConstraints gbc_btnOutlineColor = new GridBagConstraints();
		gbc_btnOutlineColor.insets = new Insets(0, 0, 0, 5);
		gbc_btnOutlineColor.gridx = 5;
		gbc_btnOutlineColor.gridy = 8;
		contentPanel.add(btnOutlineColor, gbc_btnOutlineColor);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							if (txtUpperLeftX.getText().trim().isEmpty() || txtUpperLeftY.getText().trim().isEmpty()
									|| txtHeight.getText().trim().isEmpty() || txtWidth.getText().trim().isEmpty()) {
								setConfirm(false);
								JOptionPane.showMessageDialog(null, "All values are required!", "Error",
										JOptionPane.ERROR_MESSAGE);
							} else {
								if (Integer.parseInt(txtWidth.getText().toString()) <= 0
										|| Integer.parseInt(txtHeight.getText().toString()) <= 0
										|| Integer.parseInt(txtUpperLeftX.getText().toString()) < 0
										|| Integer.parseInt(txtUpperLeftY.getText().toString()) < 0) {
									JOptionPane.showMessageDialog(null, "Insert values greater then 0!", "Error",
											JOptionPane.ERROR_MESSAGE);

								} else {
									rect = new Rectangle(
											new Point(Integer.parseInt(getTxtUpperLeftX().getText().toString()),
													Integer.parseInt(getTxtUpperLeftY().getText().toString())),
											Integer.parseInt(getTxtWidth().getText().toString()),
											Integer.parseInt(getTxtHeight().getText().toString()), false,
											btnOutlineColor.getBackground(), btnInnerColor.getBackground());

									setConfirm(true);
									setVisible(false);
								}
							}
						} catch (NumberFormatException e1) {
							JOptionPane.showMessageDialog(null, "Enter numbers only!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public JTextField getTxtUpperLeftX() {
		return txtUpperLeftX;
	}

	public void setTxtUpperLeftX(JTextField txtUpperLeftX) {
		this.txtUpperLeftX = txtUpperLeftX;
	}

	public JTextField getTxtUpperLeftY() {
		return txtUpperLeftY;
	}

	public void setTxtUpperLeftY(JTextField txtUpperLeftY) {
		this.txtUpperLeftY = txtUpperLeftY;
	}

	public JTextField getTxtHeight() {
		return txtHeight;
	}

	public void setTxtHeight(JTextField txtHeight) {
		this.txtHeight = txtHeight;
	}

	public JTextField getTxtWidth() {
		return txtWidth;
	}

	public void setTxtWidth(JTextField txtWidth) {
		this.txtWidth = txtWidth;
	}

	public boolean isConfirm() {
		return isOk;
	}

	public void setConfirm(boolean isOk) {
		this.isOk = isOk;
	}

	public Rectangle getRect() {
		return rect;
	}

	public void setRect(Rectangle rect) {
		this.rect = rect;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public JButton getBtnOutlineColor() {
		return btnOutlineColor;
	}

	public void setBtnOutlineColor(JButton btnOutlineColor) {
		this.btnOutlineColor = btnOutlineColor;
	}


}
