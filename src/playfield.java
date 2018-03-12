import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class playfield {
	private spot minefield[][];
	private int mineAmount;
	private int remainingSpaces;
	private JTextField information;
	
	playfield(int x, int y, int mines){
		mineAmount = mines;
		minefield = new spot[x][y];
		for(int i = 0; i < x; i++) {
			for(int j = 0; j < y; j++) {
				minefield[i][j] = new spot();
			}
		}
		remainingSpaces = (x*y) - mines;
		information = new JTextField();
	}

	spot[][] getMinefield(){
		return minefield;
	}
	
	int checkNeighbours(int x, int y) {
		int counter = 0;
		for(int i = -1; i<=1; i++) {
			for(int j = -1; j<=1; j++) {
				if(i == 0 && j == 0) {
					continue;
				}
				if(x+i<0 || x+i>=minefield.length){
					continue;
				}
				if(y+j<0 || y+j>=minefield[x].length){
					continue;
				}
				if(minefield[x+i][y+j].getMine()) {
					counter++;
				}
				minefield[x][y].addNeighbour(minefield[x+i][y+j]);
			}	
		}
		return counter;
	}
	
	void changeAll(boolean op) {
		for(int i = 0; i < minefield.length; i++) {
			for(int j = 0; j < minefield[0].length; j++) {
				minefield[i][j].open(op);
			}
		}
	}

	void setupMines() {
		Random generator = new Random();
		for(int i = 0; i < mineAmount; i++) {
			int genX = generator.nextInt(minefield.length);
			int genY = generator.nextInt(minefield[0].length);
			if(minefield[genX][genY].getMine() == false) {
				minefield[genX][genY].setMine(true);
			}
			else {
				i = i - 1;
			}
		}
	}
	
	void create(JPanel panel, JPanel infoPanel) {
		setupMines();
		panel.setLayout(new GridLayout(minefield.length, minefield[0].length, 0, 0));
		for(int i = 0; i < minefield.length; i++) {
			for(int j = 0; j < minefield[0].length; j++) {
				minefield[i][j].setNeighbourAmount(checkNeighbours(i,j));
				minefield[i][j].add(panel);
			}
		}
		for(spot[] tmp: minefield) {
			for(final spot a: tmp) {
				a.getButton().addMouseListener(new MouseAdapter() {
					public void mousePressed(MouseEvent e) {
						if(a.isOpened()) {
							return;
						}
						if(SwingUtilities.isRightMouseButton(e)) {
							switch(a.getButton().getText()) {
							case "?": 
								a.getButton().setText("!");
								a.getButton().setBackground(Color.PINK);
								break;
							case "!": 
								a.getButton().setText("");
								a.getButton().setBackground(Color.WHITE);
								a.getButton().setEnabled(true);
								break;
								
							default: 
								a.getButton().setText("?");
								a.getButton().setBackground(Color.YELLOW);
								a.getButton().setEnabled(false);
							}
							return;
						}
						if(a.getButton().isEnabled()) {
							if(check(a) == false) {
								changeAll(true);
							};
						}
						
					}
				});
			}
		}
		information.setEditable(false);
		information.setHorizontalAlignment(JTextField.CENTER);
		information.setBackground(Color.white);
		infoPanel.add(information);
		information.setColumns(10);
	}

	void clearMinefield() {
		for(int i = 0; i < minefield.length; i++) {
			for(int j = 0; j < minefield[0].length; j++) {
				minefield[i][j].setMine(false);
			}
		}
	}

	void restart() {
		clearMinefield();
		setupMines();
		for(int i = 0; i < minefield.length; i++) {
			for(int j = 0; j < minefield[0].length; j++) {
				minefield[i][j].setNeighbourAmount(checkNeighbours(i,j));
				minefield[i][j].open(false);
			}
		}
		remainingSpaces = (minefield.length*minefield[0].length) - mineAmount;
		information.setText("");
	}

	boolean check(spot clicked) {
		
		if(clicked.isOpened())
			return true;
		
		remainingSpaces -= 1;
		
		if(clicked.open(true) == false && clicked.getNeighbourAmount() == 0)
			for(spot a: clicked.getNeighbours())
				check(a);

		if(clicked.open(true) == true) {
			information.setText("You lost");
			return false;
		}
		
		if(remainingSpaces == 0){
			changeAll(true);
			information.setText("You won");
		}
		return true;
	}
}
