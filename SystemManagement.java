package Restaurant;



import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class SystemManagement 
{
    boolean exist = false;
	Scanner sc;
	private Item itm;
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private ArrayList<promo> pList = new ArrayList<promo>();
	Staff s=new Staff();
	Menu m=new Menu();
	static int itemID=0;
	
	public SystemManagement()
	{
	
	}
	
	public void getItems()throws IOException // reading from the file and updating it into the array list
	{
		try{
			String path = "C:" + File.separator + "Restaurant" + File.separator + "item.txt";
			// Use relative path for Unix systems
			File f = new File(path);
			f.getParentFile().mkdirs(); 
			if (!f.exists()) 
				f.createNewFile();
			Scanner sc = new Scanner(f);
			sc.nextLine();
			while(sc.hasNext())
			{
				String current[] = sc.nextLine().split(",");
				Item item = new Item(current[0],current[1],current[2].charAt(0),current[3],current[4],current[5]);
				itemList.add(item);
			}
			sc.close();
		}
		catch(FileNotFoundException err1)
		{
			err1.printStackTrace();
		}
	}
	public void getPromotion()throws IOException
	{
		try
		{
			String path = "C:" + File.separator + "Restaurant" + File.separator + "promotion.txt";
			// Use relative path for Unix systems
			File f = new File(path);
			f.getParentFile().mkdirs(); 
			if (!f.exists()) 
				f.createNewFile();
			sc = new Scanner(f);
			sc.nextLine(); //since the first line is the format for the text file
			while(sc.hasNext())
			{
				String current[] = sc.nextLine().split(",");
				promo p = new promo();
				ArrayList<Item> tempPList = new ArrayList<Item>();
				p.setPromoID(current[0]);
				p.setName(current[1]);
				for(int i=2;i<current.length;i++)
				{
					for (int j = 0; j < itemList.size(); j++) {
						
					Item check = itemList.get(j);
					if(current[i].equals(check.getItemID()))
				       tempPList.add(check);
					}
				p.setItemList(tempPList);
				pList.add(p);
				}
			}
			sc.close();
		    
		} catch (FileNotFoundException ex1)
		{
			  ex1.printStackTrace();
		}
   }
	
	public boolean createItem()
	{
		boolean result = false;
		char check;
		sc = new Scanner(System.in);
		itm = new Item();
		System.out.println("Please enter the name of the item:");
	    itm.setName(sc.next());
	    itemID++;
		do{
			System.out.println("Please enter the type of item:");
			System.out.println("a. Appetisers");
			System.out.println("m. Main course");
			System.out.println("d. Desserts");
			System.out.println("b. Beverages");
			check = sc.next().charAt(0);
		  }while(check=='a'||check=='m'||check=='d'||check=='b');
		itm.setType(check);
		System.out.println("Please enter price of item");
		itm.setPrice(sc.next());
		System.out.println("Please enter description of the item");
		itm.setDescription(sc.next());
		System.out.println("Please enter the discounted price of the item");
		itm.setDiscountedPrice(sc.next());
		itm.setItemID(Integer.toString(itemID));
	    for(Item i:itemList)
	    {
	    	if(i.getName().equals(itm.getName()))
	    		exist = true;
	    }
	    
	    if(!exist)
	    {
	   
	    	try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("item.txt",true))))
	    	{
	    		out.print("\n"+itm.getItemID()+","+itm.getType()+","+itm.getName()+","+itm.getPrice()+","+itm.getDescription()+","+itm.getDiscountedPrice());	    				
	            result = true;    	
	    	}
	    	catch(IOException ex)
	    	{
	    		ex.printStackTrace();
	    	}
	    }
		return result;
	}
	
	public void updateItem()
	{
	
		sc = new Scanner(System.in);
		int choice;
		String itemID;
		System.out.print("ItemID"+ "       " + "Item Name" + "       " + "Item Price" + "       " + "Item Description" + "                  " + "Item Type" + "       " + "Discounted Price");
		for (int i = 0; i < itemList.size(); i++) {

			Item itm=itemList.get(i);
        	System.out.print("\n"+itm.getItemID() + "            "+ itm.getName() + "            "+ itm.getPrice() + "            "+ itm.getDescription()+ "            "+ itm.getType() + "            "+ itm.getDiscountedPrice());

		}
		System.out.println();
		System.out.println("Please enter itemID of item to be updated");
		itemID = sc.next();
		itm = m.selectItem(itemID);
		System.out.println("1. Change Name");
		System.out.println("2. Change Price");
		System.out.println("3. Change Description");
		System.out.println("4. Change Type");
		System.out.println("5. Change Discounted Price");
		choice = sc.nextInt();
		switch(choice){
			case 1: System.out.print("Enter new name of item:");
				    itm.setName(sc.nextLine());
				    writeArrayToItemFile();
				    break;
			case 2: System.out.print("Enter new price of item:");
				    itm.setPrice(sc.nextLine());
				    writeArrayToItemFile();
				    break;
			case 3: System.out.print("Enter new description of item:");
				    itm.setDescription(sc.nextLine());
				    writeArrayToItemFile();
				    break;
			case 4: char check;
				    do
				    {
				      System.out.println("Enter new type of item");
					  System.out.println("Please enter the type of item:");
				      System.out.println("a. Appetisers");
					  System.out.println("m. Main course");
					  System.out.println("d. Desserts");
					  System.out.println("b. Beverages");
				      check = sc.next().charAt(0);
					}while(check!='a'&&check!='m'&&check!='d'&&check!='b');
				    itm.setType(check);
				    writeArrayToItemFile();
				    break;
			case 5: System.out.println("Enter new discounted price of item");
				    itm.setDiscountedPrice(sc.nextLine());
				    writeArrayToItemFile();
				    break;
			}
		}
		
	public void deleteItem(){
		sc = new Scanner(System.in);
		String itemID;
		itm = new Item();
		System.out.print("ItemID"+ "       " + "Item Name" + "       " + "Item Price" + "       " + "Item Description" + "                  " + 
				"Item Type" + "       " + "Discounted Price");
		
		for (int i = 0; i < itemList.size(); i++) {

			Item item=itemList.get(i);
			System.out.print("\n"+item.getItemID() + "            "+ item.getName() + "            "+ item.getPrice() + "            "+ item.getDescription()
					+ "            "+ item.getType() + "            "+ item.getDiscountedPrice());
		}
		System.out.println("Please enter itemID of item to be deleted");
		itemID = sc.nextLine();
		System.out.println("itemList size: "+ itemList.size());
		System.out.println("itemID: "+ itemID);
		itm = m.selectItem(itemID);
		itemList.remove(itm);
		System.out.println("itemList size: "+ itemList.size());
		writeArrayToItemFile();
	}
	private void writeArrayToItemFile()
	{	
		try 
		{
			String path = "C:" + File.separator + "Restaurant" + File.separator + "item.txt";
			// Use relative path for Unix systems
			File f = new File(path);

			f.getParentFile().mkdirs(); 
			if (!f.exists()) 
				f.createNewFile();
			Scanner sc = new Scanner(f);
			sc.nextLine();
			BufferedWriter out = new BufferedWriter(new FileWriter(f));
			out.write("itemID, type, name, price, desc, discounted price");
			out.newLine();
			for (int i = 0; i < itemList.size(); i++) {

				Item item=itemList.get(i);
				out.write("\n"+item.getItemID()+","+item.getType()+","+item.getName()+","+item.getPrice()+","+item.getDescription()+","+item.getDiscountedPrice());
			}
			System.out.println("Update Successful!");
			out.close();
			sc.close();
		}
		catch (IOException ex) 
		{
			ex.printStackTrace();
		}
	 }
	
	public boolean createPromo()
	{
	    boolean result = false;
		sc = new Scanner(System.in);
		String itemID, promoName;
		ArrayList<String> tempList = new ArrayList<String>();

		System.out.print("ItemID"+ "       " + "Item Name" + "       " + "Item Price" + "       " + "Item Description" + "                  " + "Item Type" + "       " + "Discounted Price");
		for (int i = 0; i < itemList.size(); i++) {

			Item item=itemList.get(i);
			System.out.print("\n"+item.getItemID() + "            "+ item.getName() + "            "+ item.getPrice() + "            "+ item.getDescription()			+ "            "+ item.getType() + "            "+ item.getDiscountedPrice());
		}
		
		do{
			System.out.println("Please enter itemID of item to add to package, press 0 to exit");
			itemID = sc.nextLine();
			if(!itemID.equals("0"))
				tempList.add(itemID);
		  }while(!itemID.equals("0"));
		
		System.out.println("Please enter name of package:");
		promoName = sc.nextLine();
			
		int promoID=0;
		for(promo prm: pList)
		{
			if(promoID < Integer.parseInt(prm.getPromoID().substring(1, prm.getPromoID().length())))
				promoID = Integer.parseInt(prm.getPromoID().substring(1, prm.getPromoID().length()));
		}
		promoID++;
		
		try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("promotion.txt", true)))) 
		{
            out.print("\n"+ "P"+ Integer.toString(promoID)+","+promoName);
			for(int j=0; j<tempList.size(); j++)
			{
			    out.print(","+tempList.get(j));
			}result = true;
	    } 
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		return result;
	}
	
	public void updatePromo()
	{
		String itemID;
		int promoID;
		promo p;
		System.out.println("--------Promotions--------");
		for (int i = 0; i < pList.size(); i++) {
		    promo prm = pList.get(i);
		    System.out.println(prm.getPromoID()+" ");
		    for (int j = 0; j < prm.getItemList().size(); j++) {

				Item itm=itemList.get(i);
		    	System.out.println(itm.getName()+"  ");    	
		    }
		    System.out.println("Meal cost: $"+prm.calculatePrice());
		}
		System.out.println();
		ArrayList<String> tempList = new ArrayList<String>();
		
		System.out.println("Enter the promo ID of the promotion you want to update:");
		promoID = sc.nextInt();
		p = m.selectPromo(Integer.toString(promoID));
		int select;
		
		do
		{
			
			System.out.println("1.Add Item");
			System.out.println("2.Remove Item");
			select = sc.nextInt();
			if(select == 1){
				System.out.println("Enter the ID of the item to be inserted: ");
				itemID = sc.nextLine();
					if(!itemID.equals("0"))
						tempList.add(itemID);
			}
			if(select==2){
				System.out.println("Enter the ID of the item to be deleted: ");
			    itemID = sc.nextLine();
				for(String j:tempList){
					if(j==itemID)
						tempList.remove(j);
				}
			}
		}while(select ==1 || select==2);
	
	 try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("promotion.txt", true)))) 
	 {
        out.print("\n"+ "P"+ Integer.toString(promoID)+","+p.getName());
		for(int i=0; i<tempList.size(); i++)
		{
		    out.print(","+tempList.get(i));
		}
    } 
	catch (IOException ex)
	{
		ex.printStackTrace();
	}
  }

	public boolean deletePromo()
	{
		boolean result = false;
		
		return result;
	}
	
	public void printRevenue()
	{
		
	}
	
	public ArrayList<Staff> getStaffID() throws FileNotFoundException,IOException{
		ArrayList<Staff> staffIDList = new ArrayList<Staff>();
		String path = "C:" + File.separator + "Restaurant" + File.separator + "staff.txt";
		// Use relative path for Unix systems
		File f = new File(path);

		f.getParentFile().mkdirs(); 
		if (!f.exists()) 
			f.createNewFile();
		sc = new Scanner(f);
		sc.nextLine();
		while (sc.hasNext()) {
			
			String temp[] = sc.nextLine().split(",");
			Staff staff = new Staff();
			staff.setStaffID(temp[0]);
			staff.setName(temp[1]);
			staff.setGender(temp[2]);
			staff.setjobTitle(temp[3]);;
			staffIDList.add(staff);
			sc.close();
			
		}
		return staffIDList;

}
}
