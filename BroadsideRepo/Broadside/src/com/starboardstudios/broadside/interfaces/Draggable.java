package com.starboardstudios.broadside.interfaces;

/**
 * Created by alex on 10/24/13.
 */
public interface Draggable
{

    public abstract void dragStarted();


    public abstract void midDrag(int x,int y);


    public abstract void endDrag(int x, int y);



}
