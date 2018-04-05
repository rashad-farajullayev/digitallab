# Rail Road Task for Digital Lab

### Overview 
This is the coding exercise task for Digital Lab developed by Rashad Farajullayev.

This solution proposes custom approach to calculate the requested values. We could have applied one of existing algorithms to calculate shortest path, however in that case, answering to other questions would require a lot more development. This solution is able to calculate not only the shortest path, but also, number of possible trips and routes, and follow predefined route and calculate its distance.

### Description of general classes required to calculated values:
* ```class Graph``` - holds collection of all Nodes, responsible for required calculations. Can be initialized from an input string. Will generate all the necessary Node and Edge instances and correlate them with each other.
* ```class Node``` - represents the nodes of the graph. Nodes also hold the outbound edges
* ```class Edge``` - represents for edges between nodes.
* ```class Route``` - used by the Graph for calculating routes, their distances and trips

### Operation instructions
#### Commands

**`-help`** 
> Prints out the operation instructions to the output console
   
**`-test`** 
> To run the sample tests as have been provided in the coding exercise e-mail
   This is to demonstrate that the code can calculate the requested parameters 
   and the output results meet the expectations
   
**`-init "<graph_initialization>" <operation> <options>`**

>This will initialize the graph with the input data. The input must follow the for-
mat described in the requirements.
   
**Example:**
```      
-init "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7" <operation> <options>
```
   
Please note the graph initialization string must be enclosed in quotation marks, and this command must be followed by the operation and corresponding options. For the operation and options see below:
	
**`-load <path-file.txt> <operation> <options>`**
> This will load graph initialization text from a text file. Text file's name must 
have a `.txt` extension. The initialization line inside of the file can be supplied 
in one line or be divided into multiple lines. This command must follow the 
operation and its corresponding options.
   
**Example:**
   
```
-load C:\sample.txt directDistance -route:A-B-C
```
    
In the above example you may see the `directDistance` operation and its options.

   #### Operations and their options
   
**`directDistance -route:<node-names>`**
>This will calculate the distance of the route. The route can be specified as dash separated node names. 

For example, if you want to calculate route distance from A to B and then to B to C, you can specify as `directDistance -route:A-B-C`

**Example:**
```
-init "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7" directDistance -route:A-B-C
```
   
**`shortestRoute -from:<from-node> -to:<to-node>`**
>This operation will calculate the length of the shortest route (in terms of distance to travel) from the `<from-node>` to `<to-node>`

**Example:**
```
-load fileName.dat shortestRoute -from:A -to:C
```
		   
**`tripsCount -from:<from-node> -to:<to-node> -where<condition>`**
>This operation calculate the count of trips between `<from-node>` and `<to-node>` which meets the `<contition>` for the number of stops. 

The condition consists of comparison operator and the value to compare to. For example, you may want to calculate the number of trips from `A` to `C` with maximum of three stops. In this case you can type:
		   
```-load fileName.dat tripsCount -from:A -to:C -where^<30```
           
The `-where` option accepts operators like `<`, `<=`, `=`, `>=`, `>` for comparison and the target value to compare against. Please note, there must be no symbol between this option and its parameters but the `^` symbol to escape special symbols.
		
**`routesCount -from:<from-node> -to:<to-node> -where<condition>`**
>This will calculate number of different routes between nodes meeting the condition.

For example, if you want to calculate the number of different routes from `C` to `C` with a distance of less than 30, then you can type:

```-load fileName.dat routesCount -from:A -to:C -where^<30 ```

The `-where` option accepts operators like `<`, `<=`, `=`, `>=`, `>` for comparison and 
		the target value to compare against. Don't forget to escape special symbols for 
		this option by using "^" symbol.
        NOTE: where option in this operation compares total route length, not trips count

### Examples 
```
-load fileName.dat directDistance -route:A-B-C
-init "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7" directDistance  -route:A-B-C
-load fileName.dat shortestRoute -from:A -to:C
-load fileName.dat tripsCount -from:A -to:C -where^<30
-load fileName.dat routesCount -from:A -to:C -where^<30
```