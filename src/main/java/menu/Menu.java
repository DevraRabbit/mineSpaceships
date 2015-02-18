package menu;

import java.util.ArrayList;


public class Menu 
{
	private final ArrayList<Menu> childrenList;
	private final String menuName;
	private static Menu selectedMenu;
	private static int menuIDcounter = 0;
	private final int menuID;
	
	public Menu(final String s)
	{
		menuName = s;
		childrenList=new ArrayList<Menu>();
		this.menuID = menuIDcounter;
		menuIDcounter++;

	}

	/**
	 * returns a String which contains the output of the Menu
	 */
	public String display()
	{
		String out = "";
		for(int i = 0; i < getChildrenList().size(); i++)
		{
			out+= menuID + ". " + getChildrenList().get(i) + "\n";
		}
		return out;
	}
	
	/**
	 * returns true if successful, false if the ID was not correct
	 * changes the static variable selectedMenu to the new Menu
	 */
	public boolean switchMenu(String name) 
	{
		int n;
		try
		{
			n = Integer.parseInt(name);
			if(n < 0 || n > menuIDcounter)
			{
				throw new Exception("ungültige ID");
			}
			
			for(Menu m : childrenList)
			{
				if(m.getMenuID() == n)
				{
					selectedMenu = m;
				}
			}
			return true;
		}
		
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void addSubMenu(Menu m)
	{
		childrenList.add(m);
	}
	
	public int getMenuID()
	{
		return this.menuID;
	}
	
	public String toString()
	{
		return this.menuName;
	}
	
	private ArrayList getChildrenList() 
	{
		return this.childrenList;
	}
	
	public static Menu getSelectedMenu()
	{
		return selectedMenu;
	}
	
	public String getMenuName()
	{
		return menuName;		
	}

}

