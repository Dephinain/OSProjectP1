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
		//NOTE: Calls J_SCHED upon publishing
		
	}
	
	/*When J_SCHED and J_TERM aren't running, this is. Handles the actual 'running' of a job. In a First Come, First Serve (FCFS) fashion, the address for the Process Control Block (PCB) next in line will get passed to this function. Passes the processing time to the CPU, which increments the clock by said processing time. After it is run (IE: time is processed), J_TERM will be called to output job stats and will remove the finished process from memory in order to make room for another process.*/
	public void J_DISPATCH()
	{
		
	}
	
	public static void main(String[] args)
	{
		//String filename = "C:\Users\Larry\Desktop\18Sp-jobs";
		//File file = new File("C:\\Users\\Larry\\Desktop\\18Sp-jobs"); Line used for CSX reading the file, other line is for testing on local machines
		File file = new File("18sp-jobs");
		String line;
		String[] tokens;
		int count = 0;
		ArrayList<String> readyQueue = new ArrayList<String>(26);
		Queue<String> disk = new LinkedList<String>();
		J_SCHED sched = new J_SCHED();
		
		
		//This chunk runs everything. J_SCHED runs first after the initial line is read in and sent to it.
		try 
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			for(int i = 0; i < 5; i++)
			{
				int queueCount = 0;
				if((line = bufferedReader.readLine()) != null)
				{
					if(disk.isEmpty() == false)
					{
						//System.out.println("This is the job that was just introduced, being put onto disk since there's a job at the top of the disk with priority: " + line);
						disk.add(line);
						line = disk.remove(); //No jobs from disk being put onto ready queue
						//System.out.println("This is the job that was popped off the top of the disk queue to be inserted into the ready queue: " + line);
						//System.out.println("Incoming job has been replaced with job from disk and put at tail end of disk to await run.");
					}
					//Need to check: if disk has a job vs. the incoming job
					if(sched.idCheck(line)) //checks if job id is 0, if so then calls J_DISPATCH to run.
					{
						//call J_DISPATCH to run job in ready queue and continue
					}
					if(readyQueue.size() == 26) //Checks if the ready queue is full. If it is, calls J_DISPATCH to run.
					{
						//call J_DISPATCH to run job and continue
					}
					if(sched.memoryCheck(line))
					{
						System.out.println("This job is being put onto the readyqueue: " + line);
						readyQueue.add(line);
						System.out.println(Arrays.toString(readyQueue.toArray()));
						queueCount++;
					}
					else
					{	
						System.out.println("Job being put onto disk since it fits no requirements: " + line);
						disk.add(line);
						count++;
					}
					
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
		
		for(Iterator<String> iterator = disk.iterator(); iterator.hasNext();) //This operation's purpose is to load a job from 'disk' when J_SCHED runs.
		{//If job on top of queue can't be entered because its memory slot(s) are taken, punt it to the bottom of the queue and try the next one. 
		 //If all 300 jobs on the disk are incompatible, tell J_SCHED to run J_DISPATCH.
		 //If both ready queue and disk are full with an incoming line, immediately run J_DISPATCH to clear up some room.
			String value = iterator.next();
			System.out.println("This is from the Object -> string loop.");
			System.out.println(value);
			//iterator.remove();
		}
		
		System.out.println("The number of items entered into the ready queue is: " + count);
	}
}