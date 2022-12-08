## Navigation


* TODO
  * Provide sample images of plants for Vision module
  * Run simple navigation algo using DJI-SDK and stream the images to machine for storage.

* Use Dockerfile to test the application
  * docker build -t slabstech/bhoomi-garuda-navigation .
  * docker scan  slabstech/bhoomi-garuda-navigation
- Use GithubAction to validate against tests for all scenarios - https://github.com/slabstech/action-cuda-compiler

* Tech Stack
  * C++
  * CUDA

* Controller for Habitat

* Build Steps
  * cmake -S . -B build
  * cmake --build build 
  * cd build && ctest
  

* TODO
  * Private and public - https://github.com/bast/gtest-demo/blob/master/src/CMakeLists.txt

* Reference
  * fprime - Rpi Test - https://github.com/sachinsshetty/fprime/blob/master/.github/workflows/build-test-rpi.yml 
  * JetBrains CLion - https://www.jetbrains.com/help/clion/unit-testing-tutorial.html
  * Test suite - Boost - https://www.boost.org/doc/libs/
  * google test - https://google.github.io/googletest/
  * cmake - https://cmake.org/cmake/help/latest/guide/tutorial/index.html
  * abseil - https://abseil.io/about/philosophy
  * test-example - https://github.com/snikulov/google-test-examples
  * gtest-parallel - https://github.com/google/gtest-parallel
  * cmake download - https://cmake.org/download/
  * gtest-demo - https://github.com/bast/gtest-demo

