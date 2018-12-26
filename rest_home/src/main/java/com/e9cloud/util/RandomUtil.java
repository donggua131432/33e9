package com.e9cloud.util;

import java.util.Random;

public class RandomUtil {

	/**
	 * 随机数生成器
	 */
	public static final Random builder = new Random();

	/**
	 * 获取范围内的随机整数（包含上、下限数值）
	 * 
	 * @param _numericLower
	 *            数值下限
	 * @param _numericUpper
	 *            数值上限
	 * @return
	 */
	public static int getInt(int numericLower, int numericUpper) {
		if (numericUpper <= numericLower)
			return numericUpper;
		return builder.nextInt(numericUpper - numericLower + 1) + numericLower;
	}

	/**
	 * 获取两个数之间的随机数<br>
	 * 两个参数不必区分谁前谁后
	 * 
	 * @param num1
	 *            第一个数
	 * @param num2
	 *            第二个数
	 * @return 返回获取到的随机数
	 */
	public static int getRandom(int num1, int num2) {
		int diff, min;
		if (num1 > num2) {
			diff = num1 - num2;
			min = num2;
		} else {
			diff = num2 - num1;
			min = num1;
		}
		return builder.nextInt(diff + 1) + min;
	}
	
	public static int[] splitInteger(int total, int n) {
		int[] num = new int[n];
		int right = total;
		for (int i = 0; i < n - 1; i++) {
			num[i] = builder.nextInt(right);
			right -= num[i];
		}
		num[n - 1] = right;
		return num;
	}
}
