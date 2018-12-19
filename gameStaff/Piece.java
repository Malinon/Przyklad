package chineseCheckers.gameStaff;

public class Piece {
    final byte client;
    private Field field;

    public Piece(byte client) {
        this.client = client;
    }

    void move(Field f){
        field.changeState();
        field = f;
        field.changeState();
    }

    void setPosition(Field f){
        System.out.println("Ustawiam pole");
        field = f;
        if(field==null)
            System.out.println("Ale pole jest nullem");
        else
            System.out.println("i to dobrze");

        field.changeState();
    }

    Field getField(){
        return  field;
    }


}
