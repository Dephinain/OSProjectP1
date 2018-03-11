import java.io.*;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

//Note: All operations need to take place within 'while' loop reading from the file - handles everything one line at a time.
public class OSP1
{
	public static J_SCHED sched = new J_SCHED();
	/*Job termination function. Documents and outputs job termination statistics, and purges the appropriate queues upon termination.*/
	public static void J_TERM(String finishedJob)
	{
		Date time = new Date();
		String[] term_tokens;
		term_tokens = finishedJob.split("\\s+");
		System.out.println("\nJob stats: ");
		//The following information needs to be ouput: 
		System.out.println("Job ID: " + term_tokens[1]);
		System.out.println("Class of Job: " + term_tokens[2]);
		System.out.println("Time job was submitted: " + term_tokens[5] + " seconds.");
		System.out.println("Time job was loaded to ready queue: " + term_tokens[6] + " seconds.");
		System.out.println("Time job was terminated: " + time.getSeconds());
		System.out.println("Time job was spent processing: " + term_tokens[4] + " milliseconds.");
		//Turnaround time - Termination time - Arrival time
		//Waiting time - time from arrival to actually being loaded (Termination time - processing time(in seconds) - arrival time)
		sched.releaseMemory(finishedJob);
	}
	
	public static void J_DISPATCH(String arrivingJob)
	{
		String[] dispatch_tokens;
		dispatch_tokens = arrivingJob.split("\\s+");
		try
		{
			Thread.sleep(Integer.parseInt(dispatch_tokens[4]));
			System.out.println("Nigga we S C H L E E P");
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
			
			for(int i = 0; i < 2000; i++) //May come into contact with an infinite loop bug when doing the 'while' conversion - be aware.
			{
				if((line = bufferedReader.readLine()) != null)
				{
					StringBuffer appendArrivalTime = new StringBuffer(line);
					appendArrivalTime.append("	" + date.getSeconds());
					line = appendArrivalTime.toString(); //appends arrival time to incoming job
					if(disk.isEmpty() == false)
					{
						if(disk.size() == 300)
						{
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
									appendLoadTime.append("	" + date.getSeconds());
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
						System.out.println("Queue is full, so we're ceasing activity to run J_DISPATCH");
						J_DISPATCH(readyQueue.take());
						continue;
					}
					
					if(sched.idCheck(line)) //checks if job id is 0, if so then fills ready queue from disk and calls J_DISPATCH to run.
					{
						System.out.println("0 encountered, so we're loading everything from the disk and running it until we get some new jobs");
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
									appendLoadTime.append("	" + date.getSeconds());
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
					if(sched.aquireMemoryCheck(line))
					{
						StringBuffer appendLoadTime = new StringBuffer(line);
						appendLoadTime.append("	" + date.getSeconds());
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
		catch(Exception ex)
		{
			ex.printStackTrace(System.out);
		}
		
		/*if(readyQueue.isEmpty() == false)
		{
			for(int i = 0; i < readyQueue.size(); i++)
			{
				J_DISPATCH(readyQueue.take());
			}
		}*/
		System.out.println("\nThe number of items entered into the disk is: " + count);
		System.out.println("The number of items entered into the ready queue is " + readyQueue.size());
	}
}