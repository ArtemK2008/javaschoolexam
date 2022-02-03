package com.tsystems.javaschool.tasks.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Calculator {

	/**
	 * Evaluate statement represented as string.
	 *
	 * @param statement mathematical statement containing digits, '.' (dot) as
	 *                  decimal mark, parentheses, operations signs '+', '-', '*',
	 *                  '/'<br>
	 *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
	 * @return string value containing result of evaluation or null if statement is
	 *         invalid
	 */
	Character[] digitsAllowed = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '(', ')', '.' };
	Character[] expAllowed = { '+', '-', '*', '/' };
	List<Character> digitsList = Arrays.asList(digitsAllowed);
	List<Character> expList = Arrays.asList(expAllowed);
	Double finalResult = 0.0;
	int count1 = 0;
	int count2 = 0;

	public String evaluate(String statement) {
		// TODO: Implement the logic here

		boolean inputValidation = inputCheck(statement);
		if (!inputValidation) {
			return null;
		}

		if (count1 > 0) {
			StringBuilder sb = new StringBuilder(statement);
			StringBuilder modifiedStatement = new StringBuilder();
			int firstBracket = statement.indexOf("(");
			int lastBracket = statement.indexOf(")", firstBracket);
			modifiedStatement.append(statement.substring(0, firstBracket));
			String inBrackets = sb.substring(firstBracket + 1, lastBracket);
			Double tempInBrackets = caluculate(inBrackets);
			if (tempInBrackets == Double.MIN_VALUE) {
				return null;
			}
			modifiedStatement.append(String.valueOf(tempInBrackets));
			modifiedStatement.append(sb.substring(lastBracket + 1));
			finalResult = caluculate(modifiedStatement.toString());
			if (finalResult == Double.MIN_VALUE) {
				return null;
			}			
		} else {
			finalResult = caluculate(statement);
		}

		DecimalFormat df = new DecimalFormat("#.####");
		String finalAnswer = String.valueOf(df.format(finalResult));
		finalAnswer = finalAnswer.replace(',', '.');
		return finalAnswer;
	}

	public Double caluculate(String arg) {

		// Splitting input by + and -
		ArrayList<Character> summOperList = new ArrayList<Character>();
		ArrayList<String> numbersArr = new ArrayList<String>();
		int start = 0;
		for (int i = 0; i < arg.length(); i++) {
			if (arg.charAt(i) == '+') {
				summOperList.add(arg.charAt(i));
				numbersArr.add(arg.substring(start, i));
				start = i + 1;
			}
			if (arg.charAt(i) == '-') {
				if (i != 0) {
					if (!expList.contains(arg.charAt(i - 1))) {
						summOperList.add(arg.charAt(i));
						numbersArr.add(arg.substring(start, i));
						start = i + 1;
					}
				}
			}
		}
		numbersArr.add(arg.substring(start));

		ArrayList<Double> multiResultsArr = new ArrayList<Double>();
		Double multiResults = 0.0;

		// Calculate all expressions with * and / first
		for (String t : numbersArr) {
			Queue<String> operations;
			Queue<String> numbers;

			String multiTimeArr[] = t.split("[/*]");
			String regExp = "-?[1-9]\\d*|0";
			String operArr[] = t.split(regExp);

			numbers = new LinkedList<String>(Arrays.asList(multiTimeArr));
			numbers.removeAll(Arrays.asList("", null));
			operations = new LinkedList<String>(Arrays.asList(operArr));
			operations.removeAll(Arrays.asList("", null));

			if (operations.isEmpty()) {
				multiResults = Double.parseDouble(t);
			} else {
				Double res = Double.parseDouble(numbers.poll());
				if (numbers.isEmpty()) {
					multiResults = res;
				} else {
					while (!numbers.isEmpty()) {
						String opr = operations.poll();
						Calculate calculate;
						switch (opr) {
						case "*":
							calculate = new Multiply();
							break;
						case "/":
							calculate = new Devide();
							break;
						default:
							continue;
						}
						Double numm = Double.parseDouble(numbers.poll());
						res = calculate.calculate(res, numm);
						if (res == Double.MIN_VALUE) {
							return Double.MIN_VALUE;
						}
						multiResults = res;
					}
				}
			}
			multiResultsArr.add(multiResults);
		}

		// now calc summ between brackets

		Queue<Double> numbers = new LinkedList<Double>(multiResultsArr);
		Queue<Character> operators = new LinkedList<Character>(summOperList);
		Double result = numbers.poll();

		while ((!numbers.isEmpty()) && (!operators.isEmpty())) {
			Calculate calculate;
			Character opr = operators.poll();
			switch (opr) {
			case '+':
				calculate = new Add();
				break;
			case '-':
				calculate = new Substruct();
				break;
			default:
				continue;
			}
			Double numm = numbers.poll();
			result = calculate.calculate(result, numm);
		}
		return result;
	}

	public boolean inputCheck(String statement) {

		if ((statement == null) || (statement == "")) {
			return false;
		}
		for (int i = 0; i < statement.length(); i++) {
			if ((expList.contains(statement.charAt(i)) || (statement.charAt(i) == '.') || (statement.charAt(i) == ','))
					&& (i < statement.length() - 1)) {
				if ((expList.contains(statement.charAt(i + 1)) || (statement.charAt(i + 1) == '.')
						|| (statement.charAt(i + 1) == ','))) {
					return false;
				}
			}
		}

		for (Character character : statement.toCharArray()) {

			if (!digitsList.contains(character) && (!expList.contains(character))) {
				return false;
			}

			if (!digitsList.contains(statement.charAt(0)) || (statement.charAt(0) == ')')) {
				return false;
			}

			if (!digitsList.contains(statement.charAt(statement.length() - 1))
					|| (statement.charAt(statement.length() - 1) == '(')) {
				return false;
			}

			for (int i = 1; i < statement.length() + 1; i++) {
				if ((statement.charAt(i - 1) == '/') && (statement.charAt(i) == '0')) {
					return false;
				}
			}

			for (Character t : statement.toCharArray()) {
				if (t == '(') {
					count1++;
				}
				if (t == ')') {
					count2++;
				}
			}
			if (count1 != count2) {
				return false;
			}
		}
		return true;
	}

}
