package dev.hugo;

import java.util.ArrayList;
import java.util.List;

public class Layer {

	private final Func activation;
	private final List<Node> nodes;
	
	public Layer(Func activation, int nodeAmount) {
		this.activation = activation;
		nodes = new ArrayList<>();
	}
}
