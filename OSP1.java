import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.lang.*;

//Note: All operations need to take place within 'while' loop reading from the file - handles everything one line at a time.
public class OSP1
{
	public static J_SCHED sched = new J_SCHED();
	public static int jobsProcessed;
	public static int cpuJobCount, ioJobCount, balancedJobCount, totalTurnaround, totalWaitTime;
	
	/*Job termination function. Documents and outputs job termination statistics, and purges the appropriate queues upon termination.*/
	public static void J_TERM(String finishedJob)
	{
		Date time = new Date();
		long term_time = time.getTime();
		long tat_time, wait_time;
		String[] term_tokens;
		term_tokens = finishedJob.split("\\s+");
		tat_time = term_time - Long.parseLong(term_tokens[5]);
		wait_time = term_time - Long.parseLong(term_tokens[4]) - Long.parseLong(term_tokens[4]);
		if(Integer.parseInt(term_tokens[2]) == 1)
			cpuJobCount++;
		else if(Integer.parseInt(term_tokens[2]) == 2)
			balancedJobCount++;
		else if(Integer.parseInt(term_tokens[2]) == 3)
			ioJobCount++;
		
		System.out.println("\nJob stats: ");
		System.out.println("Job ID: " + term_tokens[1]);
		System.out.println("Class of Job: " + term_tokens[2]);
		System.out.println("Time job was submitted: " + term_tokens[5] + " milliseconds.");
		System.out.println("Time job was loaded to ready queue: " + term_tokens[6] + " milliseconds.");
		System.out.println("Time job was terminated: " + term_time);
		System.out.println("Time job was spent processing: " + term_tokens[4] + " milliseconds.");
		System.out.println("Turnaround time: " + tat_time + " milliseconds.");
		System.out.println("Waiting time: " + wait_time + " milliseconds.");
		sched.releaseMemory(finishedJob);
		totalTurnaround += tat_time;
		totalWaitTime += wait_time;
		jobsProcessed++;
	}
	
	public static void J_DISPATCH(String arrivingJob)
	{
		String[] dispatch_tokens;
		dispatch_tokens = arrivingJob.split("\\s+");
		try
		{
			Thread.sleep(Integer.parseInt(dispatch_tokens[4]));
			J_TERM(arrivingJob);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		//File file = new File("C:\\Users\\Larry\\Desktop\\18Sp-jobs"); Line used for CSX reading the file, other line is for testing on local machines
		File file = new File("18sp-jobs");
		String line;
		String[] tokens;
		int count = 0, queueCount = 0;
		final int qCONSTRAINT = 26;
		BlockingQueue<String> readyQueue = new ArrayBlockingQueue<String>(qCONSTRAINT);
		BlockingQueue<String> disk = new ArrayBlockingQueue<String>(300);
		Date date = new Date();
		
		
		//This chunk runs everything. J_SCHED runs first after the initial line is read in and sent to it.
		try 
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null)
			{
				
					StringBuffer appendArrivalTime = new StringBuffer(line);
					appendArrivalTime.append("	" + date.getTime());
					line = appendArrivalTime.toString(); //appends arrival time to incoming job
					if(disk.isEmpty() == false)
					{
						if(disk.size() == 300) //If disk is full, proceeds to empty the ready queue to offload jobs from disk
						{
							System.out.println("THIS DISK IS ONE THICC BIH");
							for(int k = 0; k < readyQueue.size(); k++)
							{
								J_DISPATCH(readyQueue.take());
							}
							
							for(int j = 0; j < qCONSTRAINT; j++)
							{
								if(readyQueue.size() == qCONSTRAINT)
									break;
								else
								{
									StringBuffer appendLoadTime = new StringBuffer(disk.element());
									String tempItem;
									appendLoadTime.append("	" + date.getTime());
									tempItem = appendLoadTime.toString();
									readyQueue.add(tempItem);
									disk.take();
								}
							}
							continue;
						}
						disk.add(line);
						line = disk.take();
					}
					
					if(readyQueue.size() == qCONSTRAINT) //Checks if the ready queue is full. If it is, calls J_DISPATCH to run.
					{
						J_DISPATCH(readyQueue.take());
						continue;
					}
					
					if(sched.idCheck(line)) //checks if job id is 0, if so then fills ready queue from disk and calls J_DISPATCH to run.
					{
						for(int j = 0; j < qCONSTRAINT; j++)
						{
							if(sched.aquireMemoryCheck(disk.element()))
							{
								if(sched.idCheck(disk.element()))
								{
									System.out.println("Henlo");
									disk.take();
								}
								else
								{
									StringBuffer appendLoadTime = new StringBuffer(disk.element());
									appendLoadTime.append("	" + date.getTime());
									if(readyQueue.size() == qCONSTRAINT)
									{
										for(int k = 0; k < readyQueue.size(); k++)
										{
											J_DISPATCH(readyQueue.take());
										}
									}
									readyQueue.add(appendLoadTime.toString());
									disk.take();
								}
							}
							else
							{	
								disk.add(disk.take());
							}
						}
						
						J_DISPATCH(readyQueue.take());
						continue;
					}
					if(sched.aquireMemoryCheck(line)) //Standard memory check/allocation for an incoming job
					{
						StringBuffer appendLoadTime = new StringBuffer(line);
						appendLoadTime.append("	" + date.getTime());
						line = appendLoadTime.toString(); //appends load time to incoming job
						readyQueue.add(line);
					}
					else
					{
						String[] check_tokens;
						check_tokens = line.split("\\s+");
						if(sched.sizeCheck(Integer.parseInt(check_tokens[3])) == false)
							continue;
						else
						{
							//System.out.println("The job takes up this much memory: " + Integer.parseInt(check_tokens[3]));
							disk.add(line);
						}
					}
			}
			bufferedReader.close();
			
			while(readyQueue.isEmpty() == false) //Empties ready queue after job input has ceased
			{
					J_DISPATCH(readyQueue.take());
			}
			
		}
		catch(FileNotFoundException ex)
		{
			System.out.println("Unable to open file.");
		}
		catch(IOException ex)
		{
			System.out.println("Error reading file.");
		}
		catch(Exception ex)
		{
			ex.printStackTrace(System.out);
		}
		System.out.println("\nThe number of items entered into the disk is: " + count);
		System.out.println("The number of items entered into the ready queue is " + readyQueue.size());
		System.out.println("Number of jobs processed: " + jobsProcessed);
		System.out.println("Number of CPU-bound jobs: " + cpuJobCount);
		System.out.println("Number of Balanced jobs: " + balancedJobCount);
		System.out.println("Number of IO-bound jobs: " + ioJobCount);
		System.out.println("Average turnaround time: " + (totalTurnaround/jobsProcessed) + " milliseconds.");
		System.out.println("Average wait time: " + Math.abs(totalWaitTime/jobsProcessed) + " milliseconds.");
		System.out.println("Time of program termination: " + date.getTime());
	}
}