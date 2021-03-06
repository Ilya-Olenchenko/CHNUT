/* Generated by Together */

package net.soft_systems.crypto.frames.structure;
import java.awt.*;
import javax.swing.*;
import net.soft_systems.crypto.base.CryptoBean;
import net.soft_systems.crypto.beans.structure.ProtectionBean;
import net.soft_systems.crypto.frames.*;
public class ProtectionEditFrame extends CryptoEditFrame {
	public ProtectionEditFrame(CryptoBean bean) { super(bean); }
	JTextField strengthField;
	private SingleRelationPanel relationsPanel;
	protected void initRelationsPanel() { relationsPanel = new SingleRelationPanel(this); }
	protected void doOk() {
		if (setStrength()) {
			setId();
			relationsPanel.setRelations();
			closeFrame();
		}
	}
	protected void init() {
		this.setSize(400, 500);
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
		initRelationsPanel();
		getContentPane().add(relationsPanel,
			new GridBagConstraints(GridBagConstraints.RELATIVE, GridBagConstraints.RELATIVE,
			GridBagConstraints.REMAINDER, GridBagConstraints.RELATIVE, 1.0, 100.0, GridBagConstraints.NORTH,
			GridBagConstraints.BOTH, new Insets(0, 5, 5, 5), 0, 0));
		initButtonPanel();
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
	protected boolean setStrength() {
		ProtectionBean protection = (ProtectionBean)getBean();
		try {
			double value = Double.valueOf(strengthField.getText()).doubleValue();
			if (value >= 0 && value <= 1) {
				protection.setStrength(value);
				return true;
			}
			else {
				JOptionPane.showInternalMessageDialog(this,
					"???????????????? ???????? ?????? ?????? ???? ? ????????? ?? 0 ?? 1 ????????????", "??????",
					JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		catch (NumberFormatException ex) {
			JOptionPane.showInternalMessageDialog(this, "???????????? ?????? ???????????????? ???????? ??????",
				"??????", JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	protected void initPropertiesPanel() {
		propertiesPanel = new JPanel();
		setBackground(propertiesPanel.getBackground());
		propertiesPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("????????"),
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
		propertiesPanel.add(new JLabel("?????????????:"), c);
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 100;
		c.insets = new Insets(0, 5, 0, 10);
		idField = new JTextField(getBean().getId());
		propertiesPanel.add(idField, c);
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1;
		c.insets = new Insets(5, 10, 0, 0);
		propertiesPanel.add(new JLabel("????????????????:"), c);
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 100;
		c.insets = new Insets(5, 5, 0, 10);
		ProtectionBean protection = (ProtectionBean)getBean();
		strengthField = new JTextField("" + protection.getStrength());
		propertiesPanel.add(strengthField, c);
	}
}

