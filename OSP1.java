import java.io.*;
import java.util.*;

public class OSP1
{
	public static Queue disk = new LinkedList();
	public static int count = 0;
	/*Job arrival routine. First routine system calls, handles incoming jobs. So need to have it be called at the beginning of each new line of the job list read - 
	so something like "line = bufferedReader.readLine() -> J_SCHED(line)". Think about moving the split string loop into here so it can treat each job uniquely and then categorize it accordingly.
	Said categorizing needs to be an 'ordered job mix' between IO-bound, CPU-bound, and balanced job types. Professor shunted the responsibility of finding out what exactly that is for your system on you, so get it working
	first, then optimize the mix.*/
	public void J_SCHED()
	{
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
	
	public void mem_manager() //Subprogram keeping track of memory, think about shunting this off to its own page and granting main program access to it when running
	{
		/*Keeps information on available memory for jobs. Should return a boolean value for 'yeah we got space', then occupy that space with some value that shows the process is running. Then, once process is done, J_TERM will be called on it to free up that space. Since the job ID is unique, should probably assign job ID to space to do a check. For the size 52K and 128K, can just tick a boolean/some value that says occupied until J_TERM is called on it since they only have the one space available. THINK ABOUT DOING INNER CLASSES FOR THIS ONE IF YOU DON'T WANNA MAKE MORE THAN 1 FILE. */
		
		//has three entries - acquire, which does the obvious and grabs the requested memory (inner class function(?))
		//release, also does the obvious, and releases the memory held by the finished process (inner class function(?))
		//Lastly, check_mem does exactly that: check to see if any of the requested memory is available. If so, call acquire. If not, tell J_SCHED to shunt it onto the disk for later consideration.
		//Possible 4th: A check to see if all memory is taken. Only needs to be done once, so J_SCHED can kick off the whole job fiesta.
	}
	
	public static void main(String[] args)
	{
		String filename = "18Sp-jobs";
		String line, parsedLine;
		String[] tokens;
		
		//This chunk runs everything. J_SCHED runs first after the initial line is read in and sent to it.
		try 
		{
			FileReader fileReader = new FileReader(filename);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			for(int i = 0; i < 15; i++)
			{
				if((line = bufferedReader.readLine()) != null)
				{
					disk.add(line);
					tokens = line.split("	");
					//J_SHCED(line);
					
					//for(String t : tokens) //splits line into array for processing. Order should always be: JOB ID, JOB CLASS, REQUESTED MEMORY, PROCESSING TIME
					//	System.out.println(t);
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
		 //If all 300 jobs on the disk are
			String value = iterator.next();
			System.out.println("This is from the Object -> string loop.");
			System.out.println(value);
			count++;
			iterator.remove();
			//disk.poll(); - removing from queue during loop gives an exception. Need to use iterator in order to remove during loop over queue
			//https://stackoverflow.com/questions/223918/iterating-through-a-collection-avoiding-concurrentmodificationexception-when-re
		}
		
		System.out.println("The number of items entered is: " + count);
	}
}