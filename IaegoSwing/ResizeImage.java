import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;


public class ResizeImage extends JFrame{

    private JPanel MainPanel;
    private JButton scaleUpButton;
    private JButton scaleDownButton;
    private JButton resetButton;
    private JLabel labelImage;
    private JPanel LeftPanel;
    private Image img;
    final private String imgLink = "C:/Users/Administrator/Desktop/picsNiYago/2_13_24/ChosenShots/_EGG3635.jpg";//"C:/Users/Administrator/Desktop/edit3.jpg";//"C:/Users/Administrator/Downloads/box.png";
    public ResizeImage(){
        setContentPane(MainPanel);
        setImage();
        setTitle("First Java Appication");
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        setSize(1000,500);
        labelImage.setSize(100,100);
        scaleUpButton.setPreferredSize(new Dimension(30, 30));

        setLocationRelativeTo(null);
        setVisible(true);
        scaleUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (labelImage.getHeight() < LeftPanel.getHeight()) {
                    scaleDownButton.setEnabled(true);
                    scaleUp();
                }else{
                    scaleUpButton.setEnabled(false);
                }

            }
        });
        scaleDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (labelImage.getHeight() > 100) {
                    scaleUpButton.setEnabled(true);
                    scaleDown();
                }else{
                    scaleDownButton.setEnabled(false);
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }
    public void setImage(){
        ImageIcon imageFlower = new ImageIcon(imgLink);
        this.img = imageFlower.getImage();
        // Convert the Image to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Scale the BufferedImage
        BufferedImage scaledImage = getScaledImage(bufferedImage, 200, 200);
        labelImage.setIcon(new ImageIcon(scaledImage));
    }

    public void scaleUp(){
        // Convert the Image to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics(); // creates a Graphics2D object from the bufferedImage, which allows us to draw on it.
        g2d.drawImage(img, 0, 0, null); //draws the original Image (img) onto the bufferedImage at coordinates (0, 0), effectively copying the contents of the original image onto the new BufferedImage.
        g2d.dispose(); //disposes of the Object

        // Scale the BufferedImage
        BufferedImage scaledImage = getScaledImage(bufferedImage, labelImage.getWidth() + 100, labelImage.getHeight() + 100);
        labelImage.setIcon(new ImageIcon(scaledImage));
    }
    public void scaleDown(){
        // Convert the Image to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Scale the BufferedImage
        BufferedImage scaledImage = getScaledImage(bufferedImage, labelImage.getWidth() - 100, labelImage.getHeight() - 100);
        labelImage.setIcon(new ImageIcon(scaledImage));
    }
    public void reset(){
        // Convert the Image to BufferedImage
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        // Scale the BufferedImage
        BufferedImage scaledImage = getScaledImage(bufferedImage, 200, 200);
        labelImage.setIcon(new ImageIcon(scaledImage));
    }
    public static BufferedImage getScaledImage(BufferedImage image, int width, int height){
        int imageWidth  = image.getWidth();
        int imageHeight = image.getHeight();

        double scaleX = (double)width/imageWidth; // calculates the scaling factor
        double scaleY = (double)height/imageHeight;
        AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY); //represents the scaling transformation
        AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR); //applies the scaling transformation specified by scaleTransform using bilinear interpolation

        return bilinearScaleOp.filter(
                image,
                new BufferedImage(width, height, image.getType()));
    }

    public static void main(String[] args) {
        new ResizeImage();
    }

}
