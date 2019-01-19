package ru.otus.borisov.factory;

public class PrimitiveArrayFactory implements IObjectFactory<int[]> {

    private boolean isEmpty;

    public PrimitiveArrayFactory(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    @Override
    public int[] getObject() {
        if (isEmpty) {
            return new int[] {};
        }
        return new int[] {0,1,2,3,4,5,6,7,8,9};
    }

    @Override
    public String getObjectType() {
        return String.class.getTypeName();
    }
}
