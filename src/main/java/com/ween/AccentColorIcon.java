package com.ween;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.icons.FlatAbstractIcon;
import com.formdev.flatlaf.util.ColorFunctions;

import javax.swing.*;
import java.awt.*;

public class AccentColorIcon extends FlatAbstractIcon {

	public static String[] accentColorKeys = {
			"Demo.accent.default", "Demo.accent.blue", "Demo.accent.purple", "Demo.accent.red",
			"Demo.accent.orange", "Demo.accent.yellow", "Demo.accent.green",
	};
	public static String [] accentColorNames={"Default", "Blue", "Purple", "Red", "Orange", "Yellow", "Green"};

	private final String colorKey;

	public AccentColorIcon(String colorKey) {
		super(16, 16, null);
		this.colorKey=colorKey;
	}

	@Override
	protected void paintIcon(Component component, Graphics2D graphics2D) {
		Color color= UIManager.getColor(colorKey);
		if(color==null){
			color=Color.LIGHT_GRAY;
		}else if(!component.isEnabled()){
			color= FlatLaf.isLafDark()? ColorFunctions.shade(color,0.5f):ColorFunctions.tint(color,0.6f);
		}
		graphics2D.setColor(color);
		graphics2D.fillRoundRect(1,1,width-2,height-2,5,5);
	}
}
