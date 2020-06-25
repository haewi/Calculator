package Calculator;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.BoxLayout;

public class Main extends Frame implements ActionListener {
	
	Panel panel;
	ArrayList<Button> button;
	TextField tf;
	TextArea ta;
	ArrayList<Double> currentNum;
	ArrayList<String> pastR;
	

	public static void main(String[] args) {
		 
		new Main();
		
	}
	
	public Main() {
		
		super("Calculator");
		
		panel = new Panel();
		button = new ArrayList<Button>();
		pastR = new ArrayList<String>();
		ta = new TextArea(2, 40); // 숫자가 출력될 
		
		
		panel.add(ta);
		button.add(new Button("C"));
		button.add(new Button("( )"));
		button.add(new Button("<--"));
		button.add(new Button("÷"));
		button.add(new Button("1"));
		button.add(new Button("2"));
		button.add(new Button("3"));
		button.add(new Button("X"));
		button.add(new Button("4"));
		button.add(new Button("5"));
		button.add(new Button("6"));
		button.add(new Button("-"));
		button.add(new Button("7"));
		button.add(new Button("8"));
		button.add(new Button("9"));
		button.add(new Button("+"));
		button.add(new Button("past"));
		button.add(new Button("0"));
		button.add(new Button("."));
		button.add(new Button("="));
		button.add(new Button("Other Symbols"));
		
		for(int i=0; i<button.size(); i++) {
			panel.add(button.get(i));
		}
		
		add(panel);
		
		setVisible(true);
		setBounds(800, 300, 350, 300);
		
		for(int i=0; i<button.size(); i++) {
			button.get(i).addActionListener(this);
		}
		
		
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);	
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Button click = (Button) e.getSource();
		String str = click.getLabel();
		String now = ta.getText();
		
		
		try {
			Integer.parseInt(str);
			String[] s = now.split(" ");
			if(s[s.length-1].equals(")")) {
				ta.append(" X ");
			} // 닫는 괄호 다음에는 곱해서 넣기
			ta.append(str); // 숫자 넣기
			return;
		} catch (Exception ex) {
			try {
				Double.parseDouble(str);
				ta.append(str);
			}
			catch(Exception ex2) {}
		} // 숫자면 뒤에 이어 붙이기
		
		
		if(str.equals("C")) { // C 면 clear
			ta.setText("");
		}
		else if(str.equals("( )")) {
			addParen(now);
		}
		else if(str.equals("<--")) { // 숫자 하나 없애기
			removeNum();
		}
		else if(str.equals("÷")) {
			division(now);
		}
		else if(str.equals("X")) {
			multiply(now);
		}
		else if(str.equals("-")) {
			minus(now);
		}
		else if(str.equals("+")) {
			plus(now);
		}
		else if(str.equals("=")) {
			equalsTo(now);
		}
		else if(str.equals(".")) {
			decimalPoint(now);
		}
		else if(str.equals("past")) {
			pastResults(now);
		}
		else if(clickPast(str)) {
			int index = getPastIndex(str);
			ta.setText("");
			ta.setText(pastR.get(index));
		}
		else if(str.contentEquals("Other Symbols")) {
			moreSymbols();
		}
		else if(str.contentEquals("√")) {
			root(now);
		}
		else if(str.contentEquals("sin")) {
			sinNum(now);
		}
		else if(str.contentEquals("cos")) {
			cosNum(now);
		}
		else if(str.contentEquals("tan")) {
			tanNum(now);
		}
	}
	
	private void root(String now) {
		if(now.length() == 0 || now.length() == 1) {
			ta.append("√  ( ");
			return;
		}
		if(now.charAt(now.length()-1) == ' ' && now.charAt(now.length()-2) != ')') return;
		
		if(now.charAt(now.length()-1) == ' ') {
			ta.append(" X ");
		}
		
		ta.append(" √  ( ");
	}
	
	private void sinNum(String now) {
		// TODO Auto-generated method stub
		
	}

	private void cosNum(String now) {
		// TODO Auto-generated method stub
		
	}
	
	private void tanNum(String now) {
		
	}

	private void addParen(String now) {
		
		if(now.length()==0) {
			ta.append("( ");
			return;
		} // 아무것도 없을 때
		
		String[] str = now.split(" ");
		
		if(!Compute.is_operand(str[str.length-1]) && !str[str.length-1].equals(")")) {
			ta.append("( ");
			return;
		} // 마지막에 연산자로 끝나면
		
		if(shouldOpen(now)) {
			ta.append(" X  ( ");
		}
		else {
			ta.append(" ) ");
		} // 그외의 경우
	}

	private boolean shouldOpen(String now) {
		
		String[] str = now.split(" ");
		
		Stack<String> check = new Stack<String>();
		
		for(int i=0; i<str.length; i++) {
			if(str[i].equals("(")) {
				check.add("(");
			}
			else if(str[i].contentEquals(")")){
				check.pop();
			}
		}
		
		if( !check.empty() ) {
			return false;
		}
		return true;
	}

	private void removeNum() {
		String now = ta.getText();
		String next;
		
		if(now.length() == 0) return;
		
		if(now.charAt(now.length()-1) == ' ') {
			next = now.substring(0, now.length()-3);
		}
		else if(now.length()>2) {
			next = now.substring(0, now.length()-1);
		}
		else if (now.length() == 2){
			next = now.charAt(0) + "";
		}
		else {
			next = "";
		}
		
		ta.setText(next);
	}
	
	private void division(String now) {
		if(now.length() == 0) return;
		if(now.charAt(now.length()-1) == ' ' && now.charAt(now.length()-2) != ')') return;
		
		ta.append(" ÷ ");
	}

	private void multiply(String now) {
		if(now.length() == 0) return;
		if(now.charAt(now.length()-1) == ' ' && now.charAt(now.length()-2) != ')') return;

		ta.append(" X ");
	}

	private void minus(String now) {
		if(now.length() == 0) return;
		if(now.charAt(now.length()-2) == '(') {
			ta.append(" -");
		}//앞에 여는 괄호 '('가 있으면 추가 가능 (음수)
		if(now.charAt(now.length()-1) == ' ' && now.charAt(now.length()-2) != ')') return;

		ta.append(" - ");
	}

	private void plus(String now) {
		if(now.length() == 0) return;
		if(now.charAt(now.length()-1) == ' ' && now.charAt(now.length()-2) != ')') return;
		
		ta.append(" + ");
	}

	private void equalsTo(String now) {
		if(now.length() == 0) return;
		if(now.charAt(now.length()-1) == ' ' && now.charAt(now.length()-2) != ')') return;
		if( !Compute.parenthese(now) ) return;
		
		
		pastR.add(now);
		
		Compute c = new Compute();
		ta.setText(Double.toString(c.operate(now)));
	}

	private void decimalPoint(String now) {
		if(now.length() == 0) {
			ta.setText("0.");
			return;
		}
		
		String[] str = now.split(" ");
		
		if(str.length == 0) return;
		
		if(check_decimal(str[str.length-1])) {
			ta.append(".");
		}
		else {
			return;
		}
	}

	private boolean check_decimal(String string) {
		try {
			Integer.parseInt(string);
			return true;
		}
		catch(Exception e1) {
			try {
				Double.parseDouble(string);
			}
			catch(Exception e2) {
				ta.append("0.");
			}
		}
		
		return false;
	}

	private void pastResults(String now) {
		
		Frame pf = new Frame("Past Results");
		Panel pan = new Panel();
		ArrayList<Button> b = new ArrayList<Button>();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		pf.setBounds(450, 300, 350, 400);
		
		for(int i=0; i<10; i++) {
			if(pastR.size() <= i) break;
			b.add(new Button(pastR.get(pastR.size()-1-i)));
			pan.add(b.get(i));
		}
		
		pf.add(pan);
		pf.setVisible(true);
		
		for(int i=0; i<b.size(); i++) {
			b.get(i).addActionListener(this);
		}
		
		
		pf.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				pf.setVisible(false);
			}
		});
	}
	
	private boolean clickPast(String str) {
		for(String s: pastR) {
			if(str.equals(s))
				return true;
		}
		return false;
	}
	
	private int getPastIndex(String str) {
		for(int i=0; i<pastR.size(); i++) {
			if(str.equals(pastR.get(i)))
				return i;
		}
		return -1;
	}
	

	private void moreSymbols() {
		
		Frame fr = new Frame("More Symbols");
		fr.setBounds(450, 300, 350, 60);
		
		Panel panel = new Panel();
//		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		ArrayList<Button> b1 = new ArrayList<Button>();
		
		b1.add(new Button("√"));
		b1.add(new Button("sin"));
		b1.add(new Button("cos"));
		b1.add(new Button("tan"));
		
		for(Button b: b1) {
			panel.add(b);
		}
		fr.setVisible(true);
		
		fr.add(panel);
		
		for(int i=0; i<b1.size(); i++) {
			b1.get(i).addActionListener(this);
		}
		
		fr.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				fr.setVisible(false);
			}
		});
		
	}
	
	


/*
	class MouseEventHandler extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			String label = e.getComponent().toString();
			if(label == "1") {
				ta.append("1");
			}
		}
		
	}
*/	
}
