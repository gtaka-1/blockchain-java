import java.util.ArrayList;

public class Blockchain{

  private ArrayList<Block> chain = new ArrayList<>();

  private Block lastBlock = null;


  public void addBlock(String data){

    Block b;

    if(lastBlock == null){
      b = new Block("0", data);
    }

    else{
      b = new Block(lastBlock.getHash(), data);
    }

    lastBlock = b;

    chain.add(b);
  }
}
