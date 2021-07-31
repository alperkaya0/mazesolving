import java.io.IOException;
import java.util.*;

public class IntiutivelyShortest {

    public static void main(String[] args) throws IOException {

    }
    public static LinkedList<Vertex> placesOtherThanYou(Vertex v) {
        LinkedList<Vertex> places = new LinkedList<>();
        for (int a = 0;a<v.getEdgesTC().size();++a){
            for (int i = 0; i < v.getEdgesTC().get(a).getVertices().length; ++i) {
                if (v.getEdgesTC().get(a).getVertices()[i] != v) {
                    places.add(v.getEdgesTC().get(a).getVertices()[i]);
                }
            }
        }
        return places;
    }
    public static void findVerticesThatHaveMostConnections(UGraph graph) {
        LinkedList<Vertex> vertices = graph.vertices;
        ArrayList<Integer> idxmax = new ArrayList<>();
        int max = 0;
        System.out.println(ANSI.PURPLE+"Nodes that have most connection are as given (in one step):"+ANSI.RESET);
        for (int i = 0;i<vertices.size();++i) {
            int count = 0;
            for (Vertex v:placesOtherThanYou(vertices.get(i))) {
                ++count;
            }
            if (i == 0) {
                max = count;
                idxmax.add(i);
            }
            if (i>0) {
                if (count>=max) {
                    max = count;
                    idxmax.add(i);
                }
            }
        }
        Collections.sort(idxmax);
        ArrayList<Integer> removeThese = new ArrayList<>();
        for (int i = 0;i<idxmax.size()-1;++i) {
            if ((vertices.get(idxmax.get(i)).getEdgesTC().size()<vertices.get(idxmax.get(idxmax.size()-1)).getEdgesTC().size())) {
                removeThese.add(i);
            }
        }
        Iterator<Integer> itr = idxmax.iterator();
        int count = 0;
        while (itr.hasNext()) {//this while loop gives what i want even if it is weird
            if (count == 0 && removeThese.contains(0)) {
                itr.next();
                itr.remove();
            }
            if (count != 0 && removeThese.contains(count)) {
                itr.remove();
            }
            int temp = itr.next();
            ++count;
        }
        for (int i:idxmax) {
            count = 0;
            for (Vertex v:placesOtherThanYou(vertices.get(i))) {
                ++count;
                String str = "";
                for (int a = 0;a<16+vertices.get(i).getName().length();++a) {
                    str += " ";
                }
                String vertical = "   \u2502";
                if (count != placesOtherThanYou(vertices.get(i)).size()){
                    if (count == 1){
                        System.out.print(ANSI.PURPLE);
                        System.out.print(vertical);
                        System.out.print(ANSI.RESET);
                        System.out.println("You can go from "+ANSI.CYAN+vertices.get(i).getName()+ANSI.RESET+"  => to "+ANSI.GREEN+v.getName()+ ANSI.RESET+",");
                    }
                    else{
                        System.out.print(ANSI.PURPLE);
                        System.out.print(vertical);
                        System.out.print(ANSI.RESET);
                        System.out.println(str+"  => to "+ANSI.GREEN+v.getName()+ANSI.RESET+",");
                    }
                }
                else {
                    System.out.print(ANSI.PURPLE);
                    System.out.print(vertical);
                    System.out.print(ANSI.RESET);
                    System.out.println(str+"  => to "+ANSI.GREEN+v.getName()+ ANSI.RESET + ".");
                    if (!idxmax.get(idxmax.size()-1).equals(i)){
                        System.out.print(ANSI.PURPLE);
                        System.out.print("");
                        System.out.print(ANSI.RESET);
                    }
                }
            }
        }
    }
    public static Object[] isValidandFindAnyPath(Vertex s,Vertex u) {
        boolean reversed = false;
        boolean bool = false;
        LinkedList<Vertex> visited = new LinkedList<>();
        if (s.equals(u)) {
            System.out.println("You are at desired location.");
            bool = true;
        }
        else if (!s.equals(u)){
            LinkedList<Vertex> paths = new LinkedList<>();

            int count = 0;
            if (placesOtherThanYou(s).size() > placesOtherThanYou(u).size()) {
                Vertex temp = s;
                s = u;
                u = temp;
                reversed = true;
            }
            bool = 0 != (int) isValPath(s, u, paths, count)[0];
            visited = (LinkedList<Vertex>) isValPath(s, u, paths, count)[1];


            while (!notLeapPath(visited)) {
                for (int i = 0; i < visited.size() - 1; ++i) {
                    if (!placesOtherThanYou(visited.get(i + 1)).contains(visited.get(i))) {
                        visited.remove(visited.get(i));
                    }
                }
            }
            int idx = -1;
            for (int i = 0; i < visited.size(); ++i) {
                if (visited.get(i).equals(u)) {
                    idx = i;
                    break;
                }
            }
            if (idx > -1) {
                while (visited.size() != idx + 1) {
                    visited.remove(idx + 1);
                }
            }

            if (reversed) {
                for (int i = 0; i < visited.size() / 2; ++i) {
                    if (visited.size() % 2 == 0) {
                        Vertex temp = visited.get(visited.size() - 1 - i);
                        visited.set(visited.size() - 1 - i, visited.get(i));
                        visited.set(i, temp);
                    }
                    if (visited.size() % 2 == 1) {
                        if (i != (visited.size() / 2) + 1) {
                            Vertex temp = visited.get(visited.size() - 1 - i);
                            visited.set(visited.size() - 1 - i, visited.get(i));
                            visited.set(i, temp);
                        }
                    }
                }
            }
            if (reversed) {
                if (!visited.get(0).equals(u)) {
                    visited.addFirst(u);
                }
                if (!visited.getLast().equals(s)) {
                    visited.addLast(s);
                }
            }else{
                if (!visited.get(0).equals(s)) {
                    visited.addFirst(s);
                }
                if (!visited.getLast().equals(u)) {
                    visited.addLast(u);
                }
            }


            System.out.println();
            for (int i = 0; i < visited.size(); ++i) {
                if (i != visited.size() - 1) System.out.print(visited.get(i).getName() + " = > ");
                else System.out.print(visited.get(i).getName());
            }
            System.out.println();
            bool = true;
        }
        return new Object[]{bool,visited};
    }
    public static boolean notLeapPath(LinkedList<Vertex> ver) {
        boolean bool = false;
        int count = 1;
        if (!(ver.size() < 2)){
            for (Vertex v : ver) {
                if (count < ver.size() - 1) {
                    if (placesOtherThanYou(v).contains(ver.get(count))) {
                        ++count;
                    }
                }
            }
            if (count == ver.size() - 1) {
                bool = true;
            }
        }else{
            bool = true;
        }
        return bool;
    }
    public static Object[] isValPath(Vertex s,Vertex u,LinkedList<Vertex> visited,int count) {
        visited.add(s);
        if (count==0){
            for (Vertex v:placesOtherThanYou(s)) {
                if (v.equals(u)) {
                    ++count;
                    visited.add(v);
                    break;
                }else if(!(visited.contains(v)) && count == 0) {
                    count += (int)isValPath(v,u,visited,count)[0];
                }
            }
        }
        if (!(count>0)){
            visited.remove(s);
        }

        return new Object[]{count,visited};
    }//Works somehow but since it only adds to list paths get concatenated kinda randomly(but there is no random event in a program)
    public static String IntuitivenessPath(Vertex s, Vertex u, UGraph graph) {
        Tree tree = new Tree();
        String superSpecialString = "";
        tree.setNodeNumAuto();
        tree.setRootAuto();
        int count = 0;
        HashSet<Vertex> visited = new HashSet<>();
        if (traverse(s,visited,count,graph,tree,null) != null)
            tree = (Tree)traverse(s,visited,count,graph,tree,null);
        if (tree != null){
            tree.setRootAuto();
            System.out.println();
            LinkedList<Node> temp = new LinkedList<>();
            int maxChildren = 0;
            for (Node n:tree.getNodeList()) {
                if (n.getChildren().size()>=maxChildren) {
                    maxChildren = n.getChildren().size();
                }
            }
            for (int a = 0;a<=maxChildren;++a){
                for (int i = 0; i < tree.getNodeList().size(); ++i) {
                    if (tree.getNodeList().get(i).getChildren().size() == a) {
                        temp.add(tree.getNodeList().get(i));
                    }
                }
                for (Node n:temp) {
                    System.out.print(n.getVertexSelf().getName()+" ");
                }
                temp = new LinkedList<>();
                System.out.println();
            }
            System.out.println();
            for(int i = 0;i<tree.getNodeList().size();++i) {
                for(int a = 0;a<tree.getNodeList().size();++a) {
                    if ((!Objects.isNull(tree.getNodeList().get(a).getParent())) && (!Objects.isNull(tree.getNodeList().get(i).getParent()))&& (!tree.getNodeList().get(i).equals(tree.getNodeList().get(a)))){
                        if (tree.getNodeList().get(a).getParent().getVertexSelf().getName().equals(tree.getNodeList().get(i).getParent().getVertexSelf().getName()) || tree.getNodeList().get(i).getParent().getVertexSelf().getName().equals(tree.getNodeList().get(a).getParent().getVertexSelf().getName())) {
                            if (!tree.getNodeList().get(a).getNeighbors().contains(tree.getNodeList().get(i))){
                                tree.getNodeList().get(a).addNeighbors(tree.getNodeList().get(i));
                            }
                            if (!tree.getNodeList().get(i).getNeighbors().contains(tree.getNodeList().get(a))){
                                tree.getNodeList().get(i).addNeighbors(tree.getNodeList().get(a));
                            }
                        }
                    }
                }
            }
            int counter = 0;
            LinkedList<Node> nodeList = new LinkedList<>(tree.getNodeList());
            HashSet<Node> visitedNodes = new HashSet<>();
            HashMap<Node,LinkedList<Node>> parentChilds = new HashMap<>();
            for (Node n:nodeList) {
                LinkedList<Node> altNodes = new LinkedList<>();
                for (Node alt:n.getChildren().keySet()) {
                    altNodes.add(alt);
                }
                parentChilds.put(n,altNodes);
            }
            for (Node nodUp:nodeList) {
                System.out.println("Name of Node: "+nodUp.getVertexSelf().getName());
                System.out.print("Children: ");
                for (Node nod:parentChilds.get(nodUp)) {
                    System.out.print(" "+nod.getVertexSelf().getName()+" ");
                }
                System.out.println();
            }
            System.out.println(ANSI.PURPLE+"Printing the Tree: "+ANSI.RESET);
            LinkedList<HashSet<Integer>> layers = new LinkedList<>();
            HashSet<Integer> tempParents = new HashSet<>();
            tempParents.add(findIndexOfNode(tree.root,tree));
            HashSet<Integer> tempChildren = new HashSet<>();
            for (Node n:tree.root.getChildren().keySet()) {
                tempChildren.add(findIndexOfNode(n,tree));
            }
            while (counter != graph.getnOv() && !tempParents.isEmpty()) {
                System.out.println();
                for (int idx:tempParents) {
                    System.out.print(tree.getNodeList().get(idx).getVertexSelf().getName()+" ");
                }
                layers.add(new HashSet<>());
                layers.getLast().addAll(tempParents);
                tempParents.clear();
                System.out.println();
                for (int idx:tempChildren) {
                    System.out.print(tree.getNodeList().get(idx).getVertexSelf().getName()+" ");
                    for (Node n:tree.getNodeList().get(idx).getChildren().keySet()) {
                        tempParents.add(findIndexOfNode(n,tree));
                    }
                }
                layers.add(new HashSet<>());
                layers.getLast().addAll(tempChildren);
                tempChildren.clear();
                for (int idx:tempParents) {
                    for (Node n:tree.getNodeList().get(idx).getChildren().keySet()) {
                        tempChildren.add(findIndexOfNode(n,tree));
                    }
                }
                ++counter;
            }

            int[] result = new int[2];
            int setCounter = 0;
            boolean key = false;
            for (HashSet<Integer> set:layers) {
                for (int idx:set) {
                    if (tree.getNodeList().get(idx).getVertexSelf().equals(u)) {
                        result[0] = setCounter;
                        result[1] = idx;
                        key = true;
                        break;
                    }
                }
                if (key) {
                    break;
                }
                ++setCounter;
            }
            Node pointer = tree.getNodeList().get(result[1]);
            System.out.println();
            System.out.println("Starter pointer: "+pointer.getVertexSelf().getName());
            String str = "";
            System.out.println("S: "+s.getName()+" U: "+u.getName());
            int resultCounter = result[0] - 1;
            while(pointer != null && pointer.getVertexSelf()!=s && !pointer.getParent().getVertexSelf().equals(s)) {
                if (resultCounter>=0) {
                    HashSet<Integer> tempSet = layers.get(resultCounter);
                    LinkedList<Node> choices = new LinkedList<>();
                    for (int idx:tempSet) {
                        LinkedList<Vertex> vertices = new LinkedList<>();
                        for (Node n:tree.getNodeList().get(idx).getChildren().keySet()) {
                            vertices.add(n.getVertexSelf());
                        }
                        if (tree.getNodeList().get(idx) != null && vertices.contains(pointer.getVertexSelf()) && !pointer.getVertexSelf().equals(tree.getNodeList().get(idx).getVertexSelf()) && placesOtherThanYou(tree.getNodeList().get(idx).getVertexSelf()).contains(pointer.getVertexSelf())){
                            choices.add(tree.getNodeList().get(idx));
                        }
                    }
                    int min = 0;
//                    System.out.println("My pointer: "+pointer.getVertexSelf().getName()+" My Choices: "+nodeArrTOnameArr(choices));
                    for (int i = 0;i<choices.size();++i) {
                        if (giveMeWeight(tree.getNodeList().get(min),pointer)>=giveMeWeight(pointer,choices.get(i))) {
                            min = i;
                        }
                    }
                    pointer = choices.get(min);
                }else {
                    pointer = null;
                }

                if (pointer != null && !pointer.getVertexSelf().equals(s))str = str + " <= " + pointer.getVertexSelf().getName();
                else if (pointer != null)str = str + " <= " +pointer.getVertexSelf().getName() ;
                --resultCounter;
            }
            String[] splitted = str.split(" <= ");
            int stringCounter = 0;
            LinkedList<String> nodeNameList = new LinkedList<>();
            for (Node n:tree.getNodeList()) {
                nodeNameList.add(n.getVertexSelf().getName());
            }
            for (String stringPiece:splitted) {
                if (nodeNameList.contains(stringPiece)) {
                    ++stringCounter;
                }
            }
            if (splitted.length-1 == stringCounter) {
                if (str.length()>=2){
                    if (str.charAt(1) == '<') {
                        str = str.substring(4);
                    }
                    if (str.charAt(str.length() - 2) == '=') {
                        str = str.substring(0, str.length() - 5);
                    }
                }
            }
            if (!str.contains(u.getName())) str = u.getName() + " <= "+ str;
            if (!str.contains(s.getName()))str = str + " <= " +s.getName();
            System.out.println(str);
            superSpecialString = str;
            //with Layers data structure we are able to *layer* our output tree
        }
        return superSpecialString;
    }
    public static String nodeArrTOnameArr(LinkedList<Node> nodes) {
        LinkedList<String> temp = new LinkedList<>();
        for (Node n:nodes) {
            temp.add(nodeTOname(n));
        }
        return temp.toString();
    }
    public static String nodeTOname(Node node) {
        return node.getVertexSelf().getName();
    }
    public static void forEachChild(Node node,Tree tree,String str) {
        if (!node.getChildren().isEmpty()){
            LinkedList<Node> temp = new LinkedList<>(tree.getNodeList().get(findIndexOfNode(node,tree)).getChildren().keySet());
            for (Node value : temp) {
                for (int a = 0; a < value.getNeighbors().size(); ++a) {
                    for (Node altChild : value.getNeighbors().get(a).getChildren().keySet()) {
                        for (int c = 0; c < altChild.getVertexSelf().getName().length(); ++c) {
                            str += " ";
                        }
                    }
                }
                System.out.print(str + value.getVertexSelf().getName() + " ");
            }
            System.out.print("\n");
        }
        for (Node n:node.getChildren().keySet()) {
            forEachChild(tree.getNodeList().get(findIndexOfNode(n,tree)),tree,str);
        }
    }
    public static int findIndexOfNode(Node n,Tree tree) {
        int idx = -1;
        for (int i = 0;i<tree.getNodeList().size();++i) {
            if (n.getVertexSelf().equals(tree.getNodeList().get(i).getVertexSelf())) {
                idx = i;
            }
        }

        return idx;
    }
    public static Object traverse(Vertex s,HashSet<Vertex> visited,int count,UGraph graph,Tree tree,Object old) {
        Node nSup = new Node(s);
        for (Vertex v:placesOtherThanYou(s)) {
            if (!visited.contains(v)){
                Node vS = new Node(v);
                nSup.addChild(vS);
            }
        }
        nSup.setParent((Node)old);
        tree.addToList(nSup);
        for (Vertex v:placesOtherThanYou(s)) {
            if (!visited.contains(v) && count != graph.getnOv()){
                ++count;
                visited.add(v);
                visited.add(s);
                traverse(v,visited,count,graph,tree,new Node(s));
            }
        }
        if (count == graph.getnOv()) {
            return tree;
        }
        return null;
    }
    public static String setTOstring(HashSet<Integer> set,Tree tree) {
        LinkedList<String> temp = new LinkedList<>();
        for (int idx:set) {
            temp.add(tree.getNodeList().get(idx).getVertexSelf().getName());
        }
        return temp.toString();
    }
    public static double giveMeWeight(Node v,Node s) {
        int count = 0;
        double weight = 0.0;
        for (Edge e:v.getVertexSelf().getEdgesTC()) {
            for (Vertex venti:e.getVertices()) {
                if (venti.equals(v.getVertexSelf()) || venti.equals(s.getVertexSelf())) {
                    ++count;
                }
            }
            weight = e.getWeight();
        }
        if (count != 2) {
            weight = Double.POSITIVE_INFINITY;
        }
        return weight;
    }
}

class Vertex{
    public String name;
    public double value;
    public LinkedList<Edge> edgesThatConnected = new LinkedList<>();

    Vertex(double value,String name) {
        this.name = name;
        this.value = value;
    }
    Vertex(String name,double value,LinkedList<Edge> edgesThatConnected) {
        this.name = name;
        this.value = value;
        this.edgesThatConnected = edgesThatConnected;
    }

    public void addEdge(Edge e) {
        if (!edgesThatConnected.contains(e))
            edgesThatConnected.add(e);
    }

    public LinkedList<Edge> getEdgesTC() {
        return edgesThatConnected;
    }
    public LinkedList<String> getEdgesTCNames() {
        LinkedList<String> temp = new LinkedList<>();
        for (Edge e:edgesThatConnected) {
            temp.add(e.getVertices()[0].getName()+"=>"+e.getVertices()[1].getName());
        }
        return temp;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
//we will add edges with usage of function of vertices
class Edge{
    public Vertex[] vertices;
    public double weight;

    Edge(Vertex[] vertices,double weight){
        this.vertices = vertices;
        this.weight = weight;
    }
    Edge(Vertex v1,Vertex v2,double weight){
        this.vertices = new Vertex[2];
        this.vertices[0] = v1;
        this.vertices[1] = v2;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public Vertex[] getVertices() {
        return vertices;
    }
}

class UGraph {
    public int nOv;
    public LinkedList<Vertex> vertices;

    UGraph(LinkedList<Vertex> vertices) {
        this.vertices = vertices;
        this.nOv = vertices.size();
    }

    public int getnOv() {
        return nOv;
    }
}

class ANSI{
    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
}

class Node{
    public Vertex vertexSelf;
    public Node parent;
    public Map<Node,Double> children = new HashMap<>();
    public LinkedList<Node> neighbors = new LinkedList<>();

    Node(Vertex vertexSelf) {
        this.vertexSelf = vertexSelf;
    }

    public LinkedList<Node> getNeighbors() {
        return this.neighbors;
    }

    public void addNeighbors(Node node) {
        this.neighbors.add(node);
    }

    public void setChildren(Map<Node,Double> children) {
        this.children = children;
    }

    public void addChild(Node child) {
        LinkedList<Vertex> vertexSet = new LinkedList<>();
        for (Node n:children.keySet()) {
            vertexSet.add(n.getVertexSelf());
        }
        if (!Objects.isNull(child) && !vertexSet.contains(child.getVertexSelf())){
            int count = 0;
            Edge target = null;
            for (Edge e : vertexSelf.getEdgesTC()) {
                for (Vertex v : e.getVertices()) {
                    if (v.equals(vertexSelf) || v.equals(child.getVertexSelf())) {
                        ++count;
                    }
                }
                if (count == 2) {
                    target = e;
                    break;
                }
                count = 0;
            }
            if (count == 2) {
                children.put(child, target.getWeight());
            }
        }
    }

    public Vertex getVertexSelf() {
        return vertexSelf;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Map<Node, Double> getChildren() {
        return children;
    }

    public Node getParent() {
        return parent;
    }
}

class Tree{
    public int nodeNum;
    public LinkedList<Node> nodeList = new LinkedList<>();
    public Node root;

    public void setRoot(Node root) {
        this.root = root;
    }

    public void addToList(Node node) {
        nodeList.add(node);
    }

    public void setRootAuto(){
        if (!nodeList.isEmpty()){
            for (Node n : nodeList) {
                if (Objects.isNull(n.getParent())) {
                    this.root = n;
                    break;
                }
            }
        }else{
            System.out.println("Node list is empty for now");
        }
    }

    public int setNodeNumAuto() {
        this.nodeNum = nodeList.size();
        return this.nodeNum;
    }

    public LinkedList<Node> getNodeList() {
        return nodeList;
    }
}