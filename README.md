icosoku-solver
==============

A fast solver for the IcoSoku 3d puzzle in Java.

You can call the programm either by using the examples (numbers[1-6]) or giving a list of the twelf edge numbers separated by commas (1,2,3,4,5,6,7,8,9,10,11,12). The solution is given like this:

```
...
area 17 piece 16 orientation 2
area 19 piece 12 orientation 0
...
```

The edges and areas are numbered counter clockwise begining at the top and the list of pieces can be found in the IcoSoku.java file. The number of dots on the pieces is given in a clockwise order and the orientation also rotates the piece clockwise. So piece 16 would translate to this:

```
piece 16: {1,3,2}
       0
       |
       v
       ^
      /1\
     /   \
    /2   3\
2->/-------\<-1
```

The standard orientation of an area is pointing to the first edge in the list for this area. Area 17 has the edges 8, 9 and 11 and the standard orientation would therefore be edge 8:

```
area 17: {8,9,11}
0->\--------/<-1
    \8    9/
     \    /
      \11/
       \/
       ^
       |
       2
```

And finally the area 17 with the piece 16 with orientation 2 would look like this:

```
area 17 piece 16 orientation 2
8\-------/9
  \3   2/
   \   /
    \1/
     v
     11
```

Time for solving the example numbers
------------------------------------

Here is a table with the runtime for the examples numbers[1-6] with the number of method calls needed to solve these instances. The runtime is the average of 30 runs.

|Example numbers           |Backtrack method calls|Time with i7-3720QM (ms)|Time with i5-3210QM (ms)|
|:------------------------:|:--------------------:|:----------------------:|:----------------------:|
|1,3,10,7,5,4,11,6,12,8,9,2|                249502|                     112|                     152|
|1,2,3,4,5,6,7,8,9,10,11,12|                    52|                       2|                       2|
|6,12,9,10,7,2,8,1,3,5,4,11|                377193|                     170|                     230|
|12,11,10,9,8,7,6,5,4,3,2,1|                   562|                       9|                      13|
|9,4,10,12,7,8,5,6,2,1,3,11|                 22952|                      47|                      75|
|1,11,2,9,4,7,6,5,8,3,10,12|                159435|                     118|                     168|

Algorithm description
---------------------

This solver is based on backtracking with a check to cut off subtrees.

Before the backtracking starts, a list with the areas and the sum of the edge numbers is created and sorted ascending. The areas will be filled with pieces in the order of this list. This was done because the pieces are sorted from few to many dots at their edges and therefore the pieces with fewer dots have a higher chance to be set at the right place in less attemps.

The check is doable. This method checks whether the edge numbers can be reached with the remaining areas filled with pieces with three or less dots and compares the sum of pieces with the number at full edges.
