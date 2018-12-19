package chineseCheckers.gameStaff;


public class Field {
   // final public byte home;
    final public Triangle triangle;
    Field[] neighbours;
    byte[] directs; // na i tej pozycji znajdue sie kierunek i-tego sasiada z neighbours
    private boolean free;
        /*         2    1
                 3  pole 0
                   4    5
         */
    /**
     *
     * @param triangle
     * @param free
     */

    public Field(Triangle triangle, boolean free) {
        this.free = free;
        neighbours = new Field[6];
        directs = new byte[6];
        this.triangle = triangle;

        //for(byte i=0; i<6; i++)
          //  neighbours[i] = null;
    }

    public boolean isFree(){
        return free;
    }

    void changeState(){
        free = !free;
    }

    boolean isNeighbour(Field f){
        for(int i=0; i<6; i++)
            if(neighbours[i]==f){
                System.out.println("K "+directs[i]);
                return true;
            }


        return false;
    }

    public Triangle getTriangle(){
        return triangle;
    }

    Field directNeighbour(int direct){
        int i;

        for(i=0;i<6;i++)
            if(directs[i]==direct)
                return neighbours[i];

        //return null;
        return null;
    }

    public byte getHome(){
        if(triangle==null)
            return 7;
        return getTriangle().home;
    }

    void addNeighbour(Field f, byte direct) throws ToManyNeighboursException {
        int i;


        for(i=0;i<6;i++) {
            if (neighbours[i] == null) {
                neighbours[i] = f;
                directs[i] = direct;
                break;
            }else {
                if(neighbours[i]==f)
                    break;
                if (i == 5)
                    throw new ToManyNeighboursException();
            }
        }

        if(i==5 && direct==3){

        }

        for(i=0;i<6;i++) {
            if (f.neighbours[i] == null) {
                f.neighbours[i] = this;
                f.directs[i] = (byte)((direct+3)%6);
                break;
            }else {
                if(f.neighbours[i]==this)
                    break;
                if (i == 5)
                    throw new ToManyNeighboursException();
            }
        }

    }
}