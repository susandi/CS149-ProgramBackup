
import java.util.*;

public class ProcessScheduler {
	
	
	private static int count=0;
	private static double[][]avgArr=new double[5][5];	
	private static double[]avgTmp=new double[4];	
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
		int low=5;int high=10;
		Random rangen=new Random();

		noOfProcess=rangen.nextInt(high-low)+low;
		
		noOfProcess=5;
		arrivalTime=new float[noOfProcess][noOfProcess];

			for(int j=0;j<noOfProcess;j++)
		{	
				arrivalTime[j][1] =(float) (Math.random()*((max-min)+1));
		
				arrivalTime[j][0]=(float)j;
			
		}			

		java.util.Arrays.sort(arrivalTime,new java.util.Comparator<float[]>()
				{
					@Override
					public int compare( float[] a1,float[] b1)
					{
						if(a1[1]<b1[1])return -1;
						else if(a1[1]==b1[1]) return 0;
						else return 1;
					}
					});
		 nameProcesses(arrivalTime, noOfProcess);
		
	}
	
	
	
	private void  nameProcesses(float[][] array,int n)
	{
		String []processNames= new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
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
			runTime[i]=rand.nextInt(10)+1;
			
			//count total time slots by adding all the burst time
			totalRunTime= totalRunTime+runTime[i];
		}

		System.out.println("\n\nRound "+count);
		System.out.println("\nTotal processes: "+noOfProcess);
		System.out.println("Total Time quantum: "+totalRunTime);
		CalAvgTime();
		
		return totalRunTime;
	}
	
	private void CalAvgTime()
	{
		System.out.println("\nProcess_ID\tarrivalTime\t\trunTime\t\tturnaroundTime\t\twaitingTime\t\tresponseTime");
		
		
		int t1=0,t2=0,d1=0,d2=0,waitTime=0,responseTime=0;
		double avgRTime=0.0,avgTTime=0,avgWTime=0,throughput=0;
		for(int i=0;i<noOfProcess;i++)
		{		  throughput=throughput+runTime[i];
				  //Turnaround=total runTime of processes-cur arrivalTime
				  t1=t1+runTime[i];
				  t2=(int)(t1-arrivalTime[i][1]);				  
				  turnaroundArr[i]=t2;
				  //t1=0;t2=0;//clear up t variable for wait time
				  avgTTime+=turnaroundArr[i];
				  
				  if(i==0){ d1=d1+runTime[i]; responseTime=0;}//add runTime[0]
				  
				  if(i>0)
				  { //wait time=total runTime of prev processes - cur arrivalTime
				  	 d2=(int)(d1-arrivalTime[i][1]);	
				  	 d1= d1+runTime[i];//add current runTime
					 waitTime=d2;
				  
					 responseTime+=runTime[i];
				  }
				  waitingTimeArr[i]=waitTime;
				  responseTimeArr[i]=responseTime;
				  avgRTime+=responseTimeArr[i];
				  avgWTime+=waitingTimeArr[i];
			      System.out.println(myQueue.get(i)+"\t\t"+arrivalTime[i][1]+"\t\t"+runTime[i]+"\t\t"+turnaroundArr[i]+"\t\t\t"+waitingTimeArr[i]+"\t\t\t"+responseTimeArr[i]);//array[i][1]
		}
		
		avgWTime=avgWTime/noOfProcess;
		avgRTime=avgRTime/noOfProcess;
		throughput=throughput/noOfProcess;

		if(count==1)
		{avgArr[0][0]=avgTTime;			
		avgArr[1][0]=avgWTime;
		avgArr[2][0]=avgRTime;
		avgArr[3][0]=throughput;	
		}
		else if(count==2)
		{ avgArr[0][1]=avgTTime;		
		avgArr[1][1]=avgWTime;
		avgArr[2][1]=avgRTime;
		avgArr[3][1]=throughput;	
		}
		else if(count==3)
		{ avgArr[0][2]=avgTTime;			
		avgArr[1][2]=avgWTime;
		avgArr[2][2]=avgRTime;
		avgArr[3][2]=throughput;	
		}
		else if(count==4)
		{ avgArr[0][3]=avgTTime;		
		avgArr[1][3]=avgWTime;
		avgArr[2][3]=avgRTime;
		avgArr[3][3]=throughput;	
		}
		else if(count==5)
		{ avgArr[0][4]=avgTTime;				
		avgArr[1][4]=avgWTime;
		avgArr[2][4]=avgRTime;
		avgArr[3][4]=throughput;	
		}
		//printing out average time for each round
		System.out.println("\nAvgTurnaroundTime\t\tAvgWaitingTime\t\tAvgResponseTime\t\tThroughput");
		System.out.println(avgTTime+"\t\t\t"+avgWTime+"\t\t\t"+avgRTime+"\t\t\t"+throughput);//array[i][1]
				
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
		//printing out the time chart with processes
		for(int k=0;k<timeChart.length;k++)
			System.out.print(" "+timeChart[k]+" ");
			System.out.println("\n\n");		

	}
	
	public static void main(String[] args)
	{ 	
		do
		{	count++;
			ProcessScheduler ps=new ProcessScheduler();
			ps.FCFS();
		
			
		}while(count<5);
		//printing out average time for 5 rounds
		for(int c=0;c<4;c++)
		{	avgTmp[c]=avgArr[c][0]+avgArr[c][1]+avgArr[c][2]+avgArr[c][3]+avgArr[c][4];}
		System.out.println("Total 5 rounds average:");
		System.out.println("TurnaroundTime\t\tWaitingTime\t\tResponseTime\t\tThroughput");
		System.out.println(avgTmp[0]+" "+avgTmp[0]/count+"\t\t"+avgTmp[1]+" "+avgTmp[1]/count+"\t\t"+avgTmp[2]+" "+avgTmp[2]/count+"\t\t"+avgTmp[3]+" "+avgTmp[3]/count);	
		
	}
}
