import java.io.IOException;
import java.io.File;
import java.io.FileWriter; 
import java.util.*;

public class Todo {
    private ArrayList<TodoItem> todoList = new ArrayList<>();
    private static final String FILE_NAME = "./TodosList.txt";
    private static File file;
    private Scanner scanner;

    public static void main(String[] args) {
        Todo todo = new Todo();
        todo.run();
    }

    /**
     * Executes the program
     */
    public void run() {
        loadSavedData();
        DisplayMenu();
    }

    /**
     * Displays program menu
     */
    private void DisplayMenu() {
        String enteredKey = "";
        do {
            System.out.println("=====  To-Do list program  =====");
            System.out.println();
            listItems();
            System.out.println(
                    "\n - Please enter a letter for the next action: [a] = Add item, [d] = delete item, [l] = list all items, [x] = Exit the program");

            enteredKey = readInput().toLowerCase();
            switch (enteredKey) {
                case "l":
                    listItems();
                    break;
                case "a":
                    addItem();
                    break;
                case "d":
                    deleteItem();
                    break;
                case "x":
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option:");
                    break;

            }
            System.out.println("\n\n");
        } while (!enteredKey.equals("x"));
    }

    /**
     * Removes item from the list
     */
    private void deleteItem() {
        System.out.println("\nPlease enter the number of item to delete:");
        String value = readInput();
        int index = Integer.parseInt(value);
        if (index > 0 && index <= this.todoList.size()){
            this.todoList.remove(index - 1);
            saveDataToFile();
        }
        else
            System.out.println("Item number not found.");
    }

    /**
     * Prints list of items in Todo list
     */
    private void listItems() {
        // listing each individual item
        Integer counter = 1;
        System.out.println("-----------To Do list------------");
        if (todoList.size() == 0) {
            System.out.println("-- The list is empty -- ");
        } else {
            for (TodoItem item : todoList) {
                System.out.println(" " + counter + " - " + item.Name);
                counter++;
            }
        }
        System.out.println("---------------------------------");
    }

    /**
     * Adds Todo item to list
     */
    private void addItem() {
        // Adds Todo item to list
        System.out.println("Please enter the To Do item description:");
        String description = readInput();
        this.todoList.add(new TodoItem(description));
        saveDataToFile();
    }

    /**
     * Stores data in text file
     */
    private void saveDataToFile() {

        String content = "";
        for (TodoItem item : todoList) {
            content += item.Name + "\n";
        }

        try {
            FileWriter fileWritter = new FileWriter(FILE_NAME, false);
            fileWritter.write(content);
            fileWritter.close();
            // System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            // System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Reads user entered value
     * 
     * @return Entered value as String
     */
    private String readInput() {
        String outputString = "";
        scanner = new Scanner(System.in);
        // String inputString = scanner.nextLine();
        if (scanner.hasNextLine())
            outputString = scanner.nextLine();
        // scanner.close();

        // if (inputString.length() > 0) {
        // outputString = inputString;
        // }

        return outputString;
    }

    /* Function loads data from text file if exists to initialize the list */
    private void loadSavedData() {
        file = new File(FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                todoList.add(new TodoItem(word));
            }
            scanner.close();
        } catch (IOException e) {

            // Print the exception along with line number
            // using printStackTrace() method
            e.printStackTrace();
        }
    }

    /**
     * To-do item class
     */
    class TodoItem {
        private String Name;

        /**
         * Creates instance ov To-do item
         * 
         * @param name Name of the item
         */
        public TodoItem(String name) {
            this.Name = name;
        }

        public String Display() {
            return this.Name;
        }

    }
}