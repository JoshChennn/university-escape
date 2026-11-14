package comp2450;
import java.util.*;

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
    
    // Maze
    public static ArrayList<Maze> mazes = new ArrayList<>();
    public static int mazeWidth;
    public static int mazeHeight;

    // Player
    public static boolean playerAdded = false;
    public static int playerX;
    public static int playerY;

    public static int get_user_selection(int highestValidNumber) {
        int input = -1;
        while (input == -1) {
            try {
                input = Integer.valueOf(scanner.nextLine());
                if (input < 1 || input > highestValidNumber) {
                    System.out.print("Please enter a valid number from 1-" + highestValidNumber + ": ");
                    input = get_user_selection(highestValidNumber);
                }
            }
            catch (InputMismatchException ime) {
                System.out.print("Please enter a valid number from 1-" + highestValidNumber + ": ");
            }
            catch (NumberFormatException nfe) {
                System.out.print("Please enter a valid number from 1-" + highestValidNumber + ": ");
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
        System.out.println("1: Edit Mazes");
        System.out.println("2: Edit Player");
        System.out.println("3: Edit Guards");
        System.out.println("4: Edit Items");
        System.out.println("5: Edit Sets");
        System.out.println("6: Edit Assignments");
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
        int selection = get_user_selection(6);
        Maze m = mazes.get(selection-1);
        System.out.println(("■  ".repeat(m.width) + "\n").repeat(m.height));
    }
    public static void remove_maze() {
        System.out.println(PRINT_CYAN + "\nMAZES" + PRINT_RESET);
        print_maze_list();
        System.out.print("Which maze would you like to delete? ");
        int selection = get_user_selection(mazes.size());
        mazes.remove(mazes.get(selection-1));
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
            System.out.print("How many columns should the maze have? ");
            mazeWidth = get_user_selection(20);
            System.out.print("How many rows should the maze have? ");
            mazeHeight = get_user_selection(20);
            Maze maze = new Maze(mazeWidth, mazeHeight);
            mazes.add(maze);
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
            text = "The player is at position ____.";
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
            highestNumber = 3;
        }
        int s = get_user_selection(highestNumber);
        if (s == 1) {
            System.out.print("Pick the player's initial X coordinate (from 1-" + mazeWidth + "): ");
            playerX = get_user_selection(mazeWidth);
            System.out.print("Pick the player's initial Y coordinate (from 1-" + mazeHeight + "): ");
            playerX = get_user_selection(mazeHeight);
            playerAdded = true;
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
            run_player_menu();
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