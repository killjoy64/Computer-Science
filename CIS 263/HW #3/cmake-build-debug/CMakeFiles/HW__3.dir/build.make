# CMAKE generated file: DO NOT EDIT!
# Generated by "MinGW Makefiles" Generator, CMake Version 3.8

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

SHELL = cmd.exe

# The CMake executable.
CMAKE_COMMAND = "C:\Program Files\JetBrains\CLion 2017.2.2\bin\cmake\bin\cmake.exe"

# The command to remove a file.
RM = "C:\Program Files\JetBrains\CLion 2017.2.2\bin\cmake\bin\cmake.exe" -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3"

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\cmake-build-debug"

# Include any dependencies generated for this target.
include CMakeFiles/HW__3.dir/depend.make

# Include the progress variables for this target.
include CMakeFiles/HW__3.dir/progress.make

# Include the compile flags for this target's objects.
include CMakeFiles/HW__3.dir/flags.make

CMakeFiles/HW__3.dir/main.cpp.obj: CMakeFiles/HW__3.dir/flags.make
CMakeFiles/HW__3.dir/main.cpp.obj: ../main.cpp
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir="C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object CMakeFiles/HW__3.dir/main.cpp.obj"
	C:\MinGW\mingw32\bin\g++.exe  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles\HW__3.dir\main.cpp.obj -c "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\main.cpp"

CMakeFiles/HW__3.dir/main.cpp.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/HW__3.dir/main.cpp.i"
	C:\MinGW\mingw32\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\main.cpp" > CMakeFiles\HW__3.dir\main.cpp.i

CMakeFiles/HW__3.dir/main.cpp.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/HW__3.dir/main.cpp.s"
	C:\MinGW\mingw32\bin\g++.exe $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\main.cpp" -o CMakeFiles\HW__3.dir\main.cpp.s

CMakeFiles/HW__3.dir/main.cpp.obj.requires:

.PHONY : CMakeFiles/HW__3.dir/main.cpp.obj.requires

CMakeFiles/HW__3.dir/main.cpp.obj.provides: CMakeFiles/HW__3.dir/main.cpp.obj.requires
	$(MAKE) -f CMakeFiles\HW__3.dir\build.make CMakeFiles/HW__3.dir/main.cpp.obj.provides.build
.PHONY : CMakeFiles/HW__3.dir/main.cpp.obj.provides

CMakeFiles/HW__3.dir/main.cpp.obj.provides.build: CMakeFiles/HW__3.dir/main.cpp.obj


# Object files for target HW__3
HW__3_OBJECTS = \
"CMakeFiles/HW__3.dir/main.cpp.obj"

# External object files for target HW__3
HW__3_EXTERNAL_OBJECTS =

HW__3.exe: CMakeFiles/HW__3.dir/main.cpp.obj
HW__3.exe: CMakeFiles/HW__3.dir/build.make
HW__3.exe: CMakeFiles/HW__3.dir/linklibs.rsp
HW__3.exe: CMakeFiles/HW__3.dir/objects1.rsp
HW__3.exe: CMakeFiles/HW__3.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir="C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\cmake-build-debug\CMakeFiles" --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable HW__3.exe"
	$(CMAKE_COMMAND) -E cmake_link_script CMakeFiles\HW__3.dir\link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
CMakeFiles/HW__3.dir/build: HW__3.exe

.PHONY : CMakeFiles/HW__3.dir/build

CMakeFiles/HW__3.dir/requires: CMakeFiles/HW__3.dir/main.cpp.obj.requires

.PHONY : CMakeFiles/HW__3.dir/requires

CMakeFiles/HW__3.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles\HW__3.dir\cmake_clean.cmake
.PHONY : CMakeFiles/HW__3.dir/clean

CMakeFiles/HW__3.dir/depend:
	$(CMAKE_COMMAND) -E cmake_depends "MinGW Makefiles" "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3" "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3" "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\cmake-build-debug" "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\cmake-build-debug" "C:\Users\Kyle Flynn\Documents\GitHub\Computer-Science\CIS 263\HW #3\cmake-build-debug\CMakeFiles\HW__3.dir\DependInfo.cmake" --color=$(COLOR)
.PHONY : CMakeFiles/HW__3.dir/depend
