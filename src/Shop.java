import java.util.ArrayList;
import java.util.Random;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Shop {
	private final String[] SHOP_TYPE = {"General", "Weaponsmith", "Magic", "Armorer",
			"Apothecary"};
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	 // Object Properties
    private String shopName;
    private int numItem;
    private boolean saved;
    
    //default constructor
    public Shop(String type) {
    	// try {
          //   String[] temp = generateShopOutput();
            // this.shopName = temp[0];
           //  this.numItem = temp[1];
             this.saved = false;
       //  } catch (IOException e) {
      //   }
    }
 // Load constructor
/*    // Used by MainView.load to populate data we know is saved
    public NPC(String name, String info) {
        this.name = name;
        this.info = info;
        this.saved = true;
    }

    // Used by MainView.loadButton listener to load selected NPC into the spotlight
    public NPC load(NPC newNPC) {
        this.name = newNPC.name;
        this.info = newNPC.info;
        this.saved = true;
        this.pcs.firePropertyChange("NPC loaded", null, null);
        return newNPC;
    }
*/
    public String getShopName() {
        return this.shopName;
    }

    public void setName(String name) {
        this.shopName = name;
        this.pcs.firePropertyChange("Name Changed", null, null);
    }

    //need getters and setters for all fields
    
    
    
    
    public void save() {
        this.saved = true;
        this.pcs.firePropertyChange("NPC Saved", null, null);
    }

    public void delete() {
        this.saved = false;
        this.pcs.firePropertyChange("NPC Deleted", null, null);
    }

    public boolean isSaved() {
        return this.saved;
    }

    private String roll(String[] array) {
        Random rand = new Random();
        return array[rand.nextInt(array.length)];
    }

    private String[] roll(String[][] array) {
        Random rand = new Random();
        return array[rand.nextInt(array.length)];
    }

    private boolean contains(final char[] array, char key) {
        for (int i = 0; i < array.length; i++) {
            if (key == array[i]) {
                return true;
            }
        }
        return false;
    }
    
	String[] generateShopName(String type) throws IOException{
		String[] shopNameType = new String[2];
		Random rand = new Random();
		Charset charset = Charset.forName("US-ASCII");
		ArrayList<String> possibleShopNames = new ArrayList<String>();
		BufferedReader reader = null;
		if(type.equals("Random")) {
			int shopNum = rand.nextInt(5);
			type = SHOP_TYPE[shopNum];
		}
		try {
			reader = Files.newBufferedReader(Paths.get(type + ".txt"), charset);
			String currentShopName;
            while ((currentShopName = reader.readLine()) != null) {
                possibleShopNames.add(currentShopName);
            }
            shopName = possibleShopNames.get(rand.nextInt(possibleShopNames.size()));
            reader.close();
		 } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
		shopNameType[0] = shopName.replace(",", "");
		shopNameType[1] = type;
        return shopNameType;
    }
		ArrayList<Integer>  itemSelect(int numItem) {
			Random rand = new Random();
			ArrayList<Integer> itemNums = new ArrayList<Integer>();
			int list = 0;
			while(list < numItem) {
				int itemSel = rand.nextInt(4);
				if(!itemNums.contains(itemSel)) {
					itemNums.add(itemSel);
					list++;
				}
			}
			return itemNums;
		}
	String[] generateShopInventory(String type, int numItem) {
		int itemCount = 0;
		String[] items = new String[numItem];
		Random rand = new Random();
		Charset charset = Charset.forName("US-ASCII");
		ArrayList<String> possibleShopInv = new ArrayList<String>();
		BufferedReader reader = null;
		
		try {
			reader = Files.newBufferedReader(Paths.get(type + "_Inv.txt"), charset);
			String currentShopInv;
			ArrayList<Integer> itemNums = itemSelect(numItem);
			   while ((currentShopInv = reader.readLine()) != null) {
	                possibleShopInv.add(currentShopInv);
	            }
			   while(itemCount < numItem) {
	            items[itemCount] = possibleShopInv.get(itemNums.get(itemCount));
	            itemCount++;
			   }
			   reader.close();
			 } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (reader != null) {
	                    reader.close();
	                }
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
			return items;		
	}
}
