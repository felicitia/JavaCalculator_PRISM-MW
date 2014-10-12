package server.distributed;

import Prism.core.*;

public class MinusClass extends AbstractImplementation {
	public void handle(Event e) {
		if (e.name.equals("minus")) {
			String num1String = (String) e.getParameter("num1");
			String num2String = (String) e.getParameter("num2");

			Double num1 = (Double.parseDouble(num1String));
			Double num2 = (Double.parseDouble(num2String));
			Double result = num1 - num2;

			Event n = new Event("Result");
			n.addParameter("result", new Double(result));
			n.eventType = PrismConstants.REPLY;
			send(n);
		}
	}
}
