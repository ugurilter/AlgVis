package algvis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;


public final class Sort extends JPanel implements Runnable {

    private int numbers[] = new int[50];                                                //Array of numbers.
    private int waitTime = 0;                                                         //Wait time in between every step.
    private int firstPivot = numbers.length;
    private int secondPivot = numbers.length;
    private String sortType;
    private boolean bubbleComplete = false;
    private boolean selectionComplete = false;
    private boolean insertionComplete = false;
    private boolean quickComplete = false;
    private long time = 0L;
    private long timer;
    
    Font font = new Font("Arial", 1, 20);                                       //Arial, Bold, 20px.

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }
        
    public Sort() {
        repaint();
        generateNumbers();
    }

    public void generateNumbers() {
        Random randomize = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = (randomize.nextInt(89) + 10);                          //Generate numbers between 10-99.
        }

//        for(int i=0; i<numbers.length; i++){
//            numbers[i] = numbers.length-i;
//        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponents(graphics);

        final int BAR_WIDTH = 15;
        int xCoord = 5;
        int yCoord = 130;

        graphics.setColor(Color.PINK);                                          //Background color.
        graphics.fillRect(0, 0, 1015, 180);                                     //Background rectangle.


        
        
        for (int i = 0; i < numbers.length; i++) {
            graphics.setColor(Color.black);                                     //Bar border color.
            graphics.drawRect(xCoord, yCoord, BAR_WIDTH + 2, -numbers[i] - 2);  //Bar border rectangle.
            if ((i == this.firstPivot) || (i == this.secondPivot)) {            //Selected bar color selection.
                graphics.setColor(Color.YELLOW);
            } else {
                graphics.setColor(Color.RED);
            }
            graphics.drawRect(xCoord + 1, yCoord - 1, BAR_WIDTH, -numbers[i]);  //Draw inner bars.
            xCoord = xCoord + 20;                                               //Bar spacing.
        }

        xCoord = 6;                                                             //Label spacing (x-axis)
        yCoord = 145;                                                           //Label spacing (y-axis)

        for (int i = 0; i < numbers.length; i++) {
            graphics.setColor(Color.black);                                     //Label color.
            graphics.drawString(String.valueOf(numbers[i]), xCoord, yCoord);    //Label value.
            xCoord = xCoord + 20;                                               //Label spacing.
        }

        if (bubbleComplete || selectionComplete || insertionComplete || quickComplete) {
            graphics.setColor(Color.black);                                     //Shadow color.
            graphics.setFont(font);
            graphics.drawString(String.valueOf(timer) + " milliseconds", 460, 20);
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

    /* BUBBLE SORT ALGORITHM */
    public void bubbleSort() {
        startTime();
        int counter = 0;
        boolean sorted;
        do {
            sorted = true;
            for (int i = 0; i < numbers.length - 1 - counter; i++) {
                if (this.numbers[i] > this.numbers[(i + 1)]) {
                    swapNumbers(i, i + 1);
                    sorted = false;
                    redraw();
                }

                this.firstPivot = i;
                this.secondPivot = (i + 1);
                redraw();
            }
            counter++;
        } while (!sorted);

        this.firstPivot = numbers.length;
        this.secondPivot = numbers.length;
        bubbleComplete = true;
        System.out.println("Bubble completed!");
        stopTime();
        redraw();
    }

    /* SELECTION SORT ALGORITHM */
    public void selectionSort() {
        startTime();
        for (int i = 0; i < numbers.length; i++) {
            int min = this.numbers[i];
            int minIndex = i;
            for (int j = i; j < numbers.length; j++) {
                if (this.numbers[j] < min) {
                    min = this.numbers[j];
                    minIndex = j;
                }

                this.firstPivot = j;
                this.secondPivot = minIndex;
                redraw();
            }
            int tmp = this.numbers[minIndex];
            this.numbers[minIndex] = this.numbers[i];
            this.numbers[i] = tmp;
        }

        this.firstPivot = numbers.length;
        this.secondPivot = numbers.length;
        selectionComplete = true;
        System.out.println("Selection completed!");
        stopTime();
        redraw();
    }
    
    /* QUICKSORT CALLER (FOR TIMER) */
    public void quickS(){
        startTime();
        quickSort(0, numbers.length-1);
        stopTime();
        quickComplete = true;
        redraw();
    }

    /* QUICK SORT ALGORITHM */
    public void quickSort(int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        int pivot = numbers[lowerIndex + (higherIndex - lowerIndex) / 2];
        // Divide into two arrays
        while (i <= j) {
            while (numbers[i] < pivot) {
                firstPivot = i;
                redraw();
                i++;
            }
            while (numbers[j] > pivot) {
                secondPivot = j;
                redraw();
                j--;
            }
            if (i <= j) {
                swapNumbers(i, j);
                firstPivot = i;
                secondPivot = j;
                redraw();
                i++;
                j--;
            }
        }
        if (lowerIndex < j) {
            quickSort(lowerIndex, j);
        }
        if (i < higherIndex) {
            quickSort(i, higherIndex);
        }
        this.firstPivot = numbers.length;
        this.secondPivot = numbers.length;
        redraw();
    }

    /* INSERTION SORT ALGORITHM */ 
    public void insertionSort() {
        startTime();
        for (int i = 1; i < this.numbers.length; i++) {
            int temp = numbers[i];
            int j;
            this.secondPivot = i;
            for (j = i - 1; j >= 0 && temp < numbers[j]; j--) {
                this.firstPivot = j;
                redraw();
                numbers[j + 1] = numbers[j];
            }

            numbers[j + 1] = temp;
        }

        this.firstPivot = numbers.length;
        this.secondPivot = numbers.length;
        insertionComplete = true;
        System.out.println("Insertion completed!");
        stopTime();
        redraw();
    }

    public void swapNumbers(int a, int b) {
        int tmp = this.numbers[a];
        this.numbers[a] = this.numbers[b];
        this.numbers[b] = tmp;
    }

    @Override
    public void run() {
        Sort sort = new Sort();
        sort.setWaitTime(waitTime);
        generateNumbers();
        
        JFrame f = new JFrame();
        
        f.setLocation(300, 300);                                                //Window start location.
        f.setDefaultCloseOperation(2);                                          //DISPOSE_ON_EXIT.
        f.add(sort);
        f.setSize(1015, 180);                                                   //Window Size.
        f.setResizable(false);                                                  //Resizable value.
        f.setVisible(true);                                                     //Visibility.
        
               
        switch (sortType) {
            case "Bubble":
                f.setTitle("Bubble Sort");
                sort.bubbleSort();
                break;
            case "Selection":
                f.setTitle("Selection Sort");
                sort.selectionSort();
                break;
            case "Quick":
                f.setTitle("Quick Sort");
                sort.quickS();
                break;
            case "Insertion":
                f.setTitle("Insertion Sort");
                sort.insertionSort();
                break;
        }
    }
    
    public void startTime() {
        this.time = System.currentTimeMillis();
    }

    public void stopTime() {
        timer = System.currentTimeMillis() - this.time;
        System.out.println(timer + " millis");
    }
}
