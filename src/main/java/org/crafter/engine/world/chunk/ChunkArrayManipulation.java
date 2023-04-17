package org.crafter.engine.world.chunk;

import org.joml.Vector3i;
import org.joml.Vector3ic;

import java.util.Arrays;

/**
 * The Basis for working with Chunk's internal Array.
 * Chunks are basically fancy arrays of data.
 * This class goes into Chunk, finalizing this snowball of inheritance.
 */
public abstract class ChunkArrayManipulation extends ChunkBitManipulation {

    // X
    private static final int width = 16;
    // Y
    static final int height = 128;
    // Z
    private static final int depth = 16;
    private static final int yStride = width * depth;
    private static final int arraySize = width * height * depth;

    // Consists of bit shifted integral values
    private final int[] data;

    public ChunkArrayManipulation() {
        this.data = new int[arraySize];
    }

    /**
     * Stream the new data into the chunk memory.
     * @param newData is an array of length 32_768 with bit-manipulated block data.
     */
    public void setData(int[] newData) {
        check(newData);
        System.arraycopy(newData, 0, data, 0, newData.length);
    }

    /**
     * Get a copy of the data array.
     * @return is a copy of the internal array of block data.
     */
    public int[] getData() {
        return Arrays.copyOf(data, data.length);
    }

    /**
     * Set a single block, think of this as minetest.set_node();
     * For bulk setting, it is currently recommended to use the array methods.
     * @param position is the 3D position in the internal array.
     * @param blockData is the constructed bit manipulated integer that represents a block.
     */
    public void setBlockData(Vector3ic position, int blockData) {
        check(position);
        data[positionToIndex(position)] = blockData;
    }

    /**
     * Set a single block, think of this as minetest.set_node();
     * For bulk setting, it is currently recommended to use the array methods.
     * @param index is the 1D index in the internal array.
     * @param blockData is the constructed bit manipulated integer that represents a block.
     */
    public void setBlockData(int index, int blockData) {
        check(index);
        data[index] = blockData;
    }

    /**
     * Get a single block, think of this as minetest.get_node();
     * For bulk getting, it is currently recommended to use the array methods.
     * @param index is the 1D position in the internal array.
     * @return is the bit manipulated data value.
     */
    public int getBlockData(int index) {
        check(index);
        return data[index];
    }

    /**
     * Get a single block, think of this as minetest.get_node();
     * For bulk getting, it is currently recommended to use the array methods.
     * @param position is the 3D position in the internal array.
     * @return is the bit manipulated data value.
     */
    public int getBlockData(Vector3ic position) {
        check(position);
        return data[positionToIndex(position)];
    }

    public int positionToIndex(Vector3ic position) {
        return (position.y() * yStride) + (position.z() * depth) + position.x();
    }

    public Vector3ic indexToPosition(int index) {
        return new Vector3i(
                index % width,
                (index / yStride) % height,
                (index / depth) % depth
        );
    }

    private void check(int[] array) {
        if (!boundsCheck(array)) {
            throw new RuntimeException("ChunkArrayManipulation: Tried to set internal data to an array length of (" + array.length + ")!");
        }
    }
    private void check(int index) {
        if (!boundsCheck(index)) {
            throw new RuntimeException("ChunkArrayManipulation: Index (" + index + ") is out of bounds!");
        }
    }
    private void check(Vector3ic position) {
        if (!boundsCheck(position)) {
            throw new RuntimeException("ChunkArrayManipulation: Position (" + position.x() + ", " + position.y() + ", " + position.z() + ") is out of bounds!");
        }
    }
    private boolean boundsCheck(int[] array) {
        return array.length == arraySize;
    }
    private boolean boundsCheck(Vector3ic position) {
        return position.x() >= 0 && position.x() < width &&
                position.y() >= 0 && position.y() < height &&
                position.z() >= 0 && position.z() < depth;
    }
    private boolean boundsCheck(int index) {
        return index >= 0 && index < arraySize;
    }

    /**
     * This makes it easier to create data and work with chunks!
     */
    public int getArraySize() {
        return arraySize;
    }
}
