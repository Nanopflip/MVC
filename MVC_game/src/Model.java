import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class Model {
    private String aWert;
    private BufferedImage img;
    private boolean spinning = false;
    private int aRow[][] = {
            {0,1,2,3,4,5},
            {0,1,2,3,4,5},
            {0,1,2,3,4,5},
    };
    private int aPos[] = {0,0,0};
    private int money = 100;

    public String getaWert(){
        return this.aWert;
    }

    public void setaWert(String pWert){
        this.aWert = pWert;
    }

    public int[][] getaRow(){
        return aRow;
    }

    public void setaRow(int[][] pRow){
        this.aRow = pRow;
    }

    public boolean isSpinning() {
        return spinning;
    }

    public void setSpinning(boolean spinning) {
        this.spinning = spinning;
    }

    public int[] getaPos() {
        return aPos;
    }

    public void setaPos(int[] pPos) {
        this.aPos = pPos;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money += money;
    }
}