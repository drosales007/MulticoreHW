#include "stdafx.h"
#include "omp.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <ctime>
#include <chrono>
using namespace std;

// Want to reuse code to init matrix
void initMatrix(double** matrix, int rm, int cm, bool zerod, ifstream &filein) {
	for (int i = 0; i < rm; i++) {
		matrix[i] = new double[cm];
		for (int j = 0; j < cm; j++) {
			if (zerod) {
				matrix[i][j] = 0;
			}
			else {
				filein >> matrix[i][j];
			}
			//cout << "Row: " << i << " col: " << j << " is read: " << matrix[i][j] << "\n";
		}
	}
}

bool MatrixMult(int rowA, int colA, double** A, int rowB, int colB, double** B,
	double** C, int T) {
	// TODO: Implement your parallel multiplication for two matrices of doubles
	// by using OpenMP
	// parameter rowA: indicates the number of rows of the matrix A
	// parameter colA: indicates the number of columns of the matrix A
	// parameter A: indicates the matrix A
	// parameter rowB: indicates the number of rows of the matrix B
	// parameter colB: indicates the number of columns of the matrix B
	// parameter B: indicates the matrix B
	// parameter C: indicates the matrix C, which is the results of A x B
	// parameter T: indicates the number of threads
	// return true if A and B can be multiplied; otherwise, return false 
	if (rowA != colB) {
		//cout << "The matix cannot be mult. \nRow A = " << rowA << " and Col B = " << colB << "\n";
		//cin.get();
		return false;
	}
	else {
		//cout << "The matix can be mult. \nRow A = " << rowA << " and Col B = " << colB << " \n";

		omp_set_num_threads(T);
		double parts = 100 / T;
		int chunk = parts;

		double temp = 0.0;
		unsigned long long Int64 = 0;
		int tid, nthreads, row, col, inner;
		unsigned long start =
			chrono::duration_cast<std::chrono::milliseconds>
			(chrono::system_clock::now().time_since_epoch()).count();
		bool done = false;
		ofstream fout("C:\\MCCHW\\hw3\\result.txt");


#pragma omp parallel for \
	shared(A, B, C, chunk) private(tid, row, col, inner) \
	schedule(static, chunk) \
	reduction(+:temp)
		for (row = 0; row < rowA; row++) {
			for (col = 0; col < colB; col++) {
				// Multiply the row of A by the column of B to get the row, column of product.
				temp = 0.0;
				for (inner = 0; inner < colA; inner++) {
					//int ID = omp_get_thread_num();
					//cout << "The thread id: " << ID << "\n";
					//cout << "Multiplying: " << "A[" << row << "][" << inner << "]" << " by " << "B[" << inner << "][" << col << "] toget C[" << row << "][" << col << "]" "\n";
					temp += A[row][inner] * B[inner][col];
				}
				C[row][col] = temp;


				//cout << C[row][col] << "  ";
			}
			//cout << "\n";
		}

		//cin.get();
		unsigned long end =
			chrono::duration_cast<std::chrono::milliseconds>
			(chrono::system_clock::now().time_since_epoch()).count();
		unsigned long timered = end - start;
		fout << "The time is: " << timered << "\n";
		fout << flush;
		fout.close();
		return true;
	}
}


int main(int argc, const char *argv[]) {
	int ROWA;
	int COLA;
	int ROWB;
	int COLB;
	int T;

	if (argc < 3) {
		cerr << "Please enter files and number of threads!" << endl;
		cerr << "Example: ./multiply matrix1.txt matrix2.txt 5" << endl;
		cin.get();
		exit(1);
	}

	ifstream inf(argv[1]);
	//cout << "The first file is: " << argv[1] << "\n";
	ifstream inf2(argv[2]);
	//cout << "The second file is: " << argv[2] << "\n";
	//cout << "The threads are: " << argv[3] << "\n";
	stringstream str;
	str << argv[3];
	str >> T;


	// If we couldn't open the output file stream for reading
	if (!inf || !inf2)
	{	// Print an error and exit
		cerr << "Uh oh, one of the files could not be opened for reading!" << endl;
		cin.get();
		exit(1);
	}
	// Read in rows and columns for the matrix.
	inf >> ROWA >> COLA;
	inf2 >> ROWB >> COLB;
	double** A;
	double** B;
	double** C;

	A = new double*[ROWA];
	B = new double*[ROWB];
	C = new double*[ROWA];
	initMatrix(A, ROWA, COLA, false, inf);
	initMatrix(B, ROWB, COLB, false, inf2);
	initMatrix(C, ROWA, COLB, true, inf2);


	// TODO: Initialize the necessary data
	if (MatrixMult(ROWA, COLA, A, ROWB, COLB, B, C, T)) {
		//ofstream fout("C:\\MCCHW\\hw3\\result.txt", std::ios::app);
		for (int row = 0; row < ROWA; row++) {
			for (int col = 0; col < COLB; col++) {
				cout << C[row][col] << " ";
				//fout << C[row][col] << " ";
			}
			cout << "\n";
			//fout << "\n";
		}
		//fout << flush;
		//fout.close();
		//cout << "The matrix can be multiplied" << endl;
		cin.get();
	}
	else {
		cout << "the colA != rowB MatrixMult return false" << endl;
		cin.get();
	}
	return 0;
}
