import javax.swing.JPanel;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;

public class spot {
	
	private JButton button;
	private boolean mine;
	private boolean opened;
	private int neighbourAmount;
	private ArrayList<spot> neighbours = new ArrayList<spot>();
	
	
	public spot() 
	{
		button = new JButton("");
		button.setBackground(Color.WHITE);
		mine = false;
		neighbourAmount = 0;
		setOpened(false);
	}
	
	public JButton getButton() 
	{
		return button;
	}
	
	public boolean getMine() 
	{
		return mine;
	}
	
	public void setMine(boolean t) 
	{
		mine = t;
	}
	public ArrayList<spot> getNeighbours() 
	{
		return neighbours;
	}
	public void addNeighbour(spot neighbour)
	{
		getNeighbours().add(neighbour);
	}
	
	public int getNeighbourAmount() 
	{
		return neighbourAmount;
	}
	
	public void disable() 
	{
		button.setEnabled(false);
	}
	
	public boolean open(boolean option) 
	{
		if(option) {
			if(mine == false) {
				button.setText(Integer.toString(neighbourAmount));
				button.setBackground(Color.green);
			}
			else {
				button.setText("M");
				button.setBackground(Color.RED);
			}
			setOpened(true);
		}
		else {
			button.setText("");
			button.setBackground(Color.WHITE);
			setOpened(false);
		}
		button.setEnabled(!option);
		return mine;
	}
	
	public void add(JPanel panel) 
	{
		panel.add(button);
	}

	public boolean isOpened() 
	{
		return opened;
	}

	public void setOpened(boolean opened) 
	{
		this.opened = opened;
	}

	public void setNeighbourAmount(int amount) 
	{
		neighbourAmount = amount;
	}
	

}

