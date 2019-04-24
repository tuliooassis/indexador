/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexador;

import indice.estrutura.IndiceLight;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author aluno
 */
public class Indexador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StringBuilder contentBuilder = new StringBuilder();
//        IndiceLight il = new IndiceLight();
        try {
            for (File file : listFileTree(new File("../wikiSample/"))) {
                BufferedReader in = new BufferedReader(new FileReader(file));
                String str;
                while ((str = in.readLine()) != null) {
                    contentBuilder.append(str);
                }
                in.close();
                String content = StringEscapeUtils.unescapeHtml4(
                        contentBuilder
                                .toString()
                                .replaceAll("<[^>]*>", "")
                );
                System.out.println(file.getName() + "content:" + content);
//                indexador.
                
            }
        } catch (IOException e) {
            System.err.print(e);
        }
    }

    public static Collection<File> listFileTree(File dir) {
        Set<File> fileTree = new HashSet<File>();
        if (dir == null || dir.listFiles() == null) {
            return fileTree;
        }
        for (File entry : dir.listFiles()) {
            if (entry.isFile()) {
                fileTree.add(entry);
            } else {
                fileTree.addAll(listFileTree(entry));
            }
        }
        return fileTree;
    }
    
}
