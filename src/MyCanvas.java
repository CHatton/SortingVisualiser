import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class MyCanvas extends JComponent {

	public static boolean sorted = false;
	
	@Override
	public void paintComponent(Graphics g) {
		
		

		Graphics2D g2 = (Graphics2D) g;
		int baseline = 760;
		for (int i = 0; i < Sorter.array.length; i++) {
			if(Sorter.selected.contains(i)){
				g2.setColor(Color.RED);
			} else{
				g2.setColor(Color.BLACK);
			}
			g2.fillRect(i * 22, baseline - 10, 20, -(Sorter.array[i]*15 + 10));
		} //for
		
		if(sorted){
			g2.setColor(Color.GREEN);
			for (int i = 0; i < Sorter.array.length; i++) {
				g2.fillRect(i * 22, baseline - 10, 20, -(Sorter.array[i]*15 + 10));
			} //for
		}
		
	}
	
}
