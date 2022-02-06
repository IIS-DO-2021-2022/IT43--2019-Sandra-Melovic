package mvc;

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
		System.out.println("jeste");
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
			gbc_lblUpperLeftX.insets = new Insets(20, 70, 5, 5);
			gbc_lblUpperLeftX.gridx = 2;
			gbc_lblUpperLeftX.gridy = 1;
			contentPanel.add(lblUpperLeftX, gbc_lblUpperLeftX);
		}
		{
			JLabel lblUpperLeftY = new JLabel("UpperLeft Y:");
			GridBagConstraints gbc_lblUpperLeftY = new GridBagConstraints();
			gbc_lblUpperLeftY.insets = new Insets(20, 70, 5, 5);
			gbc_lblUpperLeftY.gridx = 8;
			gbc_lblUpperLeftY.gridy = 1;
			contentPanel.add(lblUpperLeftY, gbc_lblUpperLeftY);
		}
		{
			txtUpperLeftX = new JTextField();
			GridBagConstraints gbc_txtUpperLeftX = new GridBagConstraints();
			gbc_txtUpperLeftX.insets = new Insets(0, 70, 5, 5);
			gbc_txtUpperLeftX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftX.gridx = 2;
			gbc_txtUpperLeftX.gridy = 2;
			contentPanel.add(txtUpperLeftX, gbc_txtUpperLeftX);
			txtUpperLeftX.setColumns(10);
		}
		{
			txtUpperLeftY = new JTextField();
			GridBagConstraints gbc_txtUpperLeftY = new GridBagConstraints();
			gbc_txtUpperLeftY.insets = new Insets(0, 70, 5, 5);
			gbc_txtUpperLeftY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtUpperLeftY.gridx = 8;
			gbc_txtUpperLeftY.gridy = 2;
			contentPanel.add(txtUpperLeftY, gbc_txtUpperLeftY);
			txtUpperLeftY.setColumns(10);
		}
		{
			JLabel lblHeight = new JLabel("Height:");
			GridBagConstraints gbc_lblHeight = new GridBagConstraints();
			gbc_lblHeight.insets = new Insets(20, 70, 5, 5);
			gbc_lblHeight.gridx = 2;
			gbc_lblHeight.gridy = 4;
			contentPanel.add(lblHeight, gbc_lblHeight);
		}
		{
			JLabel lblWidth = new JLabel("Width:");
			GridBagConstraints gbc_lblWidth = new GridBagConstraints();
			gbc_lblWidth.insets = new Insets(20, 70, 5, 5);
			gbc_lblWidth.gridx = 8;
			gbc_lblWidth.gridy = 4;
			contentPanel.add(lblWidth, gbc_lblWidth);
		}
				{
					txtHeight = new JTextField();
					GridBagConstraints gbc_txtHeight = new GridBagConstraints();
					gbc_txtHeight.insets = new Insets(0, 70, 5, 5);
					gbc_txtHeight.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtHeight.gridx = 2;
					gbc_txtHeight.gridy = 5;
					contentPanel.add(txtHeight, gbc_txtHeight);
					txtHeight.setColumns(10);
				}
				{
					txtWidth = new JTextField();
					GridBagConstraints gbc_txtWidth = new GridBagConstraints();
					gbc_txtWidth.insets = new Insets(0, 70, 5, 5);
					gbc_txtWidth.fill = GridBagConstraints.HORIZONTAL;
					gbc_txtWidth.gridx = 8;
					gbc_txtWidth.gridy = 5;
					contentPanel.add(txtWidth, gbc_txtWidth);
					txtWidth.setColumns(10);
				}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setConfirm(true);
						setVisible(false);
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


}
