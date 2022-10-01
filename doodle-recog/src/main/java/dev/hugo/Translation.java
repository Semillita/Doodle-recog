package dev.hugo;

public class Translation {

	private double weight;
	private double bias;
	
	public Translation(double weight, double bias) {
		this.weight = weight;
		this.bias = bias;
	}
	
	public double apply(double input) {
		return input * weight + bias;
	}

	/**Derive with respect to weight*/
	public double deriveRespectToWeight(double input) {
		return input;
	}

	/**Derive with respect to bias*/
	public double deriveRespectToBias(double input) {
		return 1;
	}
	
	public double getWeight() {
		return weight;
	}
	
	public double getBias() {
		return bias;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public void setBias(double bias) {
		this.bias = bias;
	}
	
}
