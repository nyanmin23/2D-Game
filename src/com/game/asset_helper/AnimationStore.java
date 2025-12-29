package com.game.asset_helper;

import java.util.EnumMap;
import java.util.Map;

public class AnimationStore {
    private final Map<ActionStore.PlayerAction, Animation> playerAnimations = new EnumMap<>(ActionStore.PlayerAction.class);

}
