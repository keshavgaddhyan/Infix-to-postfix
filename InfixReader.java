import java.io.*;
/**
 * 
 * @author keshavgaddhyan
 * This program converts infix expression to postfix
 * It also evaluates the postfix and prints the final answer
 */

public class InfixReader {
	public static void main(String[] args) {
		/**
		 * creates an object myAnswer of class InfixReader
		 */
		InfixReader myAnswer = new InfixReader();
		myAnswer.doConversion();
	}
	/**
	 * 
	 * This function takes an input from the user and converts the infix expression to postfix
	 */
	public void doConversion() {
		/**
		 *  convert infix to postfix and save it in pfix
		 */
		String pfix="";
		/**
		 * String array ar is used to save the input from readInfix
		 */
		String[] ar = new String[1000];
		ar=readInfix();
		for(int i=0;i<ar.length;i++)
		{
			if(ar[i].matches("-?\\d+(\\.\\d+)?"))  //checks if the element is integer or not
			{
				pfix=pfix+ar[i]+" ";
			}
				else if(ar[i].equals("^")|| ar[i].equals("/") || ar[i].equals("*") || ar[i].equals("-") || ar[i].equals("+"))
				{
					while(Stack.check()==false && Stack.hprec(ar[i])==true && Stack.oparenthesis(Stack.top())==false)
					{
						pfix=pfix+Stack.top()+" ";
						Stack.pop();
						
					}
					Stack.push(ar[i]);
				}
				else if(Stack.oparenthesis(ar[i]))
				{
					Stack.push(ar[i]);
				}
				else if(ar[i].equals(")") || ar[i].equals("}") || ar[i].equals("]"))
						{
					
					while(Stack.check()==false && Stack.oparenthesis(Stack.top())==false)
					{
						pfix=pfix+Stack.top()+" ";
						Stack.pop();
					}
					Stack.pop();
						}
			}
		
		while(Stack.check()==false)
		{
			pfix=pfix+Stack.top()+" ";
			Stack.pop();
		}
		System.out.print("Postfix: ");
		System.out.println(pfix);
		evalPostfix(pfix);
		
	}

	
	/**
	 * print result evaluated postfix
	 * the expression generated by doconversion() is passed into this function and result is printed
	 * @param postfix is expression which is evaluated
	 * 
	 */
	public void evalPostfix(String postfix) {
		
		/**
		 * Variable nar[] stores the postfix expression
		 */
		String nar[] = new String[1000];
		nar=postfix.split(" ");
		for(int i=0;i<nar.length;i++)
		{
			if(nar[i].matches("-?\\d+(\\.\\d+)?"))
			{
				Stack.push(nar[i]);
			}
			else
			{
				Double y= Double.parseDouble(Stack.top());
				Stack.pop();
				Double z= Double.parseDouble(Stack.top());
				Stack.pop();
				switch (nar[i])
				{
				case "+":
					Stack.push(Double.toString(z+y));
					break;
				
				case "-":
					Stack.push(Double.toString(z-y));
					break;
				
				case "*":
					Stack.push(Double.toString(z*y));
					break;
					
				case "/":
					Stack.push(Double.toString(z/y));
					break;
					
				case "^":
					Stack.push(Double.toString(Math.pow(z, y)));
					break;
					
					
				default:
					System.out.println("Error");
					
					
				}
					
				}
				
			}
		System.out.print("Result: ");
		System.out.println((int)(Double.parseDouble(Stack.top())));
	
		
	}

	public String[] readInfix() {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String inputLine;
		try {
			System.out.print("Input infix: ");
			inputLine = input.readLine();
			return inputLine.split(" ");
		} catch (IOException e) {
			System.err.println("Input ERROR.");
		}

		// return empty array if error occurs
		return new String[] {};
	}

}


/**
 * 
 * @author keshavgaddhyan
 * This class contains the implementation of stack
 * it contains multiple functions such as push,pop,check,top,rtn,oparenthesis and hprec.
 */
class Stack {
	static String stck[] = new String[1000];
	static int i=0;
	
	/**
	 * get top element of stack
	 * @return the string from the top of the stack
	 */
	public static String top()
	{
		return stck[i-1];
	}
	
	/**
	 * push an element inside the stack
	 * @param x as which is pushed inside the stack.
	 */
	public static void push(String x)
	{
		stck[i]=x;
		i++;
	}
	
/**
 *
 * this function pops the last element from the stack
 */
	public static void pop()
	{
		if(i>0)
		i--;
	
    }
	
	/**
	 * check if stack is empty or not
	 * @return true if stack is empty else return false
	 */
	public static boolean check()
	{
		if(i==0)
		{
			return true;
			
		}
		else
		{
			return false;
		}
	}
	/**
	 * show last element of stack
	 * @return the top most element of the stack
	 */
	public static String rtn()
	{
		return stck[i];
	}
	
	/**
	 * checks if the passed argument is parenthesis or not
	 * @param x is the variable to check whether it is parenthesis or not
	 * @return true is it is parenthesis else returns false
	 */
	public static boolean oparenthesis(String x)
	{
		if(x.equals("(") || x.equals("{") || x.equals("["))
			return true;
		else
			return false;
	}
	
	/**
	 * compare precedence of operators with the last element in stack
	 * @param x is the argument to compare the precedence of operator
	 * @return true is top most element in stack has higher precedence than argument x
	 * returns false if last elemnt of stack has lower precedence
	 */
	public static boolean hprec(String x)
	{
		if (stck[i-1].equals("^"))
		return true;
		else if(stck[i-1].equals("*") && (x.equals("+") || x.equals("-") || x.equals("/")))
		return true;
		else if (stck[i-1].equals("/") && (x.equals("+") || x.equals("-") || x.equals("*")))
		return true;
		else if(stck[i-1].equals("+") && x.equals("-"))
		return true;
		else if(stck[i-1].equals("-") && x.equals("+"))
		return true;
		else
		return false;
	}
}