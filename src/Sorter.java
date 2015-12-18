import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Sorter extends JFrame {
	public static int[] array = new int[130]; // main array
	public static int[] helper = new int[130]; // for merge sort
	public static final int NUM_UNIQUE = 5; // number of unique values in the
											// few unique button
	public static JButton[] sortButtons = new JButton[4];
	public static JButton[] fillButtons = new JButton[4];
	public static HashSet<Integer> selected = new HashSet<Integer>();
	// for highlighting in RED
	public static HashSet<Integer> sorted = new HashSet<Integer>();
	// highlighting in GREEN
	public static final int SHORT_WAIT = 5;
	public static final int LONG_WAIT = 15;
	public static MyCanvas m = new MyCanvas();

	public static void main(String[] args) {
		start();
	}

	public static void start() {
		randomFill(); // fill the array randomly
		Sorter s = new Sorter(); // start the board
	}

	public Sorter() {

		JFrame window = new JFrame("Sorting Visualiser - Cian Hatton");
		window.setLayout(new BorderLayout());
		window.setSize(1215, 800);
		window.add(m); // main canvas
		window.setVisible(true);
		window.getContentPane().setBackground(new Color(0x090A0A)); // black
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		window.setResizable(false);

		JPanel sortBtns = new JPanel(); // add all buttons to this
		sortBtns.setLayout(new FlowLayout());
		sortBtns.setPreferredSize(new Dimension(150,150));
	
		JPanel fillBtns = new JPanel();
		fillBtns.setLayout(new FlowLayout(FlowLayout.CENTER)); // 4 rows, 2 cols

		// ===== Button Functions =====
		JButton randomFillButton = new JButton();
		randomFillButton.setText("Random Fill");
		randomFillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				randomFill();
			}
		});

		JButton fewUniqueButton = new JButton();
		fewUniqueButton.setText("Few Unique");
		fewUniqueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fewUnique();
			}
		});

		JButton almostSortedButton = new JButton();
		almostSortedButton.setText("Almost Sorted");
		almostSortedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				almostSorted();
			}
		});

		JButton reverseFillButton = new JButton();
		reverseFillButton.setText("Reversed");
		reverseFillButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reverseFill();
			}
		});

		JButton bubbleSortButton = new JButton();
		bubbleSortButton.setText("Bubble Sort");
		bubbleSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> bubbleSortButton());
			}
		});

		JButton selectionSortButton = new JButton();
		selectionSortButton.setText("Selection Sort");
		selectionSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> selectionSortButton());
			}
		});

		JButton insertionSortButton = new JButton();
		insertionSortButton.setText("Insertion Sort");
		insertionSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> insertionSortButton());
			}
		});

		JButton mergeSortButton = new JButton();
		mergeSortButton.setText("Merge Sort");
		mergeSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CompletableFuture.runAsync(() -> buttonMergeSort(0, array.length - 1));
			}
		});

		// ===== End Button Functions =====
		fillButtons[0] = randomFillButton;
		fillButtons[1] = fewUniqueButton;
		fillButtons[2] = almostSortedButton;
		fillButtons[3] = reverseFillButton;

		sortButtons[0] = bubbleSortButton;
		sortButtons[1] = selectionSortButton;
		sortButtons[2] = insertionSortButton;
		sortButtons[3] = mergeSortButton;
		// populate arrays with the buttons

		// add the buttons

		for (JButton b : sortButtons) {
		
			JLabel j = new JLabel();
			sortBtns.add(b); // add each button to the window
			j.setPreferredSize(new Dimension(80,80));
			j.setVisible(true); // fill out space
			sortBtns.add(j);
			
		}

		JLabel text = new JLabel();
		text.setText("Fill Options");
		fillBtns.add(text);

		for (JButton b : fillButtons) {
			
			fillBtns.add(b); // add each button to the window
			// fillBtns.add(new JLabel()); // fill up space
		
		}

		window.add(fillBtns, BorderLayout.SOUTH);
		window.add(sortBtns, BorderLayout.EAST);
		// put all buttons on the bottom of the window
	} // Sorter Constructor

	// FILL BUTTONS
	public static void randomFill() {
		Random rnd = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = rnd.nextInt(100);
		}
		m.repaint();
	} // fillArray

	public static void fewUnique() {
		Random rnd = new Random();
		int[] uniqueNums = new int[NUM_UNIQUE];

		for (int i = 0; i < uniqueNums.length; i++) {
			uniqueNums[i] = rnd.nextInt(100);
		} // pick few different values

		int index = 0;
		for (int i = 0; i < NUM_UNIQUE; i++) {
			for (int j = 0; j < array.length / NUM_UNIQUE; j++) {
				array[index++] = uniqueNums[i];
			} // fill up from the few values
		}
		m.repaint();
	} // fewUnique

	public static void almostSorted() {
		Random rnd = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = i; // fill array already sorted
		}
		for (int i = 0; i < 10; i++) { // swap 10 number pairs

			int pos1 = rnd.nextInt(100);
			int pos2 = rnd.nextInt(100);

			int temp = array[pos1];

			array[pos1] = array[pos2];
			array[pos2] = temp;
		}
		m.repaint();
	} // almostSorted

	public static void reverseFill() {
		for (int i = 0; i < array.length; i++) {
			array[i] = (array.length - 1) - i;
		} // fill up the array backwards
		m.repaint();
	}

	// END FILL BUTTONS

	// SORT BUTTONS

	public static void bubbleSortButton() {
		toggleButtons();
		bubbleSort();
		makeGreen();
		toggleButtons();
	}

	public static void selectionSortButton() {
		toggleButtons();
		selectionSort();
		makeGreen();
		toggleButtons();
	}

	public static void insertionSortButton() {
		toggleButtons();
		insertionSort();
		makeGreen();
		toggleButtons();
	}

	public static void buttonMergeSort(int low, int high) {
		toggleButtons();
		mergeSort(low, high);
		makeGreen();
		toggleButtons();
	}

	// END SORT BUTTONS

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

				if (array[i] > array[i + 1]) {
					// if the previous is greater than the next one
					temp = array[i + 1]; // temporary store
					array[i + 1] = array[i]; // switch values
					array[i] = temp;
					switched = true; // a switch has occurred
				}
			} // for
		} // while

		selected.clear(); // no more things to be highlighted
		selected.remove(array.length - 1);
		m.repaint();
	} // bubbleSort

	public static void selectionSort() {

		int lowest;
		int pos = 0;
		int startPoint = 0;
		int temp;

		for (int i = 0; i < array.length; i++) {

			lowest = Integer.MAX_VALUE;
			// ensure that you'll find a value lower
			sorted.add(i - 1);
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
	} // insertion sort

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

		while (i <= middle && j <= high) {

			selected.add(i);
			m.repaint();
			pause(LONG_WAIT);
			selected.remove(i);

			if (helper[i] <= helper[j]) {
				array[k] = helper[i]; // i is the lower of the 2
				i++;// one is gone from that section of the array
			} else {
				array[k] = helper[j];
				j++;
			}

			sorted.add(k);
			m.repaint();
			k++;
		}
		sorted.clear();
		m.repaint();
		// copy the rest of the LHS of the array to the target array
		while (i <= middle) {
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
		} // pause so user can see
	} // pause

	public static void makeGreen() {
		for (int i = 0; i < array.length; i++) {
			sorted.add(i);
			m.repaint();
			pause(5);
		}
		sorted.clear();
		// highlight all buttons green
	} // makeGreen

	public static void toggleButtons() {
		for (JButton b : sortButtons) {
			b.setEnabled(!b.isEnabled());
			// disable or enable all buttons
		}
		for (JButton b : fillButtons) {
			b.setEnabled(!b.isEnabled());
			// disable or enable all buttons
		}
	}

}
