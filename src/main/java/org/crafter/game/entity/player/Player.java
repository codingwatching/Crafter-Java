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
package org.crafter.game.entity.player;

import org.crafter.game.entity.entity_prototypes.Entity;

import static org.crafter.engine.collision_detection.world_collision.DebugCollisionBoxMeshFactory.generateCollisionBox;

public class Player extends Entity {
    private final String name;
    // Basically, the client player is the player that the client's camera gets glued to
    private final boolean clientPlayer;

    private final String collisionBoxMesh;

    public Player(String name, boolean clientPlayer) {
        this.name = name;
        this.clientPlayer = clientPlayer;
        collisionBoxMesh = generateCollisionBox(this.getSize());
    }

    public String getName() {
        return name;
    }

    public boolean isClientPlayer() {
        return clientPlayer;
    }

    public String getCollisionBoxMesh() {
        return collisionBoxMesh;
    }

    public void renderCollisionBox() {

    }
}
