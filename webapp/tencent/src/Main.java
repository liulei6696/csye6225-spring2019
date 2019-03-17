
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Main game = new Main();

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[]shoot = new int[n];
        int m = in.nextInt();
        int[]color = new int[m];

        for(int i=0;i<n;i++)
            shoot[i]=in.nextInt();

       int count = m;
       int shootCount=0;
       int res = n+1;
       for(int i=0;i<n;i++){
           if(shoot[i]==0){
               shootCount=0;
               color=new int[m];
               continue;
           }
           int start = i;
           shootCount++;
           if(color[shoot[i]-1]==0){
               color[shoot[i]-1]++;
               count--;
           }
           if(count==0){
               res = Math.min(res,shootCount);

               color[shoot[start]-1]--;
               if(color[shoot[start]-1]==0)
                   count--;

           }
       }
       if(res<=n)
           System.out.println(res);
       else
           System.out.println(-1);
    }

}