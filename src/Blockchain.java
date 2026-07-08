import java.util.ArrayList;

public class BlockChain {

  private ArrayList<Block> chain = new ArrayList<>();

  private ArrayList<Transaction> pendingTransactions = new ArrayList<Transaction>();

  private Miner miner = new Miner();

  private int difficulty = 4;

  public void addBlock(String data){

    Block b; 

    if(chain.isEmpty()){
      b = new Block("0");
    }

    else{
      b = new Block(chain.get(chain.size() - 1).getHash());
    }

    for(Transaction t : pendingTransactions){
      b.addTransactionToBlock(t);
    }

    miner.mine(b, difficulty);

    chain.add(b);
  }

  public boolean isChainValid(){
    
    Block b1;
    Block b2;

    if(chain.isEmpty()){
      return true;
    }

    if(!chain.get(0).getHash().equals(chain.get(0).calculateHash()) || !chain.get(0).getHash().startsWith("0".repeat(difficulty))){
      return false;
    }

    for(int i = 1; i < chain.size(); i++){
      b1 = chain.get(i);
      b2 = chain.get(i - 1);

      if(!b1.getPreviousHash().equals(b2.getHash()) || !b1.getHash().equals(b1.calculateHash()) || !b1.getHash().startsWith("0".repeat(difficulty))){
        return false;
      }

    }
    return true;
  }

  private void addPendingTransactions(Transaction transaction){
    if(transaction.verifySignature()){
      pendingTransactions.add(transaction);
    }
    else{
      throw new IllegalArgumentException("Invalid Transaction Signature");
    }
  }
}
