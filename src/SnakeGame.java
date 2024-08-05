import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import javax.swing.Timer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;




public class SnakeGame extends JPanel implements KeyListener{

    private final int windowWidth, windowHeight, TileSize = 20;
    private final double fps = 10;
    private Tile food;
    private Deque<Tile> snake = new LinkedList<>();
    private final Random random = new Random();
    private final Timer timer;
    private int dx = 0, dy = 1, direction = 0;
    private boolean moveMade = true;
    private SpriteManager spriteManager = new SpriteManager("src/SnakeAllSprites.png");

    private class Tile {
        int x;
        int y;
        BufferedImage sprite;
        int direction;
        Tile(int x, int y, BufferedImage sprite, int direction) {
            this.x = x;
            this.y = y;
            this.sprite = sprite;
            this.direction = direction;
        }
        public boolean checkTileCollision(Tile tile) {
            return x == tile.x && y == tile.y;
        }
        public void draw(Graphics g) {
            // g.setColor(color);
            // g.fillRect(x * TileSize, y * TileSize, TileSize, TileSize);
            g.drawImage(sprite, x * TileSize, y * TileSize, TileSize, TileSize, null);
        }
        public void setSprite(BufferedImage sprite) {
            this.sprite = sprite;
        }
        public void draw(Graphics g, Color color) {
            g.setColor(color);
            g.fillRect(x * TileSize, y * TileSize, TileSize, TileSize);
        }
        public int getDirection() {
            return direction;
        }
        public void setDirection(int direction) {
            this.direction = direction;
        }
    }
    

    SnakeGame(int Width, int Height) {
        windowWidth = Width;
        windowHeight = Height;
        setPreferredSize(new Dimension(windowWidth, windowHeight));
        setBackground(Color.BLACK);
        food = randomFoodTile();

        snake.add(new Tile(6, 6, spriteManager.getSprite(0),0));
        snake.add(new Tile(6, 5, spriteManager.getSprite(16),0));
        snake.add(new Tile(6, 4, spriteManager.getSprite(20),0));
        // snake.add(new Tile(4, 5));

        setFocusable(true);
        addKeyListener(this);

        int delay = (int) (1000 / fps);
        timer = new Timer(delay, e -> gameLoop());
        timer.start();

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
        Graphics2D g2d = (Graphics2D) g;
        food.draw(g);
        snake.forEach(tile -> tile.draw(g)); // to optimize it is posible to draw only the head and tail
        g2d.setColor(Color.WHITE);

        // Set the thickness for the grid lines
        float thickness = 0.1f;
        g2d.setStroke(new BasicStroke(thickness));

        for (int x = 0; x < windowWidth; x += TileSize) {
            g2d.drawLine(x, 0, x, windowHeight);
        }
        for (int y = 0; y < windowHeight; y += TileSize) {
            g2d.drawLine(0, y, windowWidth, y);
        }
    }

    public void gameLoop() {
        if (dy == 0 && dx == 0) return; // if no move is made
        Tile head = snake.peekFirst();
        //Tile newHead = new Tile(head.x + dx, head.y + dy);
        Tile newHead = new Tile((head.x + dx + windowWidth / TileSize) % (windowWidth / TileSize), 
                                (head.y + dy + windowHeight / TileSize) % (windowHeight / TileSize),
                                spriteManager.getSprite(direction),direction);
        if(moveMade)
        //snake.peekFirst().setSprite(spriteManager.getSprite(12 + direction));
        snake.peekFirst().setSprite(spriteManager.getSprite(16 + direction));
        else{
            int oldDirection = snake.peekFirst().getDirection();
            snake.peekFirst().setDirection(direction);
            snake.peekFirst().setSprite(getBodySprite(oldDirection, direction));
        }
        snake.addFirst(newHead);

        if (newHead.checkTileCollision(food)) food = randomFoodTile();
        else {
            //snake.peekLast().setSprite(spriteManager.getSprite(20 + snake.peekLast().getDirection()));
            snake.removeLast();
            snake.peekLast().setSprite(spriteManager.getSprite(20 + snake.peekLast().getDirection()));
            // Not working because tail need to whait to rotate
        }
        for (Tile tile : snake) {
            if (tile != newHead && newHead.checkTileCollision(tile)) {
                timer.stop();
                System.out.println("Game Over");
                break;
            }
        }
        moveMade = true;
        repaint();
    }

    public Tile randomFoodTile() {
        int maxTilesX = windowWidth / TileSize;
        int maxTilesY = windowHeight / TileSize;

        List<Tile> possibleTiles = new ArrayList<>();

        for (int x = 0; x < maxTilesX; x++) {
            for (int y = 0; y < maxTilesY; y++) {
                possibleTiles.add(new Tile(x, y, null,-1));
            }
        }
        // Remove positions occupied by the snake
        for (Tile snakeTile : snake) {
            possibleTiles.removeIf(tile -> tile.x == snakeTile.x && tile.y == snakeTile.y);
        }
        if (possibleTiles.isEmpty()) {
            throw new IllegalStateException("No possible positions for food"); // @TODO change it to game over
        }

        Tile randomTile = possibleTiles.get(random.nextInt(possibleTiles.size()));
        return new Tile(randomTile.x, randomTile.y, spriteManager.getSprite(24),-1);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!moveMade) return;
        if (e.getKeyCode() == KeyEvent.VK_UP && dy != 1 && dy != -1) {
            dx = 0;
            dy = -1;
            direction = 2;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && dy != -1 && dy != 1) {
            dx = 0;
            dy = 1;
            direction = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && dx != 1 && dx != -1) {
            dx = -1;
            dy = 0;
            direction = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && dx != -1 && dx != 1) {
            dx = 1;
            dy = 0;
            direction = 3;
        }
        else return;

        moveMade = false;
    }

    private BufferedImage getBodySprite(int prevDirection, int currentDirection) {
        if (prevDirection == 0 && currentDirection == 1) return spriteManager.getSprite(14); // Down to Left
        if (prevDirection == 0 && currentDirection == 3) return spriteManager.getSprite(15); // Down to Right
        if (prevDirection == 1 && currentDirection == 0) return spriteManager.getSprite(12); // Left to Down
        if (prevDirection == 1 && currentDirection == 2) return spriteManager.getSprite(15); // Left to Up
        if (prevDirection == 2 && currentDirection == 1) return spriteManager.getSprite(13); // Up to Left
        if (prevDirection == 2 && currentDirection == 3) return spriteManager.getSprite(12); // Up to Right
        if (prevDirection == 3 && currentDirection == 0) return spriteManager.getSprite(13); // Right to Down
        if (prevDirection == 3 && currentDirection == 2) return spriteManager.getSprite(14); // Right to Up
    
        // // For straight segments
        // if ((prevDirection == 0 && currentDirection == 0) || (prevDirection == 2 && currentDirection == 2)) return spriteManager.getSprite(16); // Vertical body
        // if ((prevDirection == 1 && currentDirection == 1) || (prevDirection == 3 && currentDirection == 3)) return spriteManager.getSprite(17); // Horizontal body
    
        return spriteManager.getSprite(16); // Default to vertical body if no match
    }

    // Unnecessary methods
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}

