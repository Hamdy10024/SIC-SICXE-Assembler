package parserImpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import parserInterafces.IStatement;

public class Assembler {
  private List<IStatement> code;
  
  public Assembler() {
    
  }
  public Assembler(List<IStatement> code) throws IOException {
    this.code = code;
    getObjectFile(code);
  }
  public File getObjectFile(List<IStatement> code) throws IOException {
    String out = "";
    out += new HeaderRecord(code.get(0),code.get(code.size()-1))+"\n";

   int n = code.size()-1;
    for(int i=1;i<n;i++) {
      TextRecord tr = new TextRecord(code.get(i++));
      while(i<n && tr.push(code.get(i)))
        i++;
      out+=tr+"\n";
    }
    File Obj = new File("ObjectCode.txt");
    FileWriter writ = new FileWriter(Obj);
    writ.write(out);
    writ.close();
    return Obj;
    
  }
  public File getObjectFile() {
    return null;
    
  }
}
