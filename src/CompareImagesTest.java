import java.io.IOException;

public class CompareImagesTest {

    public static void main(String[] args) throws IOException {
        CompareImages cti = new CompareImages("C:\\Users\\User\\Desktop\\image1.png", "C:\\Users\\User\\Desktop\\image2.png");
        cti.setParameters(10, 10);
        cti.compare();
        if (!cti.isIdentical()) {
            System.out.println("no match");
            CompareImages.savePNG(cti.getImageResult(), "C:\\Users\\User\\Desktop\\result.png");
        } else {
            System.out.println("match");
        }
    }
}
