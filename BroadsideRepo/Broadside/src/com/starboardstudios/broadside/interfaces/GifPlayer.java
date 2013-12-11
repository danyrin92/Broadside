package com.starboardstudios.broadside.interfaces;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.view.View;

import java.io.InputStream;

/**
 * Created by Alex on 12/10/13.
 */
public class GifPlayer extends View {


        private Movie mMovie;
        private long movieStart;

        public GifPlayer(Context context,  int resource ) {
            super(context);
            initializeView(resource);
        }



        private void initializeView(int resource) {
            InputStream i = getContext().getResources().openRawResource(resource);
            mMovie = Movie.decodeStream(i);
        }



        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.TRANSPARENT);
            super.onDraw(canvas);
            long now = android.os.SystemClock.uptimeMillis();

            if (movieStart == 0) {
                movieStart = (int) now;
            }
            if (mMovie != null) {
                int relTime = (int) ((now - movieStart) % mMovie.duration());
                mMovie.setTime(relTime);
                mMovie.draw(canvas, getWidth() - mMovie.width(), getHeight()
                        - mMovie.height());
                this.invalidate();
            }
        }

}
