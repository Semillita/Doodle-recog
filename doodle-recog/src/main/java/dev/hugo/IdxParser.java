package dev.hugo;

import java.io.IOException;
import java.util.Optional;

public class IdxParser {
	public static Optional<ImageFile> getImageFile(String filepath) {
		try {
			var data = IdxParser.class.getResourceAsStream(filepath).readAllBytes();
			var depth = toInt(data[4], data[5], data[6], data[7]);
			var width = toInt(data[8], data[9], data[10], data[11]);
			var height = toInt(data[12], data[13], data[14], data[15]);
			
			var images = new ImageFile(depth, width, height, data);
			return Optional.ofNullable(images);
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	public static Optional<LabelFile> getLabelFile(String filepath) {
		try {
			var data = IdxParser.class.getResourceAsStream(filepath).readAllBytes();
			var depth = toInt(data[4], data[5], data[6], data[7]);
			
			var labels = new LabelFile(depth, data);
			return Optional.ofNullable(labels);
		} catch (IOException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	private static int toInt(byte b0, byte b1, byte b2, byte b3) {
		return ((b0 & 0xFF) << 24)
				| ((b1 & 0xFF) << 16)
				| ((b2 & 0xFF) << 8)
				| ((b3 & 0xFF) << 0);
	}
	
	public static record LabelFile(int depth, byte[] data) {
		private static final int MARGIN = 8;
		
		public int getLabelAt(int index) {
			if (index >= depth) {
				return -1;
			}
			
			byte b = data[MARGIN + index];
			int i = b & 0xFF;
			
			return i;
		}
	}
}
