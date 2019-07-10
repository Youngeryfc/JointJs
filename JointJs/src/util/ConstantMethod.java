package util;

public class ConstantMethod {
   
    public static String fbackflushtype(int num){
        String name=null;
        switch (num) {
        case 1:
            name="不倒冲";
            break;
        case 2:
            name="自动倒冲";
            break;
        case 3:
            name="交互式倒冲";
            break;
        default:
            break;
        }
        return name;
    }
    
    public static String fcontrol(int num){
        String name=null;
        switch (num) {
        case 1:
            name="控制";
            break;
        case 2:
            name="不控制";
            break;
        default:
            break;
        }
        return name;
    }
    
    public static String fitemsource(int num){
        String name=null;
        switch (num) {
        case 1:
            name="备料";
            break;
        case 2:
            name="领料";
            break;
        case 3:
            name="传输";
            break;
        default:
            break;
        }
        return name;
    }
    
    public static String fitetype(int num){
        String name=null;
        switch (num) {
        case 1:
            name="普通";
            break;
        case 2:
            name="工具";
            break;
        case 3:
            name="技术资料";
            break;
        case 4:
            name="用户手册";
            break;
        default:
            break;
        }
        return name;
    }
    
    public static String fsupplymode(int num){
        String name=null;
        switch (num) {
        case 1:
            name="一般发料";
            break;
        case 2:
            name="定量发料";
            break;
        default:
            break;
        }
        return name;
    }
}
