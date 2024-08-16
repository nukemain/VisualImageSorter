import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<File> ImageList = new ArrayList<>();
    static List<String> CategoryList = new ArrayList<>();
    static String MainDirectory = "C:\\Users\\Sowap\\Desktop\\testujemy";
    static Integer ImageIndex = 0;

    public static void main(String[] args) {
        CategoryList.add("ForDeletion");
        CategoryList.add("Kept");
        PrepareFolders(MainDirectory);
        PrepareFiles(MainDirectory);
        GUI.mainWindow();
    }

    public static void PrepareFolders(String dir){
        for(int i = 0; i< CategoryList.size(); i++){
            String folderPath = dir+"\\"+ CategoryList.get(i);
            File folder = new File(folderPath);
            if (!folder.exists() || (!folder.exists() && !folder.isDirectory())) {
                boolean created = folder.mkdirs();
                if (created) {
                    System.out.println("Folder created successfully: " + folderPath);
                } else {
                    System.out.println("Failed to create the folder: " + folderPath);
                    JOptionPane.showMessageDialog(null, "Yo folders fucked bro: "+folderPath);
                }
            } else {
                System.out.println("Folder already exists: " + folderPath);
            }
        }
    }

    public static void PrepareFiles(String dir) {
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if (listOfFiles[i].getName().endsWith(".png") || listOfFiles[i].getName().endsWith(".PNG")||listOfFiles[i].getName().endsWith(".jpg") || listOfFiles[i].getName().endsWith(".JPG")) {
                        ImageList.add(listOfFiles[i]);
                    }
                }
            }
            /*
            //separate loop to add .jpg files after the .png for reasons im not going to explain here
            //(if you really need to know, ask me directly)

            //currently not needed
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    if (listOfFiles[i].getName().endsWith(".jpg") || listOfFiles[i].getName().endsWith(".JPG")) {
                        ImageList.add(listOfFiles[i]);
                    }
                }
            }

             */
        }
    }

    public static void MoveImage(String target,String destination) {
        System.out.println(target);
        int lastBackslash = target.lastIndexOf("\\");
        String destination2 = target.substring(0, lastBackslash + 1) + destination + "\\" + target.substring(lastBackslash + 1);
        System.out.println(destination2);
        File targetFile = new File(target);
        targetFile.renameTo(new File(destination2));
        targetFile.delete();
    }
}