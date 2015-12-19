import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JComponent;

public class Display extends JComponent {
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		int baseline = 740;
		for (int i = 0; i < Sorter.array.length; i++) {
			if(Sorter.partial.contains(i)){
				g2.setColor(new Color(0x1F8FB5));// blue
			} else if(Sorter.sorted.contains(i)){
				g2.setColor(new Color(0x72D12A)); // green
			}else if(Sorter.selected.contains(i)){
				g2.setColor(new Color(0xC90000)); // red
			} else{
				g2.setColor(new Color(0xCCDDE3)); // white
			}
			g2.fillRect(i * 8 + 10, baseline - 15, 6, -(Sorter.array[i]*5 + 10));
		} //for
	}
}
