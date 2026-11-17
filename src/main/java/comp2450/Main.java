package comp2450;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    // Pretty print colours
    public static final String PRINT_RESET = "\u001B[0m";
    public static final String PRINT_RED = "\u001B[31m";
    public static final String PRINT_GREEN = "\u001B[32m";
    public static final String PRINT_YELLOW = "\u001B[33m";
    public static final String PRINT_BLUE = "\u001B[34m";
    public static final String PRINT_PURPLE = "\u001B[35m";
    public static final String PRINT_CYAN = "\u001B[36m";

    public static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Coordinates> coords = new ArrayList<>();
    public static ArrayList<Item> items = new ArrayList<>();
    public static ArrayList<Set> sets = new ArrayList<>();
    public static ArrayList<Assignment> assignments = new ArrayList<>();
    
    // Maze
    public static ArrayList<Maze> mazes = new ArrayList<>();
    public static int mazeWidth;
    public static int mazeHeight;

    // Player
    public static boolean playerAdded = false;
    public static int playerX;
    public static int playerY;

    // Guards
    public static ArrayList<Guard> guards = new ArrayList<>();

    public static int get_user_selection(int highestValidNumber) {
        int input = -1;
        while (input == -1) {
            try {
                input = Integer.valueOf(scanner.nextLine());
                if (input < 1 || input > highestValidNumber) {
                    System.out.print(PRINT_RED + "Invalid selection. " + PRINT_RESET + "Please enter a valid number from 1-" + highestValidNumber + ": ");
                    input = get_user_selection(highestValidNumber);
                }
            }
            catch (InputMismatchException ime) {
                System.out.print(PRINT_RED + "Invalid selection. " + PRINT_RESET + "Please enter a valid number from 1-" + highestValidNumber + ": ");
            }
            catch (NumberFormatException nfe) {
                System.out.print(PRINT_RED + "Invalid selection. " + PRINT_RESET + "Please enter a valid number from 1-" + highestValidNumber + ": ");
            }
        }
        return input;
    }
    public static void clear_output() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public static void print_menu() {
        System.out.println(PRINT_CYAN + "\nMAIN MENU\n" + PRINT_RESET + "These are your options:");
        System.out.println("1: Mazes");
        System.out.println("2: Player");
        System.out.println("3: Guards");
        System.out.println("4: Items");
        System.out.println("5: Sets");
        System.out.println("6: Assignments");
        System.out.print("Enter a number from 1-6 to make your selection: ");
    }
    public static void print_legend() {
        System.out.println("+----------LEGEND----------+");
        System.out.println("| " + PRINT_BLUE + "Player: ■" + PRINT_RESET + "                |");
        System.out.println("| " + PRINT_BLUE + "End: □" + PRINT_RESET + "                   |");
        System.out.println("| " + PRINT_RED + "Enemy: ■" + PRINT_RESET + "                 |");
        System.out.println("| Wall: ■                  |");
        System.out.println("| " + PRINT_YELLOW + "Storable Power-up: ■" + PRINT_RESET + "     |");
        System.out.println("| " + PRINT_GREEN + "Instant Power-up: ■" + PRINT_RESET + "      |");
        System.out.println("| " + PRINT_PURPLE + "Assignment Type 1: ■" + PRINT_RESET + "     |");
        System.out.println("| " + PRINT_CYAN + "Assignment Type 2: ■" + PRINT_RESET + "     |");
        System.out.println("+--------------------------+");
    }

    // MAZE methods
    public static void print_maze_menu() {
        System.out.println(PRINT_CYAN + "\nMAZES\n" + PRINT_RESET + "There are " + mazes.size() + " mazes. You can:");
        System.out.println("1. Add a maze");
        if (mazes.size() > 0) {
            System.out.println("2. Show mazes");
            System.out.println("3. Remove a maze");
        }
        System.out.print("Enter a number to make your selection: ");
    }
    public static void print_maze_list() {
        for (int i = 0; i < mazes.size(); i++) {
            System.out.println((i+1) + ": (" + mazes.get(i).width + "x" + mazes.get(i).height + ")");
        }
    }
    public static void show_maze() {
        System.out.println(PRINT_CYAN + "\nMAZES" + PRINT_RESET);
        print_maze_list();
        System.out.print("Which maze would you like to show? ");
        int selection = get_user_selection(mazes.size());
        Coordinates c = coords.get(selection-1);
        for (int i = 0; i < c.width; i++) {
            for (int j = 0; j < c.height; j++) {
                System.out.print(c.coords[i][j]);
            }
            System.out.println();
        }
    }
    public static void remove_maze() {
        System.out.println(PRINT_CYAN + "\nMAZES" + PRINT_RESET);
        print_maze_list();
        System.out.print("Which maze would you like to delete? ");
        int selection = get_user_selection(mazes.size());
        mazes.remove(mazes.get(selection-1));
        coords.remove(coords.get(selection-1));
    }
    public static void run_maze_menu() {
        print_maze_menu();
        int s;
        if (mazes.size() > 0) {
            s = get_user_selection(3);
        }
        else {
            s = get_user_selection(1);
        }
        if (s == 1) {
            System.out.print("How many columns should the maze have (up to 20)? ");
            mazeWidth = get_user_selection(20);
            System.out.print("How many rows should the maze have (up to 20)? ");
            mazeHeight = get_user_selection(20);
            Maze maze = new Maze(mazeWidth, mazeHeight);
            mazes.add(maze);
            Coordinates coord = new Coordinates(maze);
            coords.add(coord);
            System.out.println(PRINT_GREEN + "\nMaze created successfully." + PRINT_RESET);
        }
        if (s == 2) {
            show_maze();
        }
        else if (s == 3) {
            remove_maze();
        }
    }

    // PLAYER methods
    public static void print_player_menu() {
        String text;
        if (!playerAdded) {
            text = "The player is not added to the maze yet.";
        }
        else {
            text = "The player is at position (" + playerX + "," + playerY + ").";
        }
        System.out.println(PRINT_CYAN + "\nPLAYER\n" + PRINT_RESET + text + " You can:");
        if (!playerAdded) {
            System.out.println("1. Add player");
        }
        else {
            System.out.println("1. Move player");
            System.out.println("2. Remove player");
        }
        System.out.print("Enter a number to make your selection: ");
    }
    public static void run_player_menu() {
        print_player_menu();
        int highestNumber;
        if (!playerAdded) {
            highestNumber = 1;
        }
        else {
            highestNumber = 2;
        }
        int s = get_user_selection(highestNumber);
        if (s == 1 && !playerAdded) {
            System.out.print("Pick the player's initial X coordinate (from 1-" + mazeWidth + "): ");
            playerX = get_user_selection(mazeWidth);
            System.out.print("Pick the player's initial Y coordinate (from 1-" + mazeHeight + "): ");
            playerY = get_user_selection(mazeHeight);
            coords.get(0).coords[playerY-1][playerX-1] = PRINT_BLUE + "■ " + PRINT_RESET;
            playerAdded = true;
            System.out.println(PRINT_GREEN + "\nPlayer created successfully." + PRINT_RESET);
        }
        else if (s == 1 && playerAdded) {
            coords.get(0).coords[playerY-1][playerX-1] = "□ ";
            System.out.print("Pick the player's new X coordinate (from 1-" + mazeWidth + "): ");
            playerX = get_user_selection(mazeWidth);
            System.out.print("Pick the player's new Y coordinate (from 1-" + mazeHeight + "): ");
            playerY = get_user_selection(mazeHeight);
            coords.get(0).coords[playerY-1][playerX-1] = PRINT_BLUE + "■ " + PRINT_RESET;
            playerAdded = true;
            System.out.println(PRINT_GREEN + "\nPlayer moved successfully." + PRINT_RESET);
        }
        else {
            coords.get(0).coords[playerY-1][playerX-1] = "□ ";
            playerAdded = false;
            playerX = -1;
            playerY = -1;
            System.out.println(PRINT_GREEN + "\nPlayer removed successfully." + PRINT_RESET);
        }
    }

    // GUARD methods
    public static void print_guard_menu() {
        String text;
        if (guards.size() == 0) {
            text = "There are no guards in the maze yet.";
        }
        else if (guards.size() == 1) {
            text = "There is 1 guard in the maze.";
        }
        else {
            text = "There are " + guards.size() + " guards in the maze.";
        }
        System.out.println(PRINT_CYAN + "\nGUARDS\n" + PRINT_RESET + text + " You can:");
        if (guards.size() == 0) {
            System.out.println("1. Add a guard");
        }
        else {
            System.out.println("1. Add a guard");
            System.out.println("2. Remove a guard");
            System.out.println("3. Remove all guards");
        }
        System.out.print("Enter a number to make your selection: ");
    }
    public static void print_guard_list() {
        for (int i = 0; i < guards.size(); i++) {
            System.out.println((i+1) + ": (" + guards.get(i).positionX + "x" + guards.get(i).positionY + ")");
        }
    }
    public static void remove_guard() {
        System.out.println(PRINT_CYAN + "\nGUARDS" + PRINT_RESET);
        print_guard_list();
        System.out.print("Which guard would you like to delete? ");
        int selection = get_user_selection(guards.size());
        Guard guard = guards.get(selection-1);
        guards.remove(guard);
        coords.get(0).coords[guard.positionY-1][guard.positionX-1] = "□ ";
        System.out.println(PRINT_GREEN + "\nGuard removed successfully." + PRINT_RESET);
    }
    public static void remove_all_guards() {
        while (guards.size() > 0) {
            coords.get(0).coords[guards.get(0).positionY-1][guards.get(0).positionX-1] = "□ ";
            guards.remove(0);
        }
    }
    public static void run_guard_menu() { 
        print_guard_menu();
        int highestNumber;
        if (guards.size() == 0) {
            highestNumber = 1;
        }
        else {
            highestNumber = 3;
        }
        int s = get_user_selection(highestNumber);
        if (s == 1) {
            System.out.print("Pick the new guard's X coordinate (from 1-" + mazeWidth + "): ");
            int guardX = get_user_selection(mazeWidth);
            System.out.print("Pick the new guard' Y coordinate (from 1-" + mazeHeight + "): ");
            int guardY = get_user_selection(mazeHeight);
            Guard guard = new Guard(guardX, guardY);
            guards.add(guard);
            coords.get(0).coords[guardY-1][guardX-1] = PRINT_RED + "■ " + PRINT_RESET;
            System.out.println(PRINT_GREEN + "\nGuard created successfully." + PRINT_RESET);
        }
        else if (s == 2) {
            remove_guard();
        }
        else if (s == 3) {
            remove_all_guards();
            System.out.println(PRINT_GREEN + "\nGuards removed successfully." + PRINT_RESET);
        }
    }

    // ITEM methods
    public static void print_item_menu() {
        String text;
        if (items.size() == 0) {
            text = "There are no items in the maze yet.";
        }
        else if (guards.size() == 1) {
            text = "There is 1 item in the maze.";
        }
        else {
            text = "There are " + items.size() + " items in the maze.";
        }
        System.out.println(PRINT_CYAN + "\nITEMS\n" + PRINT_RESET + text + " You can:");
        if (items.size() == 0) {
            System.out.println("1. Add an item");
        }
        else {
            System.out.println("1. Add an item");
            System.out.println("2. Remove an item");
            System.out.println("3. Remove all items");
        }
        System.out.print("Enter a number to make your selection: ");
    }
    public static void print_item_list() {
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i+1) + ": (" + items.get(i).positionX + "x" + items.get(i).positionY + ")");
        }
    }
    public static void remove_item() {
        System.out.println(PRINT_CYAN + "\nITEMS" + PRINT_RESET);
        print_item_list();
        System.out.print("Which item would you like to delete? ");
        int selection = get_user_selection(items.size());
        Item item = items.get(selection-1);
        items.remove(item);
        coords.get(0).coords[item.positionY-1][item.positionX-1] = "□ ";
        System.out.println(PRINT_GREEN + "\nItem removed successfully." + PRINT_RESET);
    }
    public static void remove_all_items() {
        while (items.size() > 0) {
            coords.get(0).coords[items.get(0).positionY-1][items.get(0).positionX-1] = "□ ";
            items.remove(0);
        }
    }
    public static void run_item_menu() { 
        print_item_menu();
        int highestNumber;
        if (items.size() == 0) {
            highestNumber = 1;
        }
        else {
            highestNumber = 3;
        }
        int s = get_user_selection(highestNumber);
        if (s == 1) {
            System.out.print("Pick the new item's X coordinate (from 1-" + mazeWidth + "): ");
            int itemX = get_user_selection(mazeWidth);
            System.out.print("Pick the new item' Y coordinate (from 1-" + mazeHeight + "): ");
            int itemY = get_user_selection(mazeHeight);
            Item item = new Item(itemX, itemY);
            coords.get(0).coords[itemY-1][itemX-1] = PRINT_YELLOW + "■ " + PRINT_RESET;
            items.add(item);
            System.out.println(PRINT_GREEN + "\nItem created successfully." + PRINT_RESET);
        }
        else if (s == 2) {
            remove_item();
        }
        else if (s == 3) {
            remove_all_items();
            System.out.println(PRINT_GREEN + "\nItems removed successfully." + PRINT_RESET);
        }
    }

    // SET methods
    public static void print_set_menu() {
        System.out.println(PRINT_CYAN + "\nSETS\n" + PRINT_RESET + "There are " + sets.size() + " sets. You can:");
        if (sets.size() == 0) {
            System.out.println("1. Add a set");
        }
        else {
            System.out.println("1. Add a set");
            System.out.println("2. Remove a set");
            System.out.println("3. Remove all assignments in a set");
        }
        System.out.print("Enter a number to make your selection: ");
    }
    public static void print_set_list() {
        for (int i = 0; i < sets.size(); i++) {
            System.out.println((i+1) + ": " + sets.get(i).name);
        }
    }
    public static void remove_set() {
        System.out.println(PRINT_CYAN + "\nSETS" + PRINT_RESET);
        print_set_list();
        System.out.print("Which set would you like to delete? ");
        int selection = get_user_selection(sets.size());
        Set set = sets.get(selection-1);
        sets.remove(set);
        System.out.println(PRINT_GREEN + "\nSet removed successfully." + PRINT_RESET);
    }
    public static void remove_all_in_set() {
        System.out.println(PRINT_CYAN + "\nSETS" + PRINT_RESET);
        print_set_list();
        System.out.print("Which set would you like to remove all assignments from? ");
        int selection = get_user_selection(sets.size());
        Set set = sets.get(selection-1);
        while (set.assignments.size() > 0) {
            set.assignments.remove(0);
        }
    }
    public static void run_set_menu() {
        print_set_menu();
        int highestNumber;
        if (sets.size() == 0) {
            highestNumber = 1;
        }
        else {
            highestNumber = 3;
        }
        int s = get_user_selection(highestNumber);
        if (s == 1) {
            System.out.print("What would you like to name the set? ");
            String name = scanner.nextLine();
            Set set = new Set(name);
            sets.add(set);
            System.out.println(PRINT_GREEN + "\nSet created successfully." + PRINT_RESET);
        }
        else if (s == 2) {
            remove_set();
        }
        else if (s == 3) {
            remove_all_in_set();
            System.out.println(PRINT_GREEN + "\nAll assignments in set removed successfully." + PRINT_RESET);
        }
    }

    // ASSIGNMENT methods
    public static void print_assignment_menu() {
        System.out.println(PRINT_CYAN + "\nASSIGNMENTS\n" + PRINT_RESET + "There are " + assignments.size() + " assignments. You can:");
        if (assignments.size() == 0) {
            System.out.println("1. Add an assignment");
        }
        else {
            System.out.println("1. Add an assignment");
            System.out.println("2. Remove an assignment");
        }
        System.out.print("Enter a number to make your selection: ");
    }
    public static void print_assignment_list() {
        for (int i = 0; i < assignments.size(); i++) {
            System.out.println((i+1) + ": " + assignments.get(i).name);
        }
    }
    public static void remove_assignment() {
        System.out.println(PRINT_CYAN + "\nASSIGNMENTS" + PRINT_RESET);
        print_assignment_list();
        System.out.print("Which assignment would you like to delete? ");
        int selection = get_user_selection(assignments.size());
        Assignment assignment = assignments.get(selection-1);
        assignments.remove(assignment);
        System.out.println(PRINT_GREEN + "\nAssignment removed successfully." + PRINT_RESET);
    }
    public static void run_assignment_menu() {
        print_assignment_menu();
        int highestNumber;
        if (assignments.size() == 0) {
            highestNumber = 1;
        }
        else {
            highestNumber = 2;
        }
        int s = get_user_selection(highestNumber);
        if (s == 1) {
            System.out.print("What would you like to name the assignment? ");
            String name = scanner.nextLine();
            System.out.println(PRINT_CYAN + "\nSETS" + PRINT_RESET);
            print_set_list();
            System.out.print("Which set would you like to add this assignment to? ");
            int selection = get_user_selection(sets.size());
            Set set = sets.get(selection-1);
            Assignment assignment = new Assignment(set, name);
            set.assignments.add(assignment);
            System.out.println(PRINT_GREEN + "\nAssignment created successfully." + PRINT_RESET);
        }
        else if (s == 2) {
            remove_assignment();
        }
    }
    
    public static void run_menu() {
        boolean end_menu = false;
        //clear_output();
        print_menu();
        int s = get_user_selection(6);
        if (s == 1) {
            run_maze_menu();
        }
        else if (s == 2) {
            if (mazes.size() > 0) {
                run_player_menu();
            }
            else {
                System.out.println(PRINT_RED + "\nThere is nowhere to put the player, please create a maze first." + PRINT_RESET);
                run_menu();
            }
        }
        else if (s == 3) {
            if (mazes.size() > 0) {
                run_guard_menu();
            }
            else {
                System.out.println(PRINT_RED + "\nThere is nowhere to put the guard, please create a maze first." + PRINT_RESET);
                run_menu();
            }
        }
        else if (s == 4) {
            if (mazes.size() > 0) {
                run_item_menu();
            }
            else {
                System.out.println(PRINT_RED + "\nThere is nowhere to put the item, please create a maze first." + PRINT_RESET);
                run_menu();
            }
        }
        else if (s == 5) {
            run_set_menu();
        }
        else if (s == 6) {
            if (sets.size() > 0) {
                run_assignment_menu();
            }
            else {
                System.out.println(PRINT_RED + "\nThere are no sets to add this assignment to. Please add a set first." + PRINT_RESET);
                run_menu();
            }
        }
        else {
            end_menu = true;
        }
        if (!end_menu) {
            run_menu();
        }
    }
    public static void main(String[] args) {
        print_legend();
        run_menu();
        scanner.close();
    }
}