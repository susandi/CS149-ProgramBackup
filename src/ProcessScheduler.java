
import java.util.*;

public class ProcessScheduler {
	
	public static int count=0;
	
	int []priority=new int[4];
	float [][] arrivalTime;
	int []runTime=new int[15];
	int []turnaroundArr=new int[15];
	int []waitingTimeArr=new int[15];
	int []responseTimeArr=new int[15];
	String []processId=new String[12];
	int noOfProcess=0, totalRunTime=0;
	int setseed=0;
	//this is process queue 
	LinkedList<String> myQueue= new LinkedList<String>();
	
	private void processGenerator()
	{	
		float min=0.0F;
		float max=10.0F;
		int low=5;int high=15;
		Random rangen=new Random();
		noOfProcess=rangen.nextInt(high-low)+low;
		
		arrivalTime=new float[noOfProcess][noOfProcess];
	
			for(int j=0;j<noOfProcess;j++)
		{		
				arrivalTime[j][1] =(float) (Math.random()*((max-min)+1));
				//REMOVE THIS WHEN YOU ARE DONE DEBUGGING
				//arrivalTime[j][1] =(float) (setseed);
				
				arrivalTime[j][0]=(float)j;
			
		}			
			
		java.util.Arrays.sort(arrivalTime,new java.util.Comparator<float[]>()
				{
					@Override
					public int compare( float[] a1,float[] b1)
					{
						return  (int) (a1[1]-b1[1]);
					}
					});
		 nameProcesses(arrivalTime, noOfProcess);
		
	}
	
	
	
	private void  nameProcesses(float[][] array,int n)
	{
		String []processNames= new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
		//first add alphabetical process Names to processId, then push them to process queue
		for(int i=0;i<n;i++)
		{
			int num=(int) array[i][0];
			//push processId to process queue based on arrival time
			myQueue.add(processNames[num]);
		}
	
	}
	private int genRandomTime()
	{	
		Random rand=new Random();
		//get random burst time for each process
		for(int i=0;i<noOfProcess;i++)
		{	
			runTime[i]=rand.nextInt(5)+5;
			//REMOVE THIS WHEN YOU ARE DONE DEBUGGING
//			runTime[i]=rand.nextInt(setseed);
			//count total time slots by adding all the burst time
			totalRunTime= totalRunTime+runTime[i];
		}
		
		System.out.println("\nTotal processes: "+noOfProcess);
		System.out.println("Total Time quantum: "+totalRunTime);
		System.out.println("\nProcess_ID\tarrivalTime\t\trunTime\t\tturnaroundTime\t\twaitingTime\t\tresponseTime");
		
		
		int t1=0,t2=0,waitTime=0,responseTime=0;
		int avgRTime=0,avgTTime=0,avgWTime=0,throughput=0;
		for(int i=0;i<noOfProcess;i++)
		{		  throughput=throughput+runTime[i];
				  //Turnaround=runTime-arrivalTime
				  t1=t1+runTime[i];
				  t2=(int)(t1-arrivalTime[i][1]);				  
				  turnaroundArr[i]=t2;
				  t1=0;t2=0;//clear up t variable for wait time
				  avgTTime+=turnaroundArr[i];
				  
				  if(i==0)responseTime=0;
				  
				  if(i>0)
				  {  t1=t1+runTime[i];
				  	 t2=(int)(t1-arrivalTime[i][1]);	
					  waitTime=t2;
					  responseTime=waitTime;
				  }
				  waitingTimeArr[i]=waitTime;
				  responseTimeArr[i]=responseTime;
				  avgRTime+=responseTimeArr[i];
				  avgWTime+=waitingTimeArr[i];
			      System.out.println(myQueue.get(i)+"\t\t"+arrivalTime[i][1]+"\t\t"+runTime[i]+"\t\t"+turnaroundArr[i]+"\t\t\t"+waitingTimeArr[i]+"\t\t\t"+responseTimeArr[i]);//array[i][1]
		}
		avgTTime=avgTTime/noOfProcess; 
		avgWTime=avgWTime/noOfProcess;
		avgRTime=avgRTime/noOfProcess;
		throughput=throughput/noOfProcess;
		int c=0;
		int[]avgArr=new int[count];
//		if(count==0)	
//		{		avgArr[0]=avgTTime;//avgArr[0][0]=turnaround
//				avgArr[1]=avgWTime;//avgArr[1][0]=waiitingtime
//				avgArr[2]=avgRTime;//avgArr[2][0]=responsetime
//				avgArr[3]=avgArr[3]+throughput;//avgArr[3][0]=throughput
//		}
//				
//				else 
//		{			avgArr[0]=avgArr[0]+avgTTime;//avgArr[0][0]=turnaround
//					avgArr[1]=avgArr[1]+avgWTime;//avgArr[1][0]=waiitingtime
//					avgArr[2]=avgArr[2]+avgRTime;//avgArr[2][0]=responsetime
//					avgArr[3]=avgArr[3]+throughput;//avgArr[3][0]=throughput
//		}
		
		System.out.println("\nAvgTurnaroundTime\tAvgWaitingTime\t\tAvgResponseTime\t\tThroughput");
		System.out.println(avgTTime+"\t\t\t"+avgWTime+"\t\t\t"+avgRTime+"\t\t\t"+throughput);//array[i][1]
//		System.out.println("Round "+count);
//		System.out.println("\nTurnaroundTime\tWaitingTime\t\tResponseTime\t\tThroughput");
//		System.out.println(avgArr[0]+"\t\t\t"+avgArr[1]+"\t\t\t"+avgArr[2]+"\t\t\t"+avgArr[3]);
	
		return totalRunTime;
	}
	private void FCFS()
	{
	
	processGenerator();
	
	String []timeChart=new String[genRandomTime()];
	int i=0,lastindex=0,range=0,x=0;
					
		
	do {	
			String tmp=myQueue.getFirst();
		
			if(x==0)	range=runTime[x];	
			
			for(i=lastindex;i<range;i++)
			{	
					timeChart[i]=tmp;				
			}
			lastindex=range;//the end index of current process is the start index of the next process
			x++;
			range=range+runTime[x]; 
			
			myQueue.pop();
			
		}while(myQueue.peek()!=null && x<noOfProcess);
		//Everything in myQueue are popped, we no longer need process queue
		myQueue.clear();
		//printing out the time chart at the end
		for(int k=0;k<timeChart.length;k++)
			System.out.print(" "+timeChart[k]+" ");
		

	}

	public static void main(String[] args)
	{ 	
		//comment it for now. a loop to run 5 times to find average
//		do{
			ProcessScheduler ps=new ProcessScheduler();
			ps.FCFS();
			count++;
//			}while(count<5);
			
	
	}
}
