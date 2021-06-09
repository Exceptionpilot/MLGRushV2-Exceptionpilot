package net.exceptionpilot.mlgrush.location.types;

import lombok.Getter;

/**
 * @author Jonas | Exceptionpilot#5555
 * Copyright (c) 2021 Exceptionpilot. All rights reserved.
 * Created on 09.06.2021 «» 17:07
 * Class «» Locs
 **/

@Getter
public enum Locations {

    SPAWN("Setze den LobbySpawn"),
    QUEUE("Setzte die Warteschlange"),
    ;

    String description;

    Locations(String description) {
        this.description = description;
    }
}
