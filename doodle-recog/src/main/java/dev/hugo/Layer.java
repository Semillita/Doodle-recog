package dev.hugo;

import java.util.stream.IntStream;

public class Layer {

	private final Func activation;
	private final Translation[][] translations;
	private double[] nodeValues;
	
	public Layer(Func activation, int nodeAmount, int nextLayerNodeAmount) {
		this.activation = activation;
		translations = IntStream.range(0, nodeAmount)
				.mapToObj(i -> IntStream.range(0, nextLayerNodeAmount)
						.mapToObj(j -> new Translation())
						.toArray(k -> new Translation[k]))
				.toArray(l -> new Translation[l][]);
	}
	
	public Func getActivation() {
		return activation;
	}
	
	public Translation[][] getTranslations() {
		return translations;
	}
}
