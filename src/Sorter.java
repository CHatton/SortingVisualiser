import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Sorter extends JFrame {
	public static int[] array = new int[235];
	public static int[] helper = new int[235];
	public static HashSet<Integer> selected = new HashSet<Integer>();
	public static HashSet<Integer> sorted = new HashSet<Integer>();
	public static final int SHORT_WAIT = 2;
	public static final int LONG_WAIT = 15;
	public static MyCanvas m = new MyCanvas();

	public static void main(String[] args) {
		Sorter s = new Sorter(); // start the board
	}

	public Sorter() {
		fillArray(); // fill the array randomly
		JFrame window = new JFrame("Sorting Visualiser - Cian Hatton");
		window.setLayout(new BorderLayout());
		window.setSize(1200, 800);
		window.add(m); // main canvas
		window.setVisible(true);
		window.getContentPane().setBackground(new Color(0x090A0A)); // black
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setResizable(false);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(4, 1));

		JButton randomFillButton = new JButton();
		randomFillButton.setText("Random Fill");
		randomFillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillArray();
			}
		});

		JButton bubbleSortButton = new JButton();
		bubbleSortButton.setText("Bubble Sort");
		bubbleSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> bubbleSort());
			}
		});

		JButton selectionSortButton = new JButton();
		selectionSortButton.setText("Selection Sort");
		selectionSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> selectionSort());
			}
		});

		JButton insertionSortButton = new JButton();
		insertionSortButton.setText("Insertion Sort");
		insertionSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> insertionSort());
			}
		});
		
		JButton mergeSortButton = new JButton();
		mergeSortButton.setText("Merge Sort");
		mergeSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> buttonMergeSort(0,array.length-1));
	
			}
		});

		// add the buttons
		buttons.add(randomFillButton);
		buttons.add(bubbleSortButton);
		buttons.add(selectionSortButton);
		buttons.add(insertionSortButton);
		buttons.add(mergeSortButton);

		window.add(buttons, BorderLayout.EAST);
		// put all buttons on the RHS of the window
	} // Sorter Constructor

	public static void bubbleSort() {
		boolean switched = true;
		int temp;

		while (switched) {
			switched = false;

			for (int i = 0; i < array.length - 1; i++) {

				selected.clear(); // clear the selected rectangles
				selected.add(i); // select i
				selected.add(i + 1); // select i + 1
				m.repaint(); // update the display
				pause(LONG_WAIT); // pause it

				if (array[i] < array[i + 1]) { // if the previous is less than
												// the next one
					temp = array[i]; // temporary store
					array[i] = array[i + 1]; // switch values
					array[i + 1] = temp;
					switched = true; // a switch has occurred
				}
			} // for
		} // while

		selected.clear(); // no more things to be highlighted
		selected.remove(array.length - 1);
		m.repaint();

		makeGreen();
	} // bubbleSort

	public static void selectionSort() {

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
				pause(LONG_WAIT);
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

			startPoint++; // start one space ahead

		}

		selected.remove(array.length - 1);
		m.repaint();

		makeGreen();

	} // selection sort

	public static void insertionSort() {

		for (int i = 1; i < array.length; i++) { // start at second value

			int valueToSort = array[i]; // value we're looking at
			int j = i;

			selected.add(i + 1); // value to sort
			m.repaint();
			pause(LONG_WAIT);

			while (j > 0 && array[j - 1] > valueToSort) {
				// if there is a value lower behind the current value
				// we go backwards from the index we're at to the start

				selected.add(j - 1);
				m.repaint();
				pause(SHORT_WAIT);
				selected.remove(j - 1);
				m.repaint();

				array[j] = array[j - 1]; // swap values
				j--; // until we get to the start
			}
			array[j] = valueToSort;

			selected.remove(i + 1);
			m.repaint();

		} // for

		selected.remove(array.length - 1);
		m.repaint();

		makeGreen();
	} // insertion sort

	public static void fillArray() {

		Random rnd = new Random();

		for (int i = 0; i < array.length; i++) {
			array[i] = rnd.nextInt(100);
		}
		m.repaint();
	} // fillArray

	
	public static void buttonMergeSort(int low, int high){
		mergeSort(low,high);
		makeGreen();
	}
	
	public static void mergeSort(int low, int high) {

	
		
		if (low < high) { // int not - the collection is already sorted
			int middle = low + (high - low) / 2;
			// get the index of the element in the middle
			mergeSort(low, middle);
			// sort the RHS of array
			mergeSort(middle + 1, high);
			// sort LHS of array
			merge(low, middle, high);
			// combine them both
		}

	} // mergeSort

	public static void merge(int low, int middle, int high) {
		// copy both parts into the helper array
		for (int i = low; i <= high; i++) {
			helper[i] = array[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;
		/*
		 * copy the smallest values from either the left or the right side back
		 * to the original array
		 */

		while(i <= middle && j <= high){
			
			selected.add(i);
			m.repaint();
			pause(LONG_WAIT);
			selected.remove(i);
			
			if(helper[i] <= helper[j]){
				array[k] = helper[i]; // i is the lower of the 2
				i++;// one is gone from that section of the array
			}else{
				array[k] = helper[j];
				j++;
			}
			k++;
			sorted.add(k);
			m.repaint();
		}
		sorted.clear();
		m.repaint();
		// copy the rest of the LHS of the array to the target array
		while(i <= middle){
			array[k] = helper[i];
			k++;
			i++;
		}
		selected.clear();
		m.repaint();
	}

	public static void pause(int milisecs) {
		try {
			Thread.sleep(milisecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} // pause

	public static void makeGreen() {
		for (int i = 0; i < array.length; i++) {
			sorted.add(i);
			m.repaint();
			pause(3);
		}
		sorted.clear();
	} // makeGreen
}
