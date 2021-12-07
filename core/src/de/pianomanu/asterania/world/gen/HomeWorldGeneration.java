package de.pianomanu.asterania.world.gen;

import de.pianomanu.asterania.AsteraniaMain;
import de.pianomanu.asterania.config.GameConfig;
import de.pianomanu.asterania.utils.perlin.PerlinNoiseAlgorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

public class HomeWorldGeneration {
    private static final Logger LOGGER = AsteraniaMain.getLogger();

    /**
     * This function calls {@link PerlinNoiseAlgorithm#createPerlinNoise} to create
     * pseudorandom height values. After that, the data is saved as CSV data to
     * the file with the name specified in the config-file at the parameter
     * "fileName", followed by the file extension ".csv".
     * <p>
     * The dimensions of the created CSV file can be edited in the config file.
     */
    public static float[][] main(int xPos, int yPos) {
        /*
        Create data array with the dimensions specified in the config file
         */
        int sizeX = GameConfig.WORLD_WIDTH;
        int sizeY = GameConfig.WORLD_HEIGHT;
        float[][] data = new float[sizeX][sizeY];

        int startX = xPos * sizeX;
        int startY = yPos * sizeY;
        /*
        We need a Random Number Generator, with or without seed, as specified
        in the config file
         */
        Random rand = AsteraniaMain.saveFile.getRandomNumberGenerator();//new Random(5);
        for (int x = startX; x < startX + sizeX; x++) {
            for (int y = startY; y < startY + sizeY; y++) {
                /*
                RNG: evenly distributed values between 0 and 100
                 */
                data[x - startX][y - startY] = PerlinNoiseAlgorithm.createPerlinNoise(x, y, rand.nextInt(40)).z;
            }
        }
        LOGGER.finest("Created random Z value array using Perlin Noise: " + Arrays.deepToString(data));
        /*
        Save resulting float array as CSV file
         */

        float lastMax = 0;
        float lastMin = 1;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (data[x][y] > lastMax)
                    lastMax = data[x][y];
                if (data[x][y] < lastMin)
                    lastMin = data[x][y];
            }
        }
        System.out.println(lastMax + ", " + lastMin);
        //System.out.println("Created random Z value array using Perlin Noise: " + Arrays.deepToString(data));
        //normalizeData(data, lastMax, lastMin);
        //System.out.println("Created random Z value array using Perlin Noise: " + Arrays.deepToString(data));
        return data;
    }

    private static float[][] normalizeData(float[][] data, float max, float min) {
        //normalize: minimum value = 0: subtract min from all values
        //           maximum value = 1: divide all values by max (after subtracting the minimum)
        for (int x = 0; x < GameConfig.WORLD_WIDTH; x++) {
            for (int y = 0; y < GameConfig.WORLD_HEIGHT; y++) {
                data[x][y] = (data[x][y] - min) / (max - min);
            }
        }
        return data;
    }
}
