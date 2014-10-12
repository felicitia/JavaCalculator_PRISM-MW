package server.distributed;

import Prism.core.AbstractImplementation;
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

public class Server_Prism {

	private static int PORT_NUM = 5000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// initialize arch
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold scaffold = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		scaffold.dispatcher = disp;
		scaffold.scheduler = sched;
		Architecture arch = new Architecture("Server_Prism");
		arch.scaffold = scaffold;

		// new components
		AbstractImplementation addClass = new AddClass();
		Component addComp = new Component("add", addClass);
		addComp.scaffold = scaffold;

		AbstractImplementation minusClass = new MinusClass();
		Component minusComp = new Component("minus", minusClass);
		minusComp.scaffold = scaffold;

		AbstractImplementation multiplyClass = new MultiplyClass();
		Component multiplyComp = new Component("multiply", multiplyClass);
		multiplyComp.scaffold = scaffold;

		AbstractImplementation divideClass = new DivideClass();
		Component divideComp = new Component("divide", divideClass);
		divideComp.scaffold = scaffold;

		// new connector
		Connector conn = new Connector("conn");
		conn.scaffold = scaffold;

		// add components and connectors to arch
		arch.add(conn);
		arch.add(addComp);
		arch.add(minusComp);
		arch.add(multiplyComp);
		arch.add(divideComp);

		// new ports and build arch
		Port addReplyPort = new Port("addReplyPort", PrismConstants.REPLY);
		addComp.addCompPort(addReplyPort);
		Port conn1RequestPort1 = new Port("conn1RequestPort1",
				PrismConstants.REQUEST);
		conn.addConnPort(conn1RequestPort1);
		arch.weld(addReplyPort, conn1RequestPort1);

		Port minusReplyPort = new Port("minusReplyPort", PrismConstants.REPLY);
		minusComp.addCompPort(minusReplyPort);
		Port conn1RequestPort2 = new Port("conn1RequestPort2",
				PrismConstants.REQUEST);
		conn.addConnPort(conn1RequestPort2);
		arch.weld(minusReplyPort, conn1RequestPort2);

		Port multiplyReplyPort = new Port("multiplyReplyPort",
				PrismConstants.REPLY);
		multiplyComp.addCompPort(multiplyReplyPort);
		Port conn1RequestPort3 = new Port("conn1RequestPort3",
				PrismConstants.REQUEST);
		conn.addConnPort(conn1RequestPort3);
		arch.weld(multiplyReplyPort, conn1RequestPort3);

		Port divideReplyPort = new Port("divideReplyPort", PrismConstants.REPLY);
		divideComp.addCompPort(divideReplyPort);
		Port conn1RequestPort4 = new Port("conn1RequestPort4",
				PrismConstants.REQUEST);
		conn.addConnPort(conn1RequestPort4);
		arch.weld(divideReplyPort, conn1RequestPort4);

		// new extensible port
		ExtensiblePort ep = new ExtensiblePort("ep", PrismConstants.REPLY);
		SocketDistribution sd = new SocketDistribution(ep, PORT_NUM);
		ep.addDistributionModule(sd);
		ep.scaffold = scaffold;
		conn.addConnPort(ep);
		arch.add(ep);
		
		disp.start();
		arch.start();
	}

}
