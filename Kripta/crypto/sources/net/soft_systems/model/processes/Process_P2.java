package net.soft_systems.model.processes;
import net.soft_systems.model.base.*;
import net.soft_systems.integrator.*;
import net.soft_systems.crypto.algo.*;
import net.soft_systems.crypto.beans.process.*;
public class Process_P2 extends net.soft_systems.model.base.ProcessModelImpl 
{

         

		
        
public Process_P2(ProcessBean processBean)
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
	        
            
	        
}
public void onReceive()
{

	        
byte key[]=Binary.setFromHex("9B58 086D 9BF9 CD96 C6EA 3381 B1B4 F637");
byte IV[]=Binary.setFromHex("7836 ECD6 C5F0 37B6");
IDEA idea=new IDEA();
idea.setIV(IV);
idea.setKey(key);
byte code[]=(byte[])recv("in");
TimeUtil t=new TimeUtil();
t.start();
byte data[]=idea.decodeData(code,idea.MODE_CBC);
t.finish();
logDataMessage("����� �������������",t.millisec());
logDataMessage("�������������� �����",data);
                        
	        
}
}
