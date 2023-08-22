package parser;

import exception.FdFExtensionException;
import exception.FdFMapFormatException;
import map.FdFMap;
import map.FdFTransform;
import map.FdFVertex;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

public class FdFParser {

    public static FdFMap parse(File file) {
        BufferedReader reader = null;
        List<FdFVertex> vertices = null;
        String line = null;
        String token = null;
        StringTokenizer tokenizer = null;
        int x = 0;
        int y = 0;
        int z = 0;
        int c = 0;
        int width = -1;

        try {
            if (!file.getName().endsWith(".fdf"))
                throw new FdFExtensionException();
            reader = new BufferedReader(new FileReader(file));
            vertices = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                tokenizer = new StringTokenizer(line);
                x = 0;
                while (tokenizer.hasMoreTokens()) {
                    token = tokenizer.nextToken();
                    if (Pattern.matches("[0-9]*,(0x|0X)[A-Fa-f0-9]{1,6}", token)) {
                        String[] values = token.split(",(0x|0X)");
                        z = Integer.parseInt(values[0]);
                        c = Integer.parseInt(values[1], 16);
                        vertices.add(new FdFVertex(x++, y, z, c));
                    } else if (Pattern.matches("^[0-9]*$", token)) {
                        z = Integer.parseInt(token);
                        vertices.add(new FdFVertex(x++, y, z));
                    } else throw new FdFMapFormatException();
                }
                if (width == -1) width = x + 1;
                else if (width != x + 1) throw new FdFMapFormatException();
                y++;
            }
            reader.close();
            FdFTransform.translateX(vertices, (x / 2) * (-1));
            FdFTransform.translateY(vertices, (y / 2) * (-1));
            return new FdFMap(x, y, vertices);
        } catch (FdFExtensionException e) {
            JOptionPane.showMessageDialog(null, "File extension is incorrect.",
                    "Parse failed.", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (FdFMapFormatException e) {
            JOptionPane.showMessageDialog(null, "Map format is incorrect.",
                    "Parse failed.", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found.",
                    "Parse failed.", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Map contains non-digit.",
                    "Parse failed.", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception has been occurred.",
                    "Parse failed.", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

}
