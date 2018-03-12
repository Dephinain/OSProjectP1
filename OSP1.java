/*
Name: Myles Brown
Course Number: CS 4332
Assignment Title: Operating Systems Project - Phase 1
Date: 2/26/2018
Global Variables: J_SCHED sched - this is the job scheduler class that properly reads, sorts, and appends relevant information to the job based upon its ID, memory need, and class.
				  jobsProcessed - Counts how many jobs have been processed by the program
				  cpuJobCount  - Counts how many cpu-based jobs have been processed
				  ioJobCount - Counts how many io-based jobs have been processed
				  balancedJobCount - Counts how many io/cpu balanced jobs have been processed
				  totalTurnaround - Gets the total turnaround time for all processed jobs
				  totalWaitTime - Gets the total wait time for all processed jobs
				  rejectCount - Counts how many jobs have been rejected due to being either empty jobs (0) or not meeting the memory requirements
				  totalProcess - Counts how many processes have been run
				  qCONSTRAINT - The hardcap on memory availability
				  
File description: Main protocol for the scheduler, takes input from the incoming job file and calls appropriate methods and functions (J_SCHED, J_DISPATCH, J_TERM) according to input.

Possible improvements: A pretty bad way of handling the 0 jobs that got loaded to disk and subsequently memory/execution. Checks built in for such a thing don't work properly, and
would require more time than alloted to fix.
				  
*/

import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.lang.*;

public class OSP1
{
	public static J_SCHED sched = new J_SCHED();
	public static int jobsProcessed;
	public static int cpuJobCount, ioJobCount, balancedJobCount, totalTurnaround, totalWaitTime, rejectCount, totalProcess;
	public static final int qCONSTRAINT = 26;
	
	/*Job termination function. Documents and outputs job termination statistics, and purges the appropriate queues upon termination.*/
	public static void J_TERM(String finishedJob)
	{
		
		Date time = new Date(); //Gets the time of execution/termination
		long term_time = time.getTime(); //Termination time
		long tat_time, wait_time; //Turnaround time and wait time
		String[] term_tokens; //Array to hold to-be tokenized job string
		term_tokens = finishedJob.split("\\s+"); //Tokenizes terminating job string
		if(Integer.parseInt(term_tokens[1]) == 0)
		{
		}
		else
		{
			tat_time = term_time - Math.abs(Long.parseLong(term_tokens[4]) - Long.parseLong(term_tokens[5]));
			wait_time = term_time - Long.parseLong(term_tokens[5]);
			if(Integer.parseInt(term_tokens[2]) == 1)
				cpuJobCount++;
			else if(Integer.parseInt(term_tokens[2]) == 2)
				balancedJobCount++;
			else if(Integer.parseInt(term_tokens[2]) == 3)
				ioJobCount++;
		
			//Output of individual job statistics.
			System.out.println("\nJob stats: ");
			System.out.println("Job ID: " + term_tokens[1]);
			System.out.println("Class of Job: " + term_tokens[2]);
			System.out.println("Time job was submitted: " + term_tokens[5] + " milliseconds.");
			System.out.println("Time job was loaded to ready queue: " + term_tokens[6] + " milliseconds.");
			System.out.println("Time job was terminated at: " + term_time + " milliseconds.");
			System.out.println("Time job was spent processing: " + term_tokens[4] + " milliseconds.");
			System.out.println("Turnaround time: " + tat_time + " milliseconds.");
			System.out.println("Waiting time: " + wait_time + " milliseconds.");
			sched.releaseMemory(finishedJob); //Inputs job string to release memory after execution
			totalTurnaround += tat_time;
			totalWaitTime += wait_time;
			jobsProcessed++;
		}
		
	}
	
	//Executes/processes incoming job from ready queue
	public static void J_DISPATCH(String arrivingJob)
	{
		String[] dispatch_tokens; //Array to hold to-be tokenized
		dispatch_tokens = arrivingJob.split("\\s+"); //Tokenizes executing job string
		try
		{
			totalProcess += Integer.parseInt(dispatch_tokens[4]);
			J_TERM(arrivingJob); //Calls after job 'execution' to output termination stats
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		File file = new File("/home/opsys/OS-I/18Sp-jobs");
		//File file = new File("18sp-jobs"); 
		String line; //Line that holds incoming job to be checked
		BlockingQueue<String> readyQueue = new ArrayBlockingQueue<String>(qCONSTRAINT); //Ready queue initialized with the constraint
		BlockingQueue<String> disk = new ArrayBlockingQueue<String>(300); //Disk initialized with the constraint given by the assignment documentation
		Date date = new Date(); //Used to get the time at certain key moments in scheduler
		
		
		//This chunk runs everything. J_SCHED runs first after the initial line is read in and sent to it.
		try 
		{
			FileReader fileReader = new FileReader(file); //Preps input stream of file
			BufferedReader bufferedReader = new BufferedReader(fileReader); //Buffers input stream for main program loop
			
			while((line = bufferedReader.readLine()) != null)
			{
				
					StringBuffer appendArrivalTime = new StringBuffer(line); //StringBuffer used to append 'job queue arrival time' to the incoming job string
					appendArrivalTime.append("	" + date.getTime());
					line = appendArrivalTime.toString(); //appends arrival time to incoming job
					if(disk.isEmpty() == false)
					{
						if(disk.size() == 300) //If disk is full, proceeds to empty the ready queue to offload jobs from disk
						{
							for(int k = 0; k < readyQueue.size(); k++) //Jobs from ready queue ran to offload jobs from disk
							{
								J_DISPATCH(readyQueue.take());
							}
							
							for(int j = 0; j < qCONSTRAINT; j++) //Adds jobs from disk to ready queue
							{
								if(readyQueue.size() == qCONSTRAINT)
									break;
								else
								{
									StringBuffer appendLoadTime = new StringBuffer(disk.element()); //Another string buffer used to append 'ready queue load time' to incoming job string
									String tempItem;
									appendLoadTime.append("	" + date.getTime());
									tempItem = appendLoadTime.toString();
									readyQueue.add(tempItem);
									disk.take();
								}
							}
							continue;
						}
						String[] check_tokens; //Array used to hold to-be tokenized job string to see if its memory is within proper constraints
						check_tokens = line.split("\\s+"); //Tokenizes job string
						if(sched.sizeCheck(Integer.parseInt(check_tokens[3])) == false) //Checks if the memory requested by the incoming job is within available parameters
						{
							rejectCount++;
							continue;
						}
						else
						{
							disk.add(line);
							line = disk.take();
						}
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
							if(sched.aquireMemoryCheck(disk.element())) //Pops 0 job off stack
							{
								if(sched.idCheck(disk.element()))
								{
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
					if(sched.aquireMemoryCheck(line)) //If the above checks are passed, then this runs to simply input the job into the ready queue (if it is able).
					{
						StringBuffer appendLoadTime = new StringBuffer(line);
						appendLoadTime.append("	" + date.getTime());
						line = appendLoadTime.toString(); 
						readyQueue.add(line);
					}
					else
					{
						String[] check_tokens;
						check_tokens = line.split("\\s+");
						if(sched.sizeCheck(Integer.parseInt(check_tokens[3])) == false) //Checks if memory requested is outside constraints.
						{
							rejectCount++;
							continue;
						}
						else
						{
							disk.add(line);
						}
					}
			}
			bufferedReader.close();
			
			while(disk.isEmpty() == false) //Empties disk after job input has ceased.
			{
				String arrival, loaded;
				StringBuffer appendArrivalTime = new StringBuffer(disk.take());
				appendArrivalTime.append("	" + date.getTime());
				arrival = appendArrivalTime.toString();
				
				if(readyQueue.size() == qCONSTRAINT)
				{
					J_DISPATCH(readyQueue.take());
				}
				else
				{
					StringBuffer appendLoadTime = new StringBuffer(arrival);
					appendLoadTime.append("	" + date.getTime());
					loaded = appendLoadTime.toString();
					readyQueue.add(loaded);
				}
			}
			
			while(readyQueue.isEmpty() == false) //Empties ready queue after job input has ceased.
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
		
		//Final output.
		System.out.println("\nNumber of jobs processed: " + jobsProcessed);
		System.out.println("Number of CPU-bound jobs: " + cpuJobCount);
		System.out.println("Number of Balanced jobs: " + balancedJobCount);
		System.out.println("Number of IO-bound jobs: " + ioJobCount);
		System.out.println("Average turnaround time: " + (totalTurnaround/jobsProcessed) + " milliseconds.");
		System.out.println("Average wait time: " + (totalWaitTime/jobsProcessed) + " milliseconds.");
		System.out.println("Number of rejected jobs: " + rejectCount);
		System.out.println("Total processing time of CPU clock: " + totalProcess);
	}
}