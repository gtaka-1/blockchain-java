import java.nio.charset.StandardCharsets;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.KeyPair;

public class Wallet {

  private PrivateKey privateKey;

  private PublicKey publicKey;

  public Wallet(){
    try{
      KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
      keyGen.initialize(2048);

      KeyPair pair = keyGen.generateKeyPair();

      this.privateKey = pair.getPrivate();
      this.publicKey = pair.getPublic();
    }
    catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public byte[] createSignature(String data){
    try{
      Signature signature = Signature.getInstance("SHA256withRSA");

      signature.initSign(privateKey);

      signature.update(data.getBytes(StandardCharsets.UTF_8));

      return signature.sign();
    }

    catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public PublicKey getPublicKey(){
    return this.publicKey;
  }
}
