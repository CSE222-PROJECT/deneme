package CityGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CityMap {

    private List<Vertex> nodes;
    private List<Edge> edges;
    private Graph graph;
    private DijkstraAlgorithm dijkstra;
    public CityMap(){
        nodes = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        readVertex();

        readEdges();

        graph = new Graph(nodes, edges);
        dijkstra = new DijkstraAlgorithm(graph);
        // Bütün şehirlere İstanbul üzerinden gönderim olacağı için
        // başlangıç yeri olarak istanbul seçildi
        dijkstra.execute(nodes.get(findIndex("İstanbul")));
    }

    public String getShortestPath(String city){
        String string ="";
        int index = -1;
        index = findIndex(city);
        if(index==-1){
            System.out.println("Girilen Şehir yanlış veya hatalı!");
            return "";
        }
        LinkedList<Vertex> path = dijkstra.getPath(nodes.get(index));

        for (Vertex vertex : path) {
            string += vertex+" ";
        }
        return string;
    }

    private void readEdges(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/CityGraph/CityMap.csv"));
            String line;
            while ( (line = in.readLine() ) != null) {
                String[] tokens = line.split(",");
                edges.add(new Edge(nodes.get(findIndex(tokens[0])),nodes.get(findIndex(tokens[1])),Integer.parseInt(tokens[2])));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private void readVertex(){
        try {
            BufferedReader in = new BufferedReader(new FileReader("src/CityGraph/Sehirler.txt"));
            String line;
            while ( (line = in.readLine() ) != null) {
                nodes.add(new Vertex(line));
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    private int findIndex(String city){
        int index = -1;
        for (int i=0; i<nodes.size(); i++)
            if(nodes.get(i).getName().equals(city)){
                index = i;
                break;
            }
        if(index==-1)
            System.out.println("Şehir Bulunamadı HATA"+ city);
        return index;
    }








}
