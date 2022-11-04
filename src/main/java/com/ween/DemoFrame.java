package com.ween;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class DemoFrame extends JFrame {

	private JMenuItem exitMenuItem;
	private JMenu scrollingPopupMenu;

	DemoFrame(){
		initComponents();
	}

	private void initComponents(){
		JMenuBar menuBar=new JMenuBar();
		JMenu fileMenu=new JMenu();
		JMenuItem newMenuItem=new JMenuItem();
		JMenuItem openMenuItem=new JMenuItem();
		JMenuItem saveAsMenuItem=new JMenuItem();
		JMenuItem closeMenuItem=new JMenuItem();
		exitMenuItem=new JMenuItem();
		{
			//fileMenu
			fileMenu.setText("文件");
			fileMenu.setMnemonic('F');

			newMenuItem.setText("新建");
			newMenuItem.setMnemonic('N');
			newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			newMenuItem.addActionListener(e->newActionPerformed());
			fileMenu.add(newMenuItem);

			openMenuItem.setText("打开");
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			openMenuItem.setMnemonic('O');
			openMenuItem.addActionListener(e->openActionPerformed());
			fileMenu.add(openMenuItem);
			menuBar.add(fileMenu);
		}

		JMenu editMenu=new JMenu();
		JMenuItem undoMenuItem = new JMenuItem();
		JMenuItem redoMenuItem = new JMenuItem();
		JMenuItem cutMenuItem = new JMenuItem();
		JMenuItem copyMenuItem = new JMenuItem();
		JMenuItem pasteMenuItem = new JMenuItem();
		JMenuItem deleteMenuItem = new JMenuItem();

		JMenu viewMenu=new JMenu();
		JCheckBoxMenuItem checkBoxMenuItem=new JCheckBoxMenuItem();
		JMenu menu1=new JMenu();
		JMenu subViewsMenu=new JMenu();
		JMenu subSbuViewsMenu=new JMenu();

		JMenuItem errorLogViewMenuItem = new JMenuItem();
		JMenuItem searchViewMenuItem = new JMenuItem();
		JMenuItem projectViewMenuItem = new JMenuItem();
		JMenuItem structureViewMenuItem = new JMenuItem();
		JMenuItem propertiesViewMenuItem = new JMenuItem();

		scrollingPopupMenu=new JMenu();
		//窗口设置
		setTitle("Swing Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		setJMenuBar(menuBar);
	}

	private void newActionPerformed(){
		NewDialog newDialog=new NewDialog(this);
		newDialog.setVisible(true);
	}

	private void openActionPerformed(){
		JFileChooser chooser=new JFileChooser();
		chooser.showOpenDialog(this);
	}
}
