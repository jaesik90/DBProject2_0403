//강아지 클래스의 인스턴스를 오직 1개만 만들기

//SingleTon pattern 개발 패턴 중 하나임...
/*
 * javaSE
 * javaEE 고급기술(javaSE 를 포함하여)
 * 
 * 
 * */
package oracle;
public class Dog {
	static private Dog instance;
	
	//new에 의한 생성을 막자!!
	private Dog(){
		
	}
	static public Dog getInstance() {
		if(instance==null){
			instance = new Dog();
		}
		return instance;
	}
}
