package util;

public class ArrayShuffler {
	private static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	public static void shuffle(int[] array) {
		for (int i = 0; i < array.length; i++) {
			int randomIndex = randomInt(i, array.length - 1);

			if (randomIndex == i) continue;
			int temp = array[i];
			array[i] = array[randomIndex];
			array[randomIndex] = temp;
		}
	}
}