package chineseCheckers.gameStaff;

import chineseCheckers.server.AbstractPlayer;

public class Bot extends AbstractPlayer {
    private Board dumpBoard;
    private Piece [][] dumpPieces;
    private byte nr;//Numer bota, a jego pozycja na planszy
    private byte position;
    private byte[] progress;
    private byte[][] possiblities;
    private byte numOfPlayers;

    private Field field1;
    private Field field2;
    private Field currField;

    private Piece selected;


    public Bot(byte numOfPlayers, byte nr, byte position) throws ToManyNeighboursException {
        dumpBoard = new Board();
        this.position = position;
        this.numOfPlayers = numOfPlayers;
        dumpPieces = new Piece[numOfPlayers][10];//ktore to wiersze a ktore to kolumny
        progress = new byte[6];
        possiblities = new byte[6][3];

        progress[position]=0;
        progress[(position+1)%6]=1;
        progress[(position+2)%6]=1;
        progress[(position+3)%6]=0;
        progress[(position+4)%6]=-1;
        progress[(position+5)%6]=-1;

        for(int i=0; i<numOfPlayers; i++)
            for(byte j=0; j<10; j++)
                dumpPieces[i][j] = new Piece(j);

        setPieces();
    }



    private void checkPossiblities(){
        int max,m;
        Piece piece;

        max = 0;
        if(selected!=null)
            for(int j=0;j<10;j++){
                piece = dumpPieces[nr][j];
                for(byte i=0;i<6;i++){
                    m = maxLine(piece,i);

                    if(m>=max){
                        max = m;
                        field1 = piece.getField();
                        field2 = currField;
                    }


                /*if(f==null)
                    System.out.println(i+" No nie, ten null "+j);



                while(true){
                    dn = f;
                    f = f.directNeighbour(i);
                    counter++;
                    //System.out.println("Siedzew w !f.directNeighbour(i).isFree()");

                    if(f==null){
                        f = dn;
                        counter--;
                        break;
                    }

                    if(!f.isFree() || (f.getHome()!=7 && f.getHome()!=(byte)((position+3)%6))){
                        f = dn;
                        counter--;
                        break;
                    }


                   // f=dn;
                }

                if(f.isFree()){
                    m = counter*progress[i];

                    if(m>=max){
                        max = m;
                        field1 = piece.getField();
                        field2 = f;
                        System.out.println();
                    }

                }

*/
            }
        }
        else{

        }

    }
    private void setPieces(){
        byte i,j;

        switch (numOfPlayers){
            case 6:
                for(i=0;i<6;i++)
                    for(j=0;j<10;j++)
                        dumpPieces[i][j].setPosition(dumpBoard.triangles[6+i].fields[j]);
                break;

            case 3:
                for(i=0;i<3;i++)
                    for(j=0;j<10;j++)
                        dumpPieces[i][j].setPosition(dumpBoard.triangles[6+i*2].fields[j]);
                break;

            case 2:
                for(j=0;j<10;j++)
                    dumpPieces[0][j].setPosition(dumpBoard.triangles[6].fields[j]);

                for(j=0;j<10;j++)
                    dumpPieces[1][j].setPosition(dumpBoard.triangles[9].fields[j]);

                break;
        }
    }
    public byte[] getMove(){
        byte[] response = new byte[4];
        byte i;

        checkPossiblities();


        response[0] = dumpBoard.getTriangleOfField(field1);
        response[1] = dumpBoard.triangles[response[0]].getFieldIndex(field1);


        response[2] = dumpBoard.getTriangleOfField(field2);
        response[3] = dumpBoard.triangles[response[2]].getFieldIndex(field2);

        return  response;
    }

    private boolean goFurther(Field f){
        if(f==null)
            return false;

        if(f.getHome()!= position && f.getHome()!=7 && f.getHome()!=(byte)((position+3)%6))
            return false;

        if(f.isFree())
            return false;

        return true;
    }

    public void inform(byte player, byte part1, byte field1, byte part2, byte field2){
        perform(player, part1, field1, part2, field2);

    }



    private int[] farJump(Field f, int direct){
        Field present = f;
        int[] ret = new int[2];
        int counter = 0;

        while(goFurther(present)){
            present = present.directNeighbour(direct);
            counter++;
        }

        currField = present;

        if(present==null) {
            ret[0] = -1;
            ret[1] = 0;
            return ret;
        }

        if(present.getHome()!= position && present.getHome()!=7 && present.getHome()!=(byte)((position+3)%6)){
            ret[0] = 0;
            ret[1] = counter;
            return ret;
        }


        ret[0] = 1;
        ret[1] = counter;
        return ret;



    }


    private int maxLine(Piece p, int direct){
        int[] counter;
        Field f = p.getField();


            if(f.directNeighbour(direct)==null)
                return -100;

            if(f.directNeighbour(direct).isFree()) {
                currField = f;
                return progress[direct];

            }

            if(f.directNeighbour(direct)!=null)
                if(f.directNeighbour(direct).isFree())
                    return 2*progress[direct];

            counter = farJump(f.directNeighbour(direct),direct);

            if(counter[0]==-1)
                return -100;

            if(counter[0]==0)
                return -1;

            return progress[direct]*counter[1];


    }
    private void perform(byte player,byte part1, byte field1, byte part2, byte field2){
        Field f2 = getField(part2, field2);
        Piece piece = getPiece(player, part1, field1);

        piece.move(f2);

    }

    private Field getField(byte part, byte field){
        if(part==12)
            return dumpBoard.middle;
        else
            return dumpBoard.triangles[part].fields[field];//A co z prawem Persefony, Moze dodać getery?
    }

    private Piece getPiece(byte player, byte part, byte field){
        Field f =  getField(part, field);

        for(int i=0; i<10; i++)
            if(dumpPieces[player][i].getField()==f)
                return dumpPieces[player][i];

        return null;
    }
}
