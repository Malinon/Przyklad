package chineseCheckers.gameStaff;

public class Board {
    public Triangle[] triangles;
    public Field middle;

    public byte getTriangleOfField(Field f){
        byte i=0;

        for(i=0; i<12; i++)
            if(f.getTriangle()==triangles[i])
                break;

        return i;
    }
    public Board() throws ToManyNeighboursException {
        triangles = new Triangle[12];
        middle = new Field(null,true);

        byte i;
        for(i=0; i<6; i++){//Trojkaty od 0 do 5 są w środku, nie są polem startowym ani końcowym
            triangles[i] = new Triangle((byte)7, true, i);
            triangles[i].addMiddle(middle);
        }

        for(i=6; i<12; i++)
            triangles[i] = new Triangle((byte)(i-6),true, (byte)((i+3)%6));


        for(i=0; i<6; i++){
            triangles[i].glueLeftRight(triangles[((i+1)%6)]);
            triangles[i].glueToHome(triangles[i+6]);
            //triangles[i].glueCorners(triangles[i+5]);
        }

        for(i=1; i<6; i++){
            triangles[i].glueCorners(triangles[i+5]);

        }

        triangles[0].glueCorners(triangles[11]);
    }

    public boolean lineAble(Field f1, Field f2){
        if(f2==null)
            return false;

        if(f2.isFree())
            for(byte i=0; i<6; i++)
                if(jumpLine(f1,f2,i))
                    return true;

        return false;
    }

    private boolean jumpLine(Field f1, Field f2, byte direct){

        if (f1 == f2)
            return true;

        if(f1 == null)
            return false;

        if(f2==f1.directNeighbour(direct))
            return true;
        else
            if(f1.directNeighbour(direct)!=null)
                if(f1.directNeighbour(direct).isFree())
                    return  false;
                else
                    if(f1.directNeighbour(direct).directNeighbour(direct)!=null) {
                        //System.out.println("direct " + direct+ "  "+f1.getTriangle().getFieldIndex(f1));
                        return jumpLine(f1.directNeighbour(direct).directNeighbour(direct), f2, direct);

                    }else
                        return false;

            else
                return false;


    }
}