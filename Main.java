import java.util.Random;

public class Main {

    private static final int RESIZE = 20;
    private static Random random = new Random();
    private static int[][] myArray;
    private static boolean isInProgress = true;
    public static void main(String[] args) {
        int hight = 900;
        int width = 1500;
        MyFrame frame = new MyFrame(hight, width, RESIZE);
        myArray = makeArray(hight/RESIZE, width/RESIZE, frame);
        updateArray(frame, 50);
    }

    public static int[][] makeArray(int y, int x, MyFrame frame) {
        int[][] newArray = new int[y][x];
        newArray = fillArrayWithRandom(newArray);
        //printArray(newArray);
        frame.makeButtonArray(newArray);
        return newArray;
    }

    public static int[][] fillArrayWithRandom(int[][] array) {
        for (int i = 0, y = array.length; i < y; i++) {
            for (int j = 0, x = array[0].length; j < x; j++) {
                array[i][j] = random.nextInt(2);
            }
        }
        return array;
    }

    public static int[][] updateArrayState(int[][] oldArray, MyFrame frame) {
        int hight = oldArray.length;
        int width = oldArray[0].length;
        int[][] newArray = new int[hight][width];                
        for (int i = 0; i < hight; i++) {
            for (int j = 0; j < width; j++) {
                int aliveNeighbours = countNeighbour(i, j, oldArray);
                if (oldArray[i][j] == 1 && aliveNeighbours > 1 && aliveNeighbours < 4) {
                    newArray[i][j] = 1;
                    continue;
                } else if (oldArray[i][j] == 0 && aliveNeighbours == 3) {
                    newArray[i][j] = 1;
                    continue;
                }
                newArray[i][j] = 0;
            }
        }
        frame.updateButtonArray(newArray);
        //printArray(newArray);
        return newArray;
    }

    public static int countNeighbour(int y, int x, int[][] array) {
        int hight = array.length;
        int width = array[0].length;
        int counter = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                counter += array[(y+i+hight)%hight][(x+j+width)%width];
            }
        }
        return counter;
    }

    public static void printArray(int[][] array) {
        int hight = array.length;
        int width = array[0].length;
        for (int i = 0; i < hight; i++) {
            System.out.print("[");
            for (int j = 0; j < width; j++) {
                System.out.print(array[i][j] + ", ");
            }
            System.out.println("]");
        }
    }

    public static void updateArray(MyFrame frame, int time) {
        while (true) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {}
            if (isInProgress) {
                myArray = updateArrayState(myArray, frame);
            }
        }
    }

    public static int[][] getMyArray() {
        return myArray;
    }

    public static boolean startOrStopGame() {
        return isInProgress = !isInProgress;
    }

    public static void restartArray() {
        myArray = fillArrayWithRandom(myArray);
    }
}