import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

public class Block {

  private String hash;

  private String data;

  private String previousHash;

  private long timeStamp;

  public Block (String previousHash, String data){ 
    this.previousHash = previousHash;
    this.data = data;
    this.timeStamp = System.currentTimeMillis();
    this.hash = calculateHash();
  }

  private String calculateHash(){
    try{

    String input = timeStamp + " " + previousHash + " " + data;
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] rawBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));

    return HexFormat.of().formatHex(rawBytes);
    }

    catch(NoSuchAlgorithmException e){
      throw new RuntimeException(e);
    }
  }

  public String getHash(){
    return hash;
  }

  public String getData(){
    return data;
  }
}
