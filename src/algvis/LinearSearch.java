package algvis;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.util.Random;
import javax.swing.JFrame;

public class LinearSearch extends JPanel implements Runnable{
   
    TextField searchField;
    LinearSearch search;
    int currentBox = 0;
    int tryCount = 0;
    boolean found = false;
    Font font = new Font("Arial", 1, 25); 
    Font labelFont = new Font("Consolas", 0, 15); 
    int searchedNumber;
    
    
    int numbers[] = new int[128];                                                //Array of numbers.
    int waitTime = 100;
    String searchType;

    public LinearSearch() {
        generateNumbers();
    }
    
    public void generateNumbers() {
        Random randomize = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (randomize.nextInt(89) + 10);                          //Generate numbers between 10-99.
        }
    }
    
    public void redraw() {
        super.repaint();
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void paint(Graphics graphics) {
        super.paintComponents(graphics);

        final int BOX_WIDTH = 50;
        final int BOX_HEIGHT = 25;
        
        int xCoord = 5;
        int yCoord = 80;

        graphics.setColor(Color.darkGray);                                          //Background color.
        graphics.fillRect(0, 0, 970, 460);                                      //Background rectangle.

        
        for(int l = 0; l < 8; l++){
            for(int i = 0; i < numbers.length/8; i++){
                graphics.setColor(Color.black);                                 
                graphics.drawRect(xCoord, yCoord, BOX_WIDTH + 1, BOX_HEIGHT + 1);                
                
                if(currentBox == (l*16 + i) && !found) graphics.setColor(Color.YELLOW);
                else if(currentBox == (l*16 + i) && found) graphics.setColor(Color.GREEN);
                else graphics.setColor(Color.ORANGE);
                graphics.fillRect(xCoord+1, yCoord+1, BOX_WIDTH, BOX_HEIGHT);
                xCoord += BOX_WIDTH + 10;                
            }
            xCoord = 5;
            yCoord += BOX_HEIGHT + 20;
        }
        
        int counter = 0;
        xCoord = 22;
        yCoord = 98;
        
        for(int l = 0; l < 8; l++){
            for(int i = 0; i < numbers.length/8; i++){
                graphics.setColor(Color.black);
                graphics.setFont(labelFont);
                graphics.drawString(String.valueOf(numbers[counter]), xCoord , yCoord);
                xCoord += BOX_WIDTH + 10;
                counter++;
            }
            xCoord = 22;
            yCoord += BOX_HEIGHT + 20;
        }
        
        graphics.setColor(Color.white);
        graphics.setFont(font);
        graphics.drawString("Random number being searched is: " + searchedNumber, 250, 25);
        
        if(found){
            graphics.setColor(Color.white);
            graphics.setFont(font);
            graphics.drawString("Found in: " + tryCount + " tries.", 365, 60);
        }
        else if(currentBox == 129){
            graphics.setColor(Color.white);
            graphics.setFont(font);
            graphics.drawString("Not Found !", 400, 60);
        }
    }
    
    
    @Override
    public void run() {
        search = new LinearSearch();
        generateNumbers();

        JFrame f = new JFrame();
        
        f.setTitle("Linear Search");
        f.setLocation(300, 300);                                                //Window start location.
        f.setDefaultCloseOperation(2);                                          //DISPOSE_ON_EXIT.
        f.add(search);
        f.setSize(970, 460);                                                    //Window Size.
        f.setResizable(false);                                                  //Resizable value.
        f.setVisible(true);                                                     //Visibility.
        
        search.linearSearch();
    }    

    private void linearSearch() {
        Random randomize = new Random();
        searchedNumber = randomize.nextInt(89) + 10;
        found = false;
        
        for(int i = 0; i < numbers.length; i++){
            currentBox = i;
            if(numbers[i] == searchedNumber){                
                found = true;
                redraw();
                break;
            }
            tryCount++;
            redraw();
        }
        
        if(!found) currentBox = 129;
        redraw();
    }
}
