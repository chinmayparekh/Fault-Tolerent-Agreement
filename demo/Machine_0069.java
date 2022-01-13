package demo;

import java.util.*;
import common.*;

public class Machine_0069 extends Machine 
{
    private int id;
    private int step;
    private int direction = 0;
    private Location pos = new Location(0, 0);
	private Location dir = new Location(0, 1);
    private ArrayList<Machine> machines = new ArrayList<Machine>();
    private boolean isCorrect;
    private int round1_decision;
    private int finalDecision;
    private int leftCount1 = 0;
    private int rightCount1 = 0;
    private int phaseNum=0;
	private String name;
	private int t;
    private int leftCount2 = 0;
    private int rightCount2 = 0;
    private boolean flag1=false;
    private boolean flag2=false;

    public Machine_0069() 
	{
        name = "0069";
    }

	@Override
	public void setMachines(ArrayList<Machine> machines) 
	{
        this.machines = machines;
		if(machines.size() % 3 == 0)//finding the number of faulty machines
            t = (machines.size()/3) - 1;
        else 
            t = machines.size()/3;
        for(int i=0;i<machines.size();i++)//to assign id to each machine based on its position in the Arraylist
		{
            if(this == machines.get(i))
                id = i;
        }
    }

    @Override
	public void setStepSize(int stepSize) 
	{
		step = stepSize;
	}

	@Override
    public void setState(boolean isCorrect) 
	{
        phaseNum++;
        this.isCorrect = isCorrect;
        leftCount1 = rightCount1 = leftCount2 = rightCount2 = 0;//resetting all the counter variables and flags 
        flag1 = flag2 = false;
    }

    public void setLeader() 
	{
        Random rand=new Random();
        int decision=rand.nextInt(2);//randomly generating a decision
        if(isCorrect)
        {
            for(int i=0;i<machines.size();i++)
			{
				machines.get(i).sendMessage(this.id,this.phaseNum,0,decision);
			}
        }
        else
        {
			int avoid = rand.nextInt(t);
			for (int i = avoid; i < machines.size(); i++) //if the leader is faulty,it will avoid the first "avoid" machines
			{
				machines.get(i).sendMessage(this.id, this.phaseNum, 0, decision);
			}
        }

    }

    public void sendMessage(int sourceId, int phaseNum, int roundNum, int decision) 
	{
		Random rand = new Random();
            if (roundNum == 0) 
			{
                if(!isCorrect)
                    decision=rand.nextInt(3);//if it is 2, the faulty machine wont send any message to the other machines 
                if(decision!=2)
                {
                    for(int i=0;i<machines.size();i++)
					{
                        machines.get(i).sendMessage(this.id,phaseNum,1,decision);
					}
                }
            }
            if (roundNum == 1   && flag1==false) 
			{
                if (decision == 0)
                    leftCount1++;
                else
                    rightCount1++;

                if(leftCount1 + rightCount1 >= 2*t+1)
                {
                    if (isCorrect) 
					{
                        if (leftCount1 > rightCount1)
                            round1_decision = 0;
                        else
                            round1_decision = 1;
                    }
                    else
                        round1_decision=rand.nextInt(3);//faulty machine will generate a random decision and if it is 2 it wont send any message.
                

                    if(round1_decision!=2)
                    {
                        for(int i=0;i<machines.size();i++)
						{
                            machines.get(i).sendMessage(this.id,phaseNum,2,round1_decision);
						}
                        flag1=true;//to ensure messages are sent only once by the machines
                    }
                    
                }
            }
            if (roundNum == 2) 
			{
                if (decision == 0)
                    leftCount2++;
                else
                    rightCount2++;

                if (leftCount2>=2*t+1 || rightCount2>=2*t+1) 
                {
                    if (leftCount2 > rightCount2)
                        finalDecision = 0;
                    else
                        finalDecision = 1;

                    if(!isCorrect)
                        finalDecision=rand.nextInt(2);
                    flag2=true;
                }

            }
        }
        
	@Override
    public void move() 
	{
        if(flag2==true)
		{
			if (finalDecision == 0)// take left
			{
				if (direction == 0) // facing north
				{
					dir.setLoc(-1, 0);
					direction = 3;// face the west direction
				}
                else if (direction == 1) // facing south
				{
					dir.setLoc(1, 0);
					direction = 2;// face the east direction
				} 
                else if (direction == 2) // facing east
				{
					dir.setLoc(0, 1);
					direction = 0; // facing the north direction
				} 
                else // facing west
				{
					dir.setLoc(0, -1);
					direction = 1; // facing the south direction
				}
	
			} 
            else if (finalDecision == 1) // take right
			{	
				if (direction == 0) // facing north
				{
					dir.setLoc(1, 0);
					direction = 2;// face the east direction
				} 
                else if (direction == 1) // facing south
				{
					dir.setLoc(-1, 0);
					direction = 3;// face the west direction
				} 
                else if (direction == 2) // facing east
				{
					dir.setLoc(0, -1);
					direction = 1; // facing the south direction
				} 
                else // facing west
				{
					dir.setLoc(0, 1);
					direction = 0; // facing the north direction
				}
			}
			flag2=false;
		}
		pos.setLoc(pos.getX() + dir.getX()*step, pos.getY() + dir.getY()*step);
    }

	@Override
	public
	String name() 
	{
        return "demo_" + name+" "+id;
	}

	@Override
	public Location getPosition() 
	{
		
		return new Location(pos.getX(), pos.getY());
	}

}