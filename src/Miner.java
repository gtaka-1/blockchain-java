public class Miner {

  public void mine(Block b, int difficulty){

    String target = "0".repeat(difficulty);

    while(true){

      String hash = b.calculateHash();

      if(hash.startsWith(target)){
        b.setHash(hash);
        break;
      }

      b.incrementNonce();
    }
  }
}
