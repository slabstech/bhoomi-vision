#include <iostream>
#include <bits/stdc++.h>
#include <chrono>
using namespace std;
using namespace std::chrono;

int cut_rod(int *pInt, int n);

void cut_rod_memoized(int *pInt, int n);

int cut_rod_memoized_aux(int *pInt, int n,int *result);

void cut_rod_recursion(int *pInt, int n);

void cut_rod_bottom_up(int *pInt, int n);

int main() {

    int n= 10;
    int p[10] = {1,5,8,9,10,17,17,20,24,30};

    cut_rod_recursion(p,n );

    cut_rod_memoized(p,n);

//    cut_rod_bottom_up(p,n);

    return 0;
}

void cut_rod_bottom_up(int *pInt, int n) {
    int *result = new int[n];
    result[0]=0;

    int q;
    for(int j=1; j<n ; j++){
        q = INT32_MIN;
        for (int i=1;i <j; i++){
            q = max( q , pInt[i] + result[j-i]);
        }
        result[j] = q;
    }

    int solution = result[n];
    cout << "Max Value :" <<  solution << endl;

}

void cut_rod_recursion(int *pInt, int n) {

    // Get starting timepoint
    auto start = high_resolution_clock::now();
    int solution = cut_rod(pInt, n);
    // Get ending timepoint
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop - start);

    cout << "Time taken by function: "
         << duration.count() << " microseconds" << endl;

    cout << "Max Value :" <<  solution << endl;

}

void cut_rod_memoized(int *pInt, int n) {

    int *result = new int[n];
    for(int i=0; i<n;i++){
        result[i] = INT32_MIN;
    }

    // Get starting timepoint
    auto start = high_resolution_clock::now();
    int solution = cut_rod_memoized_aux(pInt, n,result);
    // Get ending timepoint
    auto stop = high_resolution_clock::now();
    auto duration = duration_cast<microseconds>(stop - start);

    cout << "Time taken by function: "
         << duration.count() << " microseconds" << endl;

    cout << "Max Value :" <<  solution << endl;


}

int cut_rod_memoized_aux(int *pInt, int n, int *result) {
    if( result[n] >= 0)
        return result[n];
    int q;

    if(n==0)
        q=0;
    else
        q = INT32_MIN;

    for(int i=1;i<n;i++){
        q = max(q , pInt[i] + cut_rod_memoized_aux(pInt, n-i, result) );
    }
    result[n] = q;
    return q;
}

int cut_rod(int *pInt, int n) {

    if(n==0)
        return 0;
    if(n==1)
        return pInt[0];
    int q = INT32_MIN;

    for (int i=1;i<n;i++){
        q = std::max(q, pInt[i] + cut_rod(pInt, n-i));
    }

    return q;
}


