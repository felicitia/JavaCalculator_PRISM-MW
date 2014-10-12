package client.distributed;

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

public class Client_Prism {

	private static int PORT_NUM = 5000;
	private static String HOST_NAME = "localhost"; 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// initialize arch
		FIFOScheduler sched = new FIFOScheduler(100);
		Scaffold scaffold = new Scaffold();
		RRobinDispatcher disp = new RRobinDispatcher(sched, 10);
		scaffold.dispatcher = disp;
		scaffold.scheduler = sched;
		Architecture arch = new Architecture("Caculator_Prism");
		arch.scaffold = scaffold;

		//add components and connectors
		CalculatorGUI gui = new CalculatorGUI();
		Component guiComp = new Component("GUI", gui);
		guiComp.scaffold = scaffold;

		Connector conn = new Connector("conn");
		conn.scaffold = scaffold;
		
		// add connectors and components to arch
		arch.add(guiComp);
		arch.add(conn);
		
		// new ports and build arch
		Port guiRequestPort = new Port("guiRequestPort", PrismConstants.REQUEST);
		guiComp.addCompPort(guiRequestPort);
		Port connReplyPort = new Port("connReplyPort1", PrismConstants.REPLY);
		conn.addConnPort(connReplyPort);
		arch.weld(guiRequestPort, connReplyPort);
		
		// add extensible ports
		ExtensiblePort ep = new ExtensiblePort("ep", PrismConstants.REQUEST);
		SocketDistribution sd = new SocketDistribution(ep);
		ep.addDistributionModule(sd);
		ep.scaffold = scaffold;
		conn.addConnPort(ep);
		
		disp.start();
		arch.start();
		
		ep.connect(HOST_NAME, PORT_NUM);
	}

}
