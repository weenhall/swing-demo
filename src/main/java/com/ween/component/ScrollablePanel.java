package com.ween.component;

import com.formdev.flatlaf.util.UIScale;

import javax.swing.*;
import java.awt.*;

public class ScrollablePanel extends JPanel implements Scrollable{

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return UIScale.scale(new Dimension(400,400));
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle rectangle, int i, int i1) {
		return UIScale.scale(50);
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction ) {
		return (orientation == SwingConstants.VERTICAL) ? visibleRect.height : visibleRect.width;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
}
