package dev.hugo;

public record ImageFile(int depth, int width, int height, byte[] data) {
	private static final int MARGIN = 16;
	
	public int[][] getImageAt(int index) {
		if (index >= depth) {
			return null;
		}
		
		var image = new int[width][height];
		var start = MARGIN + index * 28 * 28;
		for (int x = 0; x < 28; x++) {
			for (int y = 0; y < 28; y++) {
				byte b = data[start + (width - y - 1) * 28 + x];
				int i = b & 0xFF;
				image[x][y] = i;
			}
		}
		return image;
	}
}
