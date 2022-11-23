package dev.hugo.impl;

import java.util.ArrayList;
import java.util.List;

import dev.hugo.ImageFile;
import dev.hugo.nn.Layer;
import dev.hugo.nn.NeuralNetwork;

public class Application {

	public static void main(String[] args) {
		new Application();
	}
	
	public Application() {
		List<int[][]> trainingImages = new ArrayList<>();
		List<int[][]> testingImages = new ArrayList<>();
		var nn = NeuralNetwork
				.from(int[][].class)
				.training(trainingImages)
				.testing(testingImages)
				.layer(this::inputMapping, firstLayer())
				.
				
				//.layer(this::firstLayer)
				
				// layer 0, translation 0, activation 0, layer 1, translation 1, activation 1, ...
				
				// kanske typ .layer(..., amount)
				// eller .layer(...).amount(...)
				// kan vilja ha typ .translate(...)
	}
	
	public List<Double> inputMapping(int[][] input) {
		return List.of();
	}
	
	public Layer<Double> firstLayer() {
		return null;
	}
	
	public Double hugo(ImageFile imageFile) {
		return null;
	}
	
}
