# GraphGraph

## About the Library

The GraphGraph library comes with a variety of methods to determine whether a graph contains an Euler Cycle or Euler Trail. Graphs are represented as adjacency matricies. Multigraphs are not supported.

A demo program is included with the library. It makes use of 5 primary methods available in the library. These are for determining whether a graph is connected, whether a graph contains an Euler Cycle, and whether a graph contains an Euler Trail as well as two methods which return the an Euler Cycle and an Euler Trail.

The library also contains an overwritten ```toString()``` method to output the adjacency matrix in a well formatted string.

## Getting Started

### Prerequisites

The [Java Develop Kit](https://www.oracle.com/java/technologies/javase-downloads.html) (JDK) must be installed for this program to run.

### Download the Program

1) Navigate to [GraphGraph](https://github.com/cross20/GraphGraph) on GitHub.

2) From the <i>Code</i> tab and on the right side of the screen, select the green button <i>Clone or download</i>.

3) Select <i>Download ZIP</i> from the menu and select a save location.

## Run the Demo

There are many ways that this program can be run. It is recommended that [Visual Studio Code](https://code.visualstudio.com/download) (VS Code) is used.

### VS Code Setup

1) Install and open VS Code.

2) Navigate to the left panel and press the <i>Extensions</i> button.

3) Search for and install the [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).

### Run the Demo in VS Code

1) Navigate to <i>File -> Open Workspace...</i> and open the directory containing the GraphGraph program.

    Note: the open directory should contain the main.java file for the program.

2) Navigate to <i>View -> Terminal</i> to open a bash terminal in VS Code.

3) In the terminal, type ```javac main.java``` to compile the program.

4) Then, type ```java main``` to run the program.