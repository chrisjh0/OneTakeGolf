import javax.swing.*;
import java.awt.*;

public class Board {
    private final int tSize = 32;
    private final int rows = 20;
    private final int cols = 20;

    private int[][] map;
    private Image grassTile;
    private Image blockTile;
    private Image holeTile;
    private Image startTile;
    private Image iceTile;
    private Image sandTile;
    private Image lavaTile;
    private Image tile;
    private int startX;
    private int startY;

    public Board() {
        grassTile = new ImageIcon(".idea/images/grassTile.png").getImage();
        blockTile = new ImageIcon(".idea/images/blockTile.png").getImage();
        holeTile = new ImageIcon(".idea/images/holeTile.png").getImage();
        startTile = new ImageIcon(".idea/images/startTile.png").getImage();
        iceTile = new ImageIcon(".idea/images/iceTile.png").getImage();
        sandTile = new ImageIcon(".idea/images/sandTile.png").getImage();
        lavaTile = new ImageIcon(".idea/images/lavaTile.png").getImage();
        int ranMap = (int) (Math.random() * Maps.getLevels() + 1);
        map = Maps.levelNum(ranMap);

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == 3) {
                    startX = c * tSize;
                    startY = r * tSize;
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == 1) {
                    tile = blockTile;
                } else if (map[r][c] == 2) {
                    tile = holeTile;
                } else if (map[r][c] == 3) {
                    tile = startTile;
                } else if (map[r][c] == 4) {
                    tile = iceTile;
                } else if (map[r][c] == 5) {
                    tile = sandTile;
                } else if (map[r][c] == 6) {
                    tile = lavaTile;
                } else {
                    tile = grassTile;
                }
                g.drawImage(tile, c * tSize, r * tSize, tSize, tSize, null);
            }
        }
    }

    public int getTileAt(int x, int y) {
        int col = x / tSize;
        int row = y / tSize;
        if (col < 0 || col >= cols || row < 0 || row >= rows) return -1;
        return map[row][col];
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public boolean isWallAt(int x, int y) {
        int col = x / tSize;
        int row = y / tSize;
        if (col < 0 || col >= cols || row < 0 || row >= rows) {
            return true;
        }
        return map[row][col] == 1;
    }
}
