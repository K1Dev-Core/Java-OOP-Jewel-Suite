import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class GridUI extends JPanel {

    private DataFile data;
    private boolean hasData = false;
    private DropTarget dropBox;
    private FileDropper dropper;
    private int hoverRow = -1;
    private int hoverCol = -1;
    private javax.swing.Timer animationTimer;
    private float hoverAlpha = 0.0f;
    private JLabel tooltipLabel;
    private boolean showTooltip = false;

    public GridUI(DataFile d) {
        this.data = d;
        this.hasData = (data.getRows() > 0 && data.getCols() > 0);

        setOpaque(true);
        setBackground(Colors.BG_PANEL);
        setLayout(null); // Use absolute positioning for tooltip
        updateSize();
        
        // Create tooltip label
        tooltipLabel = new JLabel();
        tooltipLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tooltipLabel.setForeground(Colors.TEXT_DARK);
        tooltipLabel.setBackground(new Color(255, 255, 220, 240));
        tooltipLabel.setOpaque(true);
        tooltipLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Colors.BORDER_DARK, 1),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        tooltipLabel.setVisible(false);
        add(tooltipLabel);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (hasData) {
                    showCellInfo(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow = -1;
                hoverCol = -1;
                startFadeAnimation(false);
                hideTooltip();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (hasData) {
                    updateHoverCell(e.getX(), e.getY());
                } else {
                    hideTooltip();
                }
            }
        });

        setupDrop();
    }

    private void updateSize() {
        if (hasData) {
            setPreferredSize(new Dimension(
                    Settings.GRID_W * Settings.CELL_DRAW + 30,
                    Settings.GRID_H * Settings.CELL_DRAW + 30
            ));
        } else {
            setPreferredSize(new Dimension(700, 380));
        }
    }

    private void setupDrop() {
        dropper = new FileDropper();
        dropBox = new DropTarget(this, dropper);
    }

    private void updateHoverCell(int mx, int my) {
        int startX = 15;
        int startY = 15;

        int newCol = (mx - startX) / Settings.CELL_DRAW;
        int newRow = (my - startY) / Settings.CELL_DRAW;

        if (newRow >= 0 && newRow < data.getRows() && newCol >= 0 && newCol < data.getCols()) {
            if (newRow != hoverRow || newCol != hoverCol) {
                hoverRow = newRow;
                hoverCol = newCol;
                startFadeAnimation(true);
                showTooltipInfo(mx, my, newRow, newCol);
            }
        } else {
            if (hoverRow != -1 || hoverCol != -1) {
                hoverRow = -1;
                hoverCol = -1;
                startFadeAnimation(false);
                hideTooltip();
            }
        }
    }

    private void startFadeAnimation(boolean fadeIn) {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }

        animationTimer = new javax.swing.Timer(16, e -> {
            if (fadeIn) {
                hoverAlpha = Math.min(1.0f, hoverAlpha + 0.1f);
                if (hoverAlpha >= 1.0f) {
                    animationTimer.stop();
                }
            } else {
                hoverAlpha = Math.max(0.0f, hoverAlpha - 0.15f);
                if (hoverAlpha <= 0.0f) {
                    animationTimer.stop();
                }
            }
            repaint();
        });
        animationTimer.start();
    }

    private void showTooltipInfo(int mx, int my, int row, int col) {
        double vol = data.getVolume(row, col);
        double per = data.getPercent(row, col) * 100;
        
        String tooltipText = String.format("<html>Cell [%d,%d]<br/>Vol: %.0f m³<br/>Gas: %.1f%%</html>", 
                                         row + 1, col + 1, vol, per);
        
        tooltipLabel.setText(tooltipText);
        
        // Calculate tooltip position
        int tooltipX = mx + 15;
        int tooltipY = my - 10;
        
        // Adjust if tooltip goes outside panel bounds
        Dimension tooltipSize = tooltipLabel.getPreferredSize();
        if (tooltipX + tooltipSize.width > getWidth()) {
            tooltipX = mx - tooltipSize.width - 15;
        }
        if (tooltipY < 0) {
            tooltipY = my + 25;
        }
        if (tooltipY + tooltipSize.height > getHeight()) {
            tooltipY = getHeight() - tooltipSize.height - 5;
        }
        
        tooltipLabel.setBounds(tooltipX, tooltipY, tooltipSize.width, tooltipSize.height);
        tooltipLabel.setVisible(true);
        showTooltip = true;
    }

    private void hideTooltip() {
        tooltipLabel.setVisible(false);
        showTooltip = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (hasData) {
            drawGrid(g2);
        } else {
            drawDropZone(g2);
        }

        g2.dispose();
    }

    private void drawGrid(Graphics2D g2) {
        int startX = 15;
        int startY = 15;

        g2.setColor(Colors.BORDER_LIGHT);
        g2.fillRect(startX-5, startY-5,
                Settings.GRID_W * Settings.CELL_DRAW + 10,
                Settings.GRID_H * Settings.CELL_DRAW + 10);

        for (int r = 0; r < data.getRows(); r++) {
            for (int c = 0; c < data.getCols(); c++) {
                int x = startX + c * Settings.CELL_DRAW;
                int y = startY + r * Settings.CELL_DRAW;

                Color cellColor = getCellColor(r, c);
                g2.setColor(cellColor);
                g2.fillRect(x, y, Settings.CELL_DRAW-2, Settings.CELL_DRAW-2);

                // Draw hover effect
                if (r == hoverRow && c == hoverCol && hoverAlpha > 0) {
                    Color hoverColor = new Color(255, 255, 255, (int)(hoverAlpha * 80));
                    g2.setColor(hoverColor);
                    g2.fillRect(x, y, Settings.CELL_DRAW-2, Settings.CELL_DRAW-2);
                    
                    // Add glow effect
                    Color glowColor = new Color(255, 255, 255, (int)(hoverAlpha * 120));
                    g2.setColor(glowColor);
                    g2.setStroke(new BasicStroke(2.0f));
                    g2.drawRect(x-1, y-1, Settings.CELL_DRAW, Settings.CELL_DRAW);
                }

                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(1.0f));
                g2.drawRect(x, y, Settings.CELL_DRAW-2, Settings.CELL_DRAW-2);
            }
        }
    }

    private void drawDropZone(Graphics2D g2) {
        int w = getWidth();
        int h = getHeight();

        g2.setColor(Colors.BG_MAIN);
        g2.fillRect(0, 0, w, h);

        g2.setColor(Colors.BLUE);
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{10, 10}, 0));
        g2.drawRect(20, 20, w-40, h-40);

        g2.setColor(Colors.BLUE);
        Font iconFont = new Font("Dialog", Font.BOLD, 48);
        g2.setFont(iconFont);
        String icon = "📁";
        FontMetrics fm = g2.getFontMetrics();
        int iconX = (w - fm.stringWidth(icon)) / 2;
        int iconY = h/2 - 20;
        g2.drawString(icon, iconX, iconY);

        g2.setColor(Colors.TEXT_DARK);
        g2.setFont(Settings.MID_FONT);
        String title = Settings.FILE_TITLE;
        fm = g2.getFontMetrics();
        int titleX = (w - fm.stringWidth(title)) / 2;
        int titleY = h/2 + 20;
        g2.drawString(title, titleX, titleY);

        g2.setColor(Colors.TEXT_LIGHT);
        g2.setFont(Settings.SMALL_FONT);
        String sub = Settings.FILE_SUB;
        fm = g2.getFontMetrics();
        int subX = (w - fm.stringWidth(sub)) / 2;
        int subY = h/2 + 45;
        g2.drawString(sub, subX, subY);
    }

    private Color getCellColor(int r, int c) {
        int level = data.getLevel(r, c);
        if (level == 0) return Colors.RED;
        if (level == 1) return Colors.YELLOW;
        return Colors.GREEN;
    }

    private void showCellInfo(int mx, int my) {
        int startX = 15;
        int startY = 15;

        int c = (mx - startX) / Settings.CELL_DRAW;
        int r = (my - startY) / Settings.CELL_DRAW;

        if (r >= 0 && r < data.getRows() && c >= 0 && c < data.getCols()) {
            double vol = data.getVolume(r, c);
            double per = data.getPercent(r, c) * 100;
            double top = data.getTop(r, c);
            double bottom = data.getBottom(r, c);

            String info = String.format(Settings.INFO,
                    r+1, c+1, top, bottom, vol, per,
                    getStatusText(data.getLevel(r, c))
            );
            Display.showMessage(this, "Info", info, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String getStatusText(int level) {
        if (level == 0) return Settings.GAS1;
        if (level == 1) return Settings.GAS2;
        return Settings.GAS3;
    }

    public void refresh() {
        this.hasData = (data.getRows() > 0 && data.getCols() > 0);
        hideTooltip();
        hoverRow = -1;
        hoverCol = -1;
        hoverAlpha = 0.0f;
        updateSize();
        revalidate();
        repaint();
    }

    public void setFileCallback(FileCallback callback) {
        dropper.setCallback(callback);
    }

    public interface FileCallback {
        void onFileDropped(File file);
    }

    private class FileDropper extends DropTargetAdapter {
        private FileCallback callback;

        public void setCallback(FileCallback callback) {
            this.callback = callback;
        }

        @Override
        public void dragEnter(DropTargetDragEvent e) {
            if (e.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                e.acceptDrag(DnDConstants.ACTION_COPY);
            } else {
                e.rejectDrag();
            }
        }

        @Override
        public void drop(DropTargetDropEvent e) {
            try {
                e.acceptDrop(DnDConstants.ACTION_COPY);
                Transferable t = e.getTransferable();
                List<File> files = (List<File>) t.getTransferData(DataFlavor.javaFileListFlavor);

                if (!files.isEmpty()) {
                    File file = files.get(0);
                    if (file.getName().toLowerCase().endsWith(".txt") && callback != null) {
                        callback.onFileDropped(file);
                    }
                }
                e.dropComplete(true);
            } catch (Exception ex) {
                e.dropComplete(false);
            }
        }
    }
}