cmake_minimum_required (VERSION 3.5)

project(Factor)


set (CMAKE_CXX_FLAGS "${MAKE_CXX_FLAGS} -Wall -Werror -std=c++14 -lgmpxx -lgmp")
set (source_dir "${PROJECT_SOURCE_DIR}/src/")

file (GLOB source_files "${source_dir}/*.cpp")

add_executable (Factor ${source_files})
