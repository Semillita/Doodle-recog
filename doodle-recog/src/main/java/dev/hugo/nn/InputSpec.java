package dev.hugo.nn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class InputSpec<T> {

	private List<T> trainingSet;
	private List<T> testingSet;
	
	public InputSpec<T> training(List<T> trainingSet) {
		this.trainingSet = trainingSet;
		return this;
	}
	
	public InputSpec<T> testing(List<T> testingSet) {
		this.testingSet = testingSet;
		return this;
	}
	
//	public <U, R> LayerSpec<T, U, R, T> layer(Supplier<Layer<T, U, R>> firstLayer) {
//		var layer = firstLayer.get();
//		var layers = new ArrayList<Layer<?, ?, ?>>(List.of(layer)).stream().toList();
//		return new LayerSpec<T, U, R, T>(this, layers);
//	}
	
	public <R> LayerDetailSpec<R> layer(Function<T, List<R>> mapping, Layer<R> target) {
		return null;
	}
	
}
