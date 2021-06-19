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
     * Main Method
     * @param args File paths to read from. If none are specified, opens a file
     *        chooser
     */
    public static void main(String[] args) {
        File[] files;
        if (args.length > 0) {
            files = getFilesFromCommandLine(args);
        }
        else {
            files = getFilesFromExplorer();
        }

        // HashMap stores the hex code and the line numbers
        HashMap<String, ArrayList<Integer>> hexMap =
            new HashMap<String, ArrayList<Integer>>();
        int lineNumber = 1;
        for (File f : files) {
            Scanner sc;
            try {
                sc = new Scanner(f);
            }
            catch (FileNotFoundException e) {
                // Handle file not found
                System.out.println("File " + f.toString() + " not found");
                continue;
            }

            // Print filepath
            System.out.println("File: " + f.toString());
            String nextLine;

            // Read lines of file
            while (sc.hasNextLine()) {
                nextLine = sc.nextLine();

                // If There is a hex
                if (nextLine.indexOf("#") >= 0) {
                    String hex = nextLine.substring(nextLine.indexOf("#") + 1,
                        nextLine.indexOf("#") + 7);
                    if (isHexCode(hex)) {

                        // If it is a new hex code for that file
                        if (!hexMap.containsKey(hex)) {
                            ArrayList<Integer> lineList =
                                new ArrayList<Integer>();
                            lineList.add(lineNumber);
                            hexMap.put(hex, lineList);
                        }

                        // Else the key exists
                        else {
                            hexMap.get(hex).add(lineNumber);
                        }

                    }
                }
                lineNumber++;
            }

            // Print data, and prepare for next file
            sc.close();
            printHexData(hexMap);
            lineNumber = 1;
            hexMap.clear();
        }
    }


    /**
     * Determines if a String is a hexCode using ANSCII values
     * @param possibleHex the possible hexcode to check
     * @return True if the String is a hexcode, false if not
     */
    private static boolean isHexCode(String possibleHex) {
        possibleHex = possibleHex.toLowerCase(); // Makes ANSCII check easier
        for (int i = 0; i < possibleHex.length(); i++) {
            // is a number 0 - 9 inclusive
            if ((possibleHex.charAt(i) < 48) || (possibleHex.charAt(i) > 57
                && possibleHex.charAt(i) < 97) || (possibleHex.charAt(
                    i) > 102)) {
                return false;
            }
        }
        return true;
    }


    /**
     * A toString method for the hex data
     * @param map Map that contains the data
     */
    private static void printHexData(HashMap<String, ArrayList<Integer>> map) {
        for (String s : map.keySet()) {
            System.out.print("\t#" + s + " : [");
            ArrayList<Integer> lineNumbers = map.get(s);
            for (int i = 0; i < lineNumbers.size() - 1; i++) {
                System.out.print(lineNumbers.get(i) + ", ");
            }
            System.out.println(lineNumbers.get(lineNumbers.size() - 1) + "]");
        }
        System.out.println("Number of Hex Codes: " + map.size() + "\n");
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
        jfc.setDialogTitle("Select Which Files to can");

        // When the user clicks "Open"
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            files = jfc.getSelectedFiles();
        }
        else {
            // System will exit if file is not found
            System.out.println("No folder chosen, system will exit");
            System.exit(0);
        }
        return files;
    }


    /**
     * Gets the files specified from the Command Line
     * @param args Command Line arguments
     * @return the files from the command line
     */
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
