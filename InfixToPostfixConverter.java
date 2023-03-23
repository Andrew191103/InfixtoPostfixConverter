import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class InfixToPostfixConverter {

    static Stack<Character> operatorStack = new Stack<>();

    public static void main(String[] args) throws IOException {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter an infix expression to convert to postfix notation: ");
        String infixExpression = inputReader.readLine();
        String postfixExpression = convertToPostfix(infixExpression);
        System.out.println("Postfix expression: " + postfixExpression);
    }

    private static String convertToPostfix(String infixExpression) {
        StringBuilder postfixExpressionBuilder = new StringBuilder();
        for (int i = 0; i < infixExpression.length(); i++) {
            char currentChar = infixExpression.charAt(i);
            if (Character.isLetterOrDigit(currentChar)) {
                postfixExpressionBuilder.append(currentChar);
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    postfixExpressionBuilder.append(operatorStack.pop());
                }
                operatorStack.pop();
            } else {
                while (!operatorStack.isEmpty() && hasHigherPrecedence(currentChar, operatorStack.peek())) {
                    postfixExpressionBuilder.append(operatorStack.pop());
                }
                operatorStack.push(currentChar);
            }
        }
        while (!operatorStack.isEmpty()) {
            postfixExpressionBuilder.append(operatorStack.pop());
        }
        return postfixExpressionBuilder.toString();
    }

    private static boolean hasHigherPrecedence(char op1, char op2) {
        int op1Precedence = getOperatorPrecedence(op1);
        int op2Precedence = getOperatorPrecedence(op2);
        return op1Precedence <= op2Precedence;
    }

    private static int getOperatorPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}
