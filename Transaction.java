import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.HexFormat;
import java.nio.ByteBuffer;

public class Transaction {

  private final Wallet sender;

  private final Wallet receiver;

  private final double amount;

  private final long timeStamp;

  private final String transactionId;

  private byte[] signature;


  public Transaction(Wallet sender, Wallet receiver, double amount){

    this.sender = sender;
    this.receiver = receiver;
    this.amount = amount;
    this.timeStamp = System.currentTimeMillis();
    this.transactionId = calculateTransactionId();
  }

  private String calculateTransactionId(){
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");

      md.update(sender.getPublicKey().getEncoded());
      md.update(receiver.getPublicKey().getEncoded());
      md.update(ByteBuffer.allocate(Double.BYTES).putDouble(amount).array());
      md.update(ByteBuffer.allocate(Long.BYTES).putLong(timeStamp).array());

      byte[] rawBytes = md.digest();

      return HexFormat.of().formatHex(rawBytes);
    }

    catch(NoSuchAlgorithmException e){
      throw new RuntimeException(e);
    }
  }

  public void signTransaction(Wallet wallet){
    if(!wallet.getPublicKey().equals(sender.getPublicKey())){
      throw new IllegalArgumentException("Invalid Key");
    }
    this.signature = wallet.createSignature(this.transactionId);
  }

  public boolean verifySignature(){
    if(signature == null){
      return false;
    }

    try{
      Signature s = Signature.getInstance("SHA256withRSA");
      
      s.initVerify(sender.getPublicKey());

      s.update(this.transactionId.getBytes(StandardCharsets.UTF_8));

      return s.verify(this.signature);
    }
    catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public String getTransactionId(){
    return transactionId;
  }
}
