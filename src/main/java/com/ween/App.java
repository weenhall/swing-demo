package com.ween;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatInspector;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;

import javax.swing.*;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

public class App {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			try {
				initUI();
				DemoFrame frame = new DemoFrame();
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static void initUI() throws Exception {
		FlatLaf.registerCustomDefaultsSource("com.ween");
		FlatLightLaf.setup();
		FlatInspector.install("ctrl shift alt X");
		FlatUIDefaultsInspector.install("ctrl shift alt Y");
		InputStreamReader inputStreamReader = new InputStreamReader(Objects.requireNonNull(App.class.getClassLoader().getResourceAsStream("com/ween/zh_CN.properties")), StandardCharsets.UTF_8);
		Properties properties = new Properties();
		properties.load(inputStreamReader);
		properties.forEach(UIManager::put);
	}
}
