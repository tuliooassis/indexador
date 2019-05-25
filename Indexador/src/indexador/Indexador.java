/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package indexador;

import indice.estrutura.IndiceLight;
import indice.estrutura.IndiceSimples;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
        HashMap<String, Integer> mapWords = new HashMap<String, Integer>();
        
        //IndiceSimples is = new IndiceSimples();
        IndiceLight is = new IndiceLight(10000);
        try {
            for (File file : listFileTree(new File("../wikiSampleTest/"))) {
                BufferedReader in = new BufferedReader(new FileReader(file));
                if (!file.getName().endsWith(".html")) continue;
                
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
                
                System.out.println("Indexando arquivo "+file.getName());
                
                // contando palavras
                for (String word : content.split(" ")){
                    word = word.replaceAll("[^a-zA-Z]+", "").toLowerCase();
                    if (word.length() > 0){   
                        if (mapWords.containsKey(word))
                            mapWords.put(word, mapWords.get(word) + 1);
                        else
                            mapWords.put(word, 1);
                    }
                }
                
                for (String word : mapWords.keySet()){
                    is.index(word, Integer.parseInt(file.getName().replace(".html", "")), mapWords.get(word));
                }
            }
            
            is.concluiIndexacao();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter("../output.txt"));
            writer.write(is.toString());
            writer.close();
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
