package PRISM_TEST;

import Prism.core.AbstractImplementation;
import Prism.core.Architecture;
import Prism.core.Component;
import Prism.core.Connector;
import Prism.core.FIFOScheduler;
import Prism.core.Port;
import Prism.core.PrismConstants;
import Prism.core.RRobinDispatcher;
import Prism.core.Scaffold;
import Prism.test.Addition;
import Prism.test.GUI;
import Prism.test.Subtract;

public class Caculator_Prism {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FIFOScheduler sched = new FIFOScheduler(100);

		Scaffold s = new Scaffold();

		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher=disp;
		s.scheduler=sched;

		Architecture arch = new Architecture("Demo");
		arch.scaffold=s;

		AbstractImplementation addition = new Addition();			
		Component t = new Component("add", addition);			
		t.scaffold=s;
	
		AbstractImplementation subtract = new Subtract();
		Component sub = new Component("Sub", subtract);
		sub.scaffold=s;
		
 		GUI gui = new GUI();
 		Component b = new Component("GUI", gui);
		b.scaffold=s;

		Connector conn1 = new Connector("conn1");
		conn1.scaffold =s;
		
		arch.add(b);
		arch.add(conn1);
		arch.add(t);
		arch.add(sub);

		Port subReplyPort = new Port("subReplyPort", PrismConstants.REPLY);
		sub.addCompPort (subReplyPort);
		Port conn1RequestPort1 = new Port("conn1RequestPort1", 				PrismConstants.REQUEST);
		conn1.addConnPort(conn1RequestPort1);
		arch.weld(subReplyPort, conn1RequestPort1);

		Port tReplyPort = new Port("tReplyPort", PrismConstants.REPLY);
		t.addCompPort(tReplyPort);
		Port conn1RequestPort2 = new Port("conn1RequestPort2", 				PrismConstants.REQUEST);
		conn1.addConnPort(conn1RequestPort2);
		arch.weld(tReplyPort, conn1RequestPort2);
		
		Port bRequestPort = new Port ("bRequestPort", PrismConstants.REQUEST);
		b.addCompPort(bRequestPort);
		Port conn1ReplyPort1 = new Port("conn1ReplyPort1", PrismConstants.REPLY);
		conn1.addConnPort(conn1ReplyPort1);
		arch.weld(bRequestPort, conn1ReplyPort1);
               
		disp.start();			
		arch.start();		                     
	}

}
