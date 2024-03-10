import java.awt.*;
import java.awt.event.*;

public class HenriAWT {
    public static void main(String[] args) {
        //Create a window for the graphics
        Frame mainFrame = new Frame("Test");
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints gridconst = new GridBagConstraints();
        mainFrame.setLayout(gridbag);
        System.out.println(mainFrame.getLayout());

        //Add functionality to close window when close button is pressed.
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainFrame.dispose();
            }
        });

        //Initialize and layout Resizing Image canvas

        ResizingImage c = new ResizingImage();
        c.setMinimumSize(new Dimension(640, 480));
        c.setPreferredSize(new Dimension(640, 480));

        gridconst.gridheight = 2;
        gridconst.weightx = 1;
        gridconst.weighty = 1;
        gridconst.fill = GridBagConstraints.BOTH;
        gridconst.ipadx = 100;
        gridconst.ipady = 100;
        gridconst.insets = new Insets(10, 10, 10, 10);
        mainFrame.add(c, gridconst);

        TextArea infoText = new TextArea("", 4, 20, TextArea.SCROLLBARS_NONE);
        updateText(infoText, c);

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateText(infoText, c);
            }
        });

        gridconst.weightx = 0.5;

        gridconst.gridheight = 1;
        gridconst.gridx = 2;
        gridconst.gridy = 0;
        gridconst.fill = GridBagConstraints.BOTH;
        mainFrame.add(infoText, gridconst);

        Panel btnPanel = new Panel(new GridLayout(3, 1, 0, 20));

        Button sizeUpBtn = new Button("Increase Size");
        sizeUpBtn.addActionListener(e -> {
            c.increaseSize();
            updateText(infoText, c);
        });
        Button sizeDownBtn = new Button("Decrease Size");
        sizeDownBtn.addActionListener(e -> {
            c.decreaseSize();
            updateText(infoText, c);
        });
        Button sizeResetBtn = new Button("Reset Size");
        sizeResetBtn.addActionListener(e -> {
            c.resetSize();
            updateText(infoText, c);
        });

        btnPanel.add(sizeUpBtn);
        btnPanel.add(sizeDownBtn);
        btnPanel.add(sizeResetBtn);

        gridconst.gridheight = 1;
        gridconst.gridx = 2;
        gridconst.gridy = 1;
        gridconst.fill = GridBagConstraints.NONE;
        mainFrame.add(btnPanel, gridconst);

        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setMinimumSize(mainFrame.getSize());
    }

    static void updateText(TextArea infoArea, ResizingImage img) {
        String infoString = "Size Info: \n\nSize Percentage: %.2f\nWidth: %d\nHeight: %d";

        infoArea.setText(String.format(infoString, img.getImageSize(),
                img.getImageDimensions().width,
                img.getImageDimensions().height));
    }
}

class ResizingImage extends Canvas {
    private double size;
    private final double sizeChange = 0.05;
    private final double sizeDefault = 0.05;
    int width;
    int height;


    ResizingImage() {
        super();
        size = sizeDefault;
    }

    public void increaseSize() {
        size = Math.min(size + sizeChange, 1);

        this.repaint();
    }

    public void decreaseSize() {
        if (size > sizeDefault) {
            size = Math.max(size - sizeChange, 0.1);
        }
        this.repaint();
    }

    public void resetSize() {
        size = sizeDefault;
        this.repaint();
    }

    public double getImageSize() {
        return size;
    }

    public Dimension getImageDimensions() {
        return new Dimension((int) (this.getWidth() * size), (int) (this.getHeight() * size));
    }

    public void paint(Graphics g) {
        int smallestAxis;
        if (this.getWidth() < this.getHeight()) {
            smallestAxis = this.getWidth();
        } else {
            smallestAxis = this.getHeight();
        }

        width = (int) (smallestAxis * size);
        height = (int) (smallestAxis * size);

        Graphics2D cg = (Graphics2D) g;
        cg.translate((this.getWidth() - width) / 2, (this.getHeight() - height) / 2);
        cg.setPaint(new Color(Color.BLUE.getRGB()));
        cg.fill3DRect(0, 0, width, height, true);
        cg.setPaint(new Color(Color.BLACK.getRGB()));
        cg.setStroke(new BasicStroke(5));
        cg.translate(-((this.getWidth() - width) / 2), -((this.getHeight() - height) / 2));
        cg.drawRect(0, 0, this.getWidth(), this.getHeight());


    }
}
