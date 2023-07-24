/*
 Crafter - A blocky game (engine) written in Java with LWJGL.
 Copyright (C) 2023  jordan4ibanez

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.crafter.engine.collision_detection.world_collision;

import org.crafter.engine.world.block.BlockDefinitionContainer;
import org.crafter.engine.world.chunk.Chunk;
import org.crafter.engine.world.chunk.ChunkStorage;
import org.crafter.game.entity.entity_prototypes.Entity;
import org.joml.*;
import org.joml.Math;

import static org.crafter.engine.collision_detection.world_collision.AABBCollision.collideEntityToTerrain;
import static org.crafter.engine.delta.Delta.getDelta;
import static org.crafter.engine.utility.UtilityPrinter.println;

/**
 * Terrain physics is how an entity moves & collides with the world.
 * This is NOT thread safe!
 * Probably needs a better name.
 */
public final class Physics {

    // Max speed is the literal max velocity that an entity can move at after the delta has been factored in.
    private static final float MAX_VELOCITY = 0.05f;
    // Max delta is the literal max delta that can be factored into an entity. 5 FPS or 0.2f.
    private static final float MAX_DELTA = 0.2f;

    private static final Vector3f oldPosition = new Vector3f();
    private static final Vector3i minPosition = new Vector3i();
    private static final Vector3i maxPosition = new Vector3i();

    private Physics(){}

    public static void entityPhysics(Entity entity) {

        Vector3f currentPosition = entity.getPosition();

        // If the chunk is unloaded, the entity gets frozen in place until it's loaded
        if (!ChunkStorage.chunkIsLoaded(currentPosition)) {
            return;
        }

        float delta = getDelta();
        if (delta > MAX_DELTA) {
            delta = MAX_DELTA;
        }

        Vector3f currentVelocity = entity.getVelocity();
        oldPosition.set(currentPosition);
        currentVelocity.y -= delta * entity.getGravity();
        if (currentVelocity.y < -MAX_VELOCITY) {
            currentVelocity.y = -MAX_VELOCITY;
        }
        currentPosition.add(currentVelocity);

        final Vector2fc entitySize = entity.getSize();

        // Scan the local area to find out which blocks the entity collides with
        minPosition.set(
                (int) Math.floor(currentPosition.x() - entitySize.x()),
                (int) Math.floor(currentPosition.y()),
                (int) Math.floor(currentPosition.z() - entitySize.x())
        );
        maxPosition.set(
                (int) Math.floor(currentPosition.x() + entitySize.x()),
                (int) Math.floor(currentPosition.y()), //fixme: + entitySize.y()
                (int) Math.floor(currentPosition.z() + entitySize.x())
        );

        ChunkStorage.setBlockManipulatorPositions(minPosition, maxPosition);
        ChunkStorage.blockManipulatorReadData();

        final BlockDefinitionContainer blockDefinitionContainer = BlockDefinitionContainer.getMainInstance();

        // FIXME: This is just to stop the player entity from falling through the world while I prototype this
        final int blockID = ChunkStorage.getBlockID(currentPosition);

        //FIXME REMOVEME: This is the prototype test
        System.out.println("NEW RUN -------");

        if (blockID == 0) {
            return;
        }

        for (int x = minPosition.x(); x <= maxPosition.x(); x++) {
            for (int z = minPosition.z(); z <= maxPosition.z(); z++) {

                // Point API
                final String gottenName = ChunkStorage.getBlockName(x,minPosition.y(),z);

//                System.out.println("Point: (" + x + ", " + z + ") is (" + gottenName + ")");

                // Bulk API
                final int gottenRawData = ChunkStorage.getBlockManipulatorData(x,minPosition.y(), z);
                final int gottenBlockID = Chunk.getBlockID(gottenRawData);
                final String blockName = blockDefinitionContainer.getDefinition(gottenBlockID).getInternalName();
//
                System.out.println("Name at (" + x + ", " + z + ") is (" + blockName + ") MATCH? (" + gottenName + ")");
                System.out.println("ID is: " + gottenBlockID);

            }
        }

        // FIXME: This is just to stop the player entity from falling through the world while I prototype this
        Vector3f blockPosition = new Vector3f(currentPosition).floor();

        Vector2f blockSize = new Vector2f(1,1);

        //TODO This is where the new api comes in!

        boolean onGround = collideEntityToTerrain(
                oldPosition,
                currentPosition,
                entity.getSize(),
                blockPosition,
                blockSize
        );

        //FIXME: TEMPORARY
        if (onGround) {
            currentVelocity.y = -0.001f;
//            println("collision");
        } else {
//            println("no collision");
        }

    }
}
