package chineseCheckers.gameStaff;

/**
 * Class responsible for logic of game and perfoming moves.
 *
 *
 */
public class Game {
    private Board board;
    private Piece [][] pieces;
    private byte numOfPlayers;
    private AbstractMoveArbiter arbiter;
    private Piece[] selected;


    public Game(byte numOfPlayers) throws ToManyNeighboursException {
        board = new Board();
        pieces = new Piece[numOfPlayers][10];//ktore to wiersze a ktore to kolumny

        this.numOfPlayers = numOfPlayers;

        for(byte i=0; i<numOfPlayers; i++)
            for(byte j=0; j<10; j++)
                pieces[i][j] = new Piece(i);



        setPieces();
        selected = new Piece[numOfPlayers];

        arbiter = new NormalMoveArbiter(board,pieces,selected);

     //   for(int i=0; i<numOfPlayers; i++)
         //   selected[i] = null;
    }

    /**
     * Check move is correct
     * @param player pl
     * @param part1 triangle, on which the piece is
     * @param field1 number of field in triangle
     * @param part2 triangle, on which the piece will be
     * @param field2  number of field in triangle
     * @return true if corretct, false if not
     */
    public boolean[] isCorrect(byte player, byte part1, byte field1, byte part2, byte field2){
        return arbiter.isCorrect(player,part1,field1,part2,field2);
    }


    public boolean hasFinished(byte player){
        for(int i=0; i<10; i++)
            if(pieces[player][i].getField().getHome()!=player)
                return false;

        return true;

    }

    /*private boolean correctFields(Field f1, Field f2, byte player){
        if(f1.getHome()==player && f2.getHome()!=player)
            return false;

        if(f2.isFree())
            if(f1.isNeighbour(f2))
                return true;
            else
                return board.lineAble(f1,f2);
        else
            return false;

    }

    private boolean correctPiece(byte player , Field f1){
        for(int i=0;i<10;i++)
            if(pieces[player][i].getField()==f1)
                return true;

        return false;
    }*/

    public void perform(byte player,byte part1, byte field1, byte part2, byte field2){
        Field f2 = getField(part2, field2);
        Piece piece = getPiece(player, part1, field1);

        selected[player] = piece;

        if(f2 == null){
        	System.out.println("POLE NULL");
        }
        
        if(piece == null){
        	System.out.println("PIONEK NULL");
        }
        piece.move(f2);

    }

    public void resetSelect(int n){
        selected[n] = null;
    }



    private Field getField(byte part, byte field){
        if(part>12 | part<0)
            return null;

        if(field<0 | field > 9)
            return null;

        if(part==12)
            return board.middle;
        else
            return board.triangles[part].fields[field];//A co z prawem Persefony, Moze dodać getery?
    }

    private Piece getPiece(byte player, byte part, byte field){
        Field f =  getField(part, field);

        for(int i=0; i<10; i++)
            if(pieces[player][i].getField()==f)
                return pieces[player][i];

        return null;
    }


    public byte getPositionOfPlayer(byte nr){
        switch (numOfPlayers){
            case 6:
                return nr;

            case 3:
                return (byte)((2*nr+3)%6);

            case 4:
                switch (nr){
                    case 0: return 2;

                    case 1: return 1;

                    case 2: return 5;

                    case 3: return 4;
                }
            case 2:

                return (byte)(3*nr);

        }
        return 15;
    }

    private void setPieces(){
        int i,j;



        switch (numOfPlayers){
            case 6:
                for(i=0;i<6;i++)
                    for(j=0;j<10;j++)
                        pieces[i][j].setPosition(board.triangles[6+i].fields[j]);
                break;

            case 4:
                for(j=0;j<10;j++)
                    pieces[0][j].setPosition(board.triangles[2].fields[j]);

                for(j=0;j<10;j++)
                    pieces[1][j].setPosition(board.triangles[1].fields[j]);

                for(j=0;j<10;j++)
                    pieces[2][j].setPosition(board.triangles[5].fields[j]);

                for(j=0;j<10;j++)
                    pieces[3][j].setPosition(board.triangles[4].fields[j]);

                break;

            case 3:
                for(i=0;i<3;i++)
                    for(j=0;j<10;j++)
                        pieces[i][j].setPosition(board.triangles[6+i*2].fields[j]);
                break;

            case 2:
                for(j=0;j<10;j++)
                    pieces[1][j].setPosition(board.triangles[6].fields[j]);

                for(j=0;j<10;j++)
                    pieces[0][j].setPosition(board.triangles[9].fields[j]);

                break;
        }
    }
}

