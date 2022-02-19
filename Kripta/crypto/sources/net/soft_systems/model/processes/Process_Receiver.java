package net.soft_systems.model.processes;
import net.soft_systems.model.base.*;
import net.soft_systems.integrator.*;
import net.soft_systems.crypto.algo.*;
import net.soft_systems.crypto.beans.process.*;
public class Process_Receiver extends net.soft_systems.model.base.ProcessModelImpl 
{

         

		
        
public Process_Receiver(ProcessBean processBean)
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
	        
DES crypter=new DES();
crypter.setIV(Binary.setFromHex("7836 ECD6 C5F0 37B6"));
crypter.setKey(Binary.setFromHex("9B58 086D 9BF9 CD96 C6EA 3381 B1B4 F637"));

byte data[]=(byte[])recv("����");
int iDataSize = data.length;

//DeCrypting
int iWholeTime = 0;
int iDataPtr = 0;
TimeUtil t=new TimeUtil();
while (iDataPtr < iDataSize) {
  byte data16[] = new byte[16];
  int iLocalI = 0, iLocalI2;
  while ((iLocalI < 16) && (iDataPtr < iDataSize)) {
    data16[iLocalI] = data[iDataPtr];
    iDataPtr++;
    iLocalI++;
  }

  t.start();
  byte code[]=crypter.decodeData(data16,crypter.MODE_CFB);
  t.finish();
  iWholeTime += t.millisec();

  iDataPtr -= iLocalI;
  for (int i = 0; i < iLocalI; i++) {
    data[iDataPtr] = code[i];
    iDataPtr++;
  }
}

logDataMessage("����� �������������",iWholeTime);
logDataMessage("�������������� �����",data);                                                                                                                                                
	        
}
}
