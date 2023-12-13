package me.elliottleow.kabbalah.api.util.datastructures;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import me.elliottleow.kabbalah.api.util.datastructures.exceptions.InsertionException;
import me.elliottleow.kabbalah.api.util.datastructures.exceptions.PositionException;
import me.elliottleow.kabbalah.api.util.datastructures.exceptions.RemovalException;


/**
 * An implementation of Graph ADT using incidence lists
 * for sparse graphs where most nodes aren't connected.
 *
 * @param <V> Vertex element type.
 * @param <E> Edge element type.
 */
public class SparseGraph<V, E> implements Graph<V, E> {

  Map<Vertex<V>, Set<Edge<E>>> incidentFrom = new HashMap<>();
  Map<Vertex<V>, Set<Edge<E>>> incidentTo = new HashMap<>();


  // Converts the vertex back to a VertexNode to use internally
  private VertexNode<V> convert(Vertex<V> v) throws PositionException {
    try {
      VertexNode<V> gv = (VertexNode<V>) v;
      if (gv.owner != this) {
        throw new PositionException();
      }
      return gv;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  // Converts and edge back to a EdgeNode to use internally
  private EdgeNode<E> convert(Edge<E> e) throws PositionException {
    try {
      EdgeNode<E> ge = (EdgeNode<E>) e;
      if (ge.owner != this) {
        throw new PositionException();
      }
      return ge;
    } catch (NullPointerException | ClassCastException ex) {
      throw new PositionException();
    }
  }

  @Override
  public Vertex<V> insert(V v) throws InsertionException {
    if (v == null) {
      throw new InsertionException();
    }
    VertexNode<V> vertexNode = new VertexNode<>(v);

    if (incidentFrom.containsKey(vertexNode)) {
      throw new InsertionException();
    }
    incidentFrom.put(vertexNode, new HashSet<>());
    incidentTo.put(vertexNode, new HashSet<>());

    return vertexNode;
  }

  @Override
  public Edge<E> insert(Vertex<V> from, Vertex<V> to, E e)
      throws PositionException, InsertionException {

    VertexNode<V> vertexFrom = convert(from);
    VertexNode<V> vertexTo = convert(to);

    if (!incidentTo.containsKey(vertexFrom) || !incidentFrom.containsKey(vertexTo)) {
      throw new PositionException();
    }


    EdgeNode<E> edge = new EdgeNode<>(vertexFrom, vertexTo, e);

    Set<Edge<E>> edgesFrom = incidentFrom.get(vertexFrom);
    Set<Edge<E>> edgesTo = incidentTo.get(vertexTo);

    if (vertexFrom.equals(vertexTo) || edgesFrom.contains(edge) || edgesTo.contains(edge)) {
      throw new InsertionException();
    }

    edgesFrom.add(edge);
    edgesTo.add(edge);

    return edge;
  }

  @Override
  public V remove(Vertex<V> v) throws PositionException, RemovalException {
    if (v == null) {
      throw new PositionException();
    }
    Set<Edge<E>> edgesFrom = incidentFrom.get(convert(v));
    Set<Edge<E>> edgesTo = incidentTo.get(convert(v));
    if (edgesFrom == null) {
      throw new PositionException();
    } else if (!edgesFrom.isEmpty() || !edgesTo.isEmpty()) {
      throw new RemovalException();
    }
    incidentFrom.remove(convert(v));
    incidentTo.remove(convert(v));

    return v.get();
  }

  @Override
  public E remove(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    EdgeNode<E> edge = convert(e);
    Set<Edge<E>> edgesFrom = incidentFrom.get(edge.from);
    Set<Edge<E>> edgesTo = incidentFrom.get(edge.from);
    if (!edgesFrom.contains(edge) || !edgesTo.contains(edge)) {
      throw new PositionException();
    }
    edgesFrom.remove(edge);
    edgesTo.remove(edge);

    return edge.data;
  }

  @Override
  public HashSet<Vertex<V>> vertices() {
    return new HashSet(Collections.unmodifiableSet(incidentFrom.keySet()));
  }


  @Override
  public Iterable<Edge<E>> edges() {
    return new EdgeIterator();
  }

  private class EdgeIterator implements Iterator<Edge<E>>, Iterable<Edge<E>> {


    Iterator<Map.Entry<Vertex<V>, Set<Edge<E>>>> vertexIterator;
    Iterator<Edge<E>> edgeIterator;

    EdgeIterator() {
      vertexIterator = incidentFrom.entrySet().iterator();
      if (vertexIterator.hasNext()) {
        edgeIterator = vertexIterator.next().getValue().iterator();
      }
    }

    @Override
    public boolean hasNext() {
      if (edgeIterator == null) {
        return false;
      }
      if (!edgeIterator.hasNext() && vertexIterator.hasNext()) {
        edgeIterator = vertexIterator.next().getValue().iterator();
        return hasNext();
      }
      return edgeIterator.hasNext();
    }

    @Override
    public Edge<E> next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return edgeIterator.next();


    }

    @Override
    public Iterator<Edge<E>> iterator() {
      return this;
    }
  }


  @Override
  public Iterable<Edge<E>> outgoing(Vertex<V> v) throws PositionException {
    return Collections.unmodifiableSet(incidentFrom.get(convert(v)));
  }

  @Override
  public Iterable<Edge<E>> incoming(Vertex<V> v) throws PositionException {
    return Collections.unmodifiableSet(incidentTo.get(convert(v)));
  }

  @Override
  public Vertex<V> from(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    return convert(e).from;
  }

  @Override
  public Vertex<V> to(Edge<E> e) throws PositionException {
    if (e == null) {
      throw new PositionException();
    }
    return convert(e).to;
  }

  @Override
  public void label(Vertex<V> v, Object l) throws PositionException {
    convert(v).label = l;
  }

  @Override
  public void label(Edge<E> e, Object l) throws PositionException {
    convert(e).label = l;
  }

  @Override
  public Object label(Vertex<V> v) throws PositionException {
    return convert(v).label;
  }

  @Override
  public Object label(Edge<E> e) throws PositionException {
    return convert(e).label;
  }

  @Override
  public void clearLabels() {
    for (Vertex<V> vertex : vertices()) {
      convert(vertex).label = null;
    }
    for (Edge<E> edge : edges()) {
      convert(edge).label = null;
    }
  }

  @Override
  public String toString() {
    GraphPrinter<V, E> gp = new GraphPrinter<>(this);
    return gp.toString();
  }
  
  public boolean isEmpty() {
	  return incidentFrom.keySet().isEmpty();
  }



  // Class for a vertex of type V
  private final class VertexNode<VDataT> implements Vertex<VDataT> {
    VDataT data;
    Graph<V, E> owner;
    Object label;

    VertexNode(VDataT v) {
      this.data = v;
      this.label = null;
      this.owner = SparseGraph.this;
    }

    @Override
    public boolean equals(Object vertex) {
      if (vertex instanceof VertexNode) {
        //unchecked cast is okay because I already checked it is a VertexNode
        //the wildcard will always be the type of the vertex
        VertexNode<?> node = (VertexNode<?>) vertex;
        return node.data.equals(this.data) && node.owner.equals(owner);
      }
      return super.equals(vertex);
    }

    @Override
    public int hashCode() {
      return data.hashCode();
    }


    @Override
    public VDataT get() {
      return this.data;
    }
  }

  //Class for an edge of type E
  private final class EdgeNode<EDataT> implements Edge<EDataT> {
    EDataT data;
    Graph<V, E> owner;
    VertexNode<V> from;
    VertexNode<V> to;
    Object label;

    // Constructor for a new edge
    EdgeNode(VertexNode<V> f, VertexNode<V> t, EDataT e) {
      this.from = f;
      this.to = t;
      this.data = e;
      this.label = null;
      this.owner = SparseGraph.this;
    }

    @Override
    public boolean equals(Object edge) {
      if (edge instanceof EdgeNode) {
        //unchecked cast is okay because I already checked it is a VertexNode
        //the wildcard will always be the type of the vertex
        EdgeNode<?> node = (EdgeNode<?>) edge;
        return node.owner.equals(owner) && node.to.equals(to) && node.from.equals(from);
      }
      return super.equals(edge);
    }

    @Override
    public int hashCode() {
      return from.hashCode() - to.hashCode();
    }

    @Override
    public EDataT get() {
      return this.data;
    }
  }
}
