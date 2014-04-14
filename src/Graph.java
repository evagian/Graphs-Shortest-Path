package assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
	public static Map<Integer, Integer> sortV(Map<Integer, Integer> map) {
        List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {

            public int compare(Map.Entry<Integer, Integer> ma, Map.Entry<Integer, Integer> mb) {
                return (mb.getValue()).compareTo(ma.getValue());
            }
        });

        Map<Integer, Integer> apotelesma = new LinkedHashMap<Integer, Integer>();
        for (Map.Entry<Integer, Integer> entry : list) {
            apotelesma.put(entry.getKey(), entry.getValue());
        }
        return apotelesma;
    }

	public ArrayList<ArrayList<Integer>> algorithmosDijkstra (HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer,HashMap<Integer,Integer>> weightedEdges, Integer s) {
	
		ArrayList<ArrayList<Integer>> apotelesmaDijkstra;
		ArrayList<Integer> dist;
		ArrayList<Integer> pred;
		HashMap<Integer,Integer> listItem;

		apotelesmaDijkstra = new ArrayList<ArrayList<Integer>>();
		dist = new ArrayList<Integer>();
		pred = new ArrayList<Integer>();	

		listItem = new HashMap<Integer,Integer>();
//		pq = PriorityQueue

		int u;

		for (Integer v : graph.keySet()){
			pred.add(v, -1);
			if (v != s){				
				dist.add(v,10^10);					//enas polu megalos arithmos
			}
			else{
				dist.add(v,0);				
			}
			listItem.put(v, dist.get(v)); //eisagei to v stin pq

		}

		sortV(listItem);

		//afairei to prwto stoixeio apo thn oura
		while (listItem.size() != 0){
			u = listItem.keySet().iterator().next();
			listItem.remove(u);
			
			if(u>=0){
				for (Integer v : graph.get(u)) {				//antiproswpeuei th lista
					if (dist.get(v) > Math.abs( dist.get(u)+weightedEdges.get(u).get(v))){
						dist.set(v, dist.get(u)+weightedEdges.get(u).get(v));
						pred.set(v, u);	
						listItem.put(v, dist.get(v));
						sortV(listItem);
					}
				}
			}
		}

		apotelesmaDijkstra.add(pred);
		apotelesmaDijkstra.add(dist);
		return apotelesmaDijkstra;
	}

	public ArrayList<int[][]> algorithmosShortestPath (HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer,HashMap<Integer,Integer>> weightedEdges, Integer s) {
	
		ArrayList<int[][]> apotelesmaShortestPath = new ArrayList<int[][]>();
		ArrayList<ArrayList<Integer>> apotelesmaDijkstra = new ArrayList<ArrayList<Integer>>();
		int[][] dist = new int[graph.size()][graph.size()];
		int[][] pred = new int[graph.size()][graph.size()];	

		for (Integer u : graph.keySet()){
			for (Integer v : graph.keySet()){
				pred[u][v] = -1;
				dist[u][v] = 0;
			}
		}

		for (Integer u : graph.keySet()){
			apotelesmaDijkstra = new ArrayList<ArrayList<Integer>>();

			apotelesmaDijkstra = algorithmosDijkstra(graph, weightedEdges, u);
			for (int i=0 ; i<apotelesmaDijkstra.size() ; i++){
				for (int j=0 ; j<apotelesmaDijkstra.get(i).size() ; j++){
					if (i==0)
						pred[u][j] = apotelesmaDijkstra.get(i).get(j);
					else
						dist[u][j] = apotelesmaDijkstra.get(i).get(j);
				}
			}	
		}

		apotelesmaShortestPath.add(pred);
		apotelesmaShortestPath.add(dist);
		return apotelesmaShortestPath;
	}


	public int DiametrosGraphou (HashMap<Integer, ArrayList<Integer>> graph, HashMap<Integer,HashMap<Integer,Integer>> weightedEdges, Integer s) {
			
		ArrayList<ArrayList<Integer>> apotelesmaDijkstra = new ArrayList<ArrayList<Integer>>();
		int[][] dist = new int[graph.size()][graph.size()];
		int[][] pred = new int[graph.size()][graph.size()];	
		int diametros = 0;

		for (Integer u : graph.keySet()){
			for (Integer v : graph.keySet()){
				pred[u][v] = -1;
				dist[u][v] = 0;
			}
		}

		for (Integer u : graph.keySet()){

			apotelesmaDijkstra = algorithmosDijkstra(graph, weightedEdges, u);
			for (int i=0 ; i<apotelesmaDijkstra.size() ; i++){
				for (int j=0 ; j<apotelesmaDijkstra.get(i).size() ; j++){
					if (i==0){
						pred[u][j] = apotelesmaDijkstra.get(i).get(j);
					}
					else{
						dist[u][j] = apotelesmaDijkstra.get(i).get(j);
						if (diametros < dist[u][j])
							diametros = dist[u][j];
					}
				}
			}	
		}

		return diametros;
	}

	public static void main(String[] args) throws IOException {
	    
	    if (args.length >= 2 && args.length <= 6){ 
	    	
	    	int an = 0;				
	    	int start = 0;
	    	boolean directed = true;
	    	String scan;
			Graph graph = new Graph();
	
			ArrayList<ArrayList<Integer>> apotelesmaDijkstra = new ArrayList<ArrayList<Integer>>();
			ArrayList<int[][]> apotelesmaShortestPath = new ArrayList<int[][]>();
			int[][] apotelesmaDist;
			int[][] apotelesmaPred;
			Integer diametros = 0;
	
			Integer vFrom = null;
			Integer vTo = null;
			Integer weight = null;
			ArrayList<Integer> verTo = new ArrayList<Integer>();
			HashMap<Integer,Integer> edges = new HashMap<Integer,Integer>();
			HashMap<Integer, ArrayList<Integer>> gDirected = new HashMap<Integer,ArrayList<Integer>>();
			HashMap<Integer, ArrayList<Integer>> gUndirected = new HashMap<Integer,ArrayList<Integer>>();
			HashMap<Integer,HashMap<Integer,Integer>> weightedEdges = new HashMap<Integer,HashMap<Integer,Integer>>();
	        String s[] = null;
	
	
			if (args[an].trim().equals("-u")){
				directed = false;
				an++;
			}
			if (args[an].trim().equals("-s")){
				an++;
				if ((start = Integer.valueOf(args[an].trim())) >= 0){
					an++;				
			}
			}else if (args[an].trim().equals("-a")){
				an++;
			}
			else if (args[an].trim().equals("-d")){
				an++;
			}
			
	
			FileReader file = new FileReader(args[an]);
	        BufferedReader br = new BufferedReader(file);
	
	        if(!directed){								//Gia '-u'
	    		while((scan = br.readLine()) != null)
	    		{
	    			s = scan.split(" ");
	    			vFrom = Integer.valueOf(s[0]);
	    			vTo = Integer.valueOf(s[1]);
	    			if (s.length==3)
	    				weight = Integer.valueOf(s[2]);
	    			else
	    				weight = 1;
	    				
	    			if (gUndirected.containsKey(vFrom)){	
	    				gUndirected.get(vFrom).add(vTo);        				        			
	    			}
	    			else{								
	        			verTo = new ArrayList<Integer>();	     				
	        			verTo.add(vTo);        				
	    				gUndirected.put(vFrom, verTo);        				        				
	    			}
	    			
	    			if (weightedEdges.containsKey(vFrom)){	
	    				weightedEdges.get(vFrom).put(vTo, weight);
	    			}
	    			else{
	    				edges = new HashMap<Integer,Integer>();
	    				edges.put(vTo,weight);
	    				weightedEdges.put(vFrom, edges);            		
	    			}
	    			
	    			vFrom = Integer.valueOf(s[1]);
	    			vTo = Integer.valueOf(s[0]);
	    			if (s.length==3)
	    				weight = Integer.valueOf(s[2]);
	    			else
	    				weight = 1;
	    				
	    			if (gUndirected.containsKey(vFrom)){	
	    				gUndirected.get(vFrom).add(vTo);        				        			
	    			}
	    			else{								
	        			verTo = new ArrayList<Integer>();	       				
	        			verTo.add(vTo);        				
	    				gUndirected.put(vFrom, verTo);        				        				
	    			}
	    			
	    			if (weightedEdges.containsKey(vFrom)){	
	    				weightedEdges.get(vFrom).put(vTo, weight);
	    			}
	    			else{
	    				edges = new HashMap<Integer,Integer>();
	    				edges.put(vTo,weight);
	    				weightedEdges.put(vFrom, edges);            		
	    			}
	    		}
	        }
	    
	    else{        		
	    		while((scan = br.readLine()) != null)
	    		{
	    			
	    			s = scan.split(" ");
	    			vFrom = Integer.valueOf(s[0]);
	    			vTo = Integer.valueOf(s[1]);
	    			if (s.length==3)
	    				weight = Integer.valueOf(s[2]);
	    			else
	    				weight = 1;
	    			
	    			if (gDirected.containsKey(vFrom)){	
	    				gDirected.get(vFrom).add(vTo);        				        			
	    			}
	    			else{								
	        			verTo = new ArrayList<Integer>();	//diagrafh ths timhs tou node				
	        			verTo.add(vTo);        				
	    				gDirected.put(vFrom, verTo);        				        				
	    			}
	    			
	    			if (weightedEdges.containsKey(vFrom)){	
	    				weightedEdges.get(vFrom).put(vTo, weight);
	    			}
	    			else{
	    				edges = new HashMap<Integer,Integer>();
	    				edges.put(vTo,weight);
	    				weightedEdges.put(vFrom, edges);            		
	    			}
	    		}
	    	}
	        br.close();
	
	        an = 0;
			if (args[an].trim().equals("-u")){
				directed = false;
				an++;
			}
	
			if (args[an].trim().equals("-s")){
				an++;
				if ((start = Integer.valueOf(args[an].trim())) >= 0){
					if (directed)
						apotelesmaDijkstra = graph.algorithmosDijkstra(gDirected,weightedEdges,start);						
					else
						apotelesmaDijkstra = graph.algorithmosDijkstra(gUndirected,weightedEdges,start);						
					an++;
				}
			}
			else if (args[an].trim().equals("-a")){
				if (directed)
					apotelesmaShortestPath = graph.algorithmosShortestPath(gDirected,weightedEdges,start);						
				else
					apotelesmaShortestPath = graph.algorithmosShortestPath(gUndirected,weightedEdges,start);							
			}
			else if (args[an].trim().equals("-d")){
				if (directed)
					diametros = graph.DiametrosGraphou(gDirected,weightedEdges,start);						
				else
					diametros = graph.DiametrosGraphou(gUndirected,weightedEdges,start);						
			}
	
	
	
	//Ektupwsh apotelesmatwn	
			if (args[an].trim().equals("-a")){
				for (int i=0 ; i<apotelesmaShortestPath.size() ; i++){
					if (i==0){
						
						apotelesmaDist  = new int[apotelesmaShortestPath.get(i).length][apotelesmaShortestPath.get(i).length];
						for(int j=0 ; j<apotelesmaShortestPath.get(i).length ; j++){
							apotelesmaDist[j] = apotelesmaShortestPath.get(i)[j];
	
							System.out.print("[");
	
							for(int k=0 ; k<apotelesmaDist[j].length ; k++){						
								System.out.print(apotelesmaDist[j][k]+", ");
								}						
	
							System.out.println("]");
						}
	
					}
					else{
						
						apotelesmaPred  = new int[apotelesmaShortestPath.get(i).length][apotelesmaShortestPath.get(i).length];
						for(int j=0 ; j<apotelesmaShortestPath.get(i).length ; j++){
							apotelesmaPred[j] = apotelesmaShortestPath.get(i)[j];
	
							System.out.print("[");
	
							for(int k=0 ; k<apotelesmaPred[j].length ; k++){
								
								System.out.print(apotelesmaPred[j][k]+", ");
						}						
	
							System.out.println("]");
						}
					}
				}
			}
			else if (args[an].trim().equals("-d")){
				System.out.println("\nDiametros = "+diametros);
			}
			else{
				for (int i=0 ; i<apotelesmaDijkstra.size() ; i++){
					if (i==0)
						System.out.print("[");
					else
						System.out.print("[");
	
					for (int j=0 ; j<apotelesmaDijkstra.get(i).size() ; j++){
						if (i==0)
				
							System.out.print(apotelesmaDijkstra.get(i).get(j)+", ");
				
							System.out.print(apotelesmaDijkstra.get(i).get(j)+", ");
					}
					if (i==0)
						System.out.println("]");
					else
						System.out.println("]");
				}	
			}
			}else {			
			
			System.err.println("Plhktrologhste kati ths morfhs:\n '-u -s 0 traffic.txt' h \n '-u -s 0 traffic.txt > output.txt' ");
		}
	
}
}