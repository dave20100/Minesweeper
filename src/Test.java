import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class Test {

	@org.junit.jupiter.api.Test
	void test() {
		spot testSpot = new spot();
		
		assertEquals(testSpot.getMine(), false);
		assertEquals(testSpot.open(true), false);
		testSpot.setMine(true);
		assertEquals(testSpot.open(true), true);
		assertEquals(testSpot.open(false), true);

		assertEquals(testSpot.isOpened(), false);
		testSpot.setNeighbourAmount(12);
		assertEquals(testSpot.getNeighbourAmount(), 12);
		spot neighbour = new spot();
		
		
		ArrayList<spot> neighbours = new ArrayList<spot>() ;
		neighbours.add(neighbour);
		testSpot.addNeighbour(neighbour);
		assertEquals(testSpot.getNeighbours(), neighbours);
		assertNotNull(testSpot.getButton());
		
		testSpot.disable();
		assertEquals(testSpot.getButton().isEnabled(), false);
		
		mainFrame testFrame = new mainFrame();
		
		playfield testPlayfield = new playfield(10,10,30);
		assertEquals(testPlayfield.check(testSpot), false);
		testSpot.setMine(false);
		assertEquals(testPlayfield.check(testSpot), true);
		
		assertEquals(testPlayfield.getMinefield().length, 10);
		assertEquals(testPlayfield.getMinefield()[0].length, 10);
		assertEquals(checkArray(testPlayfield.getMinefield(), true), true);
		testPlayfield.changeAll(true);
		assertEquals(checkArray(testPlayfield.getMinefield(), true), false);
		assertEquals(checkArray(testPlayfield.getMinefield(), false), true);
		
		playfield testPlayfield2 = new playfield(10,10,0);
		assertEquals(testPlayfield2.check(testPlayfield2.getMinefield()[0][0]), true);
		
		testPlayfield.restart();
		assertEquals(checkArray(testPlayfield.getMinefield(), true), true);
		
	}

	boolean checkArray(spot[][] tmp, boolean dec) {
		for(spot[] vec: tmp) {
			for(spot s: vec) {
				if(s.isOpened() == dec) {
					return false;
				}
			}
		}
		return true;
	}
}
