#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>

char *greeting;            // shared data
void *start_officeH(void *parm);  // the thread
void create_threads();

#define num_students 5
int startT=1;
int count=0;
//int thread_ids[5];
int pwaitT;
int readyIndx;
int totalT=30;
int stop=0;

int seat1=0,seat2=0,seat3=0;


 enum states {EMPTY,ARRIVES, STARTS,WAITING,MEETING,WORKING_PERFORE};
 enum states pst,stu1st,stu2st,stu3st;//states of prof,student1,student2,student3,
struct student
{		int sid;
		int arrivalT;
		int visit_duration;
		char state[15];

} ;

struct student studentArr[num_students];
int arrivalT_compare(const void *t1, const void *t2)
{	
	   struct student *s1=(struct student *)t1;
	   struct student *s2=(struct student *)t2;
	
	if(s1->arrivalT < s2->arrivalT) return -1;
	else if(s1->arrivalT > s2->arrivalT) return 1;
	else return 0;
		
}

void generate_randomTime()
{	int max=60, min=0;
	int rNum,i,j;
	//initialized random seed
	srand(time(NULL));
	for( i=0;i<num_students;i++)
	{
		 
		studentArr[i].arrivalT=(rand()%(max-min+1))-min; //assume office hour as 1 hour(60 mins)
		studentArr[i].visit_duration=rand()%5+1;//generate random num between 1 and 5
						
	}
}
int check_waitingseats()
{	if(seat1==0) return 1;
	else if(seat2==0) return 2;
	else if(seat3==0)return 3;
	else return 0;
}
int main(int argc, char *argv[])
{
	
    
    
    int t=0,j=0;
	
	int studentid[num_students];
	generate_randomTime();	
	qsort(studentArr,5, sizeof(struct student),arrivalT_compare);//sorted student arrival time
	
		
	printf("student record: \n");
	for( j=0;j<num_students;j++)
	{
		printf("studentid: %d ",studentArr[j].sid);
		printf("arrivalTime: %d ",studentArr[j].arrivalT);
		printf("visit dur: %d ",studentArr[j].visit_duration);
		printf("\n");
						
	}
	 	create_threads();
	 	exit(0);
}
void create_threads()
{	int i=0,rc=0;
	printf("Office hour starts!\n");
	printf("seat:%d\n",check_waitingseats());
	 	
		
		
	  		
	for(i=0;i<num_students;i++)	
    {	studentArr[i].sid=i+1;
    	printf("\ncount:%d\n",i);
    		   	pthread_t tid;
    			pthread_attr_t attr;
    			pthread_attr_init(&attr);
    			int arrTime=rand()%5;
    			if(arrTime>1)
    			{	printf("Prof: is working on ParFore.");
    				sleep(arrTime);
    			}
    			rc=pthread_create(&tid, &attr, start_officeH, (void*)i);
    			if(rc)
        		{
            	 	printf("\n ERROR: return code from pthread_create is %d \n", rc);
             		exit(1);
        		}
   	 	
    			pthread_join(tid, NULL);
        		if(stop==1) exit(0);
        	
  	}
  printf("Parent: Terminating.\n");
    exit(0);

}

void *start_officeH(void * data)
{	
	
	int tmp=rand()%5+1;
	if(totalT>=tmp)
	{	totalT=totalT-tmp;
    	
    	printf("\t\t Student %d arrives!\n", data+1);
    	printf("meeting with prof now\n"); 
    	printf("*lasts for %d secs*\n",tmp);
   		sleep(tmp);
   		printf("**time left  %d secs\n",totalT);
    	printf("done!\n");
    	if(totalT==0) stop=1;
    	pthread_exit(0);
    }else
     {		printf("*****time left  %d sec\n",totalT);
    		printf("run out of time. come back again\n");
			printf("done!\n");
    		stop=1;
    		pthread_exit(0);
    		
     }
}
