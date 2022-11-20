package dev.hugo.nn;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class LayerSpec<T, U, R, E> {

	private InputSpec<E> inputConfig;
	private List<Layer<?, ?, ?>> layers; // Layers up to but not including this one
	
	<T, R>LayerSpec(InputSpec<E> inputConfig, List<Layer<?, ?, ?>> layers) {
		this.inputConfig = inputConfig;
		this.layers = layers;
	}
	
	public <Q> LayerSpec<U, R, Q, E> layer(Supplier<Layer<U, R, Q>> nextLayer) {
		var newLayer = nextLayer.get();
		layers.add(newLayer);
		return new LayerSpec<U, R, Q, E>(inputConfig, layers);
	}
	
	public NeuralNetwork<E, R> result() {
		return null;
	}
	
	protected E backFeed(R)
}
