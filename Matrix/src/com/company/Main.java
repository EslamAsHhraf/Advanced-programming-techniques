package com.company;
interface Addable{
    Object Add(Object a);

}
class Matrix implements Addable{
    protected int[][] Numbers;
    protected int n,m;
    public Matrix(int row,int col){
        n=row;
        m=col;
        Numbers=new int [n][m];
    }
    @Override
    public Matrix Add(Object a){
        if(a instanceof Matrix) {
            Matrix result=(Matrix) a;
            if (result.n != this.n && result.m != this.m)
                return null;
            Matrix temp = new Matrix(n, m);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    temp.Numbers[i][j] = this.Numbers[i][j] + result.Numbers[i][j];
                }
            }
            return result;
        }
        return null;
    }
    public boolean SetNumbers(int arr[]){

        if(arr.length!=n*m)
             return false;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                Numbers[i][j]=arr[i*m+j];
            }
        }
        return true;
    }
    public void Print()
    {
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                System.out.print( Numbers[i][j] +" ");
            }
            System.out.println();
        }
    }
    public void Transpose()
    {
        int [][]temp=Numbers;
        Numbers=new int [m][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
               Numbers[j][i] =temp[i][j];
            }
        }
        int ball=n;
        n=m;
        m=ball;
    }
}
class IdentityMatrix extends Matrix{
    public IdentityMatrix(int row){
        super(row,row);
    }

    @Override
    public boolean SetNumbers(int[] arr) {
        if(arr.length!=n*n)
            return false;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(i==j && arr[i*n+j]!=1)
                    return false;
                if(i!=j && arr[i*n+j]!=0)
                    return false;
            }
        }
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                Numbers[i][j]=arr[i*m+j];
            }
        }
        return true;
    }
    @Override
    public void Transpose()
    {
    }
}
public class Main {

    public static void main(String[] args) {
	// write your code here

       int arr[]={1,2,3,4,5,6,7,8,9};
        int arr2[]={11,12,13,14,15,16,17,18,19};
        Matrix m=new Matrix(3,3);
        System.out.println ("Set Value: "+  m.SetNumbers(arr));
        m.Print();
        System.out.println("*************** Transpose ************************");
        m.Transpose();
        m.Print();
        System.out.println("***************************************");
        Matrix m2=new Matrix(3,3);
        System.out.println ("Set Value: "+ m2.SetNumbers(arr2));
        m2.Print();
        System.out.println("*************** Transpose ************************");
        m2.Transpose();
        m2.Print();
        System.out.println("******************* ADD m + m2 ********************");
        Matrix z=  m.Add(m2);
        z.Print();
        System.out.println("*************** Transpose ************************");
        z.Transpose();
        z.Print();
        System.out.println("******************** Identity Matrix *******************");

        int arr3[]={1,0,0,0,1,0,0,0,1};
        int arr4[]={1,0,0,1};
        IdentityMatrix i=new IdentityMatrix(3) ;
        System.out.println ("Set Value: "+  i.SetNumbers(arr3));
        i.Print();
        System.out.println("*************** Transpose ************************");
        i.Transpose();
        i.Print();
        System.out.println("***************************************");
        IdentityMatrix i2=new IdentityMatrix(2);
        System.out.println ("Set Value: "+ i2.SetNumbers(arr4));
        i2.Print();
        System.out.println("*************** Transpose ************************");
        i2.Transpose();
        i2.Print();
        System.out.println("********************** ADD i + m *****************");

        Matrix z2=m.Add(i);
        z2.Print();
        System.out.println("*************** Transpose ************************");
        z2.Transpose();
        z2.Print();
        System.out.println("**********************End *****************");

    }
}
