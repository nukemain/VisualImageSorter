import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GUI {
    static JLabel MainWindowMainLabel = new JLabel();
    static JFrame frame = new JFrame();
    static JPanel MainWindowMainButtonPanel = new JPanel();
    static JLabel MainWindowTextLabel = new JLabel();
    private static Timer resizeTimer;


    public static void mainWindow() {

        ImageIcon image = resizeImg(Main.ImageList.get(0).getAbsolutePath(),800,600);
        MainWindowMainLabel.setSize(image.getIconWidth(),image.getIconHeight());
        frame.add(MainWindowMainLabel);

        JButton ButtonSkip = new JButton("Skip Image");

        ButtonSkip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serveNextImg();
            }
        });
        JButton ButtonDelete = new JButton("Delete Image");
        ButtonDelete.setBackground(Color.RED.brighter().brighter());

        ButtonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(0));
                serveNextImg();
            }
        });

        JButton ButtonKeep = new JButton("Keep Image");
        ButtonKeep.setBackground(Color.GREEN.brighter().brighter());

        ButtonKeep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(1));
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
            for (int i = 0; (i < Main.CategoryList.size() - 2); i++) {
                JButton CategoryButton = new JButton(Main.CategoryList.get(i+2));
                //String name = Main.CategoryList.get(i+2);
                int finalI = i+2;
                CategoryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(finalI));
                        //System.out.println(Main.CategoryList.get(finalI));
                        serveNextImg();
                    }
                });
                BottomButtonPanel.add(CategoryButton);
            }
            MainWindowMainButtonPanel.add(BottomButtonPanel);
        }

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println("Key pressed code=" + e.getKeyCode() + ", char=" + e.getKeyChar());
                int keyCode = e.getKeyCode();

                if (keyCode == KeyEvent.VK_RIGHT) {
                    Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(0));
                    //System.out.println("del by keybind");
                    serveNextImg();

                }else if (keyCode == KeyEvent.VK_LEFT) {
                    Main.MoveImage(String.valueOf(Main.ImageList.get(Main.ImageIndex-1)),Main.CategoryList.get(1));
                    //System.out.println("kept by keybind");
                    serveNextImg();

                }else if (keyCode == KeyEvent.VK_DOWN) {
                    //System.out.println("skipped by keybind");
                    serveNextImg();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //unused
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //unused
            }
        });

        frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (!(Main.ImageIndex == 0)) {
                    if (resizeTimer == null) {
                        resizeTimer = new Timer(250, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ImageIcon RefreshedImage = resizeImg(Main.ImageList.get(Main.ImageIndex - 1).getAbsolutePath(), MainWindowMainLabel.getWidth(), MainWindowMainLabel.getHeight());
                                MainWindowMainLabel.setIcon(RefreshedImage);
                                resizeTimer.stop();
                                resizeTimer = null;
                            }
                        });
                        resizeTimer.setRepeats(false);
                    }
                    resizeTimer.restart();

                }
            }

        });

        frame.setSize(1000, 800);
        MainWindowMainLabel.setSize(frame.getWidth(),1);
        MainWindowTextLabel.setText("<html>How to use:" +
                "<br>Click\"Delete image\" button to move it to a folder named \"ForDeletion\" in the programs active directory - bound to left arrow key" +
                "<br>Click\"Skip image\" button to skip to the next image - bound to down arrow key" +
                "<br>Click\"Keep image\" button to move it to a folder named \"Kept\" in the programs active directory - bound to right arrow key" +
                "<br>"+
                "<br>Custom sorting options:" +
                "<br>Add (or remove added) sorting options in the \"SorterCategories.txt\" file by writing category names, each in a new line (changes will apply after program restart)." +
                "<br>Adding a category will create a corresponding button and directory in programs active directory." +
                "<br>"+
                "<br>Note that for unknown to me reasons, some immages cannot be displayed in the program. Those images are filtered out during programs use. Sorry about that :( ."+
                "<br>"+
                "<br>Resize the window to your liking and click \"Skip image\" to continue.</br></html>");

        frame.add(MainWindowTextLabel,BorderLayout.NORTH);

        frame.setTitle("Visual Image Sorter");
        frame.add(MainWindowMainLabel, BorderLayout.CENTER);


        frame.add(MainWindowMainButtonPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.requestFocus();

    }


    public static void serveNextImg() {
        if(Main.ImageList.size()==(Main.ImageIndex-1)){
            JOptionPane.showMessageDialog(null, "All Images Sorted!", "Done!", JOptionPane.WARNING_MESSAGE);
            System.exit(0);
        }else{
            CheckIfNextImageIsValid();
            ImageIcon nextImage = resizeImg(Main.ImageList.get(Main.ImageIndex).getAbsolutePath(), MainWindowMainLabel.getWidth(), MainWindowMainLabel.getHeight());
            //System.out.println(Main.ImageList.get(Main.ImageIndex).getAbsolutePath());

            MainWindowMainLabel.setIcon(nextImage);
            MainWindowTextLabel.setText(Main.ImageIndex + "/" + Main.ImageList.size() + " " + Main.ImageList.get(Main.ImageIndex).getAbsolutePath());
            Main.ImageIndex++;

            frame.repaint();
            frame.requestFocus();
        }
    }
    public static void CheckIfNextImageIsValid() {
        while(true) {
            if(Main.ImageList.size()<=(Main.ImageIndex)){
                JOptionPane.showMessageDialog(null, "<html>All images Sorted !<br>(No more suitable images found in this directory!)<html>", "Done!", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            try {
                BufferedImage image = ImageIO.read(new File(Main.ImageList.get(Main.ImageIndex).getAbsolutePath()));
                System.out.println(image.getHeight());
                break;

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NullPointerException e) {
                //System.out.println("fucked up image big dawg");
                Main.ImageIndex++;
            }
        }
    }
    public static ImageIcon resizeImg(String img, int width, int height) {
        ImageIcon OG = new ImageIcon(img);
        if(height==0){
            height=OG.getIconHeight();
        }
        Image newImg = OG.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}
