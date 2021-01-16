package HospitalModel;
import GenCol.DEVSQueue;
import GenCol.entity;
import genDevs.modeling.content;
import genDevs.modeling.message;
import simView.ViewableAtomic;
public class pharmacy extends ViewableAtomic 
{
	String IN = "IN", OUT = "OUT";
	String ACTIVE = "ACTIVE", OFF = "OFF", ON = "ON", BUSY = "BUSY";
	int totalPatients=0;
	entity message = null;
	String currPatient = "";
	public pharmacy() 
	{
		this("pharmacy");
	}

	public pharmacy(String name) 
	{
		super(name);
		addInport(IN);
		addOutport(OUT);
	}
	
	public void initialize() 
	{
		holdIn("passive", INFINITY);
	}
	
	public void deltext(double e, message x)
	{
		Continue(e);
		for (int i = 0; i < x.size(); i++)
		{
			if (messageOnPort(x, IN, i)) 
			{
				message = x.getValOnPort(IN, i);
				holdIn("active",0);
				totalPatients++;
				currPatient = message.getName();
			}
		}
	}
	
	public void deltint()
	{
		holdIn("active", INFINITY);
	}
	
	public message out() {
		message m = new message();
		m.add(makeContent(OUT, new entity(currPatient)));
		return m;
	}
	
	
	
}
