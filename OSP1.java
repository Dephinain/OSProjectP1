import java.io.*;
import java.util.*;

//Note: All operations need to take place within 'while' loop reading from the file - handles everything one line at a time.
public class OSP1
{
	/*Job termination function. Documents and outputs job termination statistics, and purges the appropriate queues upon termination.*/
	public void J_TERM()
	{
		//The following information needs to be ouput: 
		//Job ID
		//Class of Job
		//Time job was submitted to system
		//Time job was loaded
		//Time job was terminated
		//Processing time
		//Turnaround time
		//Waiting time
		
	}
	
	/*When J_SCHED and J_TERM aren't running, this is. Handles the actual 'running' of a job. In a First Come, First Serve (FCFS) fashion, the address for the Process Control Block (PCB) next in line will get passed to this function. Passes the processing time to the CPU, which increments the clock by said processing time. After it is run (IE: time is processed), J_TERM will be called to output job stats and will remove the finished process from memory in order to make room for another process.*/
	public void J_DISPATCH()
	{
		
	}
	
	public static void initialize(boolean[][] input)
	{
		for(int i = 0; i < input.length; i++)
		{
			for(int j = 0; j < input[i].length; j++)
			{
				input[i][j] = true;
			}
		}
	}
	
	public static void main(String[] args)
	{
		String filename = "18Sp-jobs";
		String line, parsedLine;
		String[] tokens;
		int count = 0;
		Queue disk = new LinkedList();
		
		boolean[][] memoryArr = new boolean[7][];
		memoryArr[0] = new boolean[4];
		memoryArr[1] = new boolean[4];
		memoryArr[2] = new boolean[6];
		memoryArr[3] = new boolean[6];
		memoryArr[4] = new boolean[1];
		memoryArr[5] = new boolean[4];
		memoryArr[6] = new boolean[1];
		
		initialize(memoryArr);
		//if(memoryArr[4][0] == true)
			//System.out.println("I got initialized!");
		mem_manager manager = new mem_manager(memoryArr);
		J_SCHED sched = new J_SCHED();
		
		
		//This chunk runs everything. J_SCHED runs first after the initial line is read in and sent to it.
		try 
		{
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			for(int i = 0; i < 1; i++)
			{
				if((line = bufferedReader.readLine()) != null)
				{
					//disk.add(line); - sdd to disk line
					//System.out.println(disk.element()); - prints out top of queue
					//disk.poll(); - removes item from top of stack. Needs to be used after job is shunted to either A.) Ready queue, or B.) Back of the disk.
					sched.run(line);
					
				}
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Unable to open file.");
		}
		catch(IOException ex)
		{
			System.out.println("Error reading file.");
		}
//System.out.println(disk.element());
		//disk.poll();
		
		for(Iterator<String> iterator = disk.iterator(); iterator.hasNext();) //This operation's purpose is to load a job from 'disk' when J_SCHED runs.
		{//If job on top of queue can't be entered because its memory slot(s) are taken, punt it to the bottom of the queue and try the next one. 
		 //If all 300 jobs on the disk are incompatible, tell J_SCHED to run J_DISPATCH.
		 //If both ready queue and disk are full with an incoming line, immediately run J_DISPATCH to clear up some room.
			String value = iterator.next();
			System.out.println("This is from the Object -> string loop.");
			System.out.println(value);
			count++;
			iterator.remove();
		}
		
		//System.out.println("The number of items entered is: " + count);
	}
}