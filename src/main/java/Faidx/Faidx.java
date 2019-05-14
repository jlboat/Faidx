/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Faidx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Modified from Faidx example by Pierre Lindenbaum 
 * @author J. Lucas Boatwright
 */

/**
 * Index a FASTA file
 */
public class Faidx{   
    public static class SequenceRecord{
        String name=null;
        int sequenceLength=0;
        long byteIndex;
        int lineLength=-1;
    }
    public final List<SequenceRecord> sequenceIndices = new ArrayList<>();
    
    public Faidx(final File file) throws IOException{
        long byteOffset=0;
        FileReader fileReader = new FileReader(file);
        int asciiInteger;
        SequenceRecord sequenceRecord = null;
        while((asciiInteger=fileReader.read())!=-1) {
            if (asciiInteger == '>') {
                byteOffset++;
                StringBuilder stringBuilder = new StringBuilder();
                sequenceRecord = new SequenceRecord();
                boolean ws=false;
                // while it's not the end of the file
                while((asciiInteger = fileReader.read()) != -1 )
                {
                    byteOffset++;
                    if( asciiInteger=='\n') {
                        break;
                    }
                    if(!ws && Character.isWhitespace(asciiInteger)){ 
                        ws=true;
                    }
                    if(!ws){ 
                        stringBuilder.append((char)asciiInteger);
                    }
                }
                sequenceRecord.name=stringBuilder.toString();
                sequenceRecord.byteIndex=byteOffset;
                sequenceIndices.add(sequenceRecord);
            } else{
                int basesPerSequence = 0;
                
                do  {
                    byteOffset++;
                    if( asciiInteger == '\n') 
                        break;
                    basesPerSequence++;
                    asciiInteger = fileReader.read();
                } while(asciiInteger != -1);
                
                sequenceRecord.sequenceLength+=basesPerSequence;
                
                if(sequenceRecord.lineLength == -1){
                    sequenceRecord.lineLength = basesPerSequence;
                }
            } // end if-else
        }// end while
    }// end Faidx
    
    public void write(String[] args) throws IOException{
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(args[0]+".fai"))) {
            sequenceIndices.forEach((SequenceRecord ssr) -> {
                try {
                    writer.write(ssr.name + "\t" +
                            ssr.sequenceLength+"\t"+
                            ssr.byteIndex+"\t"+
                            ssr.lineLength+"\t"+
                            (ssr.lineLength+1)+"\n");
                } catch (IOException ex) {
                    Logger.getLogger(Faidx.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
    
    public static void main(String args[]) throws IOException {
        new Faidx(new File(args[0])).write(args);
    }
}

