public class Miner {

  public void mine(Block b, int dificulty){

    String target = "0".repeat(dificulty);

    while(true){

      String hash = b.calculateHash();

      if(hash.startsWith(target)){
        break;
      }

      b.incrementNonce();
    }
  }
}
