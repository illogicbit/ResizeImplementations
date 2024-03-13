import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/* GROUP 5:
 * Agustin, Henri
 * Hernandez, Keith
 * Manuel, Raymundo III
 * Palaris, John Lex
 * Pueblo, Danielle Joy
 * Villanueva, Iaego
 */

public class Group5 extends JFrame {
    public static void main(String[] args) {
        new Group5();
    }

    public Group5(){
        //Get size of screen.
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        //Set size of frame to percentage of screen size.
        double percentageOfFrame= 0.6;
        Dimension frameSize = new Dimension((int) Math.round(screenSize.getWidth() * percentageOfFrame), (int) Math.round(screenSize.getHeight() * percentageOfFrame));

        //Create new panel to contain buttons & the display area for the box.
        JPanel buttonContainer = new JPanel(new GridBagLayout());
        MyCanvas displayBox = new MyCanvas();

        //Used to set properties of component layout
        GridBagConstraints gbc = new GridBagConstraints();

        //Create buttons and assign functionality.
        JButton incBtn = new JButton("SIZE++");
        incBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBox.increaseSize();
            }
        });

        JButton decBtn = new JButton("SIZE--");
        decBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBox.decreaseSize();
            }
        });

        JButton resBtn = new JButton("RESET SIZE");
        resBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayBox.resetSize();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                displayBox.resetSize();
            }
        });

        //Layout components in window.
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 50, 0, 50);
        gbc.weighty = 0.1;
        buttonContainer.add(incBtn, gbc);
        gbc.gridy = 1;
        buttonContainer.add(decBtn, gbc);
        gbc.gridy = 2;
        buttonContainer.add(resBtn, gbc);

        setLayout(new BorderLayout());

        add(displayBox, BorderLayout.CENTER);
        add(buttonContainer, BorderLayout.EAST);

        setBounds((int) (screenSize.width - frameSize.width) / 2, (int) (screenSize.height - frameSize.height) / 2, frameSize.width, frameSize.height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class MyCanvas extends JComponent{
    private Dimension containerSize;
    private int minSizeH = 50;
    private int minSizeW = 50;
    private Dimension currentSize = new Dimension(minSizeW, minSizeH);
    private int sizeChange = 50;

    //Code used to display the component.
    public void paint(Graphics g){
        super.paintComponent(g);
        containerSize = new Dimension(getWidth(), getHeight());
        g.drawRect((containerSize.width - currentSize.width) / 2, (containerSize.height - currentSize.height) / 2, currentSize.width, currentSize.height);
    }

    public void increaseSize(){
        //Check before increasing size if size is greater than container.
        if(currentSize.height + sizeChange < containerSize.height && currentSize.width + sizeChange < containerSize.width) {
            currentSize.height += sizeChange;
            currentSize.width += sizeChange;
        }
        repaint();
    }

    public void decreaseSize(){
        if(currentSize.height - sizeChange >= minSizeH && currentSize.width - sizeChange >= minSizeW) {
            currentSize.height -= sizeChange;
            currentSize.width -= sizeChange;
        }
        repaint();
    }

    public void resetSize(){
        currentSize.height = minSizeH;
        currentSize.width = minSizeW;
        repaint();
    }
}
