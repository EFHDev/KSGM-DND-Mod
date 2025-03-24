package org.kestrel.ksgm.common.Luck;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class LuckBasedDraw {
    private static final Random RANDOM = new Random();

    /**
     * Determines the "winner" between two players based on their luck.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     * @return The winning player (ServerPlayer), or null.
     */
    public static ServerPlayer  determineWinner(LivingEntity player1, LivingEntity player2) {
        if (!(player1 instanceof ServerPlayer serverPlayer1) || !(player2 instanceof ServerPlayer serverPlayer2)) {
            return null;
        }

        float player1Luck = serverPlayer1.getLuck();
        float player2Luck = serverPlayer2.getLuck();

        double baseChance = 0.5; // 50% base chance

        // Apply luck modifiers
        double player1AdjustedChance = baseChance + (player1Luck * 0.03); // 3% increase per point of luck.
        double player2AdjustedChance = baseChance + (player2Luck * 0.03);

        //clamp that hoe.
        player1AdjustedChance = Math.max(0, Math.min(1, player1AdjustedChance));
        player2AdjustedChance = Math.max(0, Math.min(1, player2AdjustedChance));

        // RNG to determine the winner.
        double randomValue = RANDOM.nextDouble();

        if (randomValue < player1AdjustedChance) {
            if (randomValue < player2AdjustedChance){
                //if both players have a chance, then use what they rolled.
                if (player1AdjustedChance > player2AdjustedChance){
                    return serverPlayer1;
                } else if (player2AdjustedChance > player1AdjustedChance){
                    return serverPlayer2;
                } else{
                    return null; //true draw
                }
            }
            return serverPlayer1;
        } else if (randomValue < player2AdjustedChance){
            return serverPlayer2;
        } else {
            return null; // Draw, no winner
        }
    }
}
