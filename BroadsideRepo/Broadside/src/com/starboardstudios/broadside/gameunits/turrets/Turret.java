package com.starboardstudios.broadside.gameunits.turrets;

import android.content.Context;

import com.starboardstudios.broadside.gameunits.BaseUnit;
import com.starboardstudios.broadside.gameunits.Model;
import com.starboardstudios.broadside.gameunits.projectile.Projectile;

public abstract class Turret extends BaseUnit {
	protected Model model;
	protected int x,y,z;
	protected int xFireSpeed, yFireSpeed, zFireSpeed;
	protected Context context;
	protected Projectile projectile;
	boolean canAffect;
	double range;
	
	public Turret(Model model, Projectile projectile) {
		this.context = model.context;
		this.model = model;
		this.projectile = projectile;
	}	    
	public Turret(Model model, Projectile projectile, int x, int y, int z) {
		this.context = model.context;
		this.model = model;
		this.projectile = projectile;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public Turret(Model model, Projectile projectile, int x, int y, int z, int xFireSpeed, int yFireSpeed, int zFireSpeed) {
		this.context = model.context;
		this.model = model;
		this.projectile = projectile;
		this.x = x;
		this.y = y;
		this.z = z;
		this.xFireSpeed = xFireSpeed;
		this.yFireSpeed = yFireSpeed;
		this.zFireSpeed = zFireSpeed;
	}
	
	
	public Projectile getProjectile()
	{
		return this.projectile;
	}
	void spawn() {
		
	}
	void destroy() {
		
	}
	void fire() {
		model.addUnit(projectile.create(model, projectile.getDefaultDamage(), x, y, z, xFireSpeed, yFireSpeed, zFireSpeed));
	}
	
	public int getXFireSpeed()
	{
		return this.xFireSpeed;
	}
	public void setXFireSpeed(int xFireSpeed)
	{
		this.xFireSpeed = xFireSpeed;
	}
	public int getYFireSpeed()
	{
		return this.yFireSpeed;
	}
	public void setYFireSpeed(int yFireSpeed)
	{
		this.yFireSpeed = yFireSpeed;
	}
	public int getZFireSpeed()
	{
		return this.zFireSpeed;
	}
	public void setZFireSpeed(int zFireSpeed)
	{
		this.zFireSpeed = zFireSpeed;
	}
}
