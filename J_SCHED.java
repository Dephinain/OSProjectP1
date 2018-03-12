import java.io.*;
import java.util.*;

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
		//Note: tokens[1-6] represent the following: Job ID(1), class of Job(2), requested memory(3), processing time(4), arrival time(5), time job was loaded to ready queue(6).
		private static mem_manager manager = new mem_manager(memoryArr);
		
		public static boolean aquireMemoryCheck(String inputLine)
		{
			String[] tokens;
			tokens = inputLine.split("\\s+");	
			if(manager.aquire(Integer.parseInt(tokens[3])))
			{
				return true;
			}
			else
				return false;
		};
		
		public static boolean releaseMemory(String inputLine)
		{
			String[] tokens;
			tokens = inputLine.split("\\s+");	
			if(manager.release(Integer.parseInt(tokens[3])))
			{
				return true;
			}
			else
				return false;
		};
		
		public static boolean idCheck(String inputLine)
		{
			String[] tokens;
			tokens = inputLine.split("\\s+");
			if(Integer.parseInt(tokens[1]) == 0)
			{
				return true;
			}
			else
				return false;
		}
		
		public static boolean sizeCheck(int input)
		{
			if((manager.memoryAllowed(input)) == false)
				return false;
			else
				return true;
		}
		public static void main(String[] args)
		{
		}
}