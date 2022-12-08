
#include "cuda_runtime.h"
#include "device_launch_parameters.h"

#include <stdio.h>
#include <iostream>
#include "StopWatch.cpp"
#include <sstream>
#include <string>
#include <fstream>
#include <vector>
#include <math.h>

#include "rapidjson/reader.h"
using namespace rapidjson;


using namespace std;

cudaError_t addWithCuda(int *c, const int *a, const int *b, unsigned int size);

__global__ void addKernel(int *c, const int *a, const int *b)
{
    int i = threadIdx.x;
    c[i] = a[i] + b[i];
}


__global__ void square(float *d_out, float *d_in){
    int idx = threadIdx.x;
    float f = d_in[idx];
    d_out[idx] = f * f ;
}
__global__ void cube(float *d_out, float *d_in){
    int idx = threadIdx.x;
    float f = d_in[idx];
    d_out[idx] = f * f * f ;
}

struct Point{
    float x,y;
};

int task_square(){
    const int ARRAY_SIZE = 64;
    const int ARRAY_BYTES = ARRAY_SIZE * sizeof(float);

    float h_in[ARRAY_SIZE];
    for(int i=0;i < ARRAY_SIZE;i++){
        h_in[i] = float(i);
    }

    float h_out[ARRAY_SIZE];

    float * d_in;
    float * d_out;


    cout << "Running square" << endl;
    cudaMalloc((void **) &d_in, ARRAY_BYTES);
    cudaMalloc((void **) &d_out, ARRAY_BYTES);


    cudaMemcpy(d_in, h_in, ARRAY_BYTES,cudaMemcpyHostToDevice );

    square<<<1, ARRAY_SIZE>>>(d_out, d_in);

    cudaMemcpy(h_out, d_out, ARRAY_BYTES,cudaMemcpyDeviceToHost );
    for(int i=0;i < ARRAY_SIZE;i++){
        cout << h_out[i] ;
        cout << (((i%4)!=3) ? "\t" : "\n");
    }

// For cube
    cout << "\n" ;
    cout << "Running Cube" << endl;
    for(int i=0;i < ARRAY_SIZE;i++){
        h_in[i] = float(i);
    }
    cudaMemcpy(d_in, h_in, ARRAY_BYTES,cudaMemcpyHostToDevice );

    cube<<<1, ARRAY_SIZE>>>(d_out, d_in);
    cudaMemcpy(h_out, d_out, ARRAY_BYTES,cudaMemcpyDeviceToHost );
    for(int i=0;i < ARRAY_SIZE;i++){
        cout << h_out[i] ;
        cout << (((i%4)!=3) ? "\t" : "\n");
    }




    cudaFree(d_in);
    cudaFree(d_out);

    return 0;
}

int cuda_sample_code(){
    const int arraySize = 5;
    const int a[arraySize] = { 1, 2, 3, 4, 5 };
    const int b[arraySize] = { 10, 20, 30, 40, 50 };
    int c[arraySize] = { 0 };

    // Add vectors in parallel.
    cudaError_t cudaStatus = addWithCuda(c, a, b, arraySize);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "addWithCuda failed!");
        return 1;
    }

    printf("{1,2,3,4,5} + {10,20,30,40,50} = {%d,%d,%d,%d,%d}\n",
           c[0], c[1], c[2], c[3], c[4]);

    // cudaDeviceReset must be called before exiting in order for profiling and
    // tracing tools such as Nsight and Visual Profiler to show complete traces.
    cudaStatus = cudaDeviceReset();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaDeviceReset failed!");
        return 1;
    }

    return 0;
}

// Helper function for using CUDA to add vectors in parallel.
cudaError_t addWithCuda(int *c, const int *a, const int *b, unsigned int size)
{
    int *dev_a = 0;
    int *dev_b = 0;
    int *dev_c = 0;
    cudaError_t cudaStatus;

    // Choose which GPU to run on, change this on a multi-GPU system.
    cudaStatus = cudaSetDevice(0);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaSetDevice failed!  Do you have a CUDA-capable GPU installed?");
        goto Error;
    }

    // Allocate GPU buffers for three vectors (two input, one output)    .
    cudaStatus = cudaMalloc((void**)&dev_c, size * sizeof(int));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed!");
        goto Error;
    }

    cudaStatus = cudaMalloc((void**)&dev_a, size * sizeof(int));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed!");
        goto Error;
    }

    cudaStatus = cudaMalloc((void**)&dev_b, size * sizeof(int));
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMalloc failed!");
        goto Error;
    }

    // Copy input vectors from host memory to GPU buffers.
    cudaStatus = cudaMemcpy(dev_a, a, size * sizeof(int), cudaMemcpyHostToDevice);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy failed!");
        goto Error;
    }

    cudaStatus = cudaMemcpy(dev_b, b, size * sizeof(int), cudaMemcpyHostToDevice);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy failed!");
        goto Error;
    }

    // Launch a kernel on the GPU with one thread for each element.
    addKernel<<<1, size>>>(dev_c, dev_a, dev_b);

    // Check for any errors launching the kernel
    cudaStatus = cudaGetLastError();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "addKernel launch failed: %s\n", cudaGetErrorString(cudaStatus));
        goto Error;
    }

    // cudaDeviceSynchronize waits for the kernel to finish, and returns
    // any errors encountered during the launch.
    cudaStatus = cudaDeviceSynchronize();
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaDeviceSynchronize returned error code %d after launching addKernel!\n", cudaStatus);
        goto Error;
    }

    // Copy output vector from GPU buffer to host memory.
    cudaStatus = cudaMemcpy(c, dev_c, size * sizeof(int), cudaMemcpyDeviceToHost);
    if (cudaStatus != cudaSuccess) {
        fprintf(stderr, "cudaMemcpy failed!");
        goto Error;
    }

    Error:
    cudaFree(dev_c);
    cudaFree(dev_a);
    cudaFree(dev_b);

    return cudaStatus;
}

int task_polygon(int argc,char *argv){
    cout << "Running Task4 to find shortest path in Polygon Maps" << endl;

    if(argc !=1)
    {
        cout << "Input file not found" << endl ;
        //exit(1);
        return 0;
    }

    string input_file_name = argv;

    cout << "Processing input file : " << input_file_name << endl;
    std::ifstream infile(input_file_name);
    string line;

    std::getline(infile, line);
    std::istringstream iss(line);


    Point start;
    iss >> start.x ;
    iss >> start.y ;

    std::getline(infile, line);
    std::istringstream isse(line);


    Point end;
    isse >> end.x ;
    isse >> end.y ;


    vector<vector<Point>> polygons;

    std::getline(infile, line); // empty line


    vector<Point> temp_point;
    while (std::getline(infile, line))
    {
        std::istringstream iss(line);

        if(line == "")
        {
            polygons.push_back(temp_point) ;
            temp_point.clear();
        }
        else
        {
            Point temp;
            iss >> temp.x ;
            iss >> temp.y ;

            temp_point.push_back(temp);

        }
    }

    cout << "total number of polygons : " << polygons.size() << endl;

    double distance = sqrt( pow((end.x - start.x),2) +  pow(( end.y - start.y),2)) ;

    cout << "Shortest possible distance(Ignoring Obstacles): " << distance << endl ;


    for(int i=0;i< polygons.size();i++){
        temp_point = polygons.at(i);
        for(int j=0;j< temp_point.size();j++){
            Point temp = temp_point.at(j);

            //cout << temp.x << " " << temp.y << endl;
        }

        //cout << "\n" ;
    }


    ofstream vis_graph_file;

    remove( "points.txt" );
    vis_graph_file.open ("points.txt",  ios::out | ios::app);


    vector<Point> vis_graph_points;


    for(int i=0;i< polygons.size();i++){
        temp_point = polygons.at(i);  // Each polygon
        for(int j=0;j< temp_point.size();j++){
            Point temp = temp_point.at(j);

            vis_graph_points.push_back(start);
            vis_graph_points.push_back(temp);

            /*

            for(int k=0 ; k < temp_point.size() ; k++ ){
                Point next_point = temp_point.at(k);
                vis_graph_points.push_back(next_point);

                //vis_graph_file << "\n" ;
            }
*/
            //vis_graph_file << "\n" ;
        }

        //vis_graph_file << "\n" ;
    }



    for(int i=0;i< polygons.size();i++){
        temp_point = polygons.at(i);  // Each polygon
        for(int j=0;j< temp_point.size();j++){
            Point temp = temp_point.at(j);

            vis_graph_points.push_back(temp);
            vis_graph_points.push_back(end);


/*
						for(int k=0 ; k < temp_point.size() ; k++ ){
							Point next_point = temp_point.at(k);
							vis_graph_points.push_back(next_point);

							//vis_graph_file << "\n" ;
						}
*/
            //vis_graph_file << "\n" ;
        }

        //vis_graph_file << "\n" ;
    }


    for(int i=0; i< vis_graph_points.size(); i++){
        Point vis_points = vis_graph_points.at(i);

        vis_graph_file << vis_points.x << " " << vis_points.y << endl;
    }



    vis_graph_file.close();


//	double time = stopWatch.elapsedTime();

    //cout << "Total Execution Time : " << time << endl;




    return 0;
}



struct MyHandler {
    bool Null() { cout << "Null()" << endl; return true; }
    bool Bool(bool b) { cout << "Bool(" << boolalpha << b << ")" << endl; return true; }
    bool Int(int i) { cout << "Int(" << i << ")" << endl; return true; }
    bool Uint(unsigned u) { cout << "Uint(" << u << ")" << endl; return true; }
    bool Int64(int64_t i) { cout << "Int64(" << i << ")" << endl; return true; }
    bool Uint64(uint64_t u) { cout << "Uint64(" << u << ")" << endl; return true; }
    bool Double(double d) { cout << "Double(" << d << ")" << endl; return true; }
    bool RawNumber(const char* str, SizeType length, bool copy) {
        cout << "Number(" << str << ", " << length << ", " << boolalpha << copy << ")" << endl;
        return true;
    }
    bool String(const char* str, SizeType length, bool copy) {
        cout << "String(" << str << ", " << length << ", " << boolalpha << copy << ")" << endl;
        return true;
    }
    bool StartObject() { cout << "StartObject()" << endl; return true; }
    bool Key(const char* str, SizeType length, bool copy) {
        cout << "Key(" << str << ", " << length << ", " << boolalpha << copy << ")" << endl;
        return true;
    }
    bool EndObject(SizeType memberCount) { cout << "EndObject(" << memberCount << ")" << endl; return true; }
    bool StartArray() { cout << "StartArray()" << endl; return true; }
    bool EndArray(SizeType elementCount) { cout << "EndArray(" << elementCount << ")" << endl; return true; }
};

int read_json_data(string file_path)
{

    string input_file_name = file_path;

    cout << "Processing input file : " << input_file_name << endl;
    std::ifstream infile(input_file_name);
    string line;

    if(infile){
        cout << "file not found : "<< file_path << endl;
    }
    std::getline(infile, line);
    std::stringstream iss(line);

    cout << line << iss.str() <<endl;
    const char json[] = " { \"hello\" : \"world\", \"t\" : true , \"f\" : false, \"n\": null, \"i\":123, \"pi\": 3.1416, \"a\":[1, 2, 3, 4] } ";

    MyHandler handler;
    Reader reader;
    StringStream ss(json);
    reader.Parse(ss, handler);

   // StringStream data(iss);
    StringStream ss_data(iss.str().c_str());
    reader.Parse(ss_data, handler);


    return 0;
}
int main()
{
    StopWatch* stopWatch= new StopWatch();
    stopWatch->start();

    //int task_square_status = task_square();

    //int cudaStatus = cuda_sample_code();

    //char* filePath="resources/data/polygons_300.txt";
    //int task_polygon_status = task_polygon(1,filePath);

    string json_file_path="resources/data.json";
    int data_read_status = read_json_data(json_file_path);
    double time = stopWatch->elapsedTime();
    cout << "Total Time" << time;
    return 0;

}
