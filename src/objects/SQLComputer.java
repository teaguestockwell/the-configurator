package objects;

import javafx.scene.image.Image;

import java.util.LinkedHashMap;

public class SQLComputer {
    private LinkedHashMap<String, String> currentConfig; //config name, config value
    private final String COST;
    private final int ITEM_ID;
    private final String name;


    public SQLComputer(String ITEM_NAME) {
        this.COST = SQLRW.getBasePriceGivenNAME(ITEM_NAME);
        this.ITEM_ID = SQLRW.getITEM_IDGivenName(ITEM_NAME);
        this.currentConfig = SQLRW.getDEFAULT_CONFIGSGivenITEM_ID(ITEM_ID);
        System.out.println("New computer made with base configs: ");
        System.out.println(this.currentConfig.toString());
        name = ITEM_NAME;
    }

    public String getName() {
        return name;
    }

    public Image getCurrentImage() {
        Image ret;
        for (String config : currentConfig.keySet()) {
            String value = currentConfig.get(config);
            Image i = SQLRW.getImageGiven(ITEM_ID, config, value);
            if (i != null) {
                ret = i;
                return ret;
            }
        }
        return SQLRW.getItemDefaultImageAtIndex(ITEM_ID);
    }

    public int getITEM_ID() {
        return ITEM_ID;
    }

    public void setCurrentConfig(String configValue) {
        String configName = SQLRW.getConfigNameFromValue(configValue, ITEM_ID);
        System.out.println("given " + configValue + " it came from" + configName);
        currentConfig.replace(configName, configValue);
        System.out.println(currentConfig.toString());
    }

    public String getCSVConfig() {
        String ret = "No config from getCSVconfig";
        StringBuilder sb = new StringBuilder();

        for (String name : currentConfig.keySet()) {
            sb.append(currentConfig.get(name));
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);


        ret = sb.toString();
        return ret;
    }

    public String getPrice() {
        String ret = "No price from SQL Reader";
        ret = "" + (Double.parseDouble(COST) + Double.parseDouble(SQLRW.getPrice(currentConfig, ITEM_ID)));
        return ret;
    }


}
