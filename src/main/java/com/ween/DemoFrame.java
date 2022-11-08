package com.ween;


import java.awt.*;
import javax.swing.*;
import javax.swing.text.StyleContext;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGUtils;
import com.formdev.flatlaf.extras.FlatUIDefaultsInspector;
import com.formdev.flatlaf.extras.components.FlatButton;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.LoggingFacade;
import com.ween.component.BasicComponentsPanel;
import com.ween.component.OptionPanePanel;
import com.ween.component.extras.ExtrasPanel;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LC;
import net.miginfocom.layout.UnitValue;
import net.miginfocom.swing.MigLayout;
import static com.ween.AccentColorIcon.accentColorKeys;
import static com.ween.AccentColorIcon.accentColorNames;

class DemoFrame extends JFrame {

	private final String[] availableFontFamilyNames;
	private int initialFontMenuItemCount = -1;

	JMenuBar menuBar;
	private JMenuItem exitMenuItem;
	private JMenu scrollingPopupMenu;
	private JMenuItem htmlMenuItem;
	private JMenu fontMenu;
	private JMenu optionsMenu;
	private JCheckBoxMenuItem windowDecorationsCheckBoxMenuItem;
	private JCheckBoxMenuItem menuBarEmbeddedCheckBoxMenuItem;
	private JCheckBoxMenuItem unifiedTitleBarMenuItem;
	private JCheckBoxMenuItem showTitleBarIconMenuItem;
	private JCheckBoxMenuItem underlineMenuSelectionMenuItem;
	private JCheckBoxMenuItem alwaysShowMnemonicsMenuItem;
	private JCheckBoxMenuItem animatedLafChangeMenuItem;
	private JMenuItem aboutMenuItem;
	private JToolBar toolBar;
	private JTabbedPane tabbedPane;
	private JLabel accentColorLabel;
	private final JToggleButton [] accentColorButtons=new JToggleButton[accentColorNames.length];

	DemoFrame() {
		availableFontFamilyNames = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames().clone();
		Arrays.sort(availableFontFamilyNames);

		initComponents();
		updateFontMenuItems();
		initAccentColors();
		//窗体图标
		setIconImages(FlatSVGUtils.createWindowIconImages("/com/ween/coffee.svg"));
	}

	private void initAccentColors(){
		accentColorLabel=new JLabel("高亮颜色：");
		toolBar.add(Box.createHorizontalGlue());
		toolBar.add(accentColorLabel);

		ButtonGroup buttonGroup=new ButtonGroup();
		for (int i = 0; i < accentColorButtons.length; i++) {
			accentColorButtons[i]=new JToggleButton(new AccentColorIcon(accentColorKeys[i]));
			accentColorButtons[i].setToolTipText(accentColorNames[i]);
			accentColorButtons[i].addActionListener(this::accentColorChanged);
			toolBar.add(accentColorButtons[i]);
			buttonGroup.add(accentColorButtons[i]);
		}
		accentColorButtons[0].setSelected(true);
		UIManager.addPropertyChangeListener(e->{
			if("lookAndFeel".equals(e.getPropertyName())){
				updateAccentColorButtons();
			}
		});
		updateAccentColorButtons();
	}

	private void initComponents() {
		menuBar= new JMenuBar();
		JMenu fileMenu = new JMenu();
		JMenuItem newMenuItem = new JMenuItem();
		JMenuItem openMenuItem = new JMenuItem();
		JMenuItem saveAsMenuItem = new JMenuItem();
		JMenuItem closeMenuItem = new JMenuItem();
		exitMenuItem = new JMenuItem();
		{
			//fileMenu
			fileMenu.setText("文件");
			fileMenu.setMnemonic('F');

			newMenuItem.setText("新建");
			newMenuItem.setMnemonic('N');
			newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			newMenuItem.addActionListener(e -> newActionPerformed());
			fileMenu.add(newMenuItem);

			openMenuItem.setText("打开");
			openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			openMenuItem.setMnemonic('O');
			openMenuItem.addActionListener(e -> openActionPerformed());
			fileMenu.add(openMenuItem);

			saveAsMenuItem.setText("保存");
			saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			saveAsMenuItem.setMnemonic('S');
			saveAsMenuItem.addActionListener(e -> saveAsActionPerformed());
			fileMenu.add(saveAsMenuItem);
			fileMenu.addSeparator();

			closeMenuItem.setText("关闭");
			closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			closeMenuItem.setMnemonic('C');
			closeMenuItem.addActionListener(this::menuItemActionPerformed);
			fileMenu.add(closeMenuItem);
			fileMenu.addSeparator();

			exitMenuItem.setText("退出");
			exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			exitMenuItem.setMnemonic('X');
			exitMenuItem.addActionListener(e -> exitActionPerformed());
			fileMenu.add(exitMenuItem);
			menuBar.add(fileMenu);
		}
		JMenu editMenu = new JMenu();
		JMenuItem undoMenuItem = new JMenuItem();
		JMenuItem redoMenuItem = new JMenuItem();
		JMenuItem cutMenuItem = new JMenuItem();
		JMenuItem copyMenuItem = new JMenuItem();
		JMenuItem pasteMenuItem = new JMenuItem();
		JMenuItem deleteMenuItem = new JMenuItem();
		{
			//editMenu
			editMenu.setText("编辑");
			editMenu.setMnemonic('E');

			undoMenuItem.setText("撤销");
			undoMenuItem.setIcon(new FlatSVGIcon("com/ween/icons/undo.svg"));
			undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			undoMenuItem.setMnemonic('U');
			undoMenuItem.addActionListener(this::menuItemActionPerformed);
			editMenu.add(undoMenuItem);

			redoMenuItem.setText("重做");
			redoMenuItem.setIcon(new FlatSVGIcon("com/ween/icons/redo.svg"));
			redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			redoMenuItem.setMnemonic('R');
			redoMenuItem.addActionListener(this::menuItemActionPerformed);
			editMenu.add(redoMenuItem);
			editMenu.addSeparator();

			cutMenuItem.setText("剪切");
			cutMenuItem.setIcon(new FlatSVGIcon("com/ween/icons/menu-cut.svg"));
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			cutMenuItem.setMnemonic('C');
			editMenu.add(cutMenuItem);

			copyMenuItem.setText("复制");
			copyMenuItem.setIcon(new FlatSVGIcon("com/ween/icons/copy.svg"));
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			closeMenuItem.setMnemonic('O');
			editMenu.add(copyMenuItem);

			pasteMenuItem.setText("粘贴");
			pasteMenuItem.setIcon(new FlatSVGIcon("com/ween/icons/menu-paste.svg"));
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			pasteMenuItem.setMnemonic('P');
			editMenu.add(pasteMenuItem);
			editMenu.addSeparator();

			deleteMenuItem.setText("删除");
			deleteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
			deleteMenuItem.setMnemonic('D');
			deleteMenuItem.addActionListener(this::menuItemActionPerformed);
			editMenu.add(deleteMenuItem);
			menuBar.add(editMenu);
		}
		JMenu viewMenu = new JMenu();
		JCheckBoxMenuItem checkBoxMenuItem = new JCheckBoxMenuItem();
		JMenu menu1 = new JMenu();
		JMenu subViewsMenu = new JMenu();
		JMenu subSbuViewsMenu = new JMenu();
		JMenuItem errorLogViewMenuItem = new JMenuItem();
		JMenuItem searchViewMenuItem = new JMenuItem();
		JMenuItem projectViewMenuItem = new JMenuItem();
		JMenuItem structureViewMenuItem = new JMenuItem();
		JMenuItem propertiesViewMenuItem = new JMenuItem();
		scrollingPopupMenu = new JMenu();
		JMenuItem disabledItem = new JMenuItem();
		htmlMenuItem = new JMenuItem();
		JRadioButtonMenuItem radioButtonMenuItem1 = new JRadioButtonMenuItem();
		JRadioButtonMenuItem radioButtonMenuItem2 = new JRadioButtonMenuItem();
		JRadioButtonMenuItem radioButtonMenuItem3 = new JRadioButtonMenuItem();
		ButtonGroup buttonGroup1 = new ButtonGroup();
		buttonGroup1.add(radioButtonMenuItem1);
		buttonGroup1.add(radioButtonMenuItem2);
		buttonGroup1.add(radioButtonMenuItem3);
		{
			viewMenu.setText("查看");
			viewMenu.setMnemonic('V');

			checkBoxMenuItem.setText("显示工具栏");
			checkBoxMenuItem.setSelected(true);
			checkBoxMenuItem.setMnemonic('T');
			checkBoxMenuItem.addActionListener(this::menuItemActionPerformed);
			viewMenu.add(checkBoxMenuItem);

			menu1.setText("一级菜单");
			menu1.setMnemonic('V');

			subViewsMenu.setText("二级菜单");
			subViewsMenu.setMnemonic('S');
			subSbuViewsMenu.setText("三级菜单");
			subSbuViewsMenu.setMnemonic('U');
			errorLogViewMenuItem.setText("错误日志");
			errorLogViewMenuItem.setMnemonic('E');
			errorLogViewMenuItem.addActionListener(this::menuItemActionPerformed);
			subSbuViewsMenu.add(errorLogViewMenuItem);
			subViewsMenu.add(subSbuViewsMenu);

			searchViewMenuItem.setText("查找");
			searchViewMenuItem.setMnemonic('S');
			searchViewMenuItem.addActionListener(this::menuItemActionPerformed);
			subViewsMenu.add(searchViewMenuItem);
			menu1.add(subViewsMenu);

			projectViewMenuItem.setText("项目");
			projectViewMenuItem.setMnemonic('P');
			projectViewMenuItem.addActionListener(this::menuItemActionPerformed);
			menu1.add(projectViewMenuItem);

			structureViewMenuItem.setText("结构");
			structureViewMenuItem.setMnemonic('T');
			structureViewMenuItem.addActionListener(this::menuItemActionPerformed);
			menu1.add(structureViewMenuItem);

			propertiesViewMenuItem.setText("属性");
			propertiesViewMenuItem.setMnemonic('O');
			propertiesViewMenuItem.addActionListener(this::menuItemActionPerformed);
			menu1.add(propertiesViewMenuItem);
			viewMenu.add(menu1);

			scrollingPopupMenu.setText("滚动菜单");
			for (int i = 0; i < 50; i++) {
				scrollingPopupMenu.add("Item " + i);
			}
			viewMenu.add(scrollingPopupMenu);

			disabledItem.setText("Disabled Item");
			disabledItem.setEnabled(false);
			viewMenu.add(disabledItem);

			htmlMenuItem.setText("<html>some <b color=\"red\">HTML</b> <i color=\"blue\">text</i></html>");
			viewMenu.add(htmlMenuItem);
			viewMenu.addSeparator();

			radioButtonMenuItem1.setText("Details");
			radioButtonMenuItem1.setSelected(true);
			radioButtonMenuItem1.setMnemonic('D');
			radioButtonMenuItem1.addActionListener(this::menuItemActionPerformed);
			radioButtonMenuItem2.setText("Small Icons");
			radioButtonMenuItem2.setMnemonic('S');
			radioButtonMenuItem2.addActionListener(this::menuItemActionPerformed);
			radioButtonMenuItem3.setText("Large Icons");
			radioButtonMenuItem3.setMnemonic('L');
			radioButtonMenuItem3.addActionListener(this::menuItemActionPerformed);
			viewMenu.add(radioButtonMenuItem1);
			viewMenu.add(radioButtonMenuItem2);
			viewMenu.add(radioButtonMenuItem3);

			menuBar.add(viewMenu);
		}
		fontMenu = new JMenu();
		JMenuItem restoreFontMenuItem = new JMenuItem();
		JMenuItem incrFontMenuItem = new JMenuItem();
		JMenuItem decrFontMenuItem = new JMenuItem();
		{
			fontMenu.setText("字体");

			restoreFontMenuItem.setText("还原字体");
			restoreFontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			restoreFontMenuItem.addActionListener(e -> restoreFont());
			fontMenu.add(restoreFontMenuItem);

			incrFontMenuItem.setText("放大字体");
			incrFontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			incrFontMenuItem.addActionListener(e -> incrFont());
			fontMenu.add(incrFontMenuItem);

			decrFontMenuItem.setText("缩小字体");
			decrFontMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			decrFontMenuItem.addActionListener(e -> decrFont());
			fontMenu.add(decrFontMenuItem);

			menuBar.add(fontMenu);
		}
		optionsMenu = new JMenu();
		windowDecorationsCheckBoxMenuItem = new JCheckBoxMenuItem();
		menuBarEmbeddedCheckBoxMenuItem = new JCheckBoxMenuItem();
		unifiedTitleBarMenuItem = new JCheckBoxMenuItem();
		showTitleBarIconMenuItem = new JCheckBoxMenuItem();
		underlineMenuSelectionMenuItem = new JCheckBoxMenuItem();
		alwaysShowMnemonicsMenuItem = new JCheckBoxMenuItem();
		animatedLafChangeMenuItem = new JCheckBoxMenuItem();
		JMenuItem showHintsMenuItem = new JMenuItem();
		JMenuItem showUIDefaultsInspectorMenuItem = new JMenuItem();
		{
			optionsMenu.setText("设置");

			windowDecorationsCheckBoxMenuItem.setText("窗口装饰");
			windowDecorationsCheckBoxMenuItem.setSelected(true);
			windowDecorationsCheckBoxMenuItem.addActionListener(e -> windowDecorationsChanged());
			optionsMenu.add(windowDecorationsCheckBoxMenuItem);

			menuBarEmbeddedCheckBoxMenuItem.setText("嵌入式菜单栏");
			menuBarEmbeddedCheckBoxMenuItem.setSelected(true);
			menuBarEmbeddedCheckBoxMenuItem.addActionListener(e -> menuBarEmbeddedChanged());
			optionsMenu.add(menuBarEmbeddedCheckBoxMenuItem);

			unifiedTitleBarMenuItem.setText("统一窗口标题栏");
			unifiedTitleBarMenuItem.setSelected(true);
			unifiedTitleBarMenuItem.addActionListener(e -> unifiedTitleBar());
			optionsMenu.add(unifiedTitleBarMenuItem);

			showTitleBarIconMenuItem.setText("显示窗口图标");
			showTitleBarIconMenuItem.setSelected(true);
			showTitleBarIconMenuItem.addActionListener(e ->showTitleBarIcon());
			optionsMenu.add(showTitleBarIconMenuItem);

			underlineMenuSelectionMenuItem.setText("菜单选项高亮下划线");
			underlineMenuSelectionMenuItem.setSelected(true);
			underlineMenuSelectionMenuItem.addActionListener(e->underlineMenuSelection());
			optionsMenu.add(underlineMenuSelectionMenuItem);

			alwaysShowMnemonicsMenuItem.setText("始终显示缩写");
			alwaysShowMnemonicsMenuItem.setSelected(true);
			alwaysShowMnemonicsMenuItem.addActionListener(e->alwaysShowMnemonics());
			optionsMenu.add(alwaysShowMnemonicsMenuItem);

			animatedLafChangeMenuItem.setText("动画效果");
			animatedLafChangeMenuItem.setSelected(true);
			animatedLafChangeMenuItem.addActionListener(e->animatedLafChangeChanged());
			optionsMenu.add(animatedLafChangeMenuItem);

			showHintsMenuItem.setText("显示提示");
			showHintsMenuItem.addActionListener(e->showHintsChanged());
			optionsMenu.add(showHintsMenuItem);

			showUIDefaultsInspectorMenuItem.setText("显示用户界面默认检查器");
			showUIDefaultsInspectorMenuItem.addActionListener(e->showUIDefaultsInspector());
			optionsMenu.add(showUIDefaultsInspectorMenuItem);
			menuBar.add(optionsMenu);
		}
		JMenu helpMenu=new JMenu();
		aboutMenuItem=new JMenuItem();
		{
			helpMenu.setText("帮助");
			helpMenu.setMnemonic('H');

			aboutMenuItem.setText("关于");
			aboutMenuItem.setMnemonic('A');
			aboutMenuItem.addActionListener(this::menuItemActionPerformed);
			helpMenu.add(aboutMenuItem);
			menuBar.add(helpMenu);
		}
		addUsersToolBarButton();

		Container contentPane=getContentPane();
		contentPane.setLayout(new BorderLayout());
		JPanel contentPanel=new JPanel();
		tabbedPane=new JTabbedPane();
		BasicComponentsPanel basicComponentsPanel=new BasicComponentsPanel();
		ExtrasPanel extrasPanel=new ExtrasPanel();
		OptionPanePanel optionPanePanel=new OptionPanePanel();
		{
			contentPanel.setLayout(new MigLayout(
					"insets dialog,hidemode 3",
					// columns
					"[grow,fill]",
					// rows
					"[grow,fill]"));
			MigLayout layout= (MigLayout) contentPanel.getLayout();
			LC lc= ConstraintParser.parseLayoutConstraint((String)layout.getLayoutConstraints());
			UnitValue [] insets=lc.getInsets();
			lc.setInsets(new UnitValue[]{
					insets[0],
					insets[1],
					new UnitValue(0,UnitValue.PIXEL,null),
					insets[3]
			});
			layout.setLayoutConstraints(lc);
		}
		{
			contentPane.setLayout(new MigLayout("insets dialog,hidemode 3",
					// columns
					"[grow,fill]",
					// rows
					"[grow,fill]"));
			tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
			tabbedPane.addTab("Basic Component",basicComponentsPanel);
			tabbedPane.addTab("Option Pane",optionPanePanel);
			tabbedPane.addTab("Extras",extrasPanel);
			contentPane.add(tabbedPane,"cell 0 0");
			contentPane.add(contentPanel,BorderLayout.CENTER);
		}
		toolBar=new JToolBar();
		JButton backButton = new JButton();
		JButton forwardButton = new JButton();
		JButton cutButton = new JButton();
		JButton copyButton = new JButton();
		JButton pasteButton = new JButton();
		JButton refreshButton = new JButton();
		JToggleButton showToggleButton = new JToggleButton();
		{
			toolBar.setMargin(new Insets(3, 3, 3, 3));

			//---- backButton ----
			backButton.setIcon(new FlatSVGIcon("com/ween/icons/back.svg"));
			backButton.setToolTipText("返回");
			toolBar.add(backButton);

			//---- forwardButton ----
			forwardButton.setIcon(new FlatSVGIcon("com/ween/icons/forward.svg"));
			forwardButton.setToolTipText("向前");
			toolBar.add(forwardButton);
			toolBar.addSeparator();

			//---- cutButton ----
			cutButton.setIcon(new FlatSVGIcon("com/ween/icons/menu-cut.svg"));
			cutButton.setToolTipText("剪切");
			toolBar.add(cutButton);

			//---- copyButton ----
			copyButton.setIcon(new FlatSVGIcon("com/ween/icons/copy.svg"));
			copyButton.setToolTipText("复制");
			toolBar.add(copyButton);

			//---- pasteButton ----
			pasteButton.setIcon(new FlatSVGIcon("com/ween/icons/menu-paste.svg"));
			pasteButton.setToolTipText("粘贴");
			toolBar.add(pasteButton);
			toolBar.addSeparator();

			//---- refreshButton ----
			refreshButton.setIcon(new FlatSVGIcon("com/ween/icons/refresh.svg"));
			refreshButton.setToolTipText("刷新");
			toolBar.add(refreshButton);
			toolBar.addSeparator();

			//---- showToggleButton ----
			showToggleButton.setIcon(new FlatSVGIcon("com/ween/icons/show.svg"));
			showToggleButton.setSelected(true);
			showToggleButton.setToolTipText("Show Details");
			toolBar.add(showToggleButton);

			contentPane.add(toolBar,BorderLayout.NORTH);
		}
		//窗口设置
		setTitle("Swing Demo");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setJMenuBar(menuBar);
	}

	private void accentColorChanged( ActionEvent e ) {
		String accentColor = accentColorKeys[0];
		for( int i = 0; i < accentColorButtons.length; i++ ) {
			if( accentColorButtons[i].isSelected() ) {
				accentColor = accentColorKeys[i];
				break;
			}
		}

		FlatLaf.setGlobalExtraDefaults( (!Objects.equals(accentColor, accentColorKeys[0]))
				? Collections.singletonMap( "@accentColor", "$" + accentColor )
				: null );

		Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
		try {
			FlatLaf.setup( lafClass.newInstance() );
			FlatLaf.updateUI();
		} catch( InstantiationException | IllegalAccessException ex ) {
			LoggingFacade.INSTANCE.logSevere( null, ex );
		}
	}

	private void updateAccentColorButtons(){
		Class<? extends LookAndFeel> lafClass = UIManager.getLookAndFeel().getClass();
		boolean isAccentColorSupported =
				(lafClass == FlatLightLaf.class) ||
						(lafClass == FlatDarkLaf.class) ||
						(lafClass == FlatIntelliJLaf.class) ||
						(lafClass == FlatDarculaLaf.class);

		accentColorLabel.setVisible( isAccentColorSupported );
		for( int i = 0; i < accentColorButtons.length; i++ )
			accentColorButtons[i].setVisible( isAccentColorSupported );
	}

	private void newActionPerformed() {
		NewDialog newDialog = new NewDialog(this);
		newDialog.setVisible(true);
	}

	private void openActionPerformed() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
	}

	private void saveAsActionPerformed() {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(this);
	}

	private void menuItemActionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, e.getActionCommand(), "Menu Item", JOptionPane.PLAIN_MESSAGE));
	}

	private void fontFamilyChanged(ActionEvent e) {
		String fontFamily = e.getActionCommand();
		FlatAnimatedLafChange.showSnapshot();
		Font font = UIManager.getFont("defaultFont");
		Font newFont = StyleContext.getDefaultStyleContext().getFont(fontFamily, font.getStyle(), font.getSize());
		newFont = FlatUIUtils.nonUIResource(newFont);
		UIManager.put("defaultFont", newFont);
		FlatLaf.updateUI();
		FlatAnimatedLafChange.hideSnapshotWithAnimation();
	}

	private void fontSizeChanged(ActionEvent e) {
		String fontSizeStr = e.getActionCommand();

		Font font = UIManager.getFont("defaultFont");
		Font newFont = font.deriveFont((float) Integer.parseInt(fontSizeStr));
		UIManager.put("defaultFont", newFont);

		FlatLaf.updateUI();
	}

	private void windowDecorationsChanged() {
		boolean windowDecorations = windowDecorationsCheckBoxMenuItem.isSelected();
		FlatLaf.setUseNativeWindowDecorations(windowDecorations);
		menuBarEmbeddedCheckBoxMenuItem.setEnabled(windowDecorations);
		unifiedTitleBarMenuItem.setEnabled(windowDecorations);
		showTitleBarIconMenuItem.setEnabled(windowDecorations);
	}

	private void menuBarEmbeddedChanged() {
		UIManager.put("TitlePane.menuBarEmbedded", menuBarEmbeddedCheckBoxMenuItem.isSelected());
		FlatLaf.revalidateAndRepaintAllFramesAndDialogs();
	}

	private void unifiedTitleBar() {
		UIManager.put("TitlePane.unifiedBackground", unifiedTitleBarMenuItem.isSelected());
		FlatLaf.repaintAllFramesAndDialogs();
	}

	private void showTitleBarIcon() {
		boolean showIcon = showTitleBarIconMenuItem.isSelected();
		getRootPane().putClientProperty(FlatClientProperties.TITLE_BAR_SHOW_ICON, showIcon);
		UIManager.put("TitlePane.showIcon", showIcon);
	}

	private void underlineMenuSelection(){
		UIManager.put("MenuItem.selectionType",underlineMenuSelectionMenuItem.isSelected());
	}

	private void alwaysShowMnemonics(){
		UIManager.put("Component.hideMnemonics",!alwaysShowMnemonicsMenuItem.isSelected());
		repaint();
	}

	private void animatedLafChangeChanged(){
		System.setProperty("flatlaf.animatedLafChange",String.valueOf(animatedLafChangeMenuItem.isSelected()));
	}

	private void showHintsChanged(){
		clearHints();
		showHints();
	}

	private void showUIDefaultsInspector(){
		FlatUIDefaultsInspector.show();
	}

	void updateFontMenuItems() {
		if (initialFontMenuItemCount < 0) {
			initialFontMenuItemCount = fontMenu.getItemCount();
		} else {
			for (int i = fontMenu.getItemCount() - 1; i >= initialFontMenuItemCount; i--) {
				fontMenu.remove(i);
			}
		}
		Font currentFont = UIManager.getFont("Label.font");
		String currentFamily = currentFont.getFamily();
		String currentSize = Integer.toString(currentFont.getSize());
		fontMenu.addSeparator();
		ArrayList<String> families = new ArrayList<>(Arrays.asList(
				"Arial", "Cantarell", "Comic Sans MS", "DejaVu Sans",
				"Dialog", "Liberation Sans", "Noto Sans", "Roboto",
				"SansSerif", "Segoe UI", "Serif", "Tahoma", "Ubuntu", "Verdana"));
		if (!families.contains(currentFamily)) {
			families.add(currentFamily);
		}
		families.sort(String.CASE_INSENSITIVE_ORDER);

		ButtonGroup familiesGroup = new ButtonGroup();
		for (String family : families) {
			if (Arrays.binarySearch(availableFontFamilyNames, family) < 0) {
				continue;
			}
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(family);
			item.setSelected(family.endsWith(currentFamily));
			item.addActionListener(this::fontFamilyChanged);
			fontMenu.add(item);
			familiesGroup.add(item);
		}

		fontMenu.addSeparator();
		ArrayList<String> sizes = new ArrayList<>(Arrays.asList(
				"10", "11", "12", "14", "16", "18", "20", "24", "28"));
		if (!sizes.contains(currentSize)) {
			sizes.add(currentSize);
		}
		sizes.sort(String.CASE_INSENSITIVE_ORDER);

		ButtonGroup sizesGroup = new ButtonGroup();
		for (String size : sizes) {
			JCheckBoxMenuItem item = new JCheckBoxMenuItem(size);
			item.setSelected(size.equals(currentSize));
			item.addActionListener(this::fontSizeChanged);
			fontMenu.add(item);

			sizesGroup.add(item);
		}

		// enabled/disable items
		boolean enabled = UIManager.getLookAndFeel() instanceof FlatLaf;
		for (Component item : fontMenu.getMenuComponents()) {
			item.setEnabled(enabled);
		}
	}

	private void restoreFont() {
		UIManager.put("defaultFont", null);
		updateFontMenuItems();
		FlatLaf.updateUI();
	}

	private void incrFont() {
		Font font = UIManager.getFont("defaultFont");
		Font incrFont = font.deriveFont((float) (font.getSize() + 1));
		UIManager.put("defaultFont", incrFont);
		updateFontMenuItems();
		FlatLaf.updateUI();
	}

	private void decrFont() {
		Font font = UIManager.getFont("defaultFont");
		Font decrFont = font.deriveFont((float) Math.max(font.getSize() - 1, 10));
		UIManager.put("defaultFont", decrFont);
		updateFontMenuItems();
		FlatLaf.updateUI();
	}

	private void showHints(){

	}

	private void clearHints(){

	}

	private void addUsersToolBarButton(){
		FlatButton usersButton=new FlatButton();
		usersButton.setIcon(new FlatSVGIcon("com/ween/icons/users.svg"));
		usersButton.setButtonType(FlatButton.ButtonType.toolBarButton);
		usersButton.setFocusable(false);
		usersButton.addActionListener(e -> JOptionPane.showMessageDialog( null, "Hello User! How are you?", "User", JOptionPane.INFORMATION_MESSAGE ));
		menuBar.add(Box.createGlue());
		menuBar.add(usersButton);
	}

	@Override
	public void dispose() {
		super.dispose();
		FlatUIDefaultsInspector.hide();
	}

	private void exitActionPerformed() {
		dispose();
	}

}
