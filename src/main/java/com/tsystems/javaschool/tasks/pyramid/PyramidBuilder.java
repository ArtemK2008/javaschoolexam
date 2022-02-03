package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PyramidBuilder {

	/**
	 * Builds a pyramid with sorted values (with minumum value at the top line and
	 * maximum at the bottom, from left to right). All vacant positions in the array
	 * are zeros.
	 *
	 * @param inputNumbers to be used in the pyramid
	 * @return 2d array with pyramid inside
	 * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build
	 *                with given input
	 */
	public int[][] buildPyramid(List<Integer> inputNumbers) {
		// TODO : Implement your solution here
		try {
			Collections.sort(inputNumbers);
		} catch (OutOfMemoryError e) {
			throw new CannotBuildPyramidException();
		} catch (NullPointerException e) {
			throw new CannotBuildPyramidException();
		}

		try {
			// Collections.sort(inputNumbers);
			Queue<Integer> allElements = new LinkedList<Integer>(inputNumbers);

			double summ = inputNumbers.size();
			int rows = 0;
			Double test = (-1 + Math.sqrt(1 + 8 * summ)) / 2;
			if (test % 1 == 0) {
				rows = test.intValue();
			} else {
				throw new CannotBuildPyramidException();
			}
			int length = rows * 2 - 1;
			int[][] pyramid = new int[rows][length];
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < length; j++) {
					pyramid[i][j] = 0;
				}
			}

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < length; j++) {
					System.out.print(pyramid[i][j]);
					;
				}
				System.out.println();
			}

			int middleElement = length / 2;
			pyramid[0][middleElement] = allElements.poll();
			for (int i = 1; i < rows; i++) {
				int tempElement = middleElement;
				for (int j = 0; j < (i + 1); j++) {
					pyramid[i][tempElement - i] = allElements.poll();
					tempElement += 2;
				}
			}

			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < length; j++) {
					System.out.print(pyramid[i][j]);
					;
				}
				System.out.println();
			}

			return pyramid;
		} catch (Exception e) {
			// TODO: handle exception
			throw new CannotBuildPyramidException();
		}
	}
}
