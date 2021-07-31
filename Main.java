import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import javax.imageio.ImageIO;

public class Main extends IntiutivelyShortest{

    public static void main(String[] args) throws IOException {
        File orIm = new File("C:\\Users\\Alper\\Desktop\\maze3.png");
        BufferedImage img = null;
        BufferedImage gray = null;
        try {
            img = ImageIO.read(orIm);
            gray = ImageIO.read(orIm);

            for (int i = 0;i<img.getWidth();++i) {
                for (int j = 0;j<img.getHeight();++j) {
                    Color c = new Color(img.getRGB(i,j));
                    int r = c.getRed();
                    int g = c.getGreen();
                    int b = c.getBlue();
                    int a = c.getAlpha();
                    int gr = (r+g+b) / 3;
                    if (gr>=127) {
                        gr = 255;
                    }
                    if (gr<127) {
                        gr = 0;
                    }
                    Color gC = new Color(gr,gr,gr,a);
                    gray.setRGB(i,j,gC.getRGB());
                }
            }
        }catch (IOException e) {
            System.out.println(e);
        }

        LinkedList<Vertex> vertices = new LinkedList<>();
        for (int i = 0;i<gray.getWidth();++i) {
            for (int j = 0;j<gray.getHeight();++j) {
                Color c = new Color(gray.getRGB(i,j));
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int a = c.getAlpha();
                int gr = (r+g+b) / 3;
                if (gr>=127){
                    String name = i + "x" + j;
                    Vertex grVertex = new Vertex(1.0, name);
                    vertices.add(grVertex);
                }
            }
        }

        for (int i = 0;i<gray.getWidth();++i) {
            for (int j = 0;j<gray.getHeight();++j) {
                Color c = new Color(gray.getRGB(i,j));
                int r = c.getRed();
                int g = c.getGreen();
                int b = c.getBlue();
                int a = c.getAlpha();
                int gr = (r+g+b) / 3;
                Color gC = new Color(gr,gr,gr,a);
                String name = i+"x"+j;
                int[] plusI = {-1,0,1};
                int[] plusJ = {-1,0,1};
                Vertex grVertex = new Vertex(1.0,name);
                int idxUp = -1;
                for (int l = 0;l<vertices.size();++l) {
                    if (vertices.get(l).getName().equals(name)) {
                        idxUp = l;
                    }
                }
                if (gr==255){
                    for (int I : plusI) {
                        for (int J : plusJ) {
                            if (0 <= (i + I) && 0 <= (j + J) && (i + I) < gray.getWidth() && (j + J) < gray.getHeight()) {
                                Color cALT = new Color(gray.getRGB(i+I,j+J));
                                int rALT = cALT.getRed();
                                int gALT = cALT.getGreen();
                                int bALT = cALT.getBlue();
                                int aALT = cALT.getAlpha();
                                if ((!(J == 0 && I == 0)) && (rALT+gALT+bALT <= 255*3) && (rALT+gALT+bALT>=127*3)) {
                                    String altName = (i + I) + "x" + (j + J);
                                    int verticesSize = vertices.size();
                                    int idx = -1;
                                    for (int k = 0; k < verticesSize; ++k) {
                                        if (vertices.get(k).getName().equals(altName)) {
                                            idx = k;
                                        }
                                    }
                                    if ((idxUp >-1 && idx >-1)) {
                                        try{
                                            Edge edge = new Edge(new Vertex[]{vertices.get(idxUp), vertices.get(idx)}, 10.0);
                                            vertices.get(idxUp).addEdge(edge);
                                            vertices.get(idx).addEdge(edge);
                                            System.out.println(vertices.get(idx).getName()+" "+vertices.get(idx).getEdgesTCNames());
                                            System.out.println(vertices.get(idxUp).getName()+" "+vertices.get(idxUp).getEdgesTCNames());
                                        }catch (Exception e) {
                                            System.out.println(e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        for (Vertex v:vertices) {
            System.out.println(v.getName()+" "+v.getEdgesTCNames());
        }
        int verticesSize = vertices.size();
        int targetStart = -1;
        for (int i = 0;i<verticesSize;++i) {
            if (vertices.get(i).getName().equals("0x0")) {//directly write the desired pixel
                targetStart = i;
            }
        }

        int target = -1;
        for (int i = 0;i<verticesSize;++i) {
            if (vertices.get(i).getName().equals("51x39")) {//directly write the desired pixel
                target = i;
            }
        }
        System.out.println("at the end.");
        for (Vertex v:vertices) {
            System.out.println(v.getName()+" "+v.getEdgesTCNames());
        }
        UGraph graph = new UGraph(vertices);
        String output = "";
        if (target != -1)
            output = IntuitivenessPath(vertices.get(targetStart),vertices.get(target),graph);
        else System.out.println("Target is null!");

        LinkedList<int[]> outputArrInt = new LinkedList<>();

        String[] outputArr = output.split(" <= ");
        for (String str:outputArr) {
            System.out.println(str);
            String[] temp = str.split("x");
            outputArrInt.add(new int[]{Integer.parseInt(temp[0]),Integer.parseInt(temp[1])});
        }

        for (int[] arr:outputArrInt) {
            Color c = new Color(73,183,210,255);
            gray.setRGB(arr[0],arr[1],c.getRGB());
        }
        boolean successBlue = false;
        try{
            successBlue = ImageIO.write(gray, "PNG", new File("C:\\Users\\Alper\\Desktop\\outputBlue.png"));
        }catch (Exception e) {
            System.out.println(e);
        }
        int G = 29;
        int R = 119;
        int B = 209;
        for (int[] arr:outputArrInt) {
            if (R<255){
                R = (int)(Math.random()*255);
            }
            if (G<255){
                G = (int)(Math.random()*255);
            }
            if (B<255){
                B = (int)(Math.random()*255);
            }
            Color c = new Color(R, G, B,255);
            gray.setRGB(arr[0],arr[1],c.getRGB());

        }
        boolean successColorful = false;
        try{
            successColorful = ImageIO.write(gray, "PNG", new File("C:\\Users\\Alper\\Desktop\\outputColorful.png"));
        }catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(successColorful?"Colorful one has printed successfully.":"Colorful one couldn't printed!");
        System.out.println(successBlue?"Blue one has printed successfully.\n":"Blue one couldn't printed!\n");
    }
}
