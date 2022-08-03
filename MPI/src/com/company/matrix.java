package tt;
import mpi.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class matrix {
    public static void main(String[] args) throws FileNotFoundException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank(); // get rank of process
        int size = MPI.COMM_WORLD.Size();
        int[] matrix= new int[9];
        int [] result=new int [1];
        int [] sendbuff =new int [1];
        if(rank==0){
            try {
                Scanner scanner = new Scanner(new File("test.txt"));//read the file
                for(int i=0 ; i<3;i++) {
                    for(int j=0 ; j<3;j++) {
                        matrix[i+j*3]= scanner.nextInt();// get the input
                    }
                }
            }
            catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            MPI.COMM_WORLD.Bcast(matrix, 0, 9, MPI.INT , 0); //send matrix to each process
        }
        else{
            MPI.COMM_WORLD.Bcast(matrix, 0, 9, MPI.INT , 0);
            int temp1=((matrix[1+((rank)%3)*3])*(matrix[2+((rank+1)%3)*3]) );
            int temp2=((matrix[1+((rank+1)%3)*3])*(matrix[2+((rank)%3)*3]));
            sendbuff[0]=(matrix[3*(rank-1)]*(temp1 - temp2 )); // determine of each sub matrix
        }
        MPI.COMM_WORLD.Reduce(sendbuff,0, result,0,1, MPI.INT, MPI.SUM, 0); // sum of each determine
        if(rank==0){
            System.out.println("Determinant of matrix = "+result[0]); //Determinant of matrix
            if(result[0]==0) // Check if singular or invertible matrix
                System.out.println("A singular matrix");
            else
                System.out.println("An invertible matrix");
        }
        MPI.Finalize();
    }
}