package com.starboardstudios.broadside.interfaces;

/**
 * Created by alex on 10/24/13.
 */
public interface Draggable
{

    public abstract void dragStarted();


    public abstract void midDrag(float x,float y);


    public abstract boolean endDrag(float x, float y);



}
