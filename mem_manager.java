import java.io.*;
import java.util.*;

public class mem_manager
{
	public static boolean[][] workingArray;
	
	public mem_manager()
	{
	};
	
	public mem_manager(boolean[][] input)
	{
		this.workingArray = input;
	};
	
	public static boolean aquire(int memoryNeed)
	{
		if(memoryNeed == 0)
		{
			return false;
		}

		if((memoryNeed >= 1) && (memoryNeed <= 8))
		{
			for(int i = 0; i < workingArray[0].length; i++)
			{
				if(workingArray[0][i] == true)
				{
					workingArray[0][i] = false;
					return true;
				}
				else if(workingArray[0][i] == false)
					continue;
			}
		}
		else if((memoryNeed > 8) && (memoryNeed <= 12))
		{
			for(int i = 0; i < workingArray[1].length; i++)
			{
				if(workingArray[1][i] == true)
				{
					workingArray[1][i] = false;
					return true;
				}
				else
				{
					continue;
				}
			}
		}
		else if((memoryNeed > 12) && (memoryNeed <= 18))
		{
			for(int i = 0; i < workingArray[2].length; i++)
			{
				if(workingArray[2][i] == true)
				{
					workingArray[2][i] = false;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 18) && (memoryNeed <= 32))
		{
			for(int i = 0; i < workingArray[3].length; i++)
			{
				if(workingArray[3][i] == true)
				{
					workingArray[3][i] = false;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 32) && (memoryNeed <= 52))
		{
			for(int i = 0; i < workingArray[4].length; i++)
			{
				if(workingArray[4][i] == true)
				{
					workingArray[4][i] = false;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 52) && (memoryNeed <= 60))
		{
			for(int i = 0; i < workingArray[5].length; i++)
			{
				if(workingArray[5][i] == true)
				{
					workingArray[5][i] = false;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 60) && (memoryNeed <= 128))
		{
			for(int i = 0; i < workingArray[6].length; i++)
			{
				if(workingArray[6][i] == true)
				{
					workingArray[6][i] = false;
					return true;
				}
				else
					continue;
			}
		}
		
		return false;
	};
	
	public static boolean release(int memoryNeed)
	{
		if(memoryNeed <= 8)
		{
			for(int i = 0; i < workingArray[0].length; i++)
			{
				if(workingArray[0][i] == false)
				{
					workingArray[0][i] = true;
					return true;
				}
				else if(workingArray[0][i] == true)
					continue;
			}
		}
		else if((memoryNeed > 8) && (memoryNeed <= 12))
		{
			for(int i = 0; i < workingArray[1].length; i++)
			{
				if(workingArray[1][i] == false)
				{
					workingArray[1][i] = true;
					return true;
				}
				else
				{
					continue;
				}
			}
		}
		else if((memoryNeed > 12) && (memoryNeed <= 18))
		{
			for(int i = 0; i < workingArray[2].length; i++)
			{
				if(workingArray[2][i] == false)
				{
					workingArray[2][i] = true;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 18) && (memoryNeed <= 32))
		{
			for(int i = 0; i < workingArray[3].length; i++)
			{
				if(workingArray[3][i] == false)
				{
					workingArray[3][i] = true;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 32) && (memoryNeed <= 52))
		{
			for(int i = 0; i < workingArray[4].length; i++)
			{
				if(workingArray[4][i] == false)
				{
					workingArray[4][i] = true;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 52) && (memoryNeed <= 60))
		{
			for(int i = 0; i < workingArray[5].length; i++)
			{
				if(workingArray[5][i] == false)
				{
					workingArray[5][i] = true;
					return true;
				}
				else
					continue;
			}
		}
		else if((memoryNeed > 60) && (memoryNeed <= 128))
		{
			for(int i = 0; i < workingArray[6].length; i++)
			{
				if(workingArray[6][i] == false)
				{
					workingArray[6][i] = true;
					return true;
				}
				else
					continue;
			}
		}
		return false;
	}
	public static boolean memoryAllowed(int memoryNeed)
	{
		if(memoryNeed > 128)
		{
			return false;
		}
		else
			return true;
	}
}