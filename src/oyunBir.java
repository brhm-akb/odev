
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class oyunBir extends JFrame{
    
    public boolean resetter = false;
    
    public boolean flagger = false;
    
    Date startDate = new Date();
    Date endDate;
    
    int spacing = 5;
    int mayinsayisi = 25;
    
    int neighs = 0;
    
    String vicMes = "Nothing Yet!";
    
    public int mx = -100;
    public int my = -100;
    
    public int smileX = 605;
    public int smileY = 5;
    
    public int smileyCenterX = smileX + 35;
    public int smileyCenterY = smileY + 35;
    
    public int flaggerX = 445;
    public int flaggerY = 5; 
    
    public int flaggerCenterX = flaggerX + 32;
    public int flaggerCenterY = flaggerY + 15;
    
    public int timeX = 5;
    public int timeY = 5;
    
    public int vicMesX = 890;
    public int vicMesY = -50;
    
    public int sec = 0;
    
    public boolean mutlu = true; 
    
    public boolean victory = false;
    
    public boolean defeat = false;
    
    Random rnd = new Random();
    
    int mines[][] = new int[16][9];
    int neighbours[][] = new int[16][9];
    boolean revealed[][] = new boolean[16][9];
    boolean flagged[][] = new boolean[16][9];
    
    public oyunBir(){
        this.setTitle("Mayın Tarlası");
        this.setSize(1286,869);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if(rnd.nextInt(100) < 20){
                    mines[i][j] = 1;
                    mayinsayisi--;
                }
                
                else{
                        mines[i][j] = 0;
                }
                revealed[i][j] = false;
               
            }
        }
        
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                neighs = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                       if(!(m == i && n == j)){
                           if(isN(i,j,m,n) == true)
                            neighs++;
                       }
                    }
                }
                neighbours[i][j] = neighs;
            }
        }

        
        Board board = new Board();
        this.setContentPane(board);
        
        Move move = new Move();
        this.addMouseMotionListener(move);
        
        Click click = new Click();
        this.addMouseListener(click);
        
        barlar();
        
    }
    
    public class Board extends JPanel{
        public void paintComponent(Graphics g){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 1280, 840);
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 9; j++) {
                    g.setColor(Color.gray);
                    if(revealed[i][j] == true){
                        g.setColor(Color.WHITE);
                        if(mines[i][j] == 1){
                            g.setColor(Color.red);
                        }
                    }
                    if(mx >= spacing+i*80 && mx< spacing+i*80+80-2*spacing && my >=spacing+j*80+80+55 && my < spacing+j*80+55+80+80-2*spacing){
                        g.setColor(Color.lightGray);
                    }
                    g.fillRect(spacing+i*80, spacing+j*80+80, 80-2*spacing, 80-2*spacing);
                    if(revealed[i][j] == true){
                        g.setColor(Color.black);
                        if(mines[i][j] == 0 && neighbours[i][j] != 0){
                            if(neighbours[i][j] == 1){
                                g.setColor(Color.BLUE);
                            }
                            else if(neighbours[i][j] == 2){
                                g.setColor(Color.green);
                            }
                            else if(neighbours[i][j] == 3){
                                g.setColor(Color.red);
                            }
                            else if(neighbours[i][j] == 4){
                                g.setColor(new Color(0,0,128));
                            }
                            else if(neighbours[i][j] == 5){
                                g.setColor(new Color(178,34,34));
                            }
                            else if(neighbours[i][j] == 6){
                                g.setColor(new Color(72,209,204));
                            }
                            else if(neighbours[i][j] == 8){
                                g.setColor(Color.DARK_GRAY);
                            }
                            g.setFont(new Font("Tahoma", Font.BOLD, 40));
                            g.drawString(Integer.toString(neighbours[i][j]), i*80+27, j*80+80+55);
                        }
                        else if(mines[i][j] == 1){
                            g.fillRect(i*80+10+20, j*80+80+20, 20, 40);
                            g.fillRect(i*80+20, j*80+80+10+20, 40, 20);
                            g.fillRect(i*80+5+20, j*80+80+5+20, 30, 30);
                            g.fillRect(i*80+38, j*80+80+15, 4, 50);
                            g.fillRect(i*80+15, j*80+80+38, 50, 4);
                            
                        }
                    }
                    
                    //bayrak yerleştirmek
                    if(flagged[i][j] == true){
                        g.setColor(Color.BLACK);
                        g.fillRect(i*80+32+5, j*80+80+15+5, 5, 40);
                        g.fillRect(i*80+20+5, j*80+80+50+5, 30, 10);
                        g.setColor(Color.red);
                        g.fillRect(i*80+16+5, j*80+80+15+5, 20, 15);
                        g.setColor(Color.BLACK);
                        g.drawRect(i*80+16+5, j*80+80+15+5, 20, 15);
                        g.drawRect(i*80+17+5, j*80+80+16+5, 18, 13);
                        g.drawRect(i*80+18+5, j*80+80+17+5, 16, 11);
                    }
                }
            }
            
            //kafası
            g.setColor(Color.YELLOW);
            g.fillOval(smileX, smileY, 70, 70);
            //gözü
            g.setColor(Color.red);
            g.fillRect(smileX+15, smileY+20, 10, 10);
            g.fillRect(smileX+45, smileY+20, 10, 10);
            //ağzı
            if(mutlu == true){
                g.setColor(Color.red);
                g.fillRect(smileX+15, smileY+50, 40, 5);
                g.fillRect(smileX+15, smileY+45, 5, 5);
                g.fillRect(smileX+50, smileY+45, 5, 5);
            }
            else{
                g.setColor(Color.red);
                g.fillRect(smileX+15, smileY+50, 40, 5);
                g.fillRect(smileX+15, smileY+55, 5, 5);
                g.fillRect(smileX+50, smileY+55, 5, 5);
            }
            
            //bayrak
            
            g.setColor(Color.BLACK);
            g.fillRect(flaggerX+32, flaggerY+15, 5, 40);
            g.fillRect(flaggerX+20, flaggerY+50, 30, 10);
            g.setColor(Color.red);
            g.fillRect(flaggerX+16, flaggerY+15, 20, 15);
            g.setColor(Color.BLACK);
            g.drawRect(flaggerX+16, flaggerY+15, 20, 15);
            g.drawRect(flaggerX+17, flaggerY+16, 18, 13);
            g.drawRect(flaggerX+18, flaggerY+17, 16, 11);
            
            if(flagger == true){
                g.setColor(Color.red);
            }
            
            g.drawOval(flaggerX, flaggerY, 70, 70);
            g.drawOval(flaggerX+1, flaggerY+1, 68, 68);
            g.drawOval(flaggerX+2, flaggerY+2, 66, 66);
            
            //süre
            
            g.setColor(Color.BLACK);
            g.fillRect(timeX, timeY, 120, 70);
            if(defeat == false && victory == false){
             sec = (int)((new Date().getTime()-startDate.getTime()) /1000);
            }
            if(sec > 999){
                sec = 999;
            }
            g.setColor(Color.WHITE);
            if(victory == true){
                g.setColor(Color.green);
            }
            else if(defeat == true){
                g.setColor(Color.red);
            }
            g.setFont(new Font("Tahoma", Font.PLAIN, 70));
            if(sec < 10){
                g.drawString("00"+Integer.toString(sec), timeX, timeY+65);
            }
            else if(sec < 100){
                g.drawString("0"+Integer.toString(sec), timeX, timeY+65);
            }
            else{
                g.drawString(Integer.toString(sec), timeX, timeY+65);
            }
            
            //oyun sonu mesajı
            
            if(victory == true){
                g.setColor(Color.green);
                vicMes = "KAZANDIN";
            }
            else if(defeat == true){
                g.setColor(Color.red);
                vicMes = "KAYBETTİN";
            }
            
            if(victory == true || defeat == true){
                vicMesY = -50 + (int) (new Date().getTime() - endDate.getTime()) / 10;
                if(vicMesY > 70){
                    vicMesY = 70;
                }
                g.setFont(new Font("Tahoma", Font.PLAIN, 70));
                g.drawString(vicMes, vicMesX, vicMesY);
            }
            
       }
    }
    
    public class Move implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            repaint();
            //System.out.println("x:"+mx);
            //System.out.println("y:"+my);
        }
        
    }
    
    public class Click implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            
                        
            if(inBoxX() != -1 && inBoxY() !=-1){
                if(flagger == true && revealed[inBoxX()][inBoxY()] == false){
                    if(flagged[inBoxX()][inBoxY()] == false){
                        flagged[inBoxX()][inBoxY()] = true;
                    }
                    else{
                        flagged[inBoxX()][inBoxY()] = false;
                    }
                }
                else{
                    if(flagged[inBoxX()][inBoxY()] == false){
                        revealed[inBoxX()][inBoxY()] = true;
                    }
                }
            }
            
            if(inSmiley() == true){
                resetAll();
            }
            
            if(inFlagger() == true){
                if(flagger == false){
                    flagger = true;
                }
                else{
                    flagger = false;
                }
            }   
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }

    }
    
    public void checkVictoryStatus(){
        
        if(defeat == false){
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 9; j++) {
                    if(revealed[i][j] == true && mines[i][j] == 1){
                        defeat = true;
                        mutlu = false;
                        endDate = new Date();
                    }
                }
            }
        }
        if(totalBoxesRevealed() >= 144 - totalMines() && victory == false){
            victory = true;
            endDate = new Date();
        }
        else{
            
        }
        
    }
    
    public int totalMines(){
        int total = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if(mines[i][j] == 1){
                    total++;
                }
            }
        }
        return total;
    }
    
    public int totalBoxesRevealed(){
        int total = 0;
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if(revealed[i][j] == true){
                    total++;
                }
            }
        }
        return total;
    }
    
    public void resetAll(){
        
        resetter = true;
        
        flagger = false;
        
        startDate = new Date();
        
        vicMesY = -50;
        
        vicMes = "Nothing Yet!";
        
        mutlu = true;
        victory = false;
        defeat = false;
        
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if(rnd.nextInt(100) < 20){
                    mines[i][j] = 1;
                    mayinsayisi--;
                }
                
                else{
                        mines[i][j] = 0;
                }
                revealed[i][j] = false;
                flagged[i][j] = false;
            }
        }
        
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                neighs = 0;
                for (int m = 0; m < 16; m++) {
                    for (int n = 0; n < 9; n++) {
                       if(!(m == i && n == j)){
                           if(isN(i,j,m,n) == true)
                            neighs++;
                       }
                    }
                }
                neighbours[i][j] = neighs;
            }
        }
        resetter = false;
    }
    
    public boolean  inSmiley(){
        int dif = (int) Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX)+Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
        if(dif < 70){
            return true;
        }
        return false;
    }
    
    public boolean  inFlagger(){
        int dif = (int) Math.sqrt(Math.abs(mx-flaggerCenterX)*Math.abs(mx-flaggerCenterX)+Math.abs(my-flaggerCenterY)*Math.abs(my-flaggerCenterY));
        if(dif < 90){
            return true;
        }
        return false;
    }
    
    public int inBoxX(){
        for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 9; j++) {
                    if(mx >= spacing+i*80 && mx< spacing+i*80+80-2*spacing && my >=spacing+j*80+80+55 && my < spacing+j*80+55+80+80-2*spacing){
                        return i;
                        
                    }
                    
                }
            }
        return -1;
    }
    
    public int inBoxY(){
        for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 9; j++) {
                    if(mx >= spacing+i*80 && mx< spacing+i*80+80-2*spacing && my >=spacing+j*80+80+55 && my < spacing+j*80+55+80+80-2*spacing){
                        return j;
                    }
                    
                }
            }
        return -1;
    }
    
    public boolean isN(int mX, int mY, int cX, int cY){
        if(mX - cX < 2 && mX- cX > -2 && mY - cY < 2 && mY - cY > -2 && mines[cX][cY] == 1){
            return true;
        }
        return false;
    }
    
    public final void barlar() {
    	JMenuBar menu = new JMenuBar();
        this.setJMenuBar(menu);
        
        JMenu ayarlar = new JMenu("Ayarlar");
        menu.add(ayarlar);

        
        JMenuItem cikis = new JMenuItem("Çıkış");
        ayarlar.add(cikis);
        
        
        cikis.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(JFrame.EXIT_ON_CLOSE);
            }
        });
    }

}