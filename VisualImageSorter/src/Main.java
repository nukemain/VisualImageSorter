import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static List<File> PNGList = new ArrayList<>();
    static List<File> JPGList = new ArrayList<>();
    static JLabel label = new JLabel();
    static JFrame frame = new JFrame();
    static Integer ImageIndex = 0;
    public static void main(String[] args) {
        File folder = new File("C:\\Users\\Sowap\\Pictures\\tapety");
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if(listOfFiles[i].getName().endsWith(".png") || listOfFiles[i].getName().endsWith(".PNG")) {
                        PNGList.add(listOfFiles[i]);
                    }else if(listOfFiles[i].getName().endsWith(".jpg") || listOfFiles[i].getName().endsWith(".JPG")) {
                        JPGList.add(listOfFiles[i]);
                    }else{
                        System.out.println("File " + listOfFiles[i].getName()+" Not a PNG or JPG file");
                    }
                } /*
                else if (listOfFiles[i].isDirectory()) {
                    System.out.println("Directory " + listOfFiles[i].getName());
                }*/
            }
        }
        System.out.println(Arrays.toString(PNGList.toArray()));
        System.out.println(Arrays.toString(JPGList.toArray()));
        final Integer[] i = {0};

        // Load the image
        ImageIcon image = resizeImg(PNGList.get(0).getAbsolutePath(),800,600);


        // Add the JLabel to the JFrame
        frame.add(label);

        JButton button = new JButton("Next Image");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serveNextImg();
            }
        });

        // Set the size of the JFrame
        frame.setSize(1000, 800);

        // Create a JLabel and set the image
        serveNextImg();

        frame.add(label, BorderLayout.CENTER);
        frame.add(button, BorderLayout.SOUTH);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the frame visible
        frame.setVisible(true);

    }
    public static void serveNextImg() {
        ImageIcon nextImage = resizeImg(PNGList.get(ImageIndex).getAbsolutePath(), frame.getWidth(), frame.getHeight());
        label.setIcon(nextImage);
        ImageIndex++;
    }
    public static ImageIcon resizeImg(String img, int width, int height) {
        ImageIcon OG = new ImageIcon(img);
        Image newImg = OG.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}