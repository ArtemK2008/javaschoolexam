package com.tsystems.javaschool.tasks.subsequence;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Subsequence {

	/**
	 * Checks if it is possible to get a sequence which is equal to the first one by
	 * removing some elements from the second one.
	 *
	 * @param x first sequence
	 * @param y second sequence
	 * @return <code>true</code> if possible, otherwise <code>false</code>
	 */
	@SuppressWarnings("rawtypes")
	public boolean find(List x, List y) {
		// TODO: Implement the logic here
		boolean possible = true;

		if (x == null || y == null) {
			throw new IllegalArgumentException();
		}
		Queue<Object> firstList = new LinkedList<Object>(x);
		Queue<Object> secondList = new LinkedList<Object>(y);
		for (Object element : firstList) {
			if (secondList.isEmpty()) {
				possible = false;
			}
			while (!secondList.isEmpty()) {
				Object temp = secondList.poll();
				System.out.println(temp);
				if (element.equals(temp)) {
					break;
				}
			}
		}

		return possible;
	}
}
