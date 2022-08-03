package com.company;

import java.util.Scanner;

import static java.lang.Math.ceil;

class Multiply implements Runnable {
    private int[][] c, A, B;
    private int n, m, nA, mA, nB, mB;

    public Multiply(int[][] A, int[][] B, int nA, int mA, int nB, int mB) {
        this.A = A;
        this.B = B;
        this.nA = nA;
        this.mA = mA;
        this.nB = nB;
        this.mB = mB;
    }

    public void run() {
        if (mA != nB) {
            System.out.println("-Thread " + Thread.currentThread().getName() + " can't multiply A * B");
            return;
        }
        if (Integer.parseInt(Thread.currentThread().getName()) == 1) {
            n=nA;
            m= (int) ceil((((float) mB) / 2));
            c = new int[n][m];
            for (int i = 0; i < nA; i++) {
                for (int j = 0; j < m; j++) {
                    c[i][j] = 0;
                    for (int k = 0; k < mA; k++) {
                        c[i][j] += A[i][k] * B[k][j];
                    }
                }
            }
        } else if (Integer.parseInt(Thread.currentThread().getName()) == 2) {
            n = nA;
            m = (mB / 2);
            c = new int[n][m];
            int temp = (int) ceil((((float) mB) / 2));
            for (int i = 0; i < nA; i++) {
                for (int j = temp; j < mB; j++) {
                    c[i][j - temp] = 0;
                    for (int k = 0; k < mA; k++) {
                        c[i][j - temp] += A[i][k] * B[k][j];
                    }
                }
            }
        }
    }
    public void print()
    {
        if(c==null) {
            System.out.println("NULL");
            return;
        }
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<m;j++)
            {
                System.out.print( c[i][j] +" ");
            }
            System.out.println();
        }
    }
    public static int[][] com(Multiply m1,Multiply m2)
    {
        if(m1.c==null||m2.c==null)
            return null;
        int[][] temp=new int [m1.nA][m2.mB];
        int split = (int) ceil((((float) m1.mB) / 2));
        for(int i=0;i<m1.n;i++)
        {
            for(int j=0;j<m1.m;j++) {
                temp[i][j] = m1.c[i][j];
            }
        }
        for(int i=0;i<m1.n;i++)
        {
            for(int j=0;j<m2.m;j++) {
                temp[i][split+j] = m2.c[i][j];
            }
        }
        return temp;
    }
}
public class Main {
    public static void main(String args[]) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        int nA, mA, nB, mB;
        int[][] matrixA, matrixB, matrixC;
        System.out.println("********** Enter rows and col of Matrix A and then the value ************");
        nA = sc.nextInt();
        mA = sc.nextInt();
        matrixA = new int[nA][mA];
        for (int i = 0; i < nA; i++) {
            for (int j = 0; j < mA; j++) {
                matrixA[i][j] = sc.nextInt();
            }
        }
        System.out.println("********** Enter rows and col of Matrix B and then the value ************");
        nB = sc.nextInt();
        mB = sc.nextInt();
        matrixB = new int[nB][mB];
        for (int i = 0; i < nB; i++) {
            for (int j = 0; j < mB; j++) {
                matrixB[i][j] = sc.nextInt();
            }
        }
        Multiply mat1 = new Multiply(matrixA, matrixB, nA, mA, nB, mB);
        Multiply mat2 = new Multiply(matrixA, matrixB, nA, mA, nB, mB);
        Thread t1 = new Thread(mat1);
        Thread t2 = new Thread(mat2);
        t1.setName("1");
        t2.setName("2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("********** Thread 1 **********" );
        mat1.print();
        System.out.println("********** Thread 2 **********");
        mat2.print();
        System.out.println("********** result **********");
        matrixC= Multiply.com(mat1,mat2);
        if(matrixC==null) {
            System.out.println("NULL");
            return;
        }
        for(int i=0;i<nA;i++)
        {
            for(int j=0;j<mB;j++)
            {
                System.out.print( matrixC[i][j] +" ");
            }
            System.out.println();
        }
    }
}