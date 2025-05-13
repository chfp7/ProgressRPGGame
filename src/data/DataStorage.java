package data;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable{//enables us to write and read this object, so this class itsself can be writable and readable

	//PLAYER STATS
	public int maxLife;
	public int maxMana;
	public int life;
	public int mana;
	public int ammo;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int xp;
	public int nextLevelXP;
	public int coin;
	
	//PLAYER INVENTORY (we will save item names and item amounts) 
	ArrayList<String> itemNames = new ArrayList<>();
	ArrayList<Integer> itemAmount = new ArrayList<>();
	int currentWeaponSlot;
	int currentShieldSlot;
	
	//OBJECT ON MAP
	String mapObjectNames[][];
	int mapObjectWorldX[][];
	int mapObjectWorldY[][];
	String mapObjectLootNames[][];//for chest
	boolean objectOpened[][];//also for chest


	
	
}
