package dev.hugo;

public interface BiFunc {

	public double apply(double input);
	
	public double deriveRespectToX(double x);
	
	public double deriveRespectToZ(double z);
}
