//������ Ŭ������ �ν��Ͻ��� ���� 1���� �����

//SingleTon pattern ���� ���� �� �ϳ���...
/*
 * javaSE
 * javaEE ���ޱ��(javaSE �� �����Ͽ�)
 * 
 * 
 * */
package oracle;
public class Dog {
	static private Dog instance;
	
	//new�� ���� ������ ����!!
	private Dog(){
		
	}
	static public Dog getInstance() {
		if(instance==null){
			instance = new Dog();
		}
		return instance;
	}
}