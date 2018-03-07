import java.io.*;
import java.util.*;


    /*Job arrival routine. First routine system calls, handles incoming jobs. So need to have it be called at the beginning of each new line of the job list read - 
	so something like "line = bufferedReader.readLine() -> J_SCHED(line)". Think about moving the split string loop into here so it can treat each job uniquely and then categorize it accordingly.
	Said categorizing needs to be an 'ordered job mix' between IO-bound, CPU-bound, and balanced job types. Professor shunted the responsibility of finding out what exactly that is for your system on you, so get it working
	first, then optimize the mix.*/
public class J_SCHED
{

		private void initialize(boolean[][] input)
		{
			for(int i = 0; i < input.length; i++)
			{
				for(int j = 0; j < input[i].length; j++)
				{
					input[i][j] = true;
				}
			}
		}
		
		private static boolean[][] memoryArr = new boolean[7][];
		
		public J_SCHED()
		{			
			memoryArr[0] = new boolean[4];
			memoryArr[1] = new boolean[4];
			memoryArr[2] = new boolean[6];
			memoryArr[3] = new boolean[6];
			memoryArr[4] = new boolean[1];
			memoryArr[5] = new boolean[4];
			memoryArr[6] = new boolean[1];
			initialize(memoryArr);
		};
		
		private static mem_manager manager = new mem_manager(memoryArr);
		
		public static boolean memoryCheck(String inputLine)
		{
			String[] tokens;
			tokens = inputLine.split("\\s+");	
			//System.out.println(memoryArr[6][0]);
			if(manager.aquire(Integer.parseInt(tokens[3])))
			{
				return true;
			}
			else
				return false;
		};
		
		public static boolean jobLoad(boolean input)
		{
			return false;
		};
		//Data structure for PCB needs to be defined. => array for ready queue, queue for disk
		//If a job is to be loaded, function needs to call mem_manager with the requested amount of memory, to check if it can be run.
		//If mem_manager returns that a chunk of memory is available, load job into ready queue for J_DISPATCH to handle.
		//If mem_manager returns that the requested memory is NOT available, shunt job into 'disk' - some defined data structure that holds up to 300 held jobs.
		//When considering a new job after memory comes in (IE: Whenever J_SCHED is called), priority is given to the jobs on the disk (run an 'isEmpty' on the disk to see if its empty).
		//If memory is available, send disk job down and put new incoming job at the back of the line (FCFS/FIFO).
		//If memory is NOT available, run standard protocol on newly arrived job
		//If all memory is full, run J_DISPATCH and proceed with standard function for all situations
		//If incoming job gives a 0 for...lets say the ID, then try to load EVERYTHING IT CAN from the disk in order to fill that memory hole in mem_manager, job mix be damned.
		//So this requires a 2nd 'load' procedure that can run under these circumstances
		
		public static void main(String[] args)
		{
		}
}