import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GUI {
    static JLabel MainWindowMainLabel = new JLabel();
    static JFrame frame = new JFrame();
    static JPanel MainWindowMainButtonPanel = new JPanel();

    /*
    public static void StartupWindow() {
    JPanel StartupWindowMainPanel = new JPanel();
        StartupWindowMainPanel.setLayout(new GridLayout(4,1));
        JLabel StartupTopLabel = new JLabel("<br>Welcome To Visual Image Sorter</br>Use the panel below");
        JPanel secondlinepanel = new JPanel();

        frame.add(StartupWindowMainPanel);
        frame.setSize(400, 400);
        frame.pack();
        frame.setVisible(true);
    }*/

    public static void mainWindow() {

        ImageIcon image = resizeImg(Main.ImageList.get(0).getAbsolutePath(),800,600);
        MainWindowMainLabel.setSize(image.getIconWidth(),image.getIconHeight());
        frame.add(MainWindowMainLabel);

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
        GridLayout TopButtonGrid = new GridLayout(1,3);
        JPanel TopButtonPanel = new JPanel();
        TopButtonPanel.setLayout(TopButtonGrid);
        TopButtonPanel.add(ButtonDelete);
        TopButtonPanel.add(ButtonSkip);
        TopButtonPanel.add(ButtonKeep);

        if(Main.CategoryList.size()>2){
            MainWindowMainButtonPanel.setLayout(new GridLayout(2,1));
        }else{
            MainWindowMainButtonPanel.setLayout(new GridLayout(1,1));
        }
        MainWindowMainButtonPanel.add(TopButtonPanel);

        if((Main.CategoryList.size()-2)>0) {
            GridLayout BottomButtonGrid = new GridLayout(1,Main.CategoryList.size()-2);
            JPanel BottomButtonPanel = new JPanel();
            BottomButtonPanel.setLayout(BottomButtonGrid);
            System.out.println("popoga");
            for (int i = 0; (i < Main.CategoryList.size() - 2); i++) {
                JButton CategoryButton = new JButton(Main.CategoryList.get(i+2));
                String name = Main.CategoryList.get(i+2);
                int finalI = i+2;
                CategoryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(finalI));
                        System.out.println(Main.CategoryList.get(finalI));
                        serveNextImg();
                    }
                });
                BottomButtonPanel.add(CategoryButton);
            }
            MainWindowMainButtonPanel.add(BottomButtonPanel);
        }

        frame.setSize(1000, 800);
        serveNextImg();

        frame.add(MainWindowMainLabel, BorderLayout.CENTER);


        frame.add(MainWindowMainButtonPanel, BorderLayout.SOUTH);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the frame visible
        frame.pack();
        frame.setVisible(true);

    }

    public static void test(){
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\Sowap\\Desktop\\testujemy\\00359d6c-4552-4f1f-a91a-3f3476bf4de6.jpg"));
            System.out.println(image.getHeight());
            ImageIcon icon = new ImageIcon(image.getScaledInstance(800,600,Image.SCALE_SMOOTH));
            MainWindowMainLabel.setIcon(icon);

        } catch (NullPointerException e) {
            System.out.println("fucked up image big dawg");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void serveNextImg() {
        CheckIfNextImageIsValid();

        ImageIcon nextImage = resizeImg(Main.ImageList.get(Main.ImageIndex).getAbsolutePath(), MainWindowMainLabel.getWidth(), MainWindowMainLabel.getHeight());
        System.out.println(Main.ImageList.get(Main.ImageIndex).getAbsolutePath());
        MainWindowMainLabel.setIcon(nextImage);
        Main.ImageIndex++;

    }
    public static void CheckIfNextImageIsValid() {
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
