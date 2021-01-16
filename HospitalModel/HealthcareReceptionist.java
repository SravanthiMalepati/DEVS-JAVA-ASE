package HospitalModel;
import simView.*;
import java.lang.*;
import java.util.LinkedList;

import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import util.*;
import statistics.*;


public class HealthcareReceptionist extends ViewableAtomic {
	protected int activeTime = 10;
	protected int count =0;
	protected LinkedList<String> list = new LinkedList<>();
	entity message = null;
	protected boolean docOne = true, docTwo = true, docThree = true;
	String outOne = "1OUT", outTwo = "2OUT", outThree = "3OUT";
	public HealthcareReceptionist()
	{
		this("Receptionist");
	}
	
	public HealthcareReceptionist(String name){
		   super(name);
		   addInport("IN");
		   addOutport("1OUT");
		   addOutport("2OUT");
		   addOutport("3OUT");
		}
	
	public void initialize(){
		   holdIn("active", INFINITY);
		   count++;
		}
	
	public boolean isDoctorFree()
	{
		if(docOne || docTwo || docThree)
			return true;
		return false;
	}
	
	public void  deltext(double e,message x)
	{
	   Continue(e);
	   for (int i=0; i< x.getLength();i++){
	     if (messageOnPort(x, "IN", i)) {
	    	 message = x.getValOnPort("IN", i);
	    	 String temp = message.getName();
	    	 if(temp.indexOf("NormalPatient") == 0)
	    	 {
	    		 System.out.println("NormalPatient");
	    		 list.addLast(temp);
	    		 if(phaseIs("active") && isDoctorFree())
	    			 holdIn("docFree",0);
	    		 else
	    			 holdIn("active",INFINITY);
	    	 }
	    		 
	    	 else if(temp.indexOf("EmergencyPatient") == 0)
	    	 {
	    		 System.out.println("Emergency");
	    		 list.addFirst(temp);
	    		 if(phaseIs("active") && isDoctorFree())
	    			 holdIn("docFree",0);
	    		 else
	    			 holdIn("active",INFINITY);
	    	 }
	    	 else
	    	 {
	    		 
	    		 if(temp.indexOf("DoctorOne")==0)
	    		 {
	    			 System.out.println("doctorone");
	    			 docOne = true;
	    		 }
		    		 
		    	 else if(temp.indexOf("DoctorTwo")==0)
		    	 {
		    		 System.out.println("doctortwo");
		    		 docTwo = true;
		    	 }
		    		
		    	 else
		    	 {
		    		 System.out.println("doctorthree");
		    		 docThree = true;
		    	 }
		    	 if(list.size()>0)
	    		 holdIn("docFree", 0);
		    	 else
		    		 holdIn("active",INFINITY);
	    	 }
	    	 System.out.println(list);
	     }
	   }
	}
	
	public void  deltint( )
	{

	if(phaseIs("docFree")){
	   holdIn("active",INFINITY);
	}
	else passivate();
	}
	
	public message  out( )
	{
	   message  m = new message();
	   content con;
	   System.out.println("docOne "+docOne);
	   if(docOne)
	   {
		   docOne = false;
		   System.out.print("reaching here");
		   con = makeContent(outOne, new entity(list.pop()));
	   }
	   else if(docTwo)
	   {
		   docTwo = false;
		   con = makeContent(outTwo, new entity(list.pop()));
	   }
	   else
	   {
		   docThree = false;
		   con = makeContent(outThree, new entity(list.pop()));
	   }
	   m.add(con);
	   return m;
	}
	
}