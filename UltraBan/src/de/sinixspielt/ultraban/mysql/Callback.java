package de.sinixspielt.ultraban.mysql;

/*
Class created on 28.02.2019 by SinixSpielt
 * */

public abstract interface Callback<T> {
	public abstract void accept(T paramT);
}