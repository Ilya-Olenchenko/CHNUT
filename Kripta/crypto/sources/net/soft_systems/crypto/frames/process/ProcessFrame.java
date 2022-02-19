package net.soft_systems.crypto.frames.process;
import java.awt.*;
import javax.swing.*;
import net.soft_systems.crypto.Run;
import net.soft_systems.crypto.base.CryptoBean;
import net.soft_systems.crypto.beans.process.ProcessBean;
import net.soft_systems.crypto.frames.CryptoEditFrame;
public class ProcessFrame extends CryptoEditFrame {
	public ProcessFrame(CryptoBean bean) { super(bean); }
	JCheckBox autoCreateCheckBox;
	JTextPane varsField;
	protected void initPropertiesPanel() {
		propertiesPanel = new JPanel();
		setBackground(propertiesPanel.getBackground());
		propertiesPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("��������"),
			BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		GridBagLayout layout = new GridBagLayout();
		propertiesPanel.setLayout(layout);
		GridBagConstraints c;
		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1;
		c.insets = new Insets(0, 10, 0, 0);
		propertiesPanel.add(new JLabel("�������������:"), c);
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 100;
		c.insets = new Insets(0, 5, 0, 10);
		idField = new JTextField(getBean().getId());
		propertiesPanel.add(idField, c);
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 100;
		c.insets = new Insets(0, 5, 0, 10);
		ProcessBean process = (ProcessBean)getBean();
		autoCreateCheckBox = new JCheckBox("��������� ��� �������", process.isAutoCreate());
		propertiesPanel.add(autoCreateCheckBox, c);
	}
	protected void setData() {
		ProcessBean editableBean = (ProcessBean)getBean();
		editableBean.setId(idField.getText());
		editableBean.setAutoCreate(autoCreateCheckBox.isSelected());
		editableBean.setVars(varsField.getText());
		Run.integrator.updateTreeEdit(editableBean);
	}
	protected void doOk() {
		setData();
		closeFrame();
	}
	protected void init() {
		ProcessBean process = (ProcessBean)getBean();
		initPropertiesPanel();
		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.NORTH;
		getContentPane().add(propertiesPanel, c);
		initButtonPanel();
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.NORTH;
		getContentPane().add(new JLabel("������� ������"), c);
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 100;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.SOUTH;
		varsField = new JTextPane();
		varsField.setContentType("text/plain");
		varsField.setEditable(true);
		varsField.setText(process.getVars());
		getContentPane().add(new JScrollPane(varsField), c);
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 0;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.SOUTH;
		getContentPane().add(buttonPanel, c);
	}
}

