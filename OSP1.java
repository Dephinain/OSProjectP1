import java.io.*;
import java.util.*;

//Note: All operations need to take place within 'while' loop reading from the file - handles everything one line at a time.
public class OSP1
{
	/*Job termination function. Documents and outputs job termination statistics, and purges the appropriate queues upon termination.*/
	public void J_TERM(String finishedJob)
	{
		//String[] tokens;
		//	tokens = finishedJob.split("\\s+");
		//System.out.println("Job stats: ")
		//The following information needs to be ouput: 
		//Job ID - System.out.println("Job ID: " + tokens[1])
		//Class of Job - System.out.println("Class of Job: " + tokens[2])
		//Time job was submitted to system - System.out.println() etc
		//Time job was loaded
		//Time job was terminated
		//Processing time
		//Turnaround time
		//Waiting time
		//NOTE: Calls J_SCHED upon publishing
		
	}
	
	public void J_DISPATCH(String arrivingJob)
	{
		//String[] tokens;
		//	tokens = arrivingJob.split("\\s+");
		//Thread.sleep(Integer.parseInt(tokens[4]));
		//call J_TERM(arrivingJob);
		//simulates CPU processing time by calling Thread.sleep(Integer.parseInt(tokens[4])
		//After 'processing', calls J_TERM to terminate job and free up some memory
	}
	
	public static void main(String[] args)
	{
		//String filename = "C:\Users\Larry\Desktop\18Sp-jobs";
		//File file = new File("C:\\Users\\Larry\\Desktop\\18Sp-jobs"); Line used for CSX reading the file, other line is for testing on local machines
		File file = new File("18sp-jobs");
		String line;
		String[] tokens;
		int count = 0, queueCount = 0;
		final int qCONSTRAINT = 26;
		ArrayList<String> readyQueue = new ArrayList<String>(qCONSTRAINT);
		Queue<String> disk = new LinkedList<String>();
		J_SCHED sched = new J_SCHED();
		Date date = new Date();
		
		
		//This chunk runs everything. J_SCHED runs first after the initial line is read in and sent to it.
		try 
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			for(int i = 0; i < 2000; i++) //May come into contact with an infinite loop bug when doing the 'while' conversion - be aware.
			{
				if((line = bufferedReader.readLine()) != null)
				{
					StringBuffer appendArrivalTime = new StringBuffer(line);
					appendArrivalTime.append("	" + date.getSeconds());
					line = appendArrivalTime.toString(); //appends arrival time to incoming job
					if(disk.isEmpty() == false)
					{
						//System.out.println("This is the job that was just introduced, being put onto disk since there's a job at the top of the disk with priority: " + line);
						disk.add(line);
						line = disk.remove(); //No jobs from disk being put onto ready queue
						//System.out.println("This is the job that was popped off the top of the disk queue to be inserted into the ready queue: " + line);
						//System.out.println("Incoming job has been replaced with job from disk and put at tail end of disk to await run.");
					}
					if(sched.idCheck(line)) //checks if job id is 0, if so then fills ready queue from disk and calls J_DISPATCH to run.
					{
						System.out.println("0 encountered, so we're loading everything from the disk and running it until we get some new jobs");
						for(int j = 0; j < qCONSTRAINT; j++)
						{
							if(sched.memoryCheck(disk.element()))
							{
								if(sched.idCheck(disk.element()))
								{
									System.out.println("Henlo");
									disk.remove();
								}
								else
								{
									readyQueue.add(disk.remove());
									System.out.println(readyQueue.size());
								}
							}
							else
							{	
								disk.add(disk.remove());
							}
						}
						//J_DISPATCH call
						continue;
					}
					if(readyQueue.size() == qCONSTRAINT) //Checks if the ready queue is full. If it is, calls J_DISPATCH to run.
					{
						System.out.println("Queue is full, so we're ceasing activity to run J_DISPATCH");
						break;
					}
					if(sched.memoryCheck(line))
					{
						readyQueue.add(line);
						//System.out.println(readyQueue.size());
						queueCount++;
					}
					else
					{	
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
			//System.out.println("This is from the Object -> string loop.");
			//System.out.println(value);
			//iterator.remove();
		}
		
		System.out.println("The number of items entered into the disk is: " + count);
		System.out.println("The number of items entered into the ready queue is " + readyQueue.size());
		
		/*for(int i = 0; i < readyQueue.size(); i++)
		{
			System.out.println(readyQueue.get(i));
		}*/
	}
}