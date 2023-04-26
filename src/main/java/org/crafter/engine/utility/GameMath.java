package org.crafter.engine.utility;

import org.joml.Vector3f;
import org.joml.Vector3ic;

public final class GameMath {

    private static final Vector3f workerVector = new Vector3f();

    private GameMath(){}

    /**
     * Utilized for calculation of 2d movement in player's direction
     */
    public static Vector3ic getHorizontalDirection(Vector3ic inputRotationVector) {
        startWork();


        // Work goes here


        return getWork();
    }


    /**
     * This is simply to neaten up the plain english readability of this.
     * I also don't want to forget to clear out the old data
     */
    private static void startWork() {
        workerVector.zero();
    }
    /**
     * this is because I don't feel like casting the worker vector into 3ic every time
     */
    private static Vector3ic getWork() {
        return (Vector3ic) workerVector;
    }
}
