package dev.hugo.nn;

import java.util.List;

public interface Layer<T, U, R> {

	public U collect(List<T> previousLayer);
	
}
