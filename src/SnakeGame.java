import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.*;



public class SnakeGame extends JPanel {
    int windowWidth;
    int windowHeight;
    int TileSize = 30;
    Tile food;
    Queue<Tile> snake = new LinkedList<Tile>();

    private class Tile {
        int x;
        int y;
        Tile(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void draw(Graphics g, Color color) {
            g.setColor(color);
            g.fillRect(x * TileSize, y * TileSize, TileSize, TileSize);
        }
    }
    

    SnakeGame(int Width, int Height) {
        windowWidth = Width;
        windowHeight = Height;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);
        food = randomFoodTile();
        snake.add(new Tile(5, 5));
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(windowWidth, windowHeight);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, windowWidth, windowHeight);
        draw(g);
    }

    public void draw(Graphics g) {
        food.draw(g, Color.RED);
        snake.forEach(tile -> tile.draw(g, Color.GREEN));
        g.setColor(Color.WHITE);
        for (int x = 0; x < windowWidth; x += TileSize) {
            g.drawLine(x, 0, x, windowHeight);
        }
        for (int y = 0; y < windowHeight; y += TileSize) {
            g.drawLine(0, y, windowWidth, y);
        }
    }

    public Tile randomFoodTile() {
        int maxTilesX = windowWidth / TileSize;
        int maxTilesY = windowHeight / TileSize;

        List<Tile> possibleTiles = new ArrayList<>();

        for (int x = 0; x < maxTilesX; x++) {
            for (int y = 0; y < maxTilesY; y++) {
                possibleTiles.add(new Tile(x, y));
            }
        }
        // Remove positions occupied by the snake
        for (Tile snakeTile : snake) {
            possibleTiles.removeIf(tile -> tile.x == snakeTile.x && tile.y == snakeTile.y);
        }
        if (possibleTiles.isEmpty()) {
            throw new IllegalStateException("No possible positions for food"); // @TODO change it to game over
        }

        Random random = new Random();
        Tile randomTile = possibleTiles.get(random.nextInt(possibleTiles.size()));
        return new Tile(randomTile.x, randomTile.y);
    }

}

