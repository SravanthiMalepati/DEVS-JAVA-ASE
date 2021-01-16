package HospitalModel;
import simView.*;import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import util.*;
import statistics.*;

public class availableDoctors extends ViewableAtomic {
	protected int activeTime = 30;
	protected int count =0;
	protected String currPatient;
	entity message;
	public availableDoctors()
	{
		this("Doctor");
	}
	
	public availableDoctors(String name){
		   super(name);
		   addInport("IN");
		   addOutport("OUT");
		}
	
	public void initialize(){
		   holdIn("passive", INFINITY);
		   count++;
		}
	
	public void  deltext(double e,message x)
	{
		double rand = (Math.random()*10)+30;
	   Continue(e);
	   for (int i=0; i< x.getLength();i++){
		   message = x.getValOnPort("IN",i);
	     if (messageOnPort(x, "IN", i)) {
	    	 currPatient = message.getName();
	    	 holdIn("Active",rand);
	     }
	   }
	}
	
	public void  deltint( )
	{

	if(phaseIs("active")){
	   count++;
	   holdIn("passive", INFINITY);
	}
	else passivate();
	}
	
	public message  out( )
	{
	System.out.println(" OUT count "+count);
	   message  m = new message();
	   content con;
	   if(name.equals("DoctorOne"))
			 con = makeContent("OUT", new entity("DoctorOne"+currPatient));
	   else if(name.equals("DoctorTwo"))
		     con = makeContent("OUT", new entity("DoctorTwo"+currPatient));
	   else
		   con = makeContent("OUT", new entity("DoctorThree"+currPatient));
	   m.add(con);
	   return m;
	}
	
}