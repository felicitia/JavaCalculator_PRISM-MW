package PRISM_TEST;

import Prism.core.Architecture;
import Prism.core.Component;
import Prism.core.Connector;
import Prism.core.FIFOScheduler;
import Prism.core.Port;
import Prism.core.PrismConstants;
import Prism.core.RRobinDispatcher;
import Prism.core.Scaffold;
import Prism.extensions.port.ExtensiblePort;
import Prism.extensions.port.distribution.SocketDistribution;
import Prism.test.Addition;

public class Calculator_Distributed_Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int portNum = 2601;
		
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold s = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher=disp;
		s.scheduler=sched;	
		Architecture arch = new Architecture("Demo");
		arch.scaffold=s;	
		
		Connector conn=new Connector("conn");
		conn.scaffold=s;

		ExtensiblePort ep = new ExtensiblePort("ep", PrismConstants.REPLY);	
		SocketDistribution sd=new SocketDistribution(ep, portNum);
		ep.addDistributionModule(sd);		
		ep.scaffold = s;
		conn.addConnPort(ep);
		
		Addition addition = new Addition();
		Component t = new Component("Add", addition);
		t.scaffold=s;
		
		arch.add(conn);
		arch.add(t);
		arch.add(ep);
		
		Port tReplyPort = new Port ("tReplyPort", PrismConstants.REPLY);
		t.addCompPort(tReplyPort);
		Port connRequestPort = new Port("connRequestPort", PrismConstants.REQUEST);
		conn.addConnPort(connRequestPort);
		arch.weld(tReplyPort, connRequestPort);
		
		disp.start();
		arch.start();
	}

}
