package Calculator;

import java.awt.Button;
import java.util.ArrayList;
import java.util.Stack;

public class Compute {
	
	String postfix;
	Double result;
	
	public Double operate(String expression) {
		Stack<String> stack;
	
		String[] str = expression.split(" ");
		
		stack = new Stack<String>();
		stack.add("$");
		
		postfix = new String("");
		
		addTerm(stack, str);
		
		while(stack.size() > 1) {
			postfix += stack.pop() + " ";
		}
		
		
		str = postfix.split(" ");
		
//		System.out.println(postfix);
		
		result = calculate(str);
		
		return result;
	}


	private void addTerm(Stack<String> stack, String[] terms) {

		
		
//		boolean sub = false;
//		Stack<String> subStack = new Stack<String>();
//		String subPost = new String("");
//		
		for(String t : terms) {
//			if(t.equals(null)) continue;
//			System.out.println("t: " + t);
//			if(is_other_operator(t)) {
//				subPost = "";
//				sub = true;
//				continue;
//			}
//			
//			
//			if(sub) {
//				makePostfix(subStack, t, subPost);
//				subPost += t;
//				System.out.println(subPost + t);
//				if(parenthese(subPost)) {
//					System.out.println("DONE! : " + subPost);
//					sub = false;
//				}
//			}
//			else {
//				makePostfix(stack, t, postfix);
//			}
			
			if(is_operand(t)){
				postfix += t + " ";
			} // 피연산자일때
			else{
				if(t.equals("(")){
					stack.add(t);
				} // "(" 일때
				else if(t.equals(")")){
					while(! stack.peek().equals("(") ){
						postfix += stack.pop() + " ";
					}
					stack.pop();
				} // ")" 일때
				else{
					int pre = precedence(t);
					String top_c = stack.peek();
					while(precedence(top_c) >= pre){
	                    postfix += stack.pop() + " ";
	                    top_c = stack.peek();
	                }
	                stack.add(t);
	            } // 연산자일때
	        } // 피연산자가 아닐때
		}
	}
	
	private void makePostfix(Stack<String> stack, String t, String p) {
		if(is_operand(t)){
			p += t + " ";
		} // 피연산자일때
		else{
			if(t.equals("(")){
				stack.add(t);
			} // "(" 일때
			else if(t.equals(")")){
				while(! stack.peek().equals("(") ){
					p += stack.pop() + " ";
				}
				stack.pop();
			} // ")" 일때
			else{
				int pre = precedence(t);
				String top_c = stack.peek();
				while(precedence(top_c) >= pre){
                    p += stack.pop() + " ";
                    top_c = stack.peek();
                }
                stack.add(t);
            } // 연산자일때
        } // 피연산자가 아닐때
	}


	private Double calculate(String[] str) {
		
		Stack<Double> dStack = new Stack<Double>();
		
		for(int i=0; i<str.length; i++) {
//			System.out.println("str[" + i + "]: " + str[i]);
			if(str[i].isEmpty()) continue;
			
			if(is_operand(str[i])) {
				dStack.add(Double.parseDouble(str[i]));
			}
			else {
				
				double one, two, newD=0;
				one = dStack.pop();
				two = dStack.pop();
				
				if(str[i].equals("+")) {
					newD = two + one;
				}
				else if(str[i].equals("-")) {
					newD = two - one;
				}
				else if(str[i].equals("X")) {
					newD = two * one;
				}
				else if(str[i].equals("÷")) {
//					newD = two / one;
				}
//				else if(str[i].equals("√")) {
////					newD = two / one;
//				}
//				else if(str[i].equals("sin")) {
////					newD = two / one;
//				}
//				else if(str[i].equals("cos")) {
////					newD = two / one;
//				}
//				else if(str[i].equals("tan")) {
////					newD = two / one;
//				}
				
				dStack.add(newD);
			}
		}
		
		return dStack.pop();
	}

	public static boolean is_operand(String term) {
		if( (term.equals("(")) || term.equals(")")
				|| term.equals("+") || term.equals("-")
				|| term.equals("X") || term.equals("÷")
				|| term.equals("$") || term.equals("√")
				|| term.equals("sin") || term.equals("cos")
				|| term.equals("tan")) {
			return false;
		}
		return true;
	}
	
	public static boolean is_other_operator(String term) {
		if( term.equals("√") || term.equals("sin")
				|| term.equals("cos") || term.equals("tan")) {
			return true;
		}
		return false;
	}
	
	private int precedence(String term) {
		if(term.equals("(") || term.equals("$")) return 0;
		else if(term.equals("+") || term.equals("-")) return 1;
		else if(term.equals("X") || term.equals("÷")) return 2;
		
		return -1;
	}


	public static boolean parenthese(String now) {
		String[] str = now.split(" ");
		Stack<String> sta = new  Stack<String>();
		
		for(String s: str) {
			if(s.equals("(")) {
				sta.add(s);
			}
			else if(s.equals(")")) {
				if(sta.empty()) return false;
				sta.pop();
			}
		}
		
		if(sta.empty()) return true;
		
		return false;
	}
	
	
}
