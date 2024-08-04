import javax.swing.JFrame;
public class App {
    public static void main(String[] args) throws Exception {
        int Width = 600;
        int Height = 600;

        JFrame frame = new JFrame("Snake Game");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Width, Height);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        SnakeGame game = new SnakeGame(Width, Height);
        frame.add(game);
        frame.pack();
        game.requestFocusInWindow();
    }
}
