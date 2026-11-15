package comp2450;

public class Coordinates {
    int width;
    int height;
    String[][] coords;
    Coordinates(Maze maze) {
        this.width = maze.width;
        this.height = maze.height;
        this.coords = new String[this.width][this.height];
        for (int i = 1; i <= this.height; i++) {
            for (int j = 1; j <= this.width; j++) {
                coords[i-1][j-1] = "□ ";
            }
        }
    }
}