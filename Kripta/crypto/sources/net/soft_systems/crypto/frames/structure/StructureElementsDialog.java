/* Generated by Together */

package net.soft_systems.crypto.frames.structure;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import javax.swing.*;
import javax.swing.event.*;
import net.soft_systems.crypto.Run;
import net.soft_systems.crypto.frames.*;
import net.soft_systems.integrator.*;
import net.soft_systems.integrator.event.BeanListener;
/**
 * ----------------------------------------------------------------------------------------------------------
 * Dialog for editing elements of cryptosystem
 * --------------------------------------------------------------------------------------------------------
 */
public class StructureElementsDialog extends BaseInternalFrame {
	/**
	 * ------------------------------------------------------------------------------------------------------
	 * Creates new form JDialog -----------------------------------------------------------------------------------------------------
	 */
	public StructureElementsDialog() {
		super(Run.integrator.messages.getMessage("elements"));
		initGUI();
	}
	/**
	 * ------------------------------------------------------------------------------------------------------
	 * This method is called from within the constructor to initialize the form.
	 * ----------------------------------------------------------------------------------------------------
	 */
	private void initGUI() {
		pack();
		setBounds(new Rectangle(0, 0, 400, 400));
		//setLocation(getParent().getX() + 100, getParent().getY() + 100);
		getContentPane().setLayout(new GridBagLayout());
		tabbedPane = new JTabbedPane();
		tabbedPane.setBorder(null);
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints c;
		//adding tabbed panel
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.RELATIVE;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 100;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.NORTH;
		getContentPane().add(tabbedPane, c);
		initPanels();
		JPanel buttonPanel = new JPanel();
		layout = new GridBagLayout();
		buttonPanel.setLayout(layout);
		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 100;
		buttonPanel.add(new JPanel(), c);
		// close button
		JButton button = new JButton(Run.integrator.messages.getMessage("close"));
		button.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) { closeDialog(); }
			});
		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1;
		c.insets = new Insets(5, 5, 5, 0);
		c.anchor = GridBagConstraints.EAST;
		buttonPanel.add(button, c);
		//previous button
		button = new JButton(Run.integrator.messages.getMessage("previous"));
		previousButton = button;
		button.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) { doPrevious(); }
			});
		c = new GridBagConstraints();
		c.gridwidth = 1;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1;
		c.insets = new Insets(5, 5, 5, 0);
		c.anchor = GridBagConstraints.EAST;
		buttonPanel.add(button, c);
		// next button
		button = new JButton(Run.integrator.messages.getMessage("next"));
		nextButton = button;
		button.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) { doNext(); }
			});
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 1;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		buttonPanel.add(button, c);
		// adding button panel
		c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		c.weighty = 1;
		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.SOUTH;
		getContentPane().add(buttonPanel, c);
		setBackground(buttonPanel.getBackground());
		tabbedPane.setTabPlacement(SwingConstants.LEFT);
		tabbedPane.addChangeListener(
			new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					updateNav();
					UpdatablePanel p = (UpdatablePanel)tabbedPane.getSelectedComponent();
					p.update();
				}
			});
		beanListener = new BeanListener() {
			public void addBean(Bean bean, Bean parent) {
				Debug.debug("UPDATE Add bean " + bean);
				try {
					UpdatablePanel p = (UpdatablePanel)tabbedPane.getSelectedComponent();
					if (p.getParentBean() == parent) { p.update(); }
				}
				catch (Exception ex)
					{ Debug.debug("Class must implement UpdatablePanel if placed into tabbedPane"); }
			}
			public void afterDelBean(String beanId, Bean parent, Class beanClass) {
				Debug.debug("UPDATE Del bean " + beanId);
				try {
					UpdatablePanel p = (UpdatablePanel)tabbedPane.getSelectedComponent();
					if (p.getParentBean() == parent) { p.update(); }
				}
				catch (Exception ex)
					{ Debug.debug("Class must implement UpdatablePanel if placed into tabbedPane"); }
			}
			public void delBean(Bean bean, Bean parent) { }
			public void beforeAddBean(Bean bean, Bean parent) { }
		};
		Run.integrator.addBeanListener(beanListener);
		addCloseListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) { Run.integrator.removeBeanListener(beanListener); }
			});
		updateNav();
	}
	BeanListener beanListener;
	protected void updateNav() {
		int index = tabbedPane.getSelectedIndex();
		nextButton.setEnabled(index < tabbedPane.getTabCount() - 1);
		previousButton.setEnabled(index > 0);
	}
	protected void doNext() {
		int index = tabbedPane.getSelectedIndex();
		if (index < tabbedPane.getTabCount() - 1) { index++; }
		tabbedPane.setSelectedIndex(index);
		updateNav();
	}
	protected void doPrevious() {
		int index = tabbedPane.getSelectedIndex();
		if (index > 0) { index--; }
		tabbedPane.setSelectedIndex(index);
		updateNav();
	}
	protected void initPanels() {
		tabbedPane.add(new ResourcesPanel(), Run.integrator.messages.getMessage("resource-name-mult"));
		tabbedPane.add(new SubjectsPanel(), Run.integrator.messages.getMessage("subject-name-mult"));
		tabbedPane.add(new ThreatsPanel(), Run.integrator.messages.getMessage("threat-name-mult"));
		tabbedPane.add(
			new VulnerabilitiesPanel(), Run.integrator.messages.getMessage("vulnerability-name-mult"));
		tabbedPane.add(
			new ElementsPanel((ParentDynamicBean)BeanUtil.getBeanById(Run.rootBean, "protections")),
			Run.integrator.messages.getMessage("protection-name-mult"));
		tabbedPane.add(
			new ViewElementsPanel(BeanUtil.getBeanById(Run.rootBean, "boundaries")),
			Run.integrator.messages.getMessage("boundary-name-mult"));
	}
	/**
	 * ------------------------------------------------------------------------------------------------------
	 * Closes the dialog -----------------------------------------------------------------------------------------------------
	 */
	private void closeDialog() {
		try { setClosed(true); }
		catch (PropertyVetoException ex) { ex.printStackTrace(); }
	}
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JButton nextButton;
	private JButton previousButton;
}

