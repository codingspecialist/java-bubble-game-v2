package bubble.test.ex00;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class Dog {
	private String name; // 10개 -> 메서드 20개
}

public class LombokTest {

	public static void main(String[] args) {
		Dog d = new Dog();
		d.setName("토토");
		System.out.println(d.getName());
	}

}
