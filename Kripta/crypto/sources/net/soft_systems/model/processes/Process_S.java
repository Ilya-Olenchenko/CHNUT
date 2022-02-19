package net.soft_systems.model.processes;
import net.soft_systems.model.base.*;
import net.soft_systems.integrator.*;
import net.soft_systems.crypto.algo.*;
import net.soft_systems.crypto.beans.process.*;
public class Process_S extends net.soft_systems.model.base.ProcessModelImpl 
{

RSA rsa = new RSA();                                                                                                 

		
        
public Process_S(ProcessBean processBean)
	 throws java.rmi.RemoteException
{

		super(processBean);
	        
}
public void onCreate()
{

            	logMessage("������� onCreate");
	        
            
	        
}
public void onDestroy()
{

	            logMessage("������� onDestroy");
	        
            
	        
}
public void onRecieve(String nodeId)
{
		
            	logMessage("������� onRecieve � ���� "+nodeId);
	        
long key[]=(long [])recv("key_in");
rsa.setOpenKey(key[0],key[1]);
logDataMessage("������� �������� ����","e="+key[0]+" n="+key[1]);                                                                                                                        
	        
}
public void do_send()
{

	        
long message=12345435;
logDataMessage("�������� �����",message);
long code = rsa.encode( message );
logDataMessage("������������� �����",code);
send("out",new Long(code));
                                                                                                                                                                                                                                                                                                                                                                                                                                     
	        
}
}
