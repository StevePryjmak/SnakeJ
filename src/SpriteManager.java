import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class SpriteManager {
    private BufferedImage spriteSheet;
    private BufferedImage[] snakeSprites;

    public SpriteManager(String path) {
        try {
            spriteSheet = ImageIO.read(new File(path));
            snakeSprites = new BufferedImage[26];

            // Each sprite is 40x40 pixels
            snakeSprites[0] = spriteSheet.getSubimage(0, 0, 40, 40); // Head1 (Down)
            snakeSprites[1] = spriteSheet.getSubimage(40, 0, 40, 40); // Head1 (Left)
            snakeSprites[2] = spriteSheet.getSubimage(80, 0, 40, 40); // Head1 (Up)
            snakeSprites[3] = spriteSheet.getSubimage(120, 0, 40, 40); // Head1 (Right)

            snakeSprites[4] = spriteSheet.getSubimage(0, 40, 40, 40); // Head2 (Down)
            snakeSprites[5] = spriteSheet.getSubimage(40, 40, 40, 40); // Head2 (Left)
            snakeSprites[6] = spriteSheet.getSubimage(80, 40, 40, 40); // Head2 (Up)
            snakeSprites[7] = spriteSheet.getSubimage(120, 40, 40, 40); // Head2 (Right)

            snakeSprites[8] = spriteSheet.getSubimage(0, 80, 40, 40); // Head3 (Down)
            snakeSprites[9] = spriteSheet.getSubimage(40, 80, 40, 40); // Head3 (Left)
            snakeSprites[10] = spriteSheet.getSubimage(80, 80, 40, 40); // Head3 (Up)
            snakeSprites[11] = spriteSheet.getSubimage(120, 80, 40, 40); // Head3 (Right)

            snakeSprites[12] = spriteSheet.getSubimage(0, 120, 40, 40); // Turn (Down -> Right)
            snakeSprites[13] = spriteSheet.getSubimage(40, 120, 40, 40); // Turn (Down -> Left)
            snakeSprites[14] = spriteSheet.getSubimage(80, 120, 40, 40); // Turn (Up -> Left)
            snakeSprites[15] = spriteSheet.getSubimage(120, 120, 40, 40); // Turn (Up -> Right)

            snakeSprites[16] = spriteSheet.getSubimage(0, 160, 40, 40); // line (Up -> Down)
            snakeSprites[17] = spriteSheet.getSubimage(40, 160, 40, 40); // line (Left -> Right)
            snakeSprites[18] = spriteSheet.getSubimage(80, 160, 40, 40); // line (Down -> Up)
            snakeSprites[19] = spriteSheet.getSubimage(120, 160, 40, 40); // line (Right -> Left)

            snakeSprites[20] = spriteSheet.getSubimage(0, 200, 40, 40); // Tail (Down open)
            snakeSprites[21] = spriteSheet.getSubimage(40, 200, 40, 40); // Tail (Left open)
            snakeSprites[22] = spriteSheet.getSubimage(80, 200, 40, 40); // Tail (Up open)
            snakeSprites[23] = spriteSheet.getSubimage(120, 200, 40, 40); // Tail (Right open)

            snakeSprites[24] = spriteSheet.getSubimage(0, 240, 40, 40); // Apple
            snakeSprites[25] = spriteSheet.getSubimage(40, 240, 40, 40); // Golden Apple

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int index) {
        return snakeSprites[index];
    }
}