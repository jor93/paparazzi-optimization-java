# Paparazzi Problem Solver
This readme covers following topics:

  - **Introduction.** *A brief introduction to the topic.*
  - **Programming Guide.** *A brief description of the project.*
  - **User Guide.** *A brief description on how to launch or extend the project.*
  - **History.** *Check what has changed.*

# Introduction!
The goal of the Paparazzi Problem is to help a paparazzi take a picture of a celebrity in the shortest possible time. The paparazzi problem consists of a two-dimensional grid with several non-moving obstacles, leading to different traversing time factors, respectively costs. The paparazzi enters the property through an entrance somewhere at the edge of the grid, called gate. From this starting point, the paparazzi looks for the fastest and thus, the most efficient way. The paparazzi moves only in horizontal and vertical direction. The location of the celebrity is represented by the grey field in the map. The red fields represent cameras that cost the paparazzi extra time, depending on how close the paparazzi gets. The blue fields are ponds and pools which slow down the paparazzi drasticly. If ponds are near cameras, their costs increases. Trees and shrubs are shown in black and cannot be passed and serve as a visual protection.

<p align="center">
 <img src="img/application.png?raw=true" alt="Application Overview"/>
</p>

To find the optimal way through the map, the paparazzi can choose from different pathfinding algorithms. The implemented search algorithms available for the paparazzi are (by category):

**Uninformed search algorithm**:
  - Dijkstra (more about [Dijkstra])

<p align="center">
 <img src="img/dijkstra.gif?raw=true" alt="Dijkstra Algorithm"/>
</p>

  - Bellman Ford (more about [Bellman Ford])

<p align="center">
 <img src="img/bellmanFord.gif?raw=true" alt="Bellman Ford"/>
</p>

  - Floyd Warshall (more about [Floyd Warshall])

<p align="center">
 <img src="img/floydWarshall.gif?raw=true" alt="Floyd Warshall Algorithm"/>
</p>

**Informed search algorithm**:
  - A* (more about [A*]) 

<p align="center">
 <img src="img/aStar.gif?raw=true" alt="A* Algorithm"/>
</p>

  - Best First Search (more about [Best First Search])

<p align="center">
 <img src="img/bestFirstSearch.gif?raw=true" alt="Best First Search Algorithm"/>
</p>

Each algorithm has its own unique characteristics and advantages that influences how the algorithm searches through the map and under which circumstances it finds the shortest path. A general knowledge about the different search algorithm is required prior to fully understand the project.

# Programming Guide!
### Programming environment
The Project is realized in Java in a MVC pattern. MVC stands for Model-View-Controller. This pattern is used to separate the application's logic from its data. This increases the reusability of the code as parts can be switched out more easily. The Model represents an object or carrying data. It can also have logic to update the controller if its data changes.The View represents the visualization of the data that the model contains. Finally, the Controller acts on both model and view. It controls the data flow into the model and updates the view whenever the data changes. It keeps views and models separate. The views are implemented with the UI library JavaFX.

The Java project can be runned in two different modes:

**Demo mode**

The demo mode aims to better understand each search algorithm and to visually show how the algorithm searches through the map. In order to do this, the project starts the application and generates a paparazzi map for the pre-definied data set. You can change the data set at any given time. As soon as your data set is set, you choose the desired search algorithm simply by clicking on the corresponding button. The GUI updates and shows the next visisted nodes of the algorithm in real time. After finding and showing the path, the application calculates and updates the results on the GUI. Be aware, the demo mode slows down the search algorithm as it needs to update the GUI. The search algorithm performs much better in Test mode where the next visited nodes are not visually displayed.

**Test mode**

The test mode aims to find the best search algorithm for every data set. In order to do this, the project generates n Number of Maps for the five different pre-definied data sets (TINY, SMALL, MEDIUM, LARGE). Afterwards every algorithm is started on the specific test run and writes the result in the corresponding folder under results. After each data set, the overall result file is created and updated. The algorithms are compared by following metrics:
  - **Execution time in ms.** *Describes how long the algorithm took to find a path from the start point to the end point.*
  - **Visited Nodes.** *Describes how many nodes have been visited by the algorithm to find a path.*
  - **Total Costs.** *Describes how high the total path costs are from the start point to the end point.*
  - **Accuracy.** *Describes how many times the algorithm has found the shortest path from the start point to the end point.*

Accordingly, the best algorithm can be chosen to solve the Paparazzi Problem for every data set. However be aware that the test mode is very RAM intensive and will throw an exception, if not enough memory is available for the JVM. This is due to a known bug of JavaFX while drawing a text. Read more about [javafx-memory-leak]

### Project structure
The project is structured as follow:


    .
    ├── diagram                 # contains ObjectAid class diagram files
    ├── docs                    # contains generated Javadoc files
	├── img                     # contains application and search algorithm image files
	├── libs                    # contains external library files
    ├── src                     # contains source files
    ├── results                 # contains results files
    ├── .gitignore
    └── README.md


### Class diagram
This class diagram gives an overview of the Java project:
<p align="center">
 <img src="diagram/class-diagram.png?raw=true" alt="Class Diagram"/>
</p>

### Documentation
To get a deep insight of the project the Javadoc has been generated. Javadoc is a documentation tool for the Java programming language. Javadoc is an extensible documentation generation system that specifically reads formatted comments in the Java source code and generates compiled documentation in form of webpages.

The Javadoc is structured as follow:


	.
	├── ...
	├── docs                    # contains generated Javadoc files
	│   ├── algorithms          # contains search algorithm Javadoc files
	│   ├── controller          # contains controller Javadoc files
	│   ├── enums               # contains enumeration Javadoc files
	│   ├── helpers             # contains helper classes Javadoc files
	│   ├── init           	    # contains launch and Settings Javadoc files
	│   ├── interfaces          # contains interfaces Javadoc files
	│   ├── model               # contains model Javadoc files
	│   ├── view           	    # contains view Javadoc files
	│   └── index.html          # index.html to launch Javadoc
	└── ...


The full Javadoc of this project is available [here](https://robertjohner.ch/paparazzi-javadoc/index.html)

# User Guide!
### Prereqisites
To launch the project you need at least following requirements:
  - Java JDK 8 or newer
  - A Java IDE
  - *Only for testmode*: At least 8GB of **free** RAM. I recommend at least a total of 16GB RAM to run the test mode and assign the free memory to the Java JVM!

### Setting up
To launch the project:
1. Clone this git repo
    ```
    git clone https://github.com/jor93/paparazzi-optimization.git
    ```
2. Fire up your favorite Java IDE and import the project
3. Launch following Java classes for:
   1. Demo mode: /init/MainGUI.java
   2. Test mode: /init/MainTest.java. Do not forget to allocate more RAM to the Java JVM by following VM argument: -Xmx8192m. Depending on your Java IDE this argument can be set in different locations.

# History!
- 11.04.2019 initial commit

   [git]: <https://github.com>
   [here]: <https://github.com/jor93/paparazzi-optimization.git>
   [Dijkstra]: <https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7>
   [A*]: <https://www.geeksforgeeks.org/a-search-algorithm>
   [Best First Search]: <https://www.geeksforgeeks.org/best-first-search-informed-search>
   [Bellman Ford]: <https://www.geeksforgeeks.org/bellman-ford-algorithm-dp-23>
   [Floyd Warshall]: <https://www.geeksforgeeks.org/floyd-warshall-algorithm-dp-16>
   [documentation]: <https://github.com>
   [javafx-memory-leak]: <https://bugs.openjdk.java.net/browse/JDK-8088175>
  
*made with ♥ by jor*