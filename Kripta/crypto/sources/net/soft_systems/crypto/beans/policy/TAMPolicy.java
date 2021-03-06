/* Generated by Together */

package net.soft_systems.crypto.beans.policy;
import java.beans.PropertyVetoException;
import net.soft_systems.crypto.frames.policy.PolicyFrame;
import net.soft_systems.integrator.*;
public class TAMPolicy extends PolicyBean {
	public String getId() { return "tam"; }
	public void initNewPolicy() { }
	public String getName() { return TAMPolicy.getDefaultName(); }
	static public String getDefaultName() { return "?????????????? ??????? ???????"; }
	private EditFrame editFrame;
	public EditFrame getEditFrame() {
		if (editFrame == null) { editFrame = new PolicyFrame(this); }
		return editFrame;
	}
	public void closeEditFrame() {
		if (editFrame != null) {
			try { editFrame.setClosed(true); }
			catch (PropertyVetoException ex) { Debug.warning("PropertyVetoException :" + ex.getMessage()); }
			editFrame = null;
		}
	}
}

