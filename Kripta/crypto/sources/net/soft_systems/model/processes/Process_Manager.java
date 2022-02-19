package net.soft_systems.model.processes;
import java.rmi.RemoteException;
import net.soft_systems.crypto.beans.process.ProcessBean;
import net.soft_systems.model.base.ProcessModelImpl;
public class Process_Manager extends ProcessModelImpl {
	public Process_Manager(ProcessBean processBean) throws RemoteException { super(processBean); }
	public void onCreate() { logMessage("������� onCreate"); }
	public void onDestroy() { logMessage("������� onDestroy"); }
	public void onRecieve(String nodeId) { logMessage("������� onRecieve � ���� " + nodeId); }
	public void chechRead() { send("out", "", "������"); }
	public void checkWrite() { send("out", "", "������"); }
}

