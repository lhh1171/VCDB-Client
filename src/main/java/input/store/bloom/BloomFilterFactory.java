/*
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

package input.store.bloom;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class BloomFilterFactory {

  private static final Log LOG =
      LogFactory.getLog(BloomFilterFactory.class.getName());

  /** This class should not be instantiated. */
  private BloomFilterFactory() {}

  /**
   * Specifies the target error rate to use when selecting the number of keys
   * per Bloom filter.
   */
  public static final String IO_STOREFILE_BLOOM_ERROR_RATE =
      "io.storefile.bloom.error.rate";

  /**
   * Maximum folding factor allowed. The Bloom filter will be shrunk by
   * the factor of up to 2 ** this times if we oversize it initially.
   */
  public static final String IO_STOREFILE_BLOOM_MAX_FOLD =
      "io.storefile.bloom.max.fold";

  /**
   * For default (single-block) Bloom filters this specifies the maximum number
   * of keys.
   */
  public static final String IO_STOREFILE_BLOOM_MAX_KEYS =
      "io.storefile.bloom.max.keys";

  /** Master switch to enable Bloom filters */
  public static final String IO_STOREFILE_BLOOM_ENABLED =
      "io.storefile.bloom.enabled";

  /** Master switch to enable Delete Family Bloom filters */
  public static final String IO_STOREFILE_DELETEFAMILY_BLOOM_ENABLED =
      "io.storefile.delete.family.bloom.enabled";

  /**
   * Target Bloom block size. Bloom filter blocks of approximately this size
   * are interleaved with data blocks.
   */
  public static final String IO_STOREFILE_BLOOM_BLOCK_SIZE =
      "io.storefile.bloom.block.size";

  /** Maximum number of times a Bloom filter can be "folded" if oversized */
  private static final int MAX_ALLOWED_FOLD_FACTOR = 7;

}
