package simple;

import Prism.core.AbstractImplementation;
import Prism.core.Architecture;
import Prism.core.Component;
import Prism.core.Connector;
import Prism.core.FIFOScheduler;
import Prism.core.Port;
import Prism.core.PrismConstants;
import Prism.core.RRobinDispatcher;
import Prism.core.Scaffold;

public class Caculator_Prism {

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

		// new components and connectors
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

		CalculatorGUI gui = new CalculatorGUI();
		Component guiComp = new Component("GUI", gui);
		guiComp.scaffold = scaffold;

		Connector conn1 = new Connector("conn1");
		conn1.scaffold = scaffold;

		// add components and connectors to arch
		arch.add(guiComp);
		arch.add(conn1);
		arch.add(addComp);
		arch.add(minusComp);
		arch.add(multiplyComp);
		arch.add(divideComp);

		// new ports and add ports to build arch
		Port addReplyPort = new Port("addReplyPort", PrismConstants.REPLY);
		addComp.addCompPort(addReplyPort);
		Port conn1RequestPort1 = new Port("conn1RequestPort1",
				PrismConstants.REQUEST);
		conn1.addConnPort(conn1RequestPort1);
		arch.weld(addReplyPort, conn1RequestPort1);

		Port minusReplyPort = new Port("minusReplyPort", PrismConstants.REPLY);
		minusComp.addCompPort(minusReplyPort);
		Port conn1RequestPort2 = new Port("conn1RequestPort2",
				PrismConstants.REQUEST);
		conn1.addConnPort(conn1RequestPort2);
		arch.weld(minusReplyPort, conn1RequestPort2);

		Port multiplyReplyPort = new Port("multiplyReplyPort",
				PrismConstants.REPLY);
		multiplyComp.addCompPort(multiplyReplyPort);
		Port conn1RequestPort3 = new Port("conn1RequestPort3",
				PrismConstants.REQUEST);
		conn1.addConnPort(conn1RequestPort3);
		arch.weld(multiplyReplyPort, conn1RequestPort3);

		Port divideReplyPort = new Port("divideReplyPort", 
				PrismConstants.REPLY);
		divideComp.addCompPort(divideReplyPort);
		Port conn1RequestPort4 = new Port("conn1RequestPort4",
				PrismConstants.REQUEST);
		conn1.addConnPort(conn1RequestPort4);
		arch.weld(divideReplyPort, conn1RequestPort4);

		Port guiRequestPort = new Port("guiRequestPort", PrismConstants.REQUEST);
		guiComp.addCompPort(guiRequestPort);
		Port conn1ReplyPort1 = new Port("conn1ReplyPort1", PrismConstants.REPLY);
		conn1.addConnPort(conn1ReplyPort1);
		arch.weld(guiRequestPort, conn1ReplyPort1);

		disp.start();
		arch.start();
	}

}
