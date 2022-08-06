package input.store.bloom;

public enum BloomType {
  /**
   * Bloomfilters disabled
   */
  NONE,
  /**
   * Bloom enabled with Table row as Key
   */
  ROW,
  /**
   * Bloom enabled with Table row & column (family+qualifier) as Key
   */
  ROWCOL
}