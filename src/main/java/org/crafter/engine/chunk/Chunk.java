package org.crafter.engine.chunk;

import org.crafter.engine.utility.NumberTools;
import org.joml.Vector2i;
import org.joml.Vector2ic;

/**
 * A chunk of map data. It is 16 wide, 128 high, and 16 long.
 *
 * Some notes:
 * << shifts to the left X amount, so << 3 of 0001 (1) in a byte now represents 1000 (8)
 * >> shifts to the right X amount
 *
 * Chunk represented as:
 * [16 bit] block | [4 bit lightLevel] | [4 bit blockState] | [ 8 bits left over for additional functionality]
 * This is literal, here is an exact representation:
 * | 0000 0000 0000 0000 | 0000 | 0000 | 0000 0000 |
 */
public class Chunk {

    NumberTools numberTools;

    private static final int arraySize = 16*16*128;
    private final Vector2ic position;



    // Consists of bit shifted integral values
    private final int[] data;

    public Chunk(int x, int y) {
        this(new Vector2i(x,y));
    }
    public Chunk(Vector2i position) {
        this.position = position;
        this.data = new int[arraySize];
        this.numberTools = new NumberTools();
    }

    public Vector2ic getPosition() {
        return position;
    }

    public int getX() {
        return position.x();
    }

    public int getY() {
        return position.y();
    }



    //Todo: idea: metadata arraymap
    //Todo: bitshift light, block id, state

    public void debugZero() {
        int test = 1 << 3;

        numberTools.printBits(test);
    }

    public int setBlockID(int input, int newID) {
        if (newID > 32_768) {
            throw new RuntimeException("Chunk: Attempted to exceed ushort limit for block ID in chunk (" + getX() + ", " + getY() + ")!");
        }


        return 1;
    }

    // Get integral bit data raw
    public int getBlockID(int input) {
        // Clear out right 16 bits
        return input >> 16 << 16;
    }
    public int getLightLevel(int input) {
        // Clear out left 16 bits
        input = input >> 16 << 16;
        // Clear out right 12 bits
        input = input << 12 >> 12;
        return input;
    }
    public int getBlockState(int input) {
        // Clear out left 20 bits
        input = input >> 20 << 20;
        // Clear out right 8 bits
        input = input << 8 >> 8;
        return input;
    }

    // Set integral bit data raw. Used for chaining. This is at the bottom because it's just boilerplate bit manipulation
    private int shiftBlock(int input) {
        return input << 16;
    }
    private int shiftLight(int input) {
        return input << 12;
    }
    private int shiftState(int input) {
        return input << 8;
    }


}
