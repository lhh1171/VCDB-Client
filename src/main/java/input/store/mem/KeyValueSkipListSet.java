/**
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package input.store.mem;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;


public class KeyValueSkipListSet implements NavigableSet<KV> {
  private final ConcurrentNavigableMap<KV, KV> delegatee;

  KeyValueSkipListSet(final KV.KVComparator c) {
    this.delegatee = new ConcurrentSkipListMap(c);
  }

  KeyValueSkipListSet(final ConcurrentNavigableMap<KV, KV> m) {
    this.delegatee = m;
  }

  public KV ceiling(KV e) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public Iterator<KV> descendingIterator() {
    return this.delegatee.descendingMap().values().iterator();
  }

  public NavigableSet<KV> descendingSet() {
    throw new UnsupportedOperationException("Not implemented");
  }

  public KV floor(KV e) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public SortedSet<KV> headSet(final KV toElement) {
    return headSet(toElement, false);
  }

  public NavigableSet<KV> headSet(final KV toElement,
      boolean inclusive) {
    return new KeyValueSkipListSet(this.delegatee.headMap(toElement, inclusive));
  }

  public KV higher(KV e) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public Iterator<KV> iterator() {
    return this.delegatee.values().iterator();
  }

  public KV lower(KV e) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public KV pollFirst() {
    throw new UnsupportedOperationException("Not implemented");
  }

  public KV pollLast() {
    throw new UnsupportedOperationException("Not implemented");
  }

  public SortedSet<KV> subSet(KV fromElement, KV toElement) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public NavigableSet<KV> subSet(KV fromElement,
      boolean fromInclusive, KV toElement, boolean toInclusive) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public SortedSet<KV> tailSet(KV fromElement) {
    return tailSet(fromElement, true);
  }

  public NavigableSet<KV> tailSet(KV fromElement, boolean inclusive) {
    return new KeyValueSkipListSet(this.delegatee.tailMap(fromElement, inclusive));
  }

  public Comparator<? super KV> comparator() {
    throw new UnsupportedOperationException("Not implemented");
  }

  public KV first() {
    return this.delegatee.get(this.delegatee.firstKey());
  }

  public KV last() {
    return this.delegatee.get(this.delegatee.lastKey());
  }

  public boolean add(KV e) {
    return this.delegatee.put(e, e) == null;
  }

  public boolean addAll(Collection<? extends KV> c) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public void clear() {
    this.delegatee.clear();
  }

  public boolean contains(Object o) {
    //noinspection SuspiciousMethodCalls
    return this.delegatee.containsKey(o);
  }

  public boolean containsAll(Collection<?> c) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public boolean isEmpty() {
    return this.delegatee.isEmpty();
  }

  public boolean remove(Object o) {
    return this.delegatee.remove(o) != null;
  }

  public boolean removeAll(Collection<?> c) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException("Not implemented");
  }

  public KV get(KV kv) {
    return this.delegatee.get(kv);
  }

  public int size() {
    return this.delegatee.size();
  }

  public Object[] toArray() {
    throw new UnsupportedOperationException("Not implemented");
  }

  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException("Not implemented");
  }
}
