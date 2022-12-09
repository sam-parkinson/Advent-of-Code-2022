import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileSystem {
    private ArrayList<String> commandList;
    private ArrayList<FileNode> fileTree;
    private int smallDirectorySize;

    public FileSystem(String address) {
        parseInput(address);
        fileTree = new ArrayList<FileNode>();
        makeFileTree();
        // findFolderSize(0);
        smallDirectorySize = findSmallDirectorySize();
    }

    public int getSmallDirectorySize() {
        return this.smallDirectorySize;
    }

    private void parseInput(String address) {
        commandList = new ArrayList<String>();
        try {
            File file = new File(address);
            Scanner stdin = new Scanner(file);

            while (stdin.hasNextLine()) {
                commandList.add(stdin.nextLine());
            }

            stdin.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void makeFileTree() {
        fileTree.add(new FileNode("/", 0, -1));

        int currentNode = 0;
        int parentNode = 0;

        for (int i = 1; i < commandList.size(); i++) {
            String[] lineArr = commandList.get(i).split(" ");

            if (lineArr[0].equals("$") && lineArr[1].equals("cd")) {
                currentNode = followCommand(fileTree.get(currentNode), lineArr[2]);
                parentNode = currentNode;
            } else if (lineArr[0].equals("dir")) {
                int n = fileTree.size();

                fileTree.add(new FileNode(lineArr[1], n, currentNode));
                fileTree.get(currentNode).children.put(lineArr[1], n);

            } else if (!lineArr[0].equals("$")) {
                int n = fileTree.size();
                int size = Integer.parseInt(lineArr[0]);

                fileTree.add(new FileNode(lineArr[1], n, currentNode, size));
                fileTree.get(parentNode).children.put(lineArr[1], n);
            }
        }
    }

    private int followCommand(FileNode node, String check) {
        if (check.equals(".."))
            return node.parent;
        else
            return node.children.get(check);
    }
    
    private int findSmallDirectorySize() {
        int size = 0;

        for (int i = 0; i < fileTree.size(); i++) {
            if (fileTree.get(i).size == 0) {
                int test;
                test = findFolderSize(i);

                if (test <= 100000) {
                    size += test;
                }
            }
        }

        return size;
    }

    // this method is key!!
    private int findFolderSize(int index) {
        int folderSize = 0;

        FileNode currentNode = fileTree.get(index);

        if (currentNode.size != 0) {
            // System.out.println(currentNode.size);
            folderSize = currentNode.size;
        } else {
            // System.out.println(currentNode.children.keySet());
            for (String child : currentNode.children.keySet()) {
                int totalSize = findFolderSize(currentNode.children.get(child));
                // System.out.println(totalSize);
                folderSize += totalSize;
            }
        }

        return folderSize;
    }

    private class FileNode {
        private String name;
        private int index, size, parent;
        private HashMap<String, Integer> children;

        // these are directories!
        private FileNode(String name, int index, int parent) {
            children = new HashMap<String, Integer>();
            this.name = name;
            this.index = index;
            this.parent = parent;
            this.size = 0;
        }

        // these are files!
        private FileNode(String name, int index, int parent, int size) {
            children = new HashMap<String, Integer>();
            this.name = name;
            this.index = index;
            this.parent = parent;
            this.size = size;
        }
    }
}