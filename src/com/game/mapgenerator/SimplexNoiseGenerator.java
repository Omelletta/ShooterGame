package com.game.mapgenerator;

import java.util.Random;

public class SimplexNoiseGenerator implements WorldGenerator {
    private int OCTAVES;
    private double ROUGHNESS;
    private double SCALE;
    public SimplexNoiseGenerator(int octaves, double roughness, double scale) {
        this.OCTAVES = octaves; // Number of Layers combined together to get a natural looking surface
        this.ROUGHNESS = roughness; // Increasing the of the range between -1 and 1, causing higher values eg more
        // rough terrain
        this.SCALE = scale; // Overall scaling of the terrain
    }
    @Override
    public double[][] createWorld(int width, int height) {
        return generateOctavedSimplexNoise(width, height);
    }

    private double[][] generateOctavedSimplexNoise(int width, int height) {
        double[][] totalNoise = new double[width][height];
        double layerFrequency = SCALE;
        double layerWeight = 1;
        double weightSum = 0;

        Random r = new Random();

        SimplexNoise.setSeed(r.nextInt(Integer.MAX_VALUE));

        // Summing up all octaves, the whole expression makes up a weighted average
        // computation where the noise with the lowest frequencies have the least effect

        for (int octave = 0; octave < OCTAVES; octave++) {
            // Calculate single layer/octave of simplex noise, then add it to total noise
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    totalNoise[x][y] += SimplexNoise.noise(x * layerFrequency, y * layerFrequency) * layerWeight;
                }
            }

            // Increase variables with each incrementing octave
            layerFrequency *= 2;
            weightSum += layerWeight;
            layerWeight *= ROUGHNESS;

        }
        return totalNoise;
    }
}
