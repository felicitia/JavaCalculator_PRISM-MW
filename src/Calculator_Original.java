
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

/**
 * @author felicitia
 *
 */
public class Calculator_Original {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculator_Original calculator = new Calculator_Original();
		calculator.createAndShowGUI();
	}

	private void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame();
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

		frame.add(new MainPanel());
		// Display the window.
		frame.setVisible(true);
	}

	class MainPanel extends JPanel implements ActionListener {
		JTextField numText1 = null;
		JTextField numText2 = null;
		JButton submit = null;
		ButtonGroup btnGroup = null;
		JRadioButton add = null;
		JRadioButton minus = null;
		JRadioButton multiply = null;
		JRadioButton divide = null;
		JLabel result = null;

		public MainPanel() {
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

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			double numValue1 = Double.parseDouble(numText1.getText());
			double numValue2 = Double.parseDouble(numText2.getText());
			String operation = null;
			for (Enumeration<AbstractButton> buttons = btnGroup.getElements(); buttons
					.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();
				if (button.isSelected()) {
					operation = button.getText();
				}
			}
			if (operation.equals("add")) {
				result.setText(numValue1 + numValue2 + "");
			} else if (operation.equals("minus")) {
				result.setText(numValue1 - numValue2 + "");
			} else if (operation.equals("multiply")) {
				result.setText(numValue1 * numValue2 + "");
			} else if (operation.equals("divide")) {
				result.setText(numValue1 / numValue2 + "");
			} else {
				result.setText("Error");
			}
			// result.setText(add1Num + add2Num + "");
		}
	}

}
