import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<File> ImageList = new ArrayList<>();
    static List<String> CategoryList = new ArrayList<>();
    static String MainDirectory = "C:\\Users\\Sowap\\Desktop\\testujemy";
    static Integer ImageIndex = 0;

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        MainDirectory = System.getProperty("user.dir");
        CategoryList.add("ForDeletion");
        CategoryList.add("Kept");
        PrepareCategories(MainDirectory);
        PrepareFiles(MainDirectory);
        if(ImageList.isEmpty()){
            JOptionPane.showMessageDialog(null, "No images found in this directory! Did you run the program in the right directory? Are your images file formats supported by the program?", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }else {
            PrepareFolders(MainDirectory);
            GUI.mainWindow();
        }
    }

    public static void PrepareCategories(String dir){
        try {
            File dataFile = new File(dir + "\\SorterCategories.txt");
            if(!dataFile.exists()){
                dataFile.createNewFile();
            }
            Scanner dataReader = new Scanner(dataFile);

            while (dataReader.hasNextLine()) {
                String dataString = dataReader.nextLine();
                if (dataString.contains("\\")||dataString.contains("/")||dataString.contains(":")||dataString.contains("*")||dataString.contains("\"")||dataString.contains("<")||dataString.contains(">")||dataString.contains("|")) {
                    JOptionPane.showMessageDialog(null, dataString + " contains an incompatible chracter ( \\ / \" * < > | ). Please remove it.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                }else{
                    CategoryList.add(dataString);
                }
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error reading SorterCategories.txt .", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public static void PrepareFolders(String dir){
        for(int i = 0; i<CategoryList.size(); i++){
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