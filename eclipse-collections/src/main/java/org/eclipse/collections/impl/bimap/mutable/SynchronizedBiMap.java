/*
 * Copyright (c) 2016 Bhavana Hindupur.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.bimap.mutable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.ByteIterable;
import org.eclipse.collections.api.CharIterable;
import org.eclipse.collections.api.DoubleIterable;
import org.eclipse.collections.api.FloatIterable;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LongIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.ShortIterable;
import org.eclipse.collections.api.bimap.ImmutableBiMap;
import org.eclipse.collections.api.bimap.MutableBiMap;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.block.function.primitive.ByteFunction;
import org.eclipse.collections.api.block.function.primitive.CharFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.block.function.primitive.LongFunction;
import org.eclipse.collections.api.block.function.primitive.ShortFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.ordered.OrderedIterable;
import org.eclipse.collections.api.partition.set.PartitionMutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.collection.mutable.SynchronizedMutableCollection;
import org.eclipse.collections.impl.factory.BiMaps;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.map.AbstractSynchronizedMapIterable;
import org.eclipse.collections.impl.map.mutable.SynchronizedBiMapSerializationProxy;
import org.eclipse.collections.impl.set.mutable.SynchronizedMutableSet;
import org.eclipse.collections.impl.utility.LazyIterate;

public class SynchronizedBiMap<K, V> extends AbstractSynchronizedMapIterable<K, V> implements MutableBiMap<K, V>, Serializable
{
    private static final long serialVersionUID = 1L;

    protected SynchronizedBiMap(MutableBiMap<K, V> delegate)
    {
        super(delegate);
    }

    protected SynchronizedBiMap(MutableBiMap<K, V> delegate, Object lock)
    {
        super(delegate, lock);
    }

    /**
     * This method will take a MutableBiMap and wrap it directly in a SynchronizedBiMap.
     */
    public static <K, V> SynchronizedBiMap<K, V> of(MutableBiMap<K, V> map)
    {
        if (map == null)
        {
            throw new IllegalArgumentException("cannot create a SynchronizedBiMap for null");
        }
        return new SynchronizedBiMap<K, V>(map);
    }

    @Override
    protected MutableBiMap<K, V> getDelegate()
    {
        return (MutableBiMap<K, V>) super.getDelegate();
    }

    public V forcePut(K key, V value)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().forcePut(key, value);
        }
    }

    public MutableBiMap<K, V> asSynchronized()
    {
        return this;
    }

    public MutableBiMap<K, V> asUnmodifiable()
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".asUnmodifiable() not implemented yet");
    }

    public MutableBiMap<K, V> clone()
    {
        synchronized (this.lock)
        {
            return SynchronizedBiMap.of(this.getDelegate().clone());
        }
    }

    public MutableBiMap<K, V> tap(Procedure<? super V> procedure)
    {
        synchronized (this.lock)
        {
            this.getDelegate().tap(procedure);
            return this;
        }
    }

    public <K2, V2> MutableBiMap<K2, V2> collect(Function2<? super K, ? super V, Pair<K2, V2>> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collect(function);
        }
    }

    public <R> MutableBiMap<K, R> collectValues(Function2<? super K, ? super V, ? extends R> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectValues(function);
        }
    }

    public MutableSet<V> select(Predicate<? super V> predicate)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().select(predicate);
        }
    }

    public MutableBiMap<K, V> select(Predicate2<? super K, ? super V> predicate)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().select(predicate);
        }
    }

    public <P> MutableSet<V> selectWith(Predicate2<? super V, ? super P> predicate, P parameter)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().selectWith(predicate, parameter);
        }
    }

    public <S> MutableSet<S> selectInstancesOf(Class<S> clazz)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().selectInstancesOf(clazz);
        }
    }

    public MutableSet<V> reject(Predicate<? super V> predicate)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().reject(predicate);
        }
    }

    public MutableBiMap<K, V> reject(Predicate2<? super K, ? super V> predicate)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().reject(predicate);
        }
    }

    public <P> MutableSet<V> rejectWith(Predicate2<? super V, ? super P> predicate, P parameter)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().rejectWith(predicate, parameter);
        }
    }

    public PartitionMutableSet<V> partition(Predicate<? super V> predicate)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().partition(predicate);
        }
    }

    public <P> PartitionMutableSet<V> partitionWith(Predicate2<? super V, ? super P> predicate, P parameter)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().partitionWith(predicate, parameter);
        }
    }

    public <V1> RichIterable<V1> collect(Function<? super V, ? extends V1> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collect(function);
        }
    }

    public BooleanIterable collectBoolean(BooleanFunction<? super V> booleanFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectBoolean(booleanFunction);
        }
    }

    public ByteIterable collectByte(ByteFunction<? super V> byteFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectByte(byteFunction);
        }
    }

    public CharIterable collectChar(CharFunction<? super V> charFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectChar(charFunction);
        }
    }

    public DoubleIterable collectDouble(DoubleFunction<? super V> doubleFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectDouble(doubleFunction);
        }
    }

    public FloatIterable collectFloat(FloatFunction<? super V> floatFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectFloat(floatFunction);
        }
    }

    public IntIterable collectInt(IntFunction<? super V> intFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectInt(intFunction);
        }
    }

    public LongIterable collectLong(LongFunction<? super V> longFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectLong(longFunction);
        }
    }

    public ShortIterable collectShort(ShortFunction<? super V> shortFunction)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectShort(shortFunction);
        }
    }

    public <P, V1> RichIterable<V1> collectWith(Function2<? super V, ? super P, ? extends V1> function, P parameter)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectWith(function, parameter);
        }
    }

    public <V1> RichIterable<V1> collectIf(Predicate<? super V> predicate, Function<? super V, ? extends V1> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().collectIf(predicate, function);
        }
    }

    public <V1> RichIterable<V1> flatCollect(Function<? super V, ? extends Iterable<V1>> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().flatCollect(function);
        }
    }

    public <V1> MutableSetMultimap<V1, V> groupBy(Function<? super V, ? extends V1> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().groupBy(function);
        }
    }

    public <V1> MutableSetMultimap<V1, V> groupByEach(Function<? super V, ? extends Iterable<V1>> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().groupByEach(function);
        }
    }

    public MutableSetMultimap<V, K> flip()
    {
        synchronized (this.lock)
        {
            return this.getDelegate().flip();
        }
    }

    public MutableBiMap<K, V> newEmpty()
    {
        synchronized (this.lock)
        {
            return this.getDelegate().newEmpty();
        }
    }

    public MutableBiMap<V, K> inverse()
    {
        synchronized (this.lock)
        {
            return new SynchronizedBiMap<V, K>(this.getDelegate().inverse(), this.lock);
        }
    }

    public MutableBiMap<V, K> flipUniqueValues()
    {
        synchronized (this.lock)
        {
            return this.getDelegate().flipUniqueValues();
        }
    }

    public RichIterable<K> keysView()
    {
        return LazyIterate.adapt(this.keySet());
    }

    public RichIterable<V> valuesView()
    {
        return LazyIterate.adapt(this.values());
    }

    public ImmutableBiMap<K, V> toImmutable()
    {
        synchronized (this.lock)
        {
            return this.getDelegate().toImmutable();
        }
    }

    public MutableSet<Pair<V, Integer>> zipWithIndex()
    {
        synchronized (this.lock)
        {
            return this.getDelegate().zipWithIndex();
        }
    }

    @Override
    public <VV> MutableBiMap<VV, V> groupByUniqueKey(Function<? super V, ? extends VV> function)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().groupByUniqueKey(function);
        }
    }

    /**
     * @deprecated in 8.0. Use {@link OrderedIterable#zip(Iterable)} instead.
     */
    @Deprecated
    public <S> MutableSet<Pair<V, S>> zip(Iterable<S> that)
    {
        synchronized (this.lock)
        {
            return this.getDelegate().zip(that);
        }
    }

    public MutableBiMap<K, V> withKeyValue(K key, V value)
    {
        synchronized (this.lock)
        {
            this.getDelegate().put(key, value);
            return this;
        }
    }

    public MutableBiMap<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        synchronized (this.lock)
        {
            for (Pair<? extends K, ? extends V> keyValue : keyValues)
            {
                this.getDelegate().put(keyValue.getOne(), keyValue.getTwo());
            }
            return this;
        }
    }

    public MutableBiMap<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        return this.withAllKeyValues(ArrayAdapter.adapt(keyValuePairs));
    }

    public MutableBiMap<K, V> withoutKey(K key)
    {
        synchronized (this.lock)
        {
            this.getDelegate().remove(key);
            return this;
        }
    }

    public MutableBiMap<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        synchronized (this.lock)
        {
            for (K key : keys)
            {
                this.getDelegate().removeKey(key);
            }
            return this;
        }
    }

    public Set<K> keySet()
    {
        synchronized (this.lock)
        {
            return SynchronizedMutableSet.of(this.getDelegate().keySet(), this.lock);
        }
    }

    public Collection<V> values()
    {
        synchronized (this.lock)
        {
            return SynchronizedMutableCollection.of(this.getDelegate().values(), this.lock);
        }
    }

    public Set<Entry<K, V>> entrySet()
    {
        synchronized (this.lock)
        {
            return SynchronizedMutableSet.of(this.getDelegate().entrySet(), this.lock);
        }
    }

    protected Object writeReplace()
    {
        return new SynchronizedBiMapSerializationProxy<K, V>(this.getDelegate());
    }
}
