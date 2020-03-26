import java.io.*;
import java.util.*;

public class Puzzle {

    int nodes=0;
    static int dimension = 4;
    // Bottom, left, top, right
    int[] row = {1, 0, -1, 0};
    int[] col = {0, -1, 0, 1};
    HashMap <Integer,Integer> rowmap = new HashMap<>();
    HashMap <Integer,Integer> colmap = new HashMap<>();
    public int calculateCost(int[][] initial, int[][] goal) {
        int count = 0;
        int n = initial.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (initial[i][j] != 0 && initial[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    public void Shifting(int[][]matrix)
    {
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix.length;j++)
            {
                rowmap.put(matrix[i][j],i);
                colmap.put(matrix[i][j],j);
            }
        }
    }
    public int Count(int a ,int i, int j)
    {

        Integer p = rowmap.get(a);
        Integer q = colmap.get(a);
        int cost = Math.abs(p-i) + Math.abs(q-j);
        return cost;
    }
    public int Manhatton(int [][] initial,int [][] goal)
    {
        int cst=0;
        int v=0;
        for(int i=0;i<initial.length;i++)
        {
            for(int j=0;j<initial.length;j++)
            {
                if(goal [i][j]!=0 && initial[i][j]!= goal[i][j])
                {
                    v=goal[i][j];
                    cst = cst+ Count(v,i,j);

                }
            }
        }
        return cst;
    }
    public void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isSafe(int x, int y) {
        return (x >= 0 && x < dimension && y >= 0 && y < dimension);
    }

    public void printPath(Node root) {
        if (root == null) {
            return;
        }
        printPath(root.parent);
        printMatrix(root.matrix);
        System.out.println();
    }

    public boolean isSolvable(int[][] matrix) {
        int count = 0;
        int p;
        List<Integer> array = new ArrayList<Integer>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                array.add(matrix[i][j]);
            }
        }

        Integer anotherArray[] = new Integer[array.size()];
        array.toArray(anotherArray);


        for (int i = 0; i < anotherArray.length - 1; i++) {
            for (int j = i + 1; j < anotherArray.length; j++) {
                if (anotherArray[i] != 0 && anotherArray[j] != 0 && anotherArray[i] > anotherArray[j]) {
                    count++;
                }
            }
        }
        // p=count;
        boolean x = position(anotherArray);
        System.out.println("Number of Inversion : " + count);
        //System.out.println(x);
        if(x==true)
        {
            System.out.println("X is in 2nd row or 4th row");
        }
        else if(x==false)
        {
            System.out.println("X is in 1st row or 3rd row");
        }
        if((count % 2 == 0 && x==true) || (count %2==1 && x==false))
        {
            System.out.println("It is Solvable");
        }
        else
        {
            System.out.println("It is NOT Solvable");
        }
        return ((count % 2 == 0 && x==true) || (count %2==1 && x==false));
        //position(anotherArray);
        //return count % 2 == 0;
    }

    public boolean position(Integer[] array) {
        int b=0;
        for (int i = 4; i < 8; i++) {
            if (array[i] != 0) {
                continue;
            } else if (array[i] == 0) {
                b = 1;
            }
        }
        for (int i = 12; i < array.length; i++) {
            if (array[i] != 0) {
                continue;
            }
            else if(array[i]==0)
            {
                b=1;
            }
        }
        return b%2==1;
    }
    public void SwapNumber(int [] [] matrix1,int [][] matrix2)
    {
        int count=0;
        for(int i=0;i<matrix1.length;i++)
        {
            for(int j=0;j<matrix2.length;j++)
            {
                if(matrix1[i][j]!=matrix2[i][j])
                {
                    count++;
                }
            }
        }
        System.out.println("Minimum number of Swap Required : "+ count);
    }
    public void solveManhatton(int[][] initial, int[][] goal, int x, int y) {
        // PriorityQueue<Node> pq = new PriorityQueue<Node>(1000, (a, b) -> (a.cost + a.level) - (b.cost + b.level));
        PriorityQueue <Node>pq= new PriorityQueue<Node>(1000, (Node a,Node b) -> (a.cost+a.level) -(b.cost+b.level));
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = Manhatton(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();
           // nodes++;
           // System.out.println("Number of nodes " + nodes);
            if (min.cost == 0) {
                printPath(min);
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i])) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = Manhatton(child.matrix, goal);
                    pq.add(child);
                }
            }
        }
    }
    public void solve(int[][] initial, int[][] goal, int x, int y) {
        // PriorityQueue<Node> pq = new PriorityQueue<Node>(1000, (a, b) -> (a.cost + a.level) - (b.cost + b.level));
        PriorityQueue <Node>pq= new PriorityQueue<Node>(1000, (Node a,Node b) -> (a.cost+a.level) -(b.cost+b.level));
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = calculateCost(initial, goal);
        pq.add(root);

        while (!pq.isEmpty()) {
            Node min = pq.poll();
           // nodes++;
           // System.out.println("Number of nodes " + nodes);
            if (min.cost == 0) {
                printPath(min);
                return;
            }

            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i])) {
                    Node child = new Node(min.matrix, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost(child.matrix, goal);
                    pq.add(child);
                }
            }
        }
    }

    public static void main(String[] args) throws ArrayIndexOutOfBoundsException ,IOException
             {

        int [][] initial = new int[dimension][dimension];
        int [][] goal = {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,0}};
        int x=0;
        int y=0;
        int c=0;
         System.out.println("Please Enter Initial matrix : ");
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<4;j++)
            {
                Scanner s= new Scanner(System.in);

                initial [i][j] = s.nextInt();
                if(initial[i][j]==0)
                {
                    x=i;
                    y=j;
                }
            }
        }

        Puzzle puzzle = new Puzzle();
        while(true) {
        System.out.println("Press 1 for displacement heuristic & 2 for Manhatton");
        System.out.println("Press 0 to EXIT");
        Scanner p = new Scanner(System.in);
        int choice=p.nextInt();

            if (choice == 1) {
                if (puzzle.isSolvable(initial)) {
                    puzzle.SwapNumber(initial, goal);
                    puzzle.solve(initial, goal, x, y);
                }
                int costing= puzzle.calculateCost(initial,goal);
                System.out.println("Displacement Heuristic cost is : " + costing);
            }
            if (choice == 2) {
                if (puzzle.isSolvable(initial)) {
                    puzzle.SwapNumber(initial, goal);
                   // puzzle.TileShift(initial);
                    puzzle.Shifting(initial);
                    puzzle.Manhatton(initial, goal);
                    puzzle.solveManhatton(initial, goal, x, y);
                }
                int man= puzzle.Manhatton(initial,goal);
                System.out.println("ManHattan Heuristic cost is : " + man);
            }
            if(choice==0)
            {
                return;
            }
        }


    }

}