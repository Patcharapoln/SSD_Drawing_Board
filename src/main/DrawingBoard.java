package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter;
	private List<GObject> gObjects;
	private GObject target;

	private int gridSize = 10;

	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}

	public void addGObject(GObject gObject) {
		gObjects.add(gObject);
		repaint();
	}

	public void groupAll() {
		CompositeGObject composite = new CompositeGObject();
		for (GObject g : gObjects) {
			composite.add(g);
		}
		gObjects.clear();
		gObjects.add(composite);
		repaint();
	}

	public void deleteSelected() {
		target.deselected();
	}

	public void clear() {
		gObjects.clear();
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {
		private int x = 0;
		private int y = 0;

		private void deselectAll() {
			for (GObject g : gObjects) {
				g.deselected();
			}
			repaint();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			this.deselectAll();
			this.x = e.getX();
			this.y = e.getY();
			target = null;
			for (GObject gObject : gObjects) {
				if (gObject.pointerHit(x, y)) {
					if(target == null) {
						target = gObject;
						target.selected();
					}
				}
			}
			repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			target.move(e.getX() - x, e.getY() - y);
			x = e.getX();
			y = e.getY();
			repaint();
		}
	}

}