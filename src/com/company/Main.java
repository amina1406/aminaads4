package com.company;

import java.util.*;

import static java.util.stream.Stream.*;


class VertexPointMap<V>{
    private V given;
    private Map<VertexPointMap<V>, Double> adjacentVer = new HashMap<>();

    public VertexPointMap(V data) {
        this.given = data;
    }

    public void addAdjacentVer(VertexPointMap<V> pMapDest, double wei){
        adjacentVer.put(pMapDest, wei);

    }

    public Map<VertexPointMap<V>, Double> getAdjacentVer() {
        return adjacentVer;
    }

    public V getGiven() {
        return given;
    }
}

class BreadthFirstSearch<T> extends Search<T>{

    public BreadthFirstSearch(weiGraph<T> graph, T source) {
        super(new VertexPointMap<>(source));
        bfs(graph, source);
    }

    private void bfs(weiGraph<T> graph, T current) {
        mapSet.add(new VertexPointMap<>(current));
        Queue<VertexPointMap<T>> mapQueue = new LinkedList<>();
        mapQueue.add(new VertexPointMap<>(current));
        if (!mapQueue.isEmpty()) {
            do {
                VertexPointMap<T> v = mapQueue.remove();
                graph.map.get(graph.map.indexOf(v)).getAdjacentVer().keySet().stream().filter(vertex -> !mapSet.contains(vertex)).forEachOrdered(vertex -> {
                    mapSet.add(vertex);
                    vertexPointMap.put(vertex, v);
                    mapQueue.add(vertex);
                });
            } while (!mapQueue.isEmpty());
        }
    }
}

class DepthFirstSearch<T> extends Search<T> {

    public DepthFirstSearch(weiGraph<T> tWeiGraph, T origin) {
        super(new VertexPointMap<>(origin));
        dfs(tWeiGraph, origin);
    }

    private void dfs(weiGraph<T> tweiGraph, T pos) {
        mapSet.add(new VertexPointMap<>(pos));
        am++;

        VertexPointMap<T> cur = new VertexPointMap<>(pos);
        tweiGraph.map.get(tweiGraph.map.indexOf(cur)).getAdjacentVer().keySet().stream().filter(vertex -> !mapSet.contains(vertex)).forEach(vertex -> {
            vertexPointMap.put(vertex, new VertexPointMap<>(pos));
            dfs(tweiGraph, vertex.getGiven());
        });
    }

}

class Search<T> {
    protected int am;
    protected Set<VertexPointMap<T>> mapSet;
    protected Map<VertexPointMap<T>, VertexPointMap<T>> vertexPointMap;
    protected final VertexPointMap<T> origin;

    public Search(VertexPointMap<T> origin) {
        this.origin = origin;
        mapSet = new HashSet<>();
        vertexPointMap = new HashMap<>();
    }

    public boolean hasPathTo(VertexPointMap<T> v) {
        return mapSet.contains(v);
    }

    public Iterable<VertexPointMap<T>> pathTo(T v) {
        if (!hasPathTo(new VertexPointMap<>(v))) return null;
        LinkedList<VertexPointMap<T>> ls = new LinkedList<>();
        iterate(new VertexPointMap<>(v), i -> i != origin, i -> vertexPointMap.get(i)).forEachOrdered(ls::push);
        ls.push(origin);

        return ls;
    }

    public int getAm() {
        return am;
    }
}

class weiGraph<T> {
    private final boolean undirected;
    public ArrayList<VertexPointMap<T>> map = new ArrayList<>();
    public weiGraph() {
        this.undirected = true;
    }

    public weiGraph(boolean undirected) {
        this.undirected = undirected;
    }

    public void addVertex(T v) {
        map.add(new VertexPointMap<T>(v));
    }

    public void addEdge(T source, T dest, double weight) {
        if (hasVertex(source)) {
            addVertex(source);
            if (hasVertex(dest))
                addVertex(dest);
        } else if (hasVertex(dest))
            addVertex(dest);

        if (hasEdge(source, dest)
                || source.equals(dest) || !map.contains(new VertexPointMap<>(source)))
            return;
        map.get(map.indexOf(new VertexPointMap<>(source))).addAdjacentVer(new VertexPointMap<>(dest), weight);


        if (undirected)
            map.get(map.indexOf(new VertexPointMap<>(dest))).addAdjacentVer(new VertexPointMap<>(source), weight);
    }


    public boolean hasVertex(T v) {
        return !map.contains(new VertexPointMap<T>(v));
    }

    public boolean hasEdge(T source, T dest) {
        if (hasVertex(source)) return false;
        if (map.get(map.indexOf(new VertexPointMap<T>(source))).getAdjacentVer().containsKey(new VertexPointMap<T>(dest)))
            return true;
        else return false;
    }
}



public class Main {

    public static void main(String[] args) {
    }
}
