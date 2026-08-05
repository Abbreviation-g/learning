/**
 * java25的紧凑源文件
 * HelloWorld
 */

// before java 25
// public class HelloWorld {
//     public static void main(String[] args) {
//         System.out.println("hello world1");
//     }
// }

// 值得注意的是，只有在没有package的情况下才可以省略类声明
// after java 25
void main(){
    IO.println("hello world");
}