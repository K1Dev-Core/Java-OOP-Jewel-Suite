import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

// คลาสแม่ MainApp สําหรับการจัดการหลักของโปรแกรม
public class MainApp extends JFrame {

    private DataFile data;
    private GridUI grid;
    private JTextField waterInput;
    private JLabel totalLabel;
    private JLabel statusLabel;
    private JButton loadButton;
    private JButton clearButton;
    private JPanel buttonPanel;

    // สร้างโปรแกรม
    public MainApp() {
        setupLook();
        data = new DataFile();
        setupWindow();
        makeContent();
        updateDisplay();
        setVisible(true);
    }

    // ตั้งค่าการแสดงผล
    private void setupLook() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ตั้งค่าหน้าต่าง หลัก
    private void setupWindow() {
        setTitle(Settings.APP_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Settings.WIN_W, Settings.WIN_H);

        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(Settings.WINDOW_MENU);
        
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(248, 248, 248, 250),
                    0, getHeight(), new Color(240, 240, 240, 230)
                );
                g2.setPaint(gradient);
                g2.fillRect(0, 0, getWidth(), getHeight());
                
                g2.dispose();
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);
    }

    private void makeContent() {
        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new EmptyBorder(25, 25, 25, 25));
        main.setOpaque(false);

        makeTopBar(main);
        makeMainArea(main);

        getContentPane().add(main);
    }

    // สร้างแถบบน
    private void makeTopBar(JPanel parent) {
        JPanel top = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Colors.BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/2, 19, 19);
                
                g2.setColor(Colors.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                
                g2.dispose();
            }
        };
        top.setBorder(new EmptyBorder(20, 25, 20, 25));
        top.setOpaque(false);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);

        JLabel icon = makeIcon();
        JLabel title = new JLabel(Settings.APP_TITLE);
        title.setFont(new Font("SF Pro Display", Font.BOLD, 28));
        title.setForeground(Colors.BLUE);

        JLabel version = new JLabel(Settings.APP_VERSION);
        version.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
        version.setForeground(Colors.TEXT_LIGHT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        titlePanel.add(version);

        left.add(icon);
        left.add(Box.createHorizontalStrut(10));
        left.add(titlePanel);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setOpaque(false);

        JButton about = ButtonHelper.createButton(Settings.BTN_ABOUT, Colors.ORANGE, 100, 45,
                "src/res/icons/about.png");
        about.addActionListener(e -> Display.showAbout(this));

        JButton exit = ButtonHelper.createButton(Settings.BTN_EXIT, Colors.DANGER_RED, 100, 45,
                "src/res/icons/exit.png");
        exit.addActionListener(e -> exitApp());

        right.add(about);
        right.add(exit);

        top.add(left, BorderLayout.WEST);
        top.add(right, BorderLayout.EAST);

        parent.add(top, BorderLayout.NORTH);
    }

    private void makeMainArea(JPanel parent) {
        JPanel middle = new JPanel(new BorderLayout(25, 25));
        middle.setOpaque(false);
        middle.setBorder(new EmptyBorder(25, 0, 25, 0));

        makeLeftPanel(middle);
        makeRightPanel(middle);

        parent.add(middle, BorderLayout.CENTER);
    }

    // สร้างแถบซ้าย
    private void makeLeftPanel(JPanel parent) {
        JPanel left = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Colors.BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/3, 19, 19);
                
                g2.setColor(Colors.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                
                g2.dispose();
            }
        };
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBorder(new EmptyBorder(25, 25, 25, 25));
        left.setPreferredSize(new Dimension(320, 0));
        left.setOpaque(false);

        JLabel controlTitle = new JLabel("Control");
        controlTitle.setFont(new Font("SF Pro Display", Font.BOLD, 24));
        controlTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        controlTitle.setForeground(Colors.BLUE);

        JPanel input = makeInputArea();
        JPanel legend = makeLegendArea();
        JPanel results = makeResultArea();

        left.add(controlTitle);
        left.add(Box.createVerticalStrut(25));
        left.add(input);
        left.add(Box.createVerticalStrut(25));
        left.add(legend);
        left.add(Box.createVerticalStrut(25));
        left.add(results);
        left.add(Box.createVerticalGlue());

        parent.add(left, BorderLayout.WEST);
    }

    // สร้างแถบอินพุต
    private JPanel makeInputArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new JLabel(Settings.INPUT_LABEL);
        label.setFont(new Font("SF Pro Display", Font.PLAIN, 15));
        label.setForeground(Colors.TEXT_LIGHT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        waterInput = new JTextField(String.valueOf(data.getWater())) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Colors.BG_INPUT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2.setColor(new Color(255, 255, 255, 80));
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/2, 11, 11);
                
                g2.setColor(Colors.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                
                super.paintComponent(g);
                g2.dispose();
            }
        };
        waterInput.setMaximumSize(new Dimension(280, 45));
        waterInput.setPreferredSize(new Dimension(280, 45));
        waterInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        waterInput.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        waterInput.setForeground(Colors.TEXT_DARK);
        waterInput.setOpaque(false);
        waterInput.setBorder(new EmptyBorder(12, 18, 12, 18));

        JButton calc = ButtonHelper.createButton(Settings.BTN_CALC, Colors.BLUE, 280, 50, Settings.ICON_BTN_CALC);
        calc.setMaximumSize(new Dimension(280, 50));
        calc.setAlignmentX(Component.LEFT_ALIGNMENT);
        calc.addActionListener(e -> calculate());

        loadButton = ButtonHelper.createButton(Settings.BTN_LOAD, Colors.SUCCESS_GREEN, 280, 50,
                Settings.ICON_BTN_LOAD);
        loadButton.setMaximumSize(new Dimension(280, 50));
        loadButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        loadButton.addActionListener(e -> openFile());

        clearButton = ButtonHelper.createButton(Settings.BTN_CLEAR, Colors.DANGER_RED, 280, 50,
                Settings.ICON_BTN_CLEAR);
        clearButton.setMaximumSize(new Dimension(280, 50));
        clearButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearButton.addActionListener(e -> clearFile());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(280, 50));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(10));
        panel.add(waterInput);
        panel.add(Box.createVerticalStrut(20));
        panel.add(calc);
        panel.add(Box.createVerticalStrut(15));
        panel.add(buttonPanel);

        return panel;
    }

    // สร้างแสดงลำดับแก๊ส
    private JPanel makeLegendArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel title = new JLabel(Settings.LEGEND_TITLE);
        title.setFont(new Font("SF Pro Display", Font.PLAIN, 15));
        title.setForeground(Colors.TEXT_LIGHT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel red = makeColorBox(Settings.NO_GAS, Colors.RED);
        JPanel yellow = makeColorBox(Settings.LOW_GAS, Colors.YELLOW);
        JPanel green = makeColorBox(Settings.HIGH_GAS, Colors.GREEN);

        panel.add(title);
        panel.add(Box.createVerticalStrut(12));
        panel.add(red);
        panel.add(Box.createVerticalStrut(8));
        panel.add(yellow);
        panel.add(Box.createVerticalStrut(8));
        panel.add(green);

        return panel;
    }

    private JPanel makeColorBox(String text, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(300, 30));

        JPanel box = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                
                g2.setColor(Colors.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 8, 8);
                
                g2.dispose();
            }
        };
        box.setPreferredSize(new Dimension(24, 24));
        box.setOpaque(false);

        JLabel label = new JLabel(text);
        label.setFont(new Font("SF Pro Display", Font.PLAIN, 14));
        label.setForeground(Colors.TEXT_LIGHT);

        panel.add(box);
        panel.add(Box.createHorizontalStrut(12));
        panel.add(label);

        return panel;
    }

    // สร้างแสดงผล
    private JPanel makeResultArea() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel title = new JLabel(Settings.RESULT_TITLE);
        title.setFont(new Font("SF Pro Display", Font.PLAIN, 15));
        title.setForeground(Colors.TEXT_LIGHT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        totalLabel = new JLabel(Settings.TOTAL_GAS);
        totalLabel.setFont(new Font("SF Pro Display", Font.BOLD, 16));
        totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        totalLabel.setForeground(Colors.TEXT_DARK);

        statusLabel = new JLabel(Settings.STATUS_READY);
        statusLabel.setFont(new Font("SF Pro Display", Font.PLAIN, 13));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setForeground(Colors.SUCCESS);

        panel.add(title);
        panel.add(Box.createVerticalStrut(12));
        panel.add(totalLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(statusLabel);

        return panel;
    }

    // สร้างแถบขวา
    private void makeRightPanel(JPanel parent) {
        JPanel right = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2.setColor(Colors.BG_PANEL);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2.setColor(new Color(255, 255, 255, 60));
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()/3, 19, 19);
                
                g2.setColor(Colors.BORDER_LIGHT);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
                
                g2.dispose();
            }
        };
        right.setBorder(new EmptyBorder(25, 25, 25, 25));
        right.setOpaque(false);

        JLabel title = new JLabel(Settings.GRID_TITLE);
        title.setFont(new Font("SF Pro Display", Font.BOLD, 24));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Colors.PURPLE);

        grid = new GridUI(data);
        grid.setFileCallback(file -> {
            if (data.loadFromFile(file.getAbsolutePath())) {
                updateDisplay();
                statusLabel.setText(Settings.STATUS_LOAD + file.getName());
                statusLabel.setForeground(Colors.SUCCESS);
                Display.showMessage(this, Settings.STATUS_OK, Settings.STATUS_DONE, JOptionPane.INFORMATION_MESSAGE);
            } else {
                statusLabel.setText(Settings.STATUS_FAIL);
                statusLabel.setForeground(Colors.DANGER);
                Display.showMessage(this, Settings.STATUS_ERROR, Settings.STATUS_CHECK, JOptionPane.ERROR_MESSAGE);
            }
        });

        grid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (!hasData()) {
                    openFile();
                }
            }
        });

        JLabel info = new JLabel(Settings.GRID_INFO);
        info.setFont(new Font("SF Pro Display", Font.PLAIN, 12));
        info.setForeground(Colors.TEXT_LIGHT);
        info.setHorizontalAlignment(SwingConstants.CENTER);

        right.add(title, BorderLayout.NORTH);
        right.add(grid, BorderLayout.CENTER);
        right.add(info, BorderLayout.SOUTH);

        parent.add(right, BorderLayout.CENTER);
    }

    // สร้างไอคอน
    private JLabel makeIcon() {
        try {
            ImageIcon icon = new ImageIcon(Settings.APP_ICON_PATH);
            Image scaled = icon.getImage().getScaledInstance(Settings.ICON_SIZE_SMALL, Settings.ICON_SIZE_SMALL,
                    Image.SCALE_SMOOTH);
            ImageIcon newIcon = new ImageIcon(scaled);
            return new JLabel(newIcon);
        } catch (Exception e) {
            JLabel fallback = new JLabel("💎");
            fallback.setFont(new Font("Segoe UI Emoji", Font.PLAIN, Settings.ICON_SIZE_SMALL));
            return fallback;
        }
    }

    private boolean hasData() {
        return data.getRows() > 0 && data.getCols() > 0;
    }

    // เปิดไฟล์
    private void openFile() {
        setupFileChooser();

        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(Settings.SELECT_FILE_TITLE);
        fc.setFileFilter(new FileNameExtensionFilter(Settings.FILE_OF_TYPE, "txt"));
        fc.setCurrentDirectory(new File("src"));

        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (data.loadFromFile(file.getAbsolutePath())) {
                updateDisplay();
                statusLabel.setText(Settings.STATUS_LOAD + file.getName());
                statusLabel.setForeground(Colors.SUCCESS);

            } else {
                statusLabel.setText(Settings.STATUS_FAIL);
                statusLabel.setForeground(Colors.DANGER);
                Display.showMessage(this, Settings.STATUS_ERROR, Settings.STATUS_CHECK, JOptionPane.ERROR_MESSAGE);
            }
        }

        resetFileChooser();
    }

    // ตั้งค่าการเลือกไฟล์ ปรับสีคอมโพเนนต์ ที่สร้างจาก UIManager swing
    private void setupFileChooser() {
        UIManager.put("FileChooser.background", Colors.BG_MAIN);
        UIManager.put("Panel.background", Colors.BG_MAIN);
        UIManager.put("Label.foreground", Colors.TEXT_DARK);
        UIManager.put("List.background", Colors.BG_PANEL);
        UIManager.put("List.foreground", Colors.TEXT_DARK);
        UIManager.put("List.selectionBackground", Colors.BLUE);
        UIManager.put("List.selectionForeground", Color.WHITE);
        UIManager.put("TextField.background", Colors.BG_INPUT);
        UIManager.put("TextField.foreground", Colors.TEXT_DARK);
        UIManager.put("Button.background", Colors.BG_PANEL);
        UIManager.put("Button.foreground", Colors.TEXT_DARK);
        UIManager.put("ComboBox.background", Colors.BG_INPUT);
        UIManager.put("ComboBox.foreground", Colors.TEXT_DARK);
    }

    // รีเซ็ตการเลือกไฟล์
    private void resetFileChooser() {
        UIManager.put("FileChooser.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Label.foreground", null);
        UIManager.put("List.background", null);
        UIManager.put("List.foreground", null);
        UIManager.put("List.selectionBackground", null);
        UIManager.put("List.selectionForeground", null);
        UIManager.put("TextField.background", null);
        UIManager.put("TextField.foreground", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("ComboBox.background", null);
        UIManager.put("ComboBox.foreground", null);
    }

    // คํานวณ
    private void calculate() {
        if (!hasData()) {
            Display.showMessage(this, Settings.LANG_WARNING, Settings.LANG_WARNING2, JOptionPane.WARNING_MESSAGE);
            statusLabel.setText(Settings.LANG_WARNING3);
            statusLabel.setForeground(Colors.DANGER);
            return;
        }

        try {
            double water = Double.parseDouble(waterInput.getText());
            data.setWater(water);
            updateDisplay();
            statusLabel.setText(Settings.LANG_WARNING4);
            statusLabel.setForeground(Colors.SUCCESS);
        } catch (NumberFormatException e) {
            Display.showMessage(this, Settings.LANG_WARNING5, Settings.LANG_WARNING6, JOptionPane.ERROR_MESSAGE);
            statusLabel.setText(Settings.LANG_WARNING7);
            statusLabel.setForeground(Colors.DANGER);
        }
    }

    // เคลียร์
    private void clearFile() {
        data.clearData();
        waterInput.setText(String.valueOf(Settings.DEFAULT_FLUID));
        updateDisplay();
        updateButtonDisplay();
        statusLabel.setText("File cleared");
        statusLabel.setForeground(Colors.TEXT_LIGHT);
    }

    private void updateButtonDisplay() {
        buttonPanel.removeAll();

        if (hasData()) {
            buttonPanel.add(clearButton);
        } else {
            buttonPanel.add(loadButton);
        }

        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    // อัพเดทการแสดงผล
    private void updateDisplay() {
        double total = data.getTotalVolume();
        totalLabel.setText(String.format(Settings.TOTAL_GAS, total));
        grid.refresh();
        updateButtonDisplay();
    }

    // ออกจากโปรแกรม
    private void exitApp() {
        UIManager.put("OptionPane.background", Colors.BG_MAIN);
        UIManager.put("Panel.background", Colors.BG_MAIN);
        UIManager.put("OptionPane.messageForeground", Colors.TEXT_DARK);
        UIManager.put("OptionPane.messageFont", Settings.MID_FONT);
        UIManager.put("Button.background", Colors.BG_PANEL);
        UIManager.put("Button.foreground", Colors.TEXT_DARK);
        UIManager.put("Button.border", BorderFactory.createLineBorder(Colors.BORDER_LIGHT));

        int result = JOptionPane.showConfirmDialog(
                this,
                Settings.EXIT_MSG,
                Settings.EXIT_TITLE,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }

        // รีเซ็ตการแสดงผล UIManager swing
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("OptionPane.messageForeground", null);
        UIManager.put("OptionPane.messageFont", null);
        UIManager.put("Button.background", null);
        UIManager.put("Button.foreground", null);
        UIManager.put("Button.border", null);
    }

    // รัน
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainApp();
        });
    }
}