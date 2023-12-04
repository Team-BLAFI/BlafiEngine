package component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Room2 extends Room{
    public Room2(){
        super();
    }
    @Override
    public void loadMap() {
        System.out.println("Loading Room2");
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/assets/roguelite1.txt"));
            for (int row = 0; row < RoomManager.roomHeight; row++){
                String line = br.readLine();

                for (int col = 0; col < RoomManager.roomWidth; col++){
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    RoomManager.mapTileNum[col][row] = num;
                }
            }
            br.close();
        }catch(IOException | NumberFormatException e){e.printStackTrace();}
    }
}

