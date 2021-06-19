package hexscanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

/**
 * Hex Scanner
 * This reads select files, finding all hex code values and printing
 * them out.
 * @author Red Williams (Red-CS)
 * @version 6/19/21
 */
public class HexScanner {

    /**
     * @param args
     */
    public static void main(String[] args) {
        File[] files;
        if (args.length > 0) {
            files = getFilesFromCommandLine(args);
        }
        else {
            files = getFilesFromExplorer();
        }

        HashMap<String, ArrayList<Integer>> hexMap =
            new HashMap<String, ArrayList<Integer>>();
        int numHexCodes = 0;
        for (File f : files) {
            Scanner sc;
            try {
                sc = new Scanner(f);
            }
            catch (FileNotFoundException e) {
                System.out.println("File " + f.toString() + " not found");
                continue;
            }
            System.out.println("File: " + f.toString());
            String nextLine;
            while (sc.hasNextLine()) {
                nextLine = sc.nextLine();
                if (nextLine.indexOf("#") >= 0) {
                    String hex = nextLine.substring(nextLine.indexOf("#") + 1,
                        nextLine.indexOf("#") + 7);
                    if (isHexCode(hex)) {

                        // If it is a new hex code for that file
                        if (!hexMap.containsKey(hex)) {
                            hexMap.put(hex, new ArrayList<Integer>(1));
                            System.out.println("\t#" + hex);
                            numHexCodes++;
                        }

                    }
                }
            }
            sc.close();
            System.out.println("Number of Hex Codes: " + numHexCodes + "\n");
            numHexCodes = 0;
            hexMap.clear();
        }
    }


    private static boolean isHexCode(String possibleHex) {
        for (int i = 0; i < possibleHex.length(); i++) {
            if ((possibleHex.charAt(i) <= 48 && possibleHex.charAt(i) >= 57)
                || (possibleHex.charAt(i) >= 65 && possibleHex.charAt(
                    i) >= 122)) {
                return false;
            }
        }
        return true;
    }


    public static File[] getFilesFromExplorer() {

        // Sets FileExplorer to a similar style as the OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        File[] files = null;

        // Using JFileChooser to open file chooser window
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView()
            .getHomeDirectory());
        jfc.setMultiSelectionEnabled(true);
        jfc.setDialogTitle("Select which files to scan");

        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            files = jfc.getSelectedFiles();
        }
        else {
            // System will exit if file is not found
            System.out.println("No folder chosen, system will exit");
            System.exit(0);
        }
        for (File f : files) {
            System.out.println(f.toString());
        }
        return files;
    }


    public static File[] getFilesFromCommandLine(String[] args) {
        File[] files = new File[args.length];
        int index = 0;
        for (int i = 0; i < args.length; i++) {
            files[index] = new File(args[i]);
            index++;
        }
        return files;
    }

}
