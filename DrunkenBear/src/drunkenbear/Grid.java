package drunkenbear;

import java.awt.Color;

/**
 * 
 * @author Jeffrey
 */
public class Grid {

	private Patch[][] _grid;

	public Grid() {
		_grid = new Patch[1][1];
		_grid[0][0] = new Patch(this, 0, 0);
	}

	public Grid(int x, int y) {
		_grid = new Patch[y][x];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				_grid[j][i] = new Patch(this, i, j);
			}
		}
	}

	public int getWidth() {
		return _grid[0].length;
	}

	public int getHeight() {
		return _grid.length;
	}

	public Patch getPatch(int x, int y) {
		if (x > getWidth() || x < 0) {
			x = 0;
		}
		if (y > getHeight() || y < 0) {
			y = 0;
		}
		return _grid[y][x];
	}

	/*
	 * Shouldn't ever need this. public void setPatch(int x, int y, Patch p) { if
	 * (x > getWidth() || x < 0){ x = 0; } if (y > getHeight()|| y < 0 ){ y = 0; }
	 * _grid[y][x] = p; }
	 */
	public void setPColor(int x, int y, Color color) {
		getPatch(x, y).setPColor(color);
	}

	public void resize(int x, int y) {
		Patch[][] newGrid = new Patch[y][x];
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (i < getHeight() && j < getWidth()) {
					newGrid[i][j] = _grid[i][j];
				} else
					newGrid[i][j] = new Patch(this, j, i);
			}
		}
		_grid = newGrid;
	}

	public String toString() {
		String ans = "";
		for (int i = 0; i < getHeight(); i++) {
			for (int j = 0; j < getWidth(); j++) {
				ans += getPatch(j, i);
			}
			ans += "\n";
		}
		return ans;
	}

	public static void main(String[] args) {
		Grid foo = new Grid(5, 7);
		System.out.println(foo);
		foo.resize(10, 4);
		System.out.println(foo);
	}
}
