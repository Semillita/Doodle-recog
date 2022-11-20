package dev.hugo.nn;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork<R, T> {
	
	private List<Layer<?, ?, ?>> layers;
	
	public NeuralNetwork(List<Layer<?, ?, ?>> layers) {
	}
	
	public T feedForward(R input) {
		return feed(input).result();
	}
	
	private FeedResult<T> feed(R input) {
		return null;
	}
	
	public static <T> InputSpec<T> from(Class<T> inputType) {
		return new InputSpec<T>();
	}
	
}
