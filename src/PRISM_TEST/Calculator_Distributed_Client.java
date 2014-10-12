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
import Prism.test.GUI;

public class Calculator_Distributed_Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String hostName = "localhost";
		int portNum = 2601;

		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold s = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		s.dispatcher = disp;
		s.scheduler = sched;
		Architecture arch = new Architecture("Demo1");
		arch.scaffold = s;

		Connector conn = new Connector("conn");
		conn.scaffold = s;

		ExtensiblePort ep = new ExtensiblePort("ep", PrismConstants.REQUEST);
		SocketDistribution sd = new SocketDistribution(ep);
		ep.addDistributionModule(sd);
		ep.scaffold = s;
		conn.addConnPort(ep);

		GUI gui = new GUI();
		Component b = new Component("GUI", gui);
		b.scaffold = s;
		arch.add(conn);
		arch.add(b);
		arch.add(ep);

		Port bRequestPort = new Port("bRequestPort", PrismConstants.REQUEST);
		b.addCompPort(bRequestPort);
		Port connReplyPort = new Port("connReplyPort", PrismConstants.REPLY);
		conn.addConnPort(connReplyPort);
		arch.weld(bRequestPort, connReplyPort);

		disp.start();
		arch.start();

		ep.connect(hostName, portNum);
	}

}
