package chineseCheckers.gameStaff;

public class Triangle {
    Field[] fields;
    final byte rotation;
    final byte home;

    void addMiddle(Field middle) throws ToManyNeighboursException {
        fields[4].addNeighbour(middle, (byte)((rotation+1)%6));
    }

    void glueLeftRight(Triangle right){
        try{
            for(int i=1;i<4;i++) {
                fields[i].addNeighbour(right.fields[8 - i],rotation);
                fields[i].addNeighbour(right.fields[7-i],(byte) ((rotation+1)%6));
            }

            fields[4].addNeighbour(right.fields[4], (rotation));

        } catch (ToManyNeighboursException e) {
                e.printStackTrace();
        }
    }

    void glueCorners(Triangle home) throws ToManyNeighboursException {
        fields[7].addNeighbour(home.fields[7],(byte)((3+rotation)%6)) ;
    }

    void glueToHome(Triangle home){
        try {
            fields[7].addNeighbour(home.fields[1],(byte)((5+rotation)%6));

            fields[8].addNeighbour(home.fields[9],(byte)((5+rotation)%6));
            fields[8].addNeighbour(home.fields[1],(byte)((4+rotation)%6));

            fields[9].addNeighbour(home.fields[8],(byte)((5+rotation)%6));
            fields[9].addNeighbour(home.fields[9],(byte)((4+rotation)%6));

            fields[1].addNeighbour(home.fields[7], (byte)((5+rotation)%6));
            fields[1].addNeighbour(home.fields[8], (byte)((4+rotation)%6));

        } catch (ToManyNeighboursException e) {
            e.printStackTrace();
        }
    }
    private void setInnerNeighoburs(){

        try {
            fields[0].addNeighbour(fields[3],(byte) ((1+rotation)%6));
            fields[0].addNeighbour(fields[5], (byte) ((2+rotation)%6));
            fields[0].addNeighbour(fields[6],(byte) ((3+rotation)%6));
            fields[0].addNeighbour(fields[8],(byte) ((4+rotation)%6));
            fields[0].addNeighbour(fields[9],(byte) ((5+rotation)%6));
            fields[0].addNeighbour(fields[2], rotation);

            fields[1].addNeighbour(fields[2], (byte) ((2+rotation)%6));
            fields[1].addNeighbour(fields[9], (byte) ((3+rotation)%6));


            fields[2].addNeighbour(fields[3],(byte) ((2+rotation)%6));
            fields[2].addNeighbour(fields[9],(byte) ((4+rotation)%6));


            fields[3].addNeighbour(fields[4], (byte) ((2+rotation)%6));
            fields[3].addNeighbour(fields[5], (byte) ((3+rotation)%6));


            fields[4].addNeighbour(fields[5], (byte) ((4+rotation)%6));


            fields[5].addNeighbour(fields[6], (byte) ((4+rotation)%6));


            fields[6].addNeighbour(fields[7], (byte) ((4+rotation)%6));
            fields[6].addNeighbour(fields[8], (byte) ((5+rotation)%6));

            fields[7].addNeighbour(fields[8],rotation);

            fields[8].addNeighbour(fields[9], rotation);

        } catch (ToManyNeighboursException e) {
            e.printStackTrace();
        }


    }

    public byte getFieldIndex(Field f){
        byte i;

        for(i =0; i<10; i++)
            if(fields[i]==f)
                break;

        return i;
    }

    public byte getHome() {
        return home;
    }

    public Triangle(byte home, boolean free, byte rotation){
        fields = new Field[10];
        this.rotation = rotation;
        this.home = home;

        for(int i=0; i<10; i++)
            fields[i] = new Field(this,free);

        setInnerNeighoburs();

    }
}
