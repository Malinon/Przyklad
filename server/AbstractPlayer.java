package chineseCheckers.server;

public abstract class AbstractPlayer {

    public void giveResponse(int n){};
    public byte[] getMove(){
        return null;
    };
    public void inform(byte player, byte part1, byte field1, byte part2, byte field2){

    }

    public void startRound(){}

    public byte getNumOfPlayers(){
        return 0;
    }

    public void close(){}
    public byte getNumOfBots(){
        return 0;
    }
   // public void countPlayers(int n){ }
}
