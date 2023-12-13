package me.elliottleow.kabbalah.api.util.datastructures;

/**
 * Generic position interface.
 *
 * @param <T> the element type.
 */
public interface Position<T> {

  /**
   * Read element from this position.
   *
   * @return element at this position.
   */
  T get();
}
