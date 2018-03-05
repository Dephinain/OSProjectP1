import java.io.*;
import java.util.*;

public class mem_manager
{
	
	public mem_manager()
	{
	};
	
	public mem_manager(boolean[][] memoryArr)
	{
	};
	
	/*Keeps information on available memory for jobs. Should return a boolean value for 'yeah we got space', then occupy that space with some value that shows the process is running. Then, once process is done, J_TERM will be called on it to free up that space. Since the job ID is unique, should probably assign job ID to space to do a check. For the size 52K and 128K, can just tick a boolean/some value that says occupied until J_TERM is called on it since they only have the one space available. THINK ABOUT DOING INNER CLASSES FOR THIS ONE IF YOU DON'T WANNA MAKE MORE THAN 1 FILE. */
		
		//has three methods - acquire, which does the obvious and grabs the requested memory (inner class function(?))
		//release, also does the obvious, and releases the memory held by the finished process (inner class function(?))
		//Lastly, check_mem does exactly that: check to see if any of the requested memory is available. If so, call acquire. If not, tell J_SCHED to shunt it onto the disk for later consideration.
		//Possible 4th: A check to see if all memory is taken. Only needs to be done once, so J_SCHED can kick off the whole job fiesta.
		//Prrrrobably need to have this run in a loop to set all values to 'true' at initialization as a global variable, then can do everything that mem_manager needs it to do
		//Need to uhhh, split memory memory into if statements. ex: job comes in with mem requirement of 20, it ticks the 'x> 18k, x <= 32k' statement, then the memory is checked to see if it has any room available in the 32k memory slots. If so, return that job should be put into ready queue and mark memory sector 'false'. If not, send back that job should be put on disk until memory is available.
}