import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Random;

import javax.swing.JFrame;

public class Sorter extends JFrame {
	public static int[] array = new int[52];
	public static HashSet<Integer> selected = new HashSet<Integer>();

	public static void main(String[] args) {

		fillArray();

		MyCanvas m = new MyCanvas();

		JFrame window = new JFrame();
		window.setLayout(new GridLayout(1, 60));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1200, 800);
		window.add(m);
		window.setVisible(true);

		 //bubbleSort(m);
		selectionSort(m);

	} // main

	public static void bubbleSort(MyCanvas m) {
		boolean switched = true;
		int temp;

		while (switched) {
			switched = false;

			for (int i = 0; i < array.length - 1; i++) {

				selected.clear(); // clear the selected rectangles
				selected.add(i); // select i
				selected.add(i + 1); // select i + 1
				m.repaint(); // update the display
				pause(); // pause it

				if (array[i] < array[i + 1]) { // if the previous is less than
												// the next one
					temp = array[i]; // temporary store
					array[i] = array[i + 1]; // switch values
					array[i + 1] = temp;
					switched = true; // a switch has occured
				}
			} // for
		} // while

		selected.clear(); // no more things to be highlighted
		m.repaint(); // show now sorted grid

	} // bubbleSort

	public static void selectionSort(MyCanvas m) {

		int lowest;
		int pos = 0;
		int startPoint = 0;
		int temp;

		for (int i = 0; i < array.length; i++) {
			
			lowest = Integer.MAX_VALUE;
			// ensure that you'll find a value lower
			
			for (int j = startPoint; j < array.length; j++) {
				
				selected.add(j); // add current
				m.repaint(); // show it
				pause();
				selected.remove(j); // remove current
				m.repaint();

				
				// skip the previous ones, as you know they're sorted
				if (array[j] < lowest) { // found new lowest
					selected.remove(pos); // remove old smallest
					lowest = array[j];
					pos = j; // locate the lowest value and store the position
					selected.add(pos); // add new smallest
				}
			}

			temp = array[pos]; // hold onto the lowest value

			array[pos] = array[startPoint];
			// switch the first value with the position of the lowest

			array[startPoint] = temp; // put the lowest at the left

			startPoint++; //  start one space ahead

		}
		
		MyCanvas.sorted = true;
		m.repaint();
		MyCanvas.sorted = false;
	} // selection sort

	public static void fillArray() {

		Random rnd = new Random();

		for (int i = 0; i < array.length; i++) {
			array[i] = rnd.nextInt(30);

		}
	} // fillArray

	public static void pause() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
