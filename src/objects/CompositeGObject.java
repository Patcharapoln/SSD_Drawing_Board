package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import main.DrawingBoard;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		super.move(dX, dY);
		for(GObject g : gObjects){
			g.move(dX, dY);
		}
	}

	public void recalculateRegion() {
		int xMax = gObjects.get(0).x + gObjects.get(0).width;
		int xMin = gObjects.get(0).x;
		int yMax = gObjects.get(0).y + gObjects.get(0).height;
		int yMin = gObjects.get(0).y;
		for (GObject e : gObjects) {
			if (e.x > xMax)
				xMax = e.x;
			else if (e.x < xMin)
				xMin = e.x;
			if (e.y > yMax)
				yMax = e.y;
			else if (e.y < yMin)
				yMin = e.y;
		}
		super.height = xMax - xMin;
		super.width = yMax - yMin;
		super.x = xMin;
		super.y = yMin;
	}

	@Override
	public void paintObject(Graphics g) {
		for (GObject object : this.gObjects) {
			object.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		g.drawString("Group", x, y);
	}

}
