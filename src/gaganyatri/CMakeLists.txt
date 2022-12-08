cmake_minimum_required(VERSION 3.22)
set(PROJECTNAME "amma")
find_package(CUDAToolkit REQUIRED)
project(${PROJECTNAME} CUDA)

# Set a default build type if not passed
# Set a default build type if not passed
set(default_build_type "Release")
if(NOT CMAKE_BUILD_TYPE AND NOT CMAKE_CONFIGURATION_TYPES)
    message(STATUS "Setting build type to '${default_build_type}' as none was specified.")
    set(CMAKE_BUILD_TYPE "${default_build_type}" CACHE STRING "Choose the type of build." FORCE)
    # Set the possible values of build type for cmake-gui
    set_property(CACHE CMAKE_BUILD_TYPE PROPERTY STRINGS
            "Debug" "Release")
endif()

if(CMAKE_CONFIGURATION_TYPES)
    set(CMAKE_CONFIGURATION_TYPES Debug Release)
    set(CMAKE_CONFIGURATION_TYPES "${CMAKE_CONFIGURATION_TYPES}" CACHE STRING
            "Reset the configurations to what we need"
            FORCE)
endif()


# Update the documentation string of CMAKE_BUILD_TYPE for GUIs
SET( CMAKE_BUILD_TYPE "${CMAKE_BUILD_TYPE}" CACHE STRING
        "Choose the type of build, options are: None Debug Release."
        FORCE )

include_directories("include/")
set(CMAKE_CUDA_STANDARD 23)


set(SRC
        main.cu
        )

set(ALL_SRC
        ${SRC_INCLUDE}
        ${SRC}
        )



add_executable(${PROJECTNAME} ${ALL_SRC})


set_target_properties(${PROJECTNAME} PROPERTIES
        CUDA_SEPARABLE_COMPILATION ON)
