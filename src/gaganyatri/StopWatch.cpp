#include <time.h>

class StopWatch
{
    struct timespec lastStartTime;

public:
    StopWatch();
    ~StopWatch(){}
    void start();
    double elapsedTime();
};

StopWatch::StopWatch()
{
    clock_gettime(CLOCK_MONOTONIC, &lastStartTime);
}

// Resets the elapsed time to 0.
void StopWatch::start()
{
    clock_gettime(CLOCK_MONOTONIC, &lastStartTime);
}

// Returns a time stamp expressed in seconds since the last start.
double StopWatch::elapsedTime()
{
    struct timespec now;
    clock_gettime(CLOCK_MONOTONIC, &now);
    long diffInSecs = now.tv_sec - lastStartTime.tv_sec;
    long diffInNanos = now.tv_nsec - lastStartTime.tv_nsec;
    return (double)diffInSecs + (double)diffInNanos/1000000000;
}
