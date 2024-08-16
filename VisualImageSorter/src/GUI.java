import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GUI {
    static JLabel label = new JLabel();
    static JFrame frame = new JFrame();
    static JPanel BottomButtonpanel = new JPanel();

    public static void mainWindow() {
        // Load the image
        ImageIcon image = resizeImg(Main.ImageList.get(0).getAbsolutePath(),800,600);
        label.setSize(image.getIconWidth(),image.getIconHeight());
        frame.add(label);

        JButton ButtonSkip = new JButton("Skip Image");

        ButtonSkip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("skipped");
                //test();
                serveNextImg();
            }
        });
        JButton ButtonDelete = new JButton("Delete Image");
        ButtonDelete.setBackground(Color.RED.brighter().brighter());

        ButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(0));
                System.out.println("del");
                serveNextImg();
            }
        });

        JButton ButtonKeep = new JButton("Keep Image");
        ButtonKeep.setBackground(Color.GREEN.brighter().brighter());

        ButtonKeep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(1));
                System.out.println("kept");
                serveNextImg();
            }
        });

        // Set the size of the JFrame
        frame.setSize(1000, 800);

        // Create a JLabel and set the image
        serveNextImg();

        frame.add(label, BorderLayout.CENTER);
        GridLayout ButtonGrid = new GridLayout(1,3);
        BottomButtonpanel.setLayout(ButtonGrid);
        BottomButtonpanel.add(ButtonDelete);
        BottomButtonpanel.add(ButtonSkip);
        BottomButtonpanel.add(ButtonKeep);
        frame.add(BottomButtonpanel, BorderLayout.SOUTH);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the frame visible
        frame.setVisible(true);

    }
    public static  void test(){
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\Sowap\\Desktop\\testujemy\\00359d6c-4552-4f1f-a91a-3f3476bf4de6.jpg"));
            System.out.println(image.getHeight());
            ImageIcon icon = new ImageIcon(image.getScaledInstance(800,600,Image.SCALE_SMOOTH));
            label.setIcon(icon);

        } catch (NullPointerException e) {
            System.out.println("fucked up image big dawg");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void serveNextImg() {
        CheckIfImageIsValid(Main.ImageList.get(Main.ImageIndex).getAbsolutePath());

        ImageIcon nextImage = resizeImg(Main.ImageList.get(Main.ImageIndex).getAbsolutePath(), label.getWidth(), label.getHeight());
        System.out.println(Main.ImageList.get(Main.ImageIndex).getAbsolutePath());
        label.setIcon(nextImage);
        Main.ImageIndex++;

    }
    public static void CheckIfImageIsValid(String imagePath) {
        while(true) {
            try {
                BufferedImage image = ImageIO.read(new File(Main.ImageList.get(Main.ImageIndex).getAbsolutePath()));
                //BufferedImage image = ImageIO.read(new File("C:\\Users\\Sowap\\Desktop\\testujemy\\00359d6c-4552-4f1f-a91a-3f3476bf4de6.jpg"));
                System.out.println(image.getHeight());
                break;

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                System.out.println("fucked up image big dawg");
                Main.ImageIndex++;
            }
        }
    }
    public static ImageIcon resizeImg(String img, int width, int height) {
        ImageIcon OG = new ImageIcon(img);
        Image newImg = OG.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
