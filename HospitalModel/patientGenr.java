package HospitalModel;
import simView.*;
import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import util.*;
import statistics.*;

public class patientGenr extends ViewableAtomic {
	protected int activeTime = 10;  
	protected int patientCount =0,emerCount=0;
	protected rand r;
	
	public patientGenr()
	{
		this("NormalPatient");
	}
	
	public patientGenr(String name)
	  {
		   super(name);
		   if(name.equals("NormalPatient"))
			   activeTime = 10;
		   else
			   activeTime= 20;
		   addInport("IN");
		   addOutport("OUT");
		}
	
	public void initialize()
	   {
		   holdIn("active", activeTime);
		   r = new rand(123456789);
		   if(name.equals("NormalPatient"))
		   patientCount=1;
		   else
		   emerCount=1;
		}
	
	public void  deltext(double e,message x)
	{
	   Continue(e);
	   for (int i=0; i< x.getLength();i++)
	   {
	     if (messageOnPort(x, "IN", i)) 
	     {
	       passivate();
	     }
	   }
	}
	
	public void  deltint()
	{	
	 if(phaseIs("active"))
	   {
	   if(name.equals("NormalPatient"))
		   		patientCount++; 
		   else
			  emerCount++;
	   holdIn("active", Math.ceil(activeTime+r.uniform(5)));
	  }
	else passivate();   
	} 
	
	public message  out( )
	{
	   message  m = new message();
	   content con;
	   System.out.println(name);
	   if(name.equals("NormalPatient"))
		   con = makeContent("OUT", new entity("NormalPatient"+Integer.toString(patientCount)));
	   else
		  con = makeContent("OUT", new entity("EmergencyPatient"+Integer.toString(emerCount)));
	   m.add(con);
	  return m;
	}
}