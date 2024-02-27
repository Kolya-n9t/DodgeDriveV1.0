import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleGame extends JPanel implements ActionListener, KeyListener {
    private int startX = 175;
    private int startY = 480;
    private int playerSpeed=15;
    private List<Integer> xEnemyPos = new ArrayList<>();
    private List<Integer> yEnemyPos = new ArrayList<>();
    private int enemySpeed=20;
    private Timer timer;
    private boolean gameOver=false;
    private int score=0;

    public SimpleGame() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(100, this);
        timer.start();
    }
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("DodgeDriver(Beta)");
        SimpleGame simpleGame = new SimpleGame();

        jFrame.add(simpleGame);
        jFrame.setSize(400,600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //background
        g.setColor(Color.black);
        g.fillRect(0,0, 400, 600);
        //player
        g.setColor(Color.white);
        g.fillRect(startX, startY, 50, 50);
        //enemy
        g.setColor(Color.red);
        for (int i = 0; i < xEnemyPos.size(); i++) {
            g.fillOval(xEnemyPos.get(i), yEnemyPos.get(i), 35,35);
        }
        //score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Рахунок "+score, 30,30);
        //gameOver
        if (gameOver){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.PLAIN, 30));
            g.drawString("КІНЕЦ ГРИ", 200, 600);
            timer.stop();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            for (int i = 0; i < xEnemyPos.size(); i++) {
                yEnemyPos.set(i, yEnemyPos.get(i) + enemySpeed);
                if (yEnemyPos.get(i) > 600) {
                    xEnemyPos.remove(i);
                    yEnemyPos.remove(i);
                    score++;
                }
            }
            repaint();
            if (xEnemyPos.isEmpty()) {
                spawnEnemy();
            }
            checkCollision();
        }
    }
    public void spawnEnemy(){
        Random random = new Random();
        int enemyCount = random.nextInt(5)+1;
        for (int i = 0; i < enemyCount; i++) {
            int x = random.nextInt(350);
            xEnemyPos.add(x);
            yEnemyPos.add(0);
        }

    }
    public void checkCollision(){
        Rectangle playerBounds = new Rectangle(startX, startY, 50, 50);  // Границы игрока
        for (int i = 0; i < xEnemyPos.size(); i++) {
            Rectangle enemyBounds = new Rectangle(xEnemyPos.get(i), yEnemyPos.get(i), 20, 20);  // Границы врага
            if (playerBounds.intersects(enemyBounds)) {
                gameOver = true;  
                break;
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        if (!gameOver) {

            if (key == KeyEvent.VK_LEFT && startX > 0) {
                startX -= playerSpeed;

            }
            if (key == KeyEvent.VK_RIGHT && startX < 350) {
                startX += playerSpeed;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
