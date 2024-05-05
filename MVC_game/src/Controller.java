import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.math.*;

public class Controller implements Runnable {
    Model dasModel;
    View dieView;
    Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private BufferedImage machine, handle;
    private BufferedImage[] img;
    private int aSpin, aSpinTotal, aSpinPosition, aHandlePos, aHandleTotal;
    private boolean aGame = false;
    private boolean aPlaying = false;
    private boolean handleAni = false, handleback = false;


    public Controller(View pView){
        dasModel = new Model();
        this.dieView = pView;
    }
    public void speichern(){
        dieView.setWert(dasModel.getaWert());
    }
    public void laden(){
        dasModel.setaWert(dieView.getWert());
    }

    public void starte(){
        dieView.deleteReference();
        dieView.repaint();
        startGameLoop();
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        aGame = true;
        img = new BufferedImage[6];
        handle = new BufferedImage(52, 52, BufferedImage.TYPE_INT_ARGB);
        machine = new BufferedImage(192, 192, BufferedImage.TYPE_INT_ARGB);
        handle = getImage("/src/pics/switch.png");
        machine = getImage("/src/pics/kara.png");
        img = getImages(new String[]{"/src/pics/1.png", "/src/pics/2.png", "/src/pics/3.png", "/src/pics/4.png", "/src/pics/5.png", "/src/pics/6.png"});
        gameThread.start();
    }

    private BufferedImage getImage(String adress){
        InputStream is = View.class.getResourceAsStream(adress);
        BufferedImage img;
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }
    private BufferedImage[] getImages(String[] adress){
        BufferedImage[] img = new BufferedImage[adress.length];
        for (int i = 0; adress.length > i; i++) {
            InputStream is = View.class.getResourceAsStream(adress[i]);
            try {
                img[i] = ImageIO.read(is);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return img;
    }

    public void spin(){
        if (dasModel.getMoney() > 0 && !aPlaying){
            aPlaying = true;
            dasModel.setaRow(shuffleArray(dasModel.getaRow()));
            dasModel.setaPos(random(dasModel.getaPos()));
            aSpinPosition = 0;
            dasModel.setSpinning(true);
            dasModel.setMoney(-10);
            dieView.setWert(String.valueOf(dasModel.getMoney()));
            handleAni = true;
        }
    }

    private int[] random(int[] array) {
        for (int i = 0; i < array.length; i++){
            array[i] = (int)(Math.random()*5);
        }
        return array;
    }

    private int[][] shuffleArray(int[][] array) {
        Random rnd = new Random();
        for (int[] row : array) {
            for (int i = row.length - 1; i > 0; i--) {
                int index = rnd.nextInt(i + 1);
                int temp = row[index];
                row[index] = row[i];
                row[i] = temp;
            }
        }
        return array;
    }

    private void update(){
        if (dasModel.isSpinning()){
            aSpin++;
            dieView.painting();
            if (aSpin >= 20){
                aSpin = 0;
                aSpinTotal++;
                if (aSpinTotal == dasModel.getaPos()[aSpinPosition] + 12){
                    aSpinTotal = 0;
                    aSpinPosition++;
                    if (aSpinPosition == 3){
                        dasModel.setSpinning(false);
                        checkForWin();
                    }
                }
            }
        }
    }

    private void checkForWin(){
        int[] pos = dasModel.getaPos();
        int[][] row = dasModel.getaRow();
        if ((row[0][pos[0]] == row[1][pos[1]] && row[1][pos[1]] == row[2][pos[2]])){
            dasModel.setMoney(50);
        } else if ((row[0][pos[0]] == row[1][pos[1]])) {
            dasModel.setMoney(10);
        } else if ((row[1][pos[1]] == row[2][pos[2]])) {
            dasModel.setMoney(10);
        }
        dieView.setWert(String.valueOf(dasModel.getMoney()));
        aPlaying = false;
    }

    public void render(Graphics g){
        if (aGame){
        // Your painting code here
        int[][] row = dasModel.getaRow();
        for (int i = aSpinPosition; i < 3; i++) {
            for (int y = 0; y < 2; y++) {
                if (aSpinTotal % 6 == 0) {
                    if (y == 0) {
                        g.drawImage(img[row[i][0]],   54 + 27 * i, 25 + (int)(1.3 * aSpin), 27, 27, null);
                    } else {
                        g.drawImage(img[row[i][5]],  54 + 27 * i, 25 + 26 * y + (int)(1.3 * aSpin), 27, 27, null);
                    }
                } else {
                    g.drawImage(img[row[i][aSpinTotal % 6 - y]],  54 + 27 * i, 25 + 26 * y + (int)(1.3 * aSpin), 27, 27, null);
                }
            }
        }
            switch (aSpinPosition){
                case 1:
                    g.drawImage(img[dasModel.getaRow()[0][dasModel.getaPos()[0]]], 54, 51, 27, 27, null);
                    break;
                case 2:
                    g.drawImage(img[dasModel.getaRow()[0][dasModel.getaPos()[0]]], 54, 51, 27, 27, null);
                    g.drawImage(img[dasModel.getaRow()[1][dasModel.getaPos()[1]]], 81, 51, 27, 27, null);
                    break;
                case 3:
                    g.drawImage(img[dasModel.getaRow()[0][dasModel.getaPos()[0]]], 54, 51, 27, 27, null);
                    g.drawImage(img[dasModel.getaRow()[1][dasModel.getaPos()[1]]], 81, 51, 27, 27, null);
                    g.drawImage(img[dasModel.getaRow()[2][dasModel.getaPos()[2]]], 108, 51, 27, 27, null);
                    break;
            }
        }
        g.drawImage(machine, 0, 0, 192, 192, null);
        if (handleAni){
            aHandlePos++;
            if (aHandlePos >= 25){
                aHandlePos = 0;
                aHandleTotal++;
                if(aHandleTotal > 0){
                    System.out.println("hier");
                    handleAni = false;
                    handleback = true;
                    aHandleTotal = 7;
                }
            }
            if (aHandleTotal >= 4){
                g.drawImage(handle.getSubimage(13 * (aHandleTotal - 4), 26, 13, 26), 145,20, 39, 78, null);
            } else {
                g.drawImage(handle.getSubimage(13 * aHandleTotal, 0, 13, 26), 145,20, 39, 78, null);
            }
        } else if (handleback) {
            aHandlePos++;
            if (aHandlePos >= 10){
                aHandlePos = 0;
                aHandleTotal--;
                if(aHandleTotal < 0){
                    System.out.println("hier");
                    handleback = false;
                    aHandleTotal = 0;
                }
            }
            if (aHandleTotal >= 4){
                g.drawImage(handle.getSubimage(13 * (aHandleTotal - 4), 26, 13, 26), 145,20, 39, 78, null);
            } else {
                g.drawImage(handle.getSubimage(13 * aHandleTotal, 0, 13, 26), 145,20, 39, 78, null);
            }
        } else {
            g.drawImage(handle.getSubimage(0,0, 13, 26), 145,20, 39, 78, null);
        }
    }

    public void run(){
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long lastFrame = System.nanoTime();
        long previesTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0.0;
        double deltaF = 0.0;
        while (true){
            long currentTime = System.nanoTime();

            deltaU += (currentTime- previesTime)/ timePerUpdate;
            deltaF += (currentTime- previesTime)/ timePerFrame;
            previesTime = currentTime;

            if (deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }
            if (deltaF >= 1){
                deltaF--;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "  | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
}