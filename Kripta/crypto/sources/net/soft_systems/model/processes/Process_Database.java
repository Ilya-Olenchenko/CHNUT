package net.soft_systems.model.processes;
import java.rmi.RemoteException;
import net.soft_systems.crypto.beans.process.ProcessBean;
import net.soft_systems.model.base.ProcessModelImpl;
public class Process_Database extends ProcessModelImpl {
	public Process_Database(ProcessBean processBean) throws RemoteException { super(processBean); }
	public void onCreate() { logMessage("������� onCreate"); }
	public void onDestroy() { logMessage("������� onDestroy"); }
	public void onRecieve(String nodeId) { logMessage("������� onRecieve � ���� " + nodeId); }
}

