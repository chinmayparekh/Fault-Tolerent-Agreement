package demo;

import java.util.ArrayList;
import java.util.Random;

import common.Game;
import common.Machine;

public class Game_0069 extends Game 
{
	private int t;
	private ArrayList<Machine> machines = new ArrayList<Machine>();
	@Override
	public void addMachines(ArrayList<Machine> machineList, int numFaulty) 
	{
		machines = machineList;
		t = numFaulty;
		for(int i = 0; i < machines.size();i++)
		{
			machines.get(i).setMachines(machines);
		}
	}
	
	@Override
	public void startPhase() 
	{
		System.out.println("*******************NEW PHASE *******************");
		Random rand = new Random();
		int index = rand.nextInt(machines.size()-t);
		for(int i = index; i < index+t;i++)
		{
			System.out.println("Faulty machine id = "+i);
			machines.get(i).setState(false);//randomly assigning faulty machines
		}
		for(int i = 0;i<index;i++)//assigning the rest of the machines as correct
		{
			machines.get(i).setState(true);
		}
		for(int i = index+t;i<machines.size();i++)
		{
			machines.get(i).setState(true);
		}
		machines.get(rand.nextInt(machines.size())).setLeader(); 
	}
}
