import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;

public class Block {

  private String hash;

  private ArrayList<Transaction> transactions = new ArrayList<>();

  private String previousHash;

  private long timeStamp;

  private int nonce;

  public Block (String previousHash){ 
    this.previousHash = previousHash;
    this.timeStamp = System.currentTimeMillis();
    this.hash = calculateHash();
  }

  public String calculateHash(){
    try{
      StringBuilder st = new StringBuilder();
      for(Transaction tx : transactions){
        st.append(tx.getTransactionId());
      }

      String input = timeStamp + " " + previousHash + " " + st + "" + nonce;
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] rawBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

      return HexFormat.of().formatHex(rawBytes);
    }

    catch(NoSuchAlgorithmException e){
      throw new RuntimeException(e);
    }
  }

  public void addTransactionToBlock(Transaction transaction){
    transactions.add(transaction);
  }

  public void incrementNonce(){
    nonce ++;
  }

  public String getHash(){
    return hash;
  }

  public void setHash(String h){
    hash = h;
  }

  public String getPreviousHash(){
    return previousHash;
  }

  public ArrayList<Transaction> getTransactions(){
    return transactions;
  }
}
