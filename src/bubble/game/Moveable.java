package bubble.game;

import bubble.game.component.Enemy;

public interface Moveable {
	public abstract void  left();
	public abstract void  right();
	public abstract void  up();
	//public abstract void  down();
	default public void down() {}; //default를 사용하면 인터페이스도 몸체가 있는 메서드를 만들 수 있다.
	default public void attack() {}; 
	default public void attack(Enemy e) {}; 
}
