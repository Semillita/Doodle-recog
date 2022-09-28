package dev.hugo;

import static java.lang.Math.*;

public class Application {

	public static void main(String[] args) {
		// 33:52
		var sigmoid = getSigmoidFunc();
		var reLu = getReLuFunc();
	}
	
	private static Func getSigmoidFunc() {
		return new Func() {
			@Override
			public double apply(double x) {
				var a = exp(2 * x);
				return (a - 1) / (a + 1);
			}
			
			@Override
			public double derive(double x) {
				var a = apply(x);
				return a * (1 - a);
			}
		};
	}
	
	private static Func getReLuFunc() {
		return new Func() {
			@Override
			public double apply(double x) {
				return max(x, 0);
			}
			
			@Override
			public double derive(double x) {
				return (x > 0) ? 1 : 0;
			}
		};
	}
	
}
