/*
Author: Karan Kotabagi
Approch Used : Bottom Up Approch 
 */
package jobmax;

import java.util.*;
import java.io.*;

//Class job holds the details of the particular job and when we have to access it we need to
//create the object and then reference the object
class job {

    Integer sttime;
    Integer entime;
    Integer value;

    //Function to set the values 
    public void setvalues(Integer sttime, Integer entime, Integer value) {
        //this.jobname = jobname;
        this.sttime = sttime;
        this.entime = entime;
        this.value = value;
    }
}

public class job_max {

    public static void main(String[] args) throws IOException {
        // TODO code application logic here

//Read the input file with the correct path as specified below. 
        Scanner scanner = new Scanner(new File("C:/Users/Karan/Documents/NetBeansProjects/jobmax/src/jobmax/input2.txt"));
        ArrayList<Integer> arl = new ArrayList<Integer>();
        int read = 0;
        int i = 0;
        while (scanner.hasNextInt()) {
            read = scanner.nextInt();
            arl.add(read);
            // System.out.println(read);
        }
//No of the objects as the no of the lines so as to store the job detail in the each job object
        job[] j = new job[arl.size() / 3];
//j[0].setvalues(0,1,3);
        int k = 0;
        int m = 0;
//Set the start time, end time and the values for each of the object.
        while (m < arl.size()) {

            Integer stime;
            stime = arl.get(m);
            //System.out.println(stime);
            Integer etime;
            etime = arl.get(m + 1);
            Integer val;
            val = arl.get(m + 2);
            j[k] = new job();
            j[k].setvalues(stime, etime, val);
//j[k].entime = stime;
            m = m + 3;
            k++;
        }

        //keep a counter and keep the value iterated untill all the lines in the files are exhausted.
        //Sort the arrays based on the endtime field and store the array of the object jobs accordingly.
        Arrays.sort(j, new Comparator<job>() {
            @Override
            public int compare(job o1, job o2) {
                return o1.entime.compareTo(o2.entime);
            }
        });
        //This is solved using the Bottom up approach and for that we need to build the DP table.
        //Now we need to contruct the DP table, I have taken 1-d array T as the  table
        //Let us declare the 1-d array with the size that is eqaul to the no of jobs, i.e.. arl.size()/3
        int T[] = new int[arl.size() / 3];

        //Assign the initial weight values to the array Table T for the each job j
        //The values for the each job are stored in the table at first.
        for (int p = 0; p < arl.size() / 3; p++) {
            T[p] = j[p].value;
        }
        int r = 0;
        //Formuation of the DP table 
        //The DP table is based on the below points
        //run the outer lop as many times as the no of jobs, we can calculate using the no of jobs using ar1.size()/3 or else
        //simply j.length
        for (r = 1; r < arl.size() / 3; r++) {
            T[r] = Math.max(j[r].value, T[r - 1]);
            // run the inner loop only till it is less than r (outer loop counter variable)
            for (int t = 0; t < r; t++) {
                //Check if the jobs overlap by comparing if the start time of the previous job is 
                //greater than the next job
                if (j[r].sttime >= j[t].entime) {
                    //Store the maximum value if the previous job + Present Job value becomes maximum
                    //in the T[r]
                    //In simple terms T[r] = job[r].value + T[t] if the sum is greater than the previous value.
                    int a = T[r];
                    int b = j[r].value + T[t];
                    //Simplified it more to get the better understanding as a and b 
                    if (a > b) {
                        T[r] = a;

                    } else {
                        T[r] = b;
                    }

                }
            }

        }
        //Find and  print the maximum value in the table.
        int max = Integer.MIN_VALUE;
        for (int weight : T) {
            if (max < weight) {
                max = weight;
            }
        }
       
        System.out.println(max);
    }

}
