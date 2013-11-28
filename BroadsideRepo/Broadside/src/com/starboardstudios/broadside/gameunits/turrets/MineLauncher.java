package com.starboardstudios.broadside.gameunits.turrets;

import java.util.ArrayList;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.starboardstudios.broadside.R.drawable;
import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.CannonBall;
import com.starboardstudios.broadside.gameunits.projectile.Mine;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

//Turret 4
public class MineLauncher extends Turret {
	MineLauncher me;
	private int maxMines, minesDeployed, minefieldCenterX, numRows, numColumns;
	private float xTarget, yTarget, spacing;
	private ArrayList<Mine> mines = new ArrayList<Mine>();
	private int[][] minePositions;

	public MineLauncher(Model model) {
		super(model);
		me = this;
		turretNum = 4;
		/* ARBITRARY VALUES */
		this.fireSpeed = 3;
		currentCooldown = cooldown = 240;
		maxMines = 9;
		minesDeployed = 0;
		spendSetCost(75);	
		this.projectile = new Mine(model, -1, -1);
		size = (float) .125;
		determineNumRowsAndCollumns();
		determineMinePositions();
		determineSpacing();
		
		/* Image */
		imageView.setImageResource(drawable.turret4); // Set to image
		imageView.setAdjustViewBounds(true);
		imageView.setLayoutParams(new LinearLayout.LayoutParams((int) (model
				.getScreenX() * size), (int) (model.getScreenY() * size))); // Set size
			
		/* Dragging */
		imageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {
				imageView.getParent().requestDisallowInterceptTouchEvent(true);
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					System.out.println(view.toString());
					ClipData data = ClipData.newPlainText("", "");
					View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
							imageView);
					view.startDrag(data, shadowBuilder, me, 0);
					view.setVisibility(View.INVISIBLE);
					return true;
				}
				return false;
			}
		});
		System.out.println("Turret4 is Created");
	}

	public void update() {
		// System.out.println("Updating Turret4");
		model.runOnMain(new Runnable() {
			@Override
			public void run() {
				imageView.setX(x);
				imageView.setY(y);
				if (currentCooldown > 0) {
					currentCooldown--;
				} else {
					imageView.setColorFilter(null);
					launchMine();
					imageView.setColorFilter(null);
				}
			}
		});	
	}
	
	//to maintain a cluster of mines centered at a set location ahead
	public void launchMine() { 
		if (minesDeployed < maxMines) {
			minesDeployed++;
			determineTarget();
			Mine mine = (Mine) fire(xTarget,yTarget);
			mine.setTarget(xTarget,yTarget);
			addMine(mine);
		} else {
			//do nothing
		}
	}

	@Override
	public ImageView getImage() {
		return imageView;
	}

	@Override
	public void collide(BaseUnit collidedWith) {
		// TODO: Turrent available drop.
	}

	@Override
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
		minefieldCenterX = (int) (2*x + Math.abs(model.getMainShip().getX()));
	}

	@Override
	public void dragStarted() {
		//
	}

	@Override
	public void midDrag(float x, float y) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean endDrag(float x, float y) {
		if (turretCheck(x,y)) {
			this.setPosition((int) x, (int) y);
			this.update();
			this.imageView.setVisibility(View.VISIBLE);
			return true;
		}
		return false;
	}

	@Override
	public void setImageView(ImageView image) {
		imageView = image;
	}

	public void setPosition(float x, float y) {
		this.x = (int) x;
		this.y = (int) y;
	}
	
	private void determineNumRowsAndCollumns() {
		if (maxMines<4) {
			numRows = 1;
			numColumns = maxMines;
		} else if (maxMines<9) { 
			numRows = 2;
			numColumns = maxMines/2 + maxMines%2;
		} else if (maxMines<16) { 
			numRows = 3;
			numColumns = maxMines/3;
			if (maxMines%3 != 0) {
				numColumns++;
			}
		} else {
			numRows = 4;
			numColumns = maxMines/4;
			if (maxMines%4 != 0) {
				numColumns++;
			}
		}
	}
	
	private void determineTarget() {
		int row,column;
		row = column = -1;
		for (int i=0; i<numRows; i++) {
			for (int j=0; j<numColumns; j++) {
				if (minePositions[i][j] == 0) {
					row = i;
					column = j;
					minePositions[i][j] = minesDeployed;
					//exit
					j = numColumns;
					i = numRows;
				} 
			}
		}
		if (row!=-1 && column!=-1) { //a mine needs to be deployed
			//convert from row and column to xTarget and yTarget
			int centerRow = numRows/2;
			int centerColumn = numColumns/2;
			int rowDiff = row - centerRow;
			int columnDiff = column - centerColumn;
			System.out.println("test Spacing is: " + spacing + " cdiff*spacing: " + columnDiff*spacing);
			xTarget = minefieldCenterX + columnDiff*spacing;
			yTarget = this.y + rowDiff*spacing;
		} else {
			System.out.println("Determine target failed...");
		}
		//printMinePositions();
	}
	
	private void determineSpacing() {
		//int spacing = ((Mine) projectile).getScaleFactor()*10;
		spacing = 50;
	}
	
	private void determineMinePositions() {
		minePositions = new int[numRows][numColumns];
		int minesPlaced = 0;
		for (int i=0; i<numRows; i++) {
			for (int j=0; j<numColumns; j++) {
				if (minesPlaced<maxMines) {
					minePositions[i][j] = 0;
					minesPlaced++;
				} else {
					minePositions[i][j] = -1;
				}
			}
		}
	}
	
	public ArrayList<Mine> getMines() {
		return mines;
	}
	
	public void removeMine(Mine mine) { //needs fixing... level change screws up list
		//update position array
		for (int i=0; i<numRows; i++) {
			for (int j=0; j<numColumns; j++) {
				if (minePositions[i][j] == mine.getMineNumber()) {
					minePositions[i][j] = 0;
				} 
			}
		}
		mines.remove(mine);
		minesDeployed--;
	}
	
	public void printMinePositions() {
		System.out.println("Mine positions: ");
		String row = "";
		for (int i=0; i<numRows; i++) {
			for (int j=0; j<numColumns; j++) {
				row += minePositions[i][j] + " ";
			}
			System.out.println(row);
			row = "";
		}
		System.out.println("Mines Deployed: " + minesDeployed);
	}
	
	public void addMine(Mine mine) {
		mines.add(mine);
		mine.setMineNumber(minesDeployed);
		mine.setMineLauncher(this);
	}
}
