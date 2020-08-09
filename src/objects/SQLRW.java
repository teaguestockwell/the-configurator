package objects;

import javafx.scene.image.Image;

import java.io.*;
import java.sql.*;
import java.util.*;

public class SQLRW {
    final static String DB_URL = "jdbc:h2:/" + System.getProperty("user.dir") + "/resources;";
    final static String DB_PASSWORD = "";
    final static String DB_USER = "sa";
    final static String DB_DRIVER = "org.h2.Driver";
    final static String sql = "UPDATE ITEM SET DEFAULT_IMAGE=? WHERE ITEM_ID=?;";
    final static String fileLocation = "/Users/teaguestockwell/Desktop/untitled folder/Blue.png";
    final static int idfor = 8;

    public static void main(String[] args) {
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement(sql);

            File file = new File(fileLocation);
            FileInputStream fis = new FileInputStream(file);

            ps.setBinaryStream(1, fis);
            ps.setInt(2, idfor);

            ps.executeUpdate();
            con.commit();

            ps.close();
            fis.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int getTotalItems() {
        int ret = 0;
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT ITEM_ID FROM ITEM");

            while (rs.next()) {
                ret += 1;
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getItemNameAtIndex(int index) {
        String ret = "noNameFound";
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT NAME FROM ITEM WHERE ITEM_ID=" + index + ";");

            while (rs.next()) {
                ret = rs.getString("NAME");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getItemCostAtIndex(int index) {
        String ret = "noCostFound";
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT COST FROM ITEM WHERE ITEM_ID=" + index + ";");

            while (rs.next()) {
                ret = rs.getString("COST");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Image getItemDefaultImageAtIndex(int index) {

        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT DEFAULT_IMAGE FROM ITEM WHERE ITEM_ID=" + index + ";");

            while (rs.next()) {
                InputStream is = rs.getBinaryStream("DEFAULT_IMAGE");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));

                byte[] content = new byte[1024];
                int size;

                while ((size = is.read(content)) != -1) {
                    os.write(content, 0, size);
                }
                os.close();
                is.close();

                con.close();
                return new Image("file:photo.jpg");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getItemDescriptionAtIndex(int index) {
        String ret = "No description found";
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT DESCRIPTION FROM ITEM WHERE ITEM_ID=" + index + ";");

            while (rs.next()) {
                ret = rs.getString("DESCRIPTION");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }

    public static int getITEM_IDGivenName(String itemName) {
        int ret = 0;
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT ITEM_ID FROM ITEM WHERE NAME='" + itemName + "'");

            while (rs.next()) {
                ret = rs.getInt("ITEM_ID");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Given " + itemName + " item id is " + ret);
        return ret;
    }

    public static List<String> getConfigNamesGivenITEM_ID(int id) {
        List<String> ret = new ArrayList<>();
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT NAME FROM CONFIGS WHERE ITEM_ID=" + id + ";");

            while (rs.next()) {
                ret.add(rs.getString("NAME"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static List<String> getCONFIGS_IDGivenITEM_ID(int id) {
        List<String> ret = new ArrayList<>();
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT CONFIGS_ID FROM CONFIGS WHERE ITEM_ID=" + id + ";");

            while (rs.next()) {
                ret.add(rs.getString("CONFIGS_ID"));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getBasePriceGivenNAME(String name) {
        String ret = "0";
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM ITEM WHERE NAME ='" + name + "'");

            while (rs.next()) {
                ret = rs.getString("COST");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static LinkedHashMap<String, String> getDEFAULT_CONFIGSGivenITEM_ID(int id) {
        LinkedHashMap<String, String> ret = new LinkedHashMap<>(); // configName, configvaluedefault;

        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT * FROM CONFIG WHERE ITEM_ID='" + id + "' AND DEFAULT_CONFIG=TRUE;");

            while (rs.next()) {
                String configValues = (rs.getString("CONFIG_VALUE"));
                String configName = rs.getString("CONFIG_NAME");
                ret.put(configName, configValues);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Image getImageGiven(int ITEM_ID, String CONFIG_NAME, String CONFIG_VALUE) {
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT CONFIG_IMAGE FROM CONFIG WHERE ITEM_ID='" + ITEM_ID + "' AND CONFIG_NAME='" + CONFIG_NAME + "' AND CONFIG_VALUE='" + CONFIG_VALUE + "';");

            while (rs.next()) {
                InputStream is = rs.getBinaryStream("CONFIG_IMAGE");
                OutputStream os = new FileOutputStream(new File("photo.jpg"));

                byte[] content = new byte[1024];
                int size;
                try {
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }
                    os.close();
                    is.close();

                    con.close();
                    return new Image("file:photo.jpg");
                } catch (Exception e) {
                    return null;
                }
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LinkedHashMap<String, String> getAllConfigValuePrice(SQLComputer comp) {
        LinkedHashMap<String, String> ret = new LinkedHashMap<>();
        int id = comp.getITEM_ID();
        List<String> listConfigs = new ArrayList<>();

        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("Select DISTINCT CONFIGS_ID  from config where ITEM_ID='" + id + "'");

            while (rs.next()) {
                listConfigs.add(rs.getString("CONFIGS_ID"));
            }

            for (String config : listConfigs) {
                rs = stmt.executeQuery("Select * from config where ITEM_ID='" + id + "' AND CONFIGS_ID='" + config + "'");
                while (rs.next()) {
                    ret.put(rs.getString("CONFIG_VALUE"), rs.getString("PRICE"));
                }
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getConfigNameFromValue(String value, int compid) {
        String ret = "no config Name Found in get config from value";
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT CONFIG_NAME FROM CONFIG WHERE ITEM_ID='" + compid + "' AND CONFIG_VALUE='"
                    + value + "'");

            while (rs.next()) {
                ret = rs.getString("CONFIG_NAME");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static String getPrice(LinkedHashMap<String, String> currentConfigs, int id) {//config name, config value
        String ret = "0";
        double price = 0.00;
        try {
            Class.forName(DB_DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = con.createStatement();
            ResultSet rs;
            for (String name : currentConfigs.keySet()) {
                String value = currentConfigs.get(name);
                rs = stmt.executeQuery("select * from config where CONFIG_NAME='" + name + "' AND CONFIG_VALUE ='" + value + "'AND ITEM_ID='" + id + "'");

                while (rs.next()) {
                    //System.out.println(name + " " + value);
                    price += rs.getDouble("PRICE");
                    //System.out.println(price);
                }
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ret = Double.toString(price);
        return ret;
    }


}


