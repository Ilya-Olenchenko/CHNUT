/* Generated by Together */

package net.soft_systems.crypto.frames.policy;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import net.soft_systems.crypto.Run;
import net.soft_systems.crypto.beans.policy.RightMatrixBean;
import net.soft_systems.integrator.*;
public class RightMatrixFrame extends EditFrame {
	public RightMatrixFrame(Bean bean) {
		super(bean);
		init();
	}
	JTextField nameField;
	JTable table = new JTable();
	JLabel titleLabel = new JLabel();
	public void init() {
		GridBagLayout layout = new GridBagLayout();
		layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		titleLabel.setText("����� �������");
		setSize(500, 600);
		//titleLabel
		getContentPane().add(titleLabel,
			new GridBagConstraints(0, 0, GridBagConstraints.REMAINDER, 1, 1.0, 0.0, GridBagConstraints.NORTH,
			GridBagConstraints.HORIZONTAL, new Insets(5, 5, 0, 5), 0, 0));
		// tableScroll
		JScrollPane tableScroll = new JScrollPane(table);
		//tableScroll.setMinimumSize(new java.awt.Dimension(300, 150));
		//tableScroll.setPreferredSize(new java.awt.Dimension(300, 150));
		getContentPane().add(tableScroll,
			new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
			GridBagConstraints.REMAINDER, 1, 1.0, 100.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
			new Insets(5, 5, 5, 5), 0, 0));
		JPanel buttonPanel = getButtonPanel();
		// adding button panel
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.SOUTH;
		getContentPane().add(buttonPanel, c);
		setBackground(buttonPanel.getBackground());
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.sizeColumnsToFit(JTable.AUTO_RESIZE_OFF);
		table.setModel(getTableModel());
		table.addMouseListener(
			new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() > 1) { editCell(); }
				}
			});
		updateData();
	}
	protected void editCell() {
		int col = table.getSelectedColumn() - 1;
		int row = table.getSelectedRow();
		Bean object, subject;
		if (col >= 0 && row >= 0) {
			RightMatrixBean rightMatrixBean = getMatrixBean();
			object = (Bean)rightMatrixBean.getObjects().elementAt(col);
			subject = (Bean)rightMatrixBean.getSubjects().elementAt(row);
			RightEditor editor = new RightEditor("����� ������� �������� " + subject + " � ������� " + object,
				rightMatrixBean.getRights(row, col), Run.infoSystem.getRights());
			Run.integrator.addFrameToDesktop(editor);
		}
	}
	protected JPanel getButtonPanel() {
		JPanel buttonPanel = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		buttonPanel.setLayout(layout);
		JButton button;
		button = new JButton("�������");
		button.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) { doCancel(); }
			});
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		buttonPanel.add(button, c);
		return buttonPanel;
	}
	protected void doCancel() { closeFrame(); }
	public void updateData() {
		AbstractTableModel model = (AbstractTableModel)table.getModel();
		model.fireTableDataChanged();
	}
	public RightMatrixBean getMatrixBean() { return (RightMatrixBean)getBean(); }
	public TableModel getTableModel() {
		return new AbstractTableModel() {
			public String getColumnName(int column) {
				if (column == 0) { return ""; }
				else {
					RightMatrixBean rightMatrixBean = getMatrixBean();
					return rightMatrixBean.getObjects().elementAt(column - 1).toString();
				}
			}
			public String getRowName(int row) {
				RightMatrixBean rightMatrixBean = getMatrixBean();
				return rightMatrixBean.getSubjects().elementAt(row).toString();
			}
			public int getRowCount() {
				RightMatrixBean rightMatrixBean = getMatrixBean();
				return rightMatrixBean.getSubjects().size();
			}
			public int getColumnCount() {
				RightMatrixBean rightMatrixBean = getMatrixBean();
				return rightMatrixBean.getObjects().size() + 1;
			}
			public Object getValueAt(int row, int column) {
				if (column == 0) { return getRowName(row); }
				else {
					RightMatrixBean rightMatrixBean = getMatrixBean();
					return rightMatrixBean.getRightsString(row, column - 1);
				}
			}
			public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) { }
		};
	}
}

