package dev.hugo;

import static java.lang.Math.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class App {

	public static void main(String[] args) {
		// Seriösa grejer:
		final int TRAIN_SIZE = 100, LAPS = 100;
		final int TEST_SIZE = 50, TEST_OFFSET = TRAIN_SIZE;

		var layerSizes = new int[] { 28 * 28, 100, 10 };

		var layers = IntStream.range(0, layerSizes.length - 1)
				.mapToObj(index -> new Layer(getReLuFunc(), layerSizes[index], layerSizes[index + 1])).toList();

		var imageFile = IdxParser.getImageFile("/train-images.idx3-ubyte").orElseThrow();
		var labelFile = IdxParser.getLabelFile("/train-labels.idx1-ubyte").orElseThrow();

		var images = IntStream.range(0, TRAIN_SIZE)
				.mapToObj(i -> imageFile.getImageAt(i)).toList();
		
		var labels = IntStream.range(0, TRAIN_SIZE)
				.mapToObj(i -> labelFile.getLabelAt(i)).toList();
		
		for (int lap = 0; lap < LAPS; lap++) {
			var caches = new FeedCache[TRAIN_SIZE];
			// Feedforward
			for (int i = 0; i < TRAIN_SIZE; i++) {
				var image = images.get(i);
				var input = flattenRasterToDoubles(image);
				var nodeValues = feedForward(input, layers);
				var cacheList = new ArrayList<List<Double>>();
				for (int x = 0; x < nodeValues.length; x++) {
					var l = new ArrayList<Double>();
					var layerValues = nodeValues[x];
					for (var d : layerValues) {
						l.add(d);
					}
					cacheList.add(l);
				}
				var cache = new FeedCache(cacheList);
				caches[i] = cache;
			}
			// Back prop
		}
	}

	private static void printRaster(int[][] raster) {
		for (int y = 27; y >= 0; y--) {
			System.out.print("[" + ((y < 10) ? (y + " ") : y) + ":");
			for (int x = 0; x < 28; x++) {
				System.out.print((raster[x][y] > 0) ? "#" : " ");
				if (x != 27)
					System.out.print("");
			}
			System.out.println("]");
		}
		System.out.println("[---0123456789012345678901234567]");
	}

	private static double[] flattenRasterToDoubles(int[][] raster) {
		var array = new double[28 * 28];
		for (int x = 0; x < 28; x++) {
			for (int y = 0; y < 28; y++) {
				array[x * 28 + y] = (double) raster[x][y];
			}
		}
		var a = Arrays.stream(raster).flatMapToDouble(row -> IntStream.of(row).mapToDouble(i -> i)).toArray();
		return a;
	}

	private static double[][] feedForward(double[] values, List<Layer> layers) {
		// Create list of values for all the layers, including input and results
		var nodeValues = new double[layers.size() + 1][];
		// Add input layer values to cache
		for (int index = 0; index < layers.size(); index++) {
			nodeValues[index] = values;
			var layer = layers.get(index);
			var nextLayerValues = getNextLayerValues(values, layer.getTranslations(), layer.getActivation());
			values = nextLayerValues;
		}
		nodeValues[layers.size()] = values;

		return nodeValues;
	}

	private static double[] getNextLayerValues(double[] previousLayerValues, Translation[][] translations,
			Func activation) {
		var nextLayer = new double[translations[0].length];

		for (int i = 0; i < previousLayerValues.length; i++) {
			var nodeTranslations = translations[i];
			var nodeVal = previousLayerValues[i];
			for (int j = 0; j < nodeTranslations.length; j++) {
				var translation = nodeTranslations[j];
				nextLayer[j] += translation.apply(nodeVal);
			}
		}

		return nextLayer;
	}

	private static double getNodeCostSlope(double translatedCost, Translation translation) {
		return translation.deriveRespectToVariable() * translatedCost;
	}

	private static double getTranslatedCostSlope(double firstNodeValue, double secondNodeCost, Translation translation,
			Func activation) {
		var translatedValue = translation.apply(firstNodeValue);
		return secondNodeCost * activation.derive(translatedValue);
	}

	private static TranslationCostSlope getTranslationCostSlope(double baseNodeValue, double translatedCost) {
		var weightCost = baseNodeValue * translatedCost;
		var biasCost = translatedCost;
		return new TranslationCostSlope(weightCost, biasCost);
	}

	private static double getCost(double expected, double actual) {
		var diff = expected - actual;
		return diff * diff;
	}

	// Basically hur mycket värdet av actual output påverkar cost, antag att actual
	// output är A(sista lager) + ...
	private static double getCostDerivativeRespectToActual(double expected, double actual) {
		return 2 * (expected - actual);
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
