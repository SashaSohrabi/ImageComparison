import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class CompareImages {
    private static BufferedImage image1, image2, imageResult;
    private static boolean isIdentical;
    private static int compareX, compareY;
    private static double sensitivity = 0.10;

    public CompareImages(String file1, String file2) throws IOException {
        image1 = loadJPG(file1);
        image2 = loadJPG(file2);
    }

    public void setParameters(int compareX, int compareY) {
        CompareImages.compareX = compareX;
        CompareImages.compareY = compareY;
    }

    public void compare() {
        imageResult = new BufferedImage(image2.getWidth(null), image2.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = imageResult.createGraphics();
        g2.drawImage(image2, null, null);
        g2.setColor(Color.RED);
        int blocksX = (image1.getWidth() / compareX);
        int blocksY = (image1.getHeight() / compareY);
        CompareImages.isIdentical = true;

        for (int y = 0; y < compareY; y++) {
            for (int x = 0; x < compareX; x++) {
                int result1[][] = convertTo2D(image1.getSubimage(x * blocksX, y * blocksY, blocksX - 1, blocksY - 1));
                int result2[][] = convertTo2D(image2.getSubimage(x * blocksX, y * blocksY, blocksX - 1, blocksY - 1));
                for (int i = 0; i < result1.length; i++) {
                    for (int j = 0; j < result1[0].length; j++) {
                        int diff = Math.abs(result1[i][j] - result2[i][j]);
                        if (diff / Math.abs(result1[i][j]) > sensitivity) {
                            g2.drawRect(x * blocksX, y * blocksY, blocksX - 1, blocksY - 1);
                            isIdentical = false;
                        }
                    }
                }
            }
        }
    }

    public BufferedImage getImageResult() {
        return imageResult;
    }

    public int[][] convertTo2D(BufferedImage subimage) {
        int width = subimage.getWidth();
        int height = subimage.getHeight();
        int[][] result = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = subimage.getRGB(col, row);
            }
        }
        return result;
    }

    public static BufferedImage loadJPG(String filename) throws IOException {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;

    }

    public static void savePNG(BufferedImage bimg, String filename) {
        try {
            File outputFile = new File(filename);
            ImageIO.write(bimg, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isIdentical() {
        return isIdentical;
    }
}
