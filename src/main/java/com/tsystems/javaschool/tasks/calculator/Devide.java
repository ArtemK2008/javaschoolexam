package com.tsystems.javaschool.tasks.calculator;

public class Devide implements Calculate {

	@Override
	public Double calculate(Double number1, Double number2) {
		// TODO Auto-generated method stub	
		if (number2 == 0) {
			return Double.MIN_VALUE;
		}
		return number1 / number2;
	}

	
	}


