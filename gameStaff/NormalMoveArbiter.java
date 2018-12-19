package chineseCheckers.gameStaff;

public class NormalMoveArbiter extends AbstractMoveArbiter {

    public NormalMoveArbiter(Board board, Piece[][] pieces, Piece[] selected) {
        super(board, pieces, selected);
    }

    private boolean correctFields(Field f1, Field f2, byte player){
        if(f1.getHome()==player && f2.getHome()!=player)
            return false;

        if(f2.isFree())
            if(f1.isNeighbour(f2))
                return true;
            else
                return lineAble(f1,f2);
        else
            return false;
    }

    private boolean lineAble(Field f1, Field f2){
        if(f2==null)
            return false;

        if(f2.isFree())
            for(byte i=0; i<6; i++)
                if(!f1.directNeighbour(i).isFree())
                    if(f1.directNeighbour(i).directNeighbour(i)==f2)
                        return true;

        return false;
    }
}
