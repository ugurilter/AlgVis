package algvis;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;

public class BinarySearch extends JPanel implements Runnable{
   
    BinarySearch search;
    Font font = new Font("Arial", 1, 25); 
    Font labelFont = new Font("Consolas", 0, 15); 
    
    private int currentBox = 0;
    private int tryCount = 0;
    private boolean found = false;
    


    private int searchedNumber;
    private int numbers[] = new int[128];                                                //Array of numbers.
    int left;
    int right;
    int middle;
    
    private int waitTime = 0;
    private String searchType;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
    
    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public BinarySearch() {
        generateNumbers();
    }
    
    public void generateNumbers() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;                  //Generate numbers between 10-99.
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
                else if(((l*16 + i) >= left) && ((l*16 + i) <= right)) graphics.setColor(Color.ORANGE);
                else graphics.setColor(Color.GRAY);
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
        search = new BinarySearch();
        search.setWaitTime(waitTime);

        JFrame f = new JFrame();
        
        f.setTitle("Binary Search");
        f.setLocation(300, 300);                                                //Window start location.
        f.setDefaultCloseOperation(2);                                          //DISPOSE_ON_EXIT.
        f.add(search);
        f.setSize(970, 460);                                                    //Window Size.
        f.setResizable(false);                                                  //Resizable value.
        f.setVisible(true);                                                     //Visibility.
        
        search.binarySearch();
    }    

    public void binarySearch() {
        Random randomize = new Random();
        left = 0;
        right = numbers.length;
        searchedNumber = randomize.nextInt(127);
        redraw();
        
        while(left <= right){
            middle = (left + right) / 2;
            currentBox = middle;
            redraw();
            tryCount++;
            if(numbers[middle] == searchedNumber){
                found = true;
                break;
            }
            else if(numbers[middle] > searchedNumber){
                right = middle;
            }
            else if(numbers[middle] < searchedNumber){
                left = middle;
            }
            redraw();
            System.out.println("L: " + left + " -R: " + right + " -M: " + middle);
        }
        redraw();
    }
}
