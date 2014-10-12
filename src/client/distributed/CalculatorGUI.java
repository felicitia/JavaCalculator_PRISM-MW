package client.distributed;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Prism.core.AbstractImplementation;
import Prism.core.PrismConstants;

public class CalculatorGUI extends AbstractImplementation {

	MainPanel mainPanel;

	public CalculatorGUI() {
	}

	public void start() {
		// Create and set up the window.
		JFrame frame = new JFrame();
		mainPanel = new MainPanel(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		// set the frame in the center of the screen
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		int screenWidth = (int) screenSize.width;
		int screenHeight = (int) screenSize.height;
		// System.out.println(screenWidth+"\n"+screenHeight+"\n"+frameWidth+"\n"+frameHeight);
		frame.setLocation(screenWidth / 2 - frameWidth / 2, screenHeight / 2
				- frameHeight / 2);

		frame.add(mainPanel);
		// Display the window.
		frame.setVisible(true);
	}

	public void addNumbers(String numValue1, String numValue2) {

		Prism.core.Event e = new Prism.core.Event("add");
		e.eventType = PrismConstants.REQUEST;
		e.addParameter("num1", numValue1);
		e.addParameter("num2", numValue2);
		send(e);
	}

	public void subNumbers(String num1, String num2) {
		Prism.core.Event e = new Prism.core.Event("minus");
		e.eventType = PrismConstants.REQUEST;
		e.addParameter("num1", num1);
		e.addParameter("num2", num2);
		send(e);
	}

	public void multiplyNumbers(String num1, String num2) {
		Prism.core.Event e = new Prism.core.Event("multiply");
		e.eventType = PrismConstants.REQUEST;
		e.addParameter("num1", num1);
		e.addParameter("num2", num2);
		send(e);
	}

	public void divideNumbers(String num1, String num2) {
		Prism.core.Event e = new Prism.core.Event("divide");
		e.eventType = PrismConstants.REQUEST;
		e.addParameter("num1", num1);
		e.addParameter("num2", num2);
		send(e);
	}

	public void handle(Prism.core.Event e) {
		if (e.name.equals("Result")) {
			Double result = ((Double) e.getParameter("result")).doubleValue();
			mainPanel.setResult(result);
		}
	}

	class MainPanel extends JPanel implements ActionListener {

		CalculatorGUI parent;

		private JTextField numText1 = null;
		private JTextField numText2 = null;
		private JButton submit = null;
		private ButtonGroup btnGroup = null;
		private JRadioButton add = null;
		private JRadioButton minus = null;
		private JRadioButton multiply = null;
		private JRadioButton divide = null;
		private JLabel result = null;

		// Constructor
		public MainPanel(CalculatorGUI parent) {
			this.parent = parent;

			numText1 = new JTextField(6);
			numText2 = new JTextField(6);
			submit = new JButton("submit");
			btnGroup = new ButtonGroup();
			add = new JRadioButton("add");
			minus = new JRadioButton("minus");
			multiply = new JRadioButton("multiply");
			divide = new JRadioButton("divide");
			btnGroup.add(add);
			btnGroup.add(minus);
			btnGroup.add(multiply);
			btnGroup.add(divide);
			result = new JLabel();

			submit.addActionListener(this);
			add.setSelected(true);
			result.setFont(new Font("Serif", Font.BOLD, 50));
			// add components to panel
			add(numText1);
			add(numText2);
			add(submit);
			add(add);
			add(minus);
			add(multiply);
			add(divide);
			add(result);

		}

		public void actionPerformed(ActionEvent e) {

			String numString1 = numText1.getText().trim();
			String numString2 = numText2.getText().trim();
			String operation = null;
			for (Enumeration<AbstractButton> buttons = btnGroup.getElements(); buttons
					.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				if (button.isSelected()) {
					operation = button.getText();
				}
			}
			if (operation.equals("add")) {
				parent.addNumbers(numString1, numString2);
			} else if (operation.equals("minus")) {
				parent.subNumbers(numString1, numString2);
			} else if (operation.equals("multiply")) {
				parent.multiplyNumbers(numString1, numString2);
			} else if (operation.equals("divide")) {
				parent.divideNumbers(numString1, numString2);
			} else {
				result.setText("Error");
			}
		}

		public void setResult(Double resultValue) {
			result.setText(resultValue + "");
		}
	}

}