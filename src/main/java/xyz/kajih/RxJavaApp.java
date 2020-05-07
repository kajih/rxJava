
package xyz.kajih;

public class RxJavaApp {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new RxJavaApp().getGreeting());
    }
}
