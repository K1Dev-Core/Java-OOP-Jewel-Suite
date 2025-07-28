import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ButtonHelper {

    public static JButton createButton(String text, Color bgColor, int width, int height) {
        return new CustomButton(text, bgColor, width, height, null);
    }

    public static JButton createButton(String text, Color bgColor, int width, int height, String iconPath) {
        return new CustomButton(text, bgColor, width, height, iconPath);
    }

    private static class CustomButton extends JButton {
        private boolean isHover = false;
        private boolean isPressed = false;
        private Color bgColor;
        private ImageIcon buttonIcon;

        public CustomButton(String text, Color bgColor, int width, int height, String iconPath) {
            super(text);
            this.bgColor = bgColor;

            if (iconPath != null && new File(iconPath).exists()) {
                try {
                    ImageIcon originalIcon = new ImageIcon(iconPath);
                    Image scaledImage = originalIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
                    buttonIcon = new ImageIcon(scaledImage);
                    setIcon(buttonIcon);
                    setHorizontalTextPosition(SwingConstants.RIGHT);
                    setIconTextGap(10);
                } catch (Exception e) {
                    buttonIcon = null;
                }
            }

            setPreferredSize(new Dimension(width, height));
            setFont(new Font("SF Pro Display", Font.PLAIN, 14));
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setBorder(new EmptyBorder(12, 20, 12, 20));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    isHover = true;
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    isHover = false;
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    isPressed = true;
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isPressed = false;
                    repaint();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            Color currentBg = new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(), 
                isPressed ? 200 : (isHover ? 220 : 180));

            g2.setColor(new Color(0, 0, 0, 20));
            g2.fillRoundRect(1, 2, w-2, h-2, 16, 16);

            g2.setColor(currentBg);
            g2.fillRoundRect(0, 0, w, h, 16, 16);

            g2.setColor(new Color(255, 255, 255, isHover ? 40 : 20));
            g2.fillRoundRect(1, 1, w-2, h/2, 15, 15);

            g2.setColor(new Color(255, 255, 255, 30));
            g2.setStroke(new BasicStroke(1.0f));
            g2.drawRoundRect(0, 0, w-1, h-1, 16, 16);

            super.paintComponent(g);
            g2.dispose();
        }
    }
}