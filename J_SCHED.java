import java.io.*;
import java.util.*;


    /*Job arrival routine. First routine system calls, handles incoming jobs. So need to have it be called at the beginning of each new line of the job list read - 
	so something like "line = bufferedReader.readLine() -> J_SCHED(line)". Think about moving the split string loop into here so it can treat each job uniquely and then categorize it accordingly.
	Said categorizing needs to be an 'ordered job mix' between IO-bound, CPU-bound, and balanced job types. Professor shunted the responsibility of finding out what exactly that is for your system on you, so get it working
	first, then optimize the mix.*/
public class J_SCHED
{
	
		public J_SCHED()
		{
		};
		
		public void run(String inputLine)
		{
			System.out.println("I got accessed!");
			String[] tokens;
			tokens = inputLine.split("\\s+");	
			for(String t : tokens)
				System.out.println(t);
		}
		//String[] tokens;
		//tokens = inputLine.split("\\s+"); //Splits string into array. NEED to convert to int when used. Extra white space given by process, so #s are 1-4.
		//manager.aquire(Integer.parseInt(tokens[3]));
		//for(String t : tokens)
		//	System.out.println(t);
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