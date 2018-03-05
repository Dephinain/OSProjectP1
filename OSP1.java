import java.io.*;
import java.util.*;

//Note: All operations need to take place within 'while' loop reading from the file - handles everything one line at a time.
public class OSP1
{

	public static Queue disk = new LinkedList();
	public static int count = 0;
	public static mem_manager manager = new mem_manager();
	/*Job arrival routine. First routine system calls, handles incoming jobs. So need to have it be called at the beginning of each new line of the job list read - 
	so something like "line = bufferedReader.readLine() -> J_SCHED(line)". Think about moving the split string loop into here so it can treat each job uniquely and then categorize it accordingly.
	Said categorizing needs to be an 'ordered job mix' between IO-bound, CPU-bound, and balanced job types. Professor shunted the responsibility of finding out what exactly that is for your system on you, so get it working
	first, then optimize the mix.*/
	public static void J_SCHED(String inputLine)
	{
		String[] tokens;
		tokens = inputLine.split("\\s+"); //Splits string into array. NEED to convert to int when used. Extra white space given by process, so #s are 1-4.
		
		manager.aquire(Integer.parseInt(tokens[3]));
		//for(String t : tokens)
		//	System.out.println(t);
		//Data structure for PCB needs to be defined.
		//If a job is to be loaded, function needs to call mem_manager with the requested amount of memory, to check if it can be run.
		//If mem_manager returns that a chunk of memory is available, load job into ready queue for J_DISPATCH to handle.
		//If mem_manager returns that the requested memory is NOT available, shunt job into 'disk' - some defined data structure that holds up to 300 held jobs.
		//When considering a new job after memory comes in (IE: Whenever J_SCHED is called), priority is given to the jobs on the disk (run an 'isEmpty' on the disk to see if its empty).
		//If memory is available, send disk job down and put new incoming job at the back of the line (FCFS/FIFO).
		//If memory is NOT available, run standard protocol on newly arrived job
		//If all memory is full, run J_DISPATCH and proceed with standard function for all situations
		//If incoming job gives a 0 for...lets say the ID, then try to load EVERYTHING IT CAN from the disk in order to fill that memory hole in mem_manager, job mix be damned.
		//So this requires a 2nd 'load' procedure that can run under these circumstances
	}
	
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
	
	public static void main(String[] args)
	{
		String filename = "18Sp-jobs";
		String line, parsedLine;
		String[] tokens;
		mem_manager manager = new mem_manager();
		
		boolean[][] memoryArr = new boolean[7][];
		memoryArr[0] = new boolean[4];
		memoryArr[1] = new boolean[4];
		memoryArr[2] = new boolean[6];
		memoryArr[3] = new boolean[6];
		memoryArr[4] = new boolean[1];
		memoryArr[5] = new boolean[4];
		memoryArr[6] = new boolean[1];
		
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

					J_SCHED(line);
					
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