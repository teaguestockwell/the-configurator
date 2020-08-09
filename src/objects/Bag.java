package objects;

import java.util.ArrayList;
import java.util.List;

public class Bag {
    public static String currentComputerName;
    public static List<SQLComputer> compList = new ArrayList<>();
    public static String getBagTotal(){
        double count=0;
        for(SQLComputer comp : compList){
            count += Double.parseDouble(comp.getPrice());
        }
        return Double.toString(count);
    }
}
