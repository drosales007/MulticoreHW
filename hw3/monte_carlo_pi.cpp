#include "omp.h"

double MonteCarloPi(int s) {
  // TODO: Implement your Monte Carlo Method
  // parameter s: number of points you randomly choose
  // return the value of pi
}
#include "stdafx.h"
#include "omp.h"
#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <ctime>
#include <chrono>
using namespace std;

double MonteCarloPi(int s) {

  int R = 50;
  int incount = 0;
  int numThreads = 8;
  int tid, i;
  double random_x, random_y, xsqplusysq;

  srand(time(NULL));
  omp_set_num_threads(numThreads);

  unsigned long start =
    chrono::duration_cast<std::chrono::milliseconds>
    (chrono::system_clock::now().time_since_epoch()).count();

    #pragma omp parallel for \
  shared(incount) private(i, random_x, random_y, xsqplusysq)
  for (i = 0; i < s; i++) {
    random_x = (rand() % (2 * R)) - R;
    random_y = (rand() % (2 * R)) - R;
    xsqplusysq = (random_x*random_x) + (random_y*random_y);
    if (xsqplusysq <= (R*R)) {
            #pragma omp atomic
      incount++;
    }
  }
  double result = (double)4 * ((double)incount / (double)s);
  unsigned long end =
    chrono::duration_cast<std::chrono::milliseconds>
    (chrono::system_clock::now().time_since_epoch()).count();
  end = end - start;
  //cout << "The time is: " << end;
  //cout << "Incount is: " << incount << " s is: " << s << " The result is: " << result << "\n";
  return result;
}


int main(int argc, const char *argv[]) {
  double result = MonteCarloPi(3000000);
  cout << "The result is: " << result;
  cin.get();
  return 0;

}
