package org.kestrel.ksgm.Server.Research;

/*
 Copyright (c) Kestrel0 2025. CC BY-NC-SA 4.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchObject {
    private String id;
    private String name;
    private String description;
    private int requiredResearchPoints;
    private int currentResearchPoints;
    private ResearchStatus status;
    private List<String> prerequisites = new ArrayList<>();
    private List<String> unlockedRecipes = new ArrayList<>();
    private Map<String, Integer> requiredItems = new HashMap<>();

    /**
     * Enum representing the current status of research
     */
    public enum ResearchStatus {
        LOCKED,       // Research not available yet (prerequisites not met)
        AVAILABLE,    // Available to research but not started
        IN_PROGRESS,  // Research has been started
        COMPLETED     // Research is complete
    }

    /**
     * Constructor for a research object
     */
    public ResearchObject(String id, String name, String description, int requiredPoints) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.requiredResearchPoints = requiredPoints;
        this.currentResearchPoints = 0;
        this.status = ResearchStatus.AVAILABLE;
    }

    /**
     * Add a required item for research
     *
     * @param itemId The ID of the required item
     * @param amount The amount of the item required
     */
    public void addRequiredItem(String itemId, int amount) {
        requiredItems.put(itemId, amount);
    }

    /**
     * Add a prerequisite research that must be completed before this one
     *
     * @param researchId The ID of the prerequisite research
     */
    public void addPrerequisite(String researchId) {
        prerequisites.add(researchId);
    }

    /**
     * Add a recipe that will be unlocked when this research is completed
     *
     * @param recipeId The ID of the recipe to unlock
     */
    public void addUnlockedRecipe(String recipeId) {
        unlockedRecipes.add(recipeId);
    }

    /**
     * Contribute research points to this research object
     *
     * @param points The number of points to contribute
     * @return true if research was completed, false otherwise
     */
    public boolean contributeResearchPoints(int points) {
        if (status != ResearchStatus.IN_PROGRESS) {
            return false;
        }

        currentResearchPoints += points;
        if (currentResearchPoints >= requiredResearchPoints) {
            status = ResearchStatus.COMPLETED;
            return true;
        }
        return false;
    }

    /**
     * Start the research process if all required items are available
     *
     * @param availableItems Map of item IDs to quantities that are available
     * @return boolean indicating if research was started successfully
     */
    public boolean startResearch(Map<String, Integer> availableItems) {
        if (status != ResearchStatus.AVAILABLE) {
            return false;
        }

        // Check if all required items are available
        for (Map.Entry<String, Integer> entry : requiredItems.entrySet()) {
            String itemId = entry.getKey();
            int requiredAmount = entry.getValue();

            if (!availableItems.containsKey(itemId) || availableItems.get(itemId) < requiredAmount) {
                return false;
            }
        }

        // All items are available, so start research
        status = ResearchStatus.IN_PROGRESS;
        return true;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredResearchPoints() {
        return requiredResearchPoints;
    }

    public int getCurrentResearchPoints() {
        return currentResearchPoints;
    }

    public ResearchStatus getStatus() {
        return status;
    }

    public List<String> getPrerequisites() {
        return prerequisites;
    }

    public List<String> getUnlockedRecipes() {
        return unlockedRecipes;
    }

    public Map<String, Integer> getRequiredItems() {
        return requiredItems;
    }

    public void setStatus(ResearchStatus status) {
        this.status = status;
    }
}