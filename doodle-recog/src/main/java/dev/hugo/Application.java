package dev.hugo;

import static java.lang.Math.*;

import java.util.List;
import java.util.stream.IntStream;

public class Application {

	public static void main(String[] args) {
		var inputNodeAmount = 3;
		var outputNodeAmount = 2;
		var layerSizes = new int[] { inputNodeAmount, 4, outputNodeAmount };

		var layers = IntStream.range(0, layerSizes.length - 1)
				.mapToObj(index -> new Layer(getReLuFunc(), layerSizes[index], layerSizes[index + 1])).toList();
		
		var input = new double[] {0, 0.4, 0.9};
		var expectedOutput = new double[] {1, 0};
		var output = feedForward(input, layers);
		
		var cost0 = getCost(expectedOutput[0], output[0]);
		var cost1 = getCost(expectedOutput[1], output[1]);
		
		System.out.println("Cost 0: " + cost0);
		System.out.println("Cost 1: " + cost1);
		
//		for (var num : output) {
//			System.out.println(num);
//		}
		
	}

	private static double[] feedForward(double[] values, List<Layer> layers) {
		for (int index = 0; index < layers.size(); index++) {
			var layer = layers.get(index);
			var nextLayerValues = getNextLayerValues(values, layer.getTranslations(), layer.getActivation());
			values = nextLayerValues;
		}
		
		return values;
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

	private static double getNodeCost(double translatedCost, Translation translation) {
		return translation.deriveRespectToVariable() * translatedCost;
	}

	private static double getTranslatedCost(double firstNodeValue, double secondNodeCost, Translation translation,
			Func activation) {
		var translatedValue = translation.apply(firstNodeValue);
		return secondNodeCost * activation.derive(translatedValue);
	}

	private static TranslationCostSlope getTranslationCost(double baseNodeValue, double translatedCost) {
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
