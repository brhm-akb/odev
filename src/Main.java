
public class Main implements Runnable{
    oyunBir oyunBir = new oyunBir();
    public static void main(String[] args) {
    new Thread(new Main()).start();
    }
    @Override
    public void run() {
        while(true){
            oyunBir.repaint();
            if(oyunBir.resetter == false){
                oyunBir.checkVictoryStatus();
                //System.out.println("Victory: "+oyunBir.victory+", Defeat: "+oyunBir.defeat);
            }
            
        }
    }
}