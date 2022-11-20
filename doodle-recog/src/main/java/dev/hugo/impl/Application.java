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
				
				.layer(this::firstLayer)
				
				// kanske typ .layer(..., amount)
				// eller .layer(...).amount(...)
				// kan vilja ha typ .translate(...)
	}
	
	public Layer<int[][], Double> firstLayer() {
		return null;
	}
	
	public Double hugo(ImageFile imageFile) {
		return null;
	}
	
}
